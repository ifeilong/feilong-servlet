/*
 * Copyright (C) 2008 feilong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.feilong.servlet;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;

import com.feilong.core.Validator;

/**
 * {@link javax.servlet.ServletContext} 工具类.
 * 
 * @author feilong
 * @version 1.0.2 2012-5-31 下午12:53:01
 * @since 1.0.2
 */
public final class ServletContextUtil{

    /** Don't let anyone instantiate this class. */
    private ServletContextUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * servletContext.log servletContext相关信息,一般启动时调用.
     *
     * @param servletContext
     *            the servlet context
     * @return the map< string, object>
     */
    public static Map<String, Object> getServletContextInfoMapForLog(ServletContext servletContext){

        Map<String, Object> map = new HashMap<String, Object>();
        // 返回servlet运行的servlet 容器的版本和名称。
        map.put("servletContext.getServerInfo()", servletContext.getServerInfo());

        // 返回这个servlet容器支持的Java Servlet API的主要版本。所有符合2.5版本的实现，必须有这个方法返回的整数2。
        // 返回这个servlet容器支持的Servlet API的次要版本。所有符合2.5版本的实现，必须有这个方法返回整数5。
        map.put("servlet version:", servletContext.getMajorVersion() + "." + servletContext.getMinorVersion());

        map.put("servletContext.getContextPath()", servletContext.getContextPath());

        map.put("servletContext.getServletContextName()", servletContext.getServletContextName());

        return map;
    }

    /**
     * 遍历显示servletContext的 {@link javax.servlet.ServletContext#getAttributeNames()},将 name/attributeValue 存入到map.
     * 
     * @param servletContext
     *            the servlet context
     * @return if isNotNullOrEmpty(attributeNames),will return {@link java.util.Collections#emptyMap()}
     */
    public static Map<String, Object> getAttributeMap(ServletContext servletContext){
        Enumeration<String> attributeNames = servletContext.getAttributeNames();

        if (Validator.isNullOrEmpty(attributeNames)){
            return Collections.emptyMap();
        }

        Map<String, Object> map = new TreeMap<String, Object>();
        while (attributeNames.hasMoreElements()){
            String name = attributeNames.nextElement();
            Object attributeValue = servletContext.getAttribute(name);

            map.put(name, attributeValue);
        }
        return map;
    }

    /**
     * 遍历显示servletContext的 {@link ServletContext#getInitParameterNames()},将 name /attributeValue 存入到map返回.
     * 
     * @param servletContext
     *            the servlet context
     * @return if isNotNullOrEmpty(initParameterNames),will return {@link java.util.Collections#emptyMap()}
     * @see javax.servlet.ServletContext#getInitParameterNames()
     * @see "org.springframework.web.context.support#registerEnvironmentBeans(ConfigurableListableBeanFactory, ServletContext)"
     */
    public static Map<String, String> getInitParameterMap(ServletContext servletContext){
        Enumeration<String> initParameterNames = servletContext.getInitParameterNames();

        if (Validator.isNullOrEmpty(initParameterNames)){
            return Collections.emptyMap();
        }

        Map<String, String> map = new TreeMap<String, String>();
        while (initParameterNames.hasMoreElements()){
            String name = initParameterNames.nextElement();
            String value = servletContext.getInitParameter(name);
            map.put(name, value);
        }
        return map;
    }
}
