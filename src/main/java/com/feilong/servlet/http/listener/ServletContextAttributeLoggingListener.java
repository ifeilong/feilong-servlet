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
package com.feilong.servlet.http.listener;

import static com.feilong.core.Validator.isNotNullOrEmpty;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.servlet.ServletContextUtil;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * The listener interface for receiving servletContextAttributeLogging events.
 * The class that is interested in processing a servletContextAttributeLogging
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addServletContextAttributeLoggingListener<code> method. When
 * the servletContextAttributeLogging event occurs, that object's appropriate
 * method is invoked.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.10.4
 */
public class ServletContextAttributeLoggingListener implements ServletContextAttributeListener{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServletContextAttributeLoggingListener.class);

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.ServletContextAttributeListener#attributeAdded(javax.servlet.ServletContextAttributeEvent)
     */
    @Override
    public void attributeAdded(ServletContextAttributeEvent servletContextAttributeEvent){
        if (LOGGER.isInfoEnabled()){
            String name = servletContextAttributeEvent.getName();

            if (isNotNullOrEmpty(name) && ArrayUtils.contains(ServletContextUtil.EXCLUDE_KEYS, name)){
                return;
            }

            //---------------------------------------------------------------

            LOGGER.info(
                            "name:[{}],value:[{}] added to [servletContext],now servletContext attribute:[{}] ",
                            name,
                            servletContextAttributeEvent.getValue(),
                            buildAttributesLogMessage(servletContextAttributeEvent.getServletContext()));
        }

    }

    /**
     * Builds the attributes log message.
     *
     * @param servletContext
     *            the servlet context
     * @return the string
     */
    private String buildAttributesLogMessage(ServletContext servletContext){
        Map<String, Object> attributeMap = ServletContextUtil.getAttributeMap(servletContext);
        // return JsonUtil.formatSimpleMap(MapUtil.removeKeys(attributeMap, EXCLUDE_KEYS));
        return JsonUtil.formatSimpleMap(attributeMap);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.ServletContextAttributeListener#attributeRemoved(javax.servlet.ServletContextAttributeEvent)
     */
    @Override
    public void attributeRemoved(ServletContextAttributeEvent servletContextAttributeEvent){
        if (LOGGER.isInfoEnabled()){
            LOGGER.info(
                            "name:[{}],value:[{}] removed from [servletContext],now servletContext attribute:[{}] ",
                            servletContextAttributeEvent.getName(),
                            servletContextAttributeEvent.getValue(),
                            buildAttributesLogMessage(servletContextAttributeEvent.getServletContext()));

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.ServletContextAttributeListener#attributeReplaced(javax.servlet.ServletContextAttributeEvent)
     */
    @Override
    public void attributeReplaced(ServletContextAttributeEvent servletContextAttributeEvent){
        if (LOGGER.isInfoEnabled()){
            LOGGER.info(
                            "name:[{}],value:[{}] replaced to [servletContext],now servletContext attribute:[{}] ",
                            servletContextAttributeEvent.getName(),
                            servletContextAttributeEvent.getValue(),
                            buildAttributesLogMessage(servletContextAttributeEvent.getServletContext()));
        }
    }
}
