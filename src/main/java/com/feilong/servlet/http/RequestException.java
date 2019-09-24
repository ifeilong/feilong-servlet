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
package com.feilong.servlet.http;

import com.feilong.core.DefaultRuntimeException;

/**
 * 请求异常.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.4.0
 */
public final class RequestException extends DefaultRuntimeException{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1699987643831455524L;

    //---------------------------------------------------------------

    /**
     * The Constructor.
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public RequestException(String message, Throwable cause){
        super(message, cause);
    }

    /**
     * The Constructor.
     *
     * @param cause
     *            the cause
     */
    public RequestException(Throwable cause){
        super(cause);
    }
}
