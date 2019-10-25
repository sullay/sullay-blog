package com.lhq.superboot.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @Description: xml工具类
 * @author: lct
 * @date: 2019年4月29日 上午11:41:14
 */
public class XmlUtils {

    /**
     * xml文档Document转对象
     * 
     * @param document  
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object getObject(Document document, Class<?> clazz) {
        Object obj = null;
        // 获取根节点
        Element root = document.getRootElement();
        try {
            obj = clazz.newInstance();// 创建对象
            List<Element> properties = root.elements();
            for (Element pro : properties) {
                // 获取属性名(首字母大写)
                String propertyname = pro.getName();
                String propertyvalue = pro.getText();
                Method m = obj.getClass().getMethod("set" + propertyname, String.class);
                m.invoke(obj, propertyvalue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * xml字符串转对象
     * 
     * @param xmlString  
     * @param clazz
     * @return
     * @throws Exception
     */
    public static Object getObject(String xmlString, Class<?> clazz) throws Exception {
        Document document = null;
        try {
            document = DocumentHelper.parseText(xmlString);
        } catch (DocumentException e) {
            throw new Exception("获取Document异常" + xmlString);
        } // 获取根节点
        return getObject(document, clazz);
    }

    /**
     * 对象转xml文件
     * 
     * @param b
     * @return
     */
    public static Document getDocument(Object b) {
        Document document = DocumentHelper.createDocument();
        try {
            // 创建根节点元素
            Element root = document.addElement(b.getClass().getSimpleName());
            Field[] field = b.getClass().getDeclaredFields(); // 获取实体类b的所有属性，返回Field数组
            for (int j = 0; j < field.length; j++) { // 遍历所有有属性
                String name = field[j].getName(); // 获取属属性的名字
                if (!name.equals("serialVersionUID")) {// 去除串行化序列属性
                    name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                    Method m = b.getClass().getMethod("get" + name);
                    // System.out.println("属性get方法返回值类型:" + m.getReturnType());
                    String propertievalue = (String) m.invoke(b);// 获取属性值
                    Element propertie = root.addElement(name);
                    propertie.setText(propertievalue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * 对象转xml格式的字符串
     * 
     * @param b
     * @return
     */
    public static String getXmlString(Object b) {
        return getDocument(b).asXML();
    }
    
    /**
     * 将微信的请求中参数转成map
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String,String> xmlToMap(HttpServletRequest request){
        Map<String,String> map = new HashMap<String,String>();
        SAXReader reader = new SAXReader();
        InputStream in = null;
        try {
            in = request.getInputStream();
            Document doc = reader.read(in);
            Element root = doc.getRootElement();
            List<Element> list = root.elements();
            for (Element element : list) {
                map.put(element.getName(), element.getText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }finally{
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

}
