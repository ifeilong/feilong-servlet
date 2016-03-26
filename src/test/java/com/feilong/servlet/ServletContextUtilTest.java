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

import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;

import com.feilong.core.util.Validator;
import com.feilong.io.IOReaderUtil;

/**
 *
 * @author feilong
 * @version 1.4.1 2015年9月19日 下午7:47:55
 * @since 1.4.1
 */
public class ServletContextUtilTest{

    /**
     * 读取servletContext.getRealPath("/")下面,文件内容
     *
     * @param servletContext
     *            servletContext上下文地址
     * @param directoryName
     *            文件夹路径
     *            <ul>
     *            <li>如果是根目录,则directoryName传null</li>
     *            <li>前面没有/ 后面有/ 如:res/html/email/</li>
     *            </ul>
     * @param fileName
     *            文件名称 如:register.html
     * @return 读取文件内容
     * @see "org.springframework.web.util.WebUtils#getRealPath(ServletContext, String)"
     * @deprecated 待重构
     */
    @Deprecated
    public static String getFileContent(ServletContext servletContext,String directoryName,String fileName){
        if (Validator.isNullOrEmpty(fileName)){
            throw new IllegalArgumentException("fileName can't be null/empty");
        }

        String filePathString = servletContext.getRealPath("/");
        if (Validator.isNullOrEmpty(directoryName)){
            filePathString = filePathString + fileName;
        }else{
            filePathString = filePathString + directoryName + fileName;
        }
        // ServletContext servletContext = request.getSession().getServletContext();
        return IOReaderUtil.getFileContent(filePathString);
    }

    /**
     * 获得 attribute string map for LOGGER.
     *
     * @param servletContext
     *            the servlet context
     * @return the attribute string map for log
     * @deprecated 没有用了
     */
    @Deprecated
    public static Map<String, String> getAttributeStringMapForLog(ServletContext servletContext){
        Map<String, String> map = new TreeMap<String, String>();
        Enumeration<String> attributeNames = servletContext.getAttributeNames();
        while (attributeNames.hasMoreElements()){
            String name = attributeNames.nextElement();
            Object attributeValue = servletContext.getAttribute(name);

            map.put(name, "" + attributeValue);
        }
        return map;
    }
}
