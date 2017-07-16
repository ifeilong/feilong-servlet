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

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.servlet.http.SessionUtil;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * The listener interface for receiving httpSessionAttributeLogging events.
 * The class that is interested in processing a httpSessionAttributeLogging
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addHttpSessionAttributeLoggingListener</code> method. When
 * the httpSessionAttributeLogging event occurs, that object's appropriate
 * method is invoked.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.10.4
 */
public class HttpSessionAttributeLoggingListener implements HttpSessionAttributeListener{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpSessionAttributeLoggingListener.class);

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.http.HttpSessionAttributeListener#attributeAdded(javax.servlet.http.HttpSessionBindingEvent)
     */
    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent){
        if (LOGGER.isDebugEnabled()){
            HttpSession session = httpSessionBindingEvent.getSession();

            LOGGER.debug(
                            "name:[{}],value:[{}] added to [session],now session info:[{}] ",
                            httpSessionBindingEvent.getName(),
                            httpSessionBindingEvent.getValue(),
                            JsonUtil.format(SessionUtil.getSessionInfoMapForLog(session)));

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.http.HttpSessionAttributeListener#attributeRemoved(javax.servlet.http.HttpSessionBindingEvent)
     */
    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent){
        if (LOGGER.isDebugEnabled()){
            HttpSession session = httpSessionBindingEvent.getSession();

            LOGGER.debug(
                            "name:[{}],value:[{}] removed from [session],now session info:[{}] ",
                            httpSessionBindingEvent.getName(),
                            httpSessionBindingEvent.getValue(),
                            JsonUtil.format(SessionUtil.getSessionInfoMapForLog(session)));

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.http.HttpSessionAttributeListener#attributeReplaced(javax.servlet.http.HttpSessionBindingEvent)
     */
    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent){
        if (LOGGER.isDebugEnabled()){
            HttpSession session = httpSessionBindingEvent.getSession();

            LOGGER.debug(
                            "name:[{}],value:[{}] replaced to [session],now session info:[{}] ",
                            httpSessionBindingEvent.getName(),
                            httpSessionBindingEvent.getValue(),
                            JsonUtil.format(SessionUtil.getSessionInfoMapForLog(session)));

        }

    }
}
