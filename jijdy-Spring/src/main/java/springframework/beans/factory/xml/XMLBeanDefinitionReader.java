package springframework.beans.factory.xml;

import cn.hutool.core.bean.BeanException;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import springframework.beans.PropertyValue;
import springframework.beans.factory.config.BeanDefinition;
import springframework.beans.factory.config.BeanReference;
import springframework.beans.factory.support.AbstractBeanDefinitionReader;
import springframework.beans.factory.support.BeanDefinitionRegistry;
import springframework.core.io.Resource;
import springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 蒋遥/jijdy
 * @createTime 2021/10/25 18:31
 * @Description bean自动加载的实现类，通过xml的形式实现bean的注入
 */
public class XMLBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XMLBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }


    public XMLBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    //主方法，调用xml的解析函数，将xml中的所有bean对象注入到bean定义集合中
    //bean注入环节在getBean()方法中模板实现
    @Override
    public void loadBeanDefinitions(Resource resource) {
       try {
           InputStream inputStream = resource.getInputStream();
            doLoadBeanDefinitions(inputStream);
       } catch (IOException | ClassNotFoundException e) {
           throw new BeanException("IOException paring XML document filed:"+resource,e);
       }
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) {
        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) {
        Resource resource = getResourceLoader().getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(String... locations) {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }
    }

    //对xml文件中的资源进行解析，使用dom树模式进行解析，并将属性添加到相应bean对象中
    protected void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException {
        Document document = XmlUtil.readXML(inputStream);
        Element element = document.getDocumentElement();
        NodeList childNodes = element.getChildNodes();

        for (int i = 0 ; i < childNodes.getLength(); i++) {
            if (!(childNodes.item(i) instanceof Element)) continue;
            if (!"bean".equals(childNodes.item(i).getNodeName())) continue;

            //除余之后进行解析
            Element bean = (Element) childNodes.item(i);
            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String className = bean.getAttribute("class");
            String initMethodName = bean.getAttribute("init-method");
            String destroyMethodName = bean.getAttribute("destroy-method");
            String beanScope = bean.getAttribute("scope");

            //通过类名拿到class对象，以便进行反射等操作
//            System.out.println(className);
            //使用className是需要注意类名，位置错误会Nfc
            Class<?> clazz = Class.forName(className);
            //优先级id大于name
            String beanName = StrUtil.isNotEmpty(id) ? id : name;
            if (StrUtil.isEmpty(beanName)) {
                //若无id和name，则使用该bean对象的类名作为beanName
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }
            //直接将clazz写入到bean定义集合中即可
            BeanDefinition beanDefinition = new BeanDefinition(clazz);

            //将初始化方法和销毁方法的信息注册掉bean定义中，便于后续的发现
            beanDefinition.setInitMethodName(initMethodName);
            beanDefinition.setDestroyMethodName(destroyMethodName);

            //将作用域设置到beanDefinition中的字段中，
            if (StrUtil.isNotEmpty(beanScope)) {
                beanDefinition.setScope(beanScope);
            }

            //检测bean对象在定义时写入的字段属性值
            for (int j = 0; j < bean.getChildNodes().getLength(); j ++) {
                if (!(bean.getChildNodes().item(j) instanceof Element)) continue;
                if (!"property".equals(bean.getChildNodes().item(j).getNodeName())) continue;

                //解析property标签，表示字段属性
                Element property = (Element) bean.getChildNodes().item(j);
                String attrName = property.getAttribute("name");
                String attrValue = property.getAttribute("value");
                String attrRef = property.getAttribute("ref");
                //获取属性值：引入对象，值对象
                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
                //创建属性信息，并将属性注入到beanDefinition中，
                PropertyValue propertyValue = new PropertyValue(attrName,value);
                beanDefinition.getFieldValue().addPropertyValue(propertyValue);

            }
            if (getRegistry().containsBeanDefinition(beanName)) {
                throw new BeanException("repeat beanName [" + beanName + "] is not allowed!");
            }
            //完成注册操作，将得到的bean定义注册到定义集合中
            getRegistry().registerBeanDefinition(beanName,beanDefinition);
        }
    }
}
