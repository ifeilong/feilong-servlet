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

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.servlet.ServletContextUtil;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * ServletContext 初始化以及销毁的监听器.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.10.4
 */
public class ServletContextLoggingListener implements ServletContextListener{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServletContextLoggingListener.class);

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent){
        if (LOGGER.isInfoEnabled()){
            ServletContext servletContext = servletContextEvent.getServletContext();

            LOGGER.info(
                            "servletContext [Initialized],base info:[{}],[attribute] info:[{}],[initParameter] info:[{}]",
                            JsonUtil.format(ServletContextUtil.getServletContextInfoMapForLog(servletContext)),
                            JsonUtil.formatSimpleMap(ServletContextUtil.getAttributeMap(servletContext)),
                            JsonUtil.format(ServletContextUtil.getInitParameterMap(servletContext)));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent){
        if (LOGGER.isInfoEnabled()){
            ServletContext servletContext = servletContextEvent.getServletContext();

            LOGGER.info(
                            "servletContext [Destroyed] info:[{}] ",
                            JsonUtil.format(ServletContextUtil.getServletContextInfoMapForLog(servletContext)));
        }

    }
}
