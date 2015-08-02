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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.feilong.core.lang.StringUtil;
import com.feilong.core.net.ParamUtil;
import com.feilong.core.net.URIComponents;
import com.feilong.core.util.Validator;

/**
 *
 * @author feilong
 * @version 1.3.1 2015年8月1日 下午8:32:08
 * @since 1.3.1
 */
public class RequestUtilTest{

    /**
     * 原样获得参数值.
     * 
     * <p>
     * <span style="color:red">注:url参数是什么,取到的就是什么,不经过处理</span>
     * </p>
     * 
     * <p>
     * 注:({@link javax.servlet.ServletRequest#getParameter(String)}函数时，会自动进行一次URI的解码过程，调用时内置的解码过程会导致乱码出现)
     * </p>
     * 
     * @param request
     *            请求
     * @param paramName
     *            参数名称
     * @return 原样获得参数值
     * @deprecated 有使用场景吗?
     */
    @Deprecated
    public static String getParameterAsItIsDecode(HttpServletRequest request,String paramName){
        String returnValue = null;
        String queryString = request.getQueryString();
        if (Validator.isNotNullOrEmpty(queryString)){
            Map<String, String> map = ParamUtil.toSingleValueMap(queryString, null);
            return map.get(paramName);
        }
        return returnValue;
    }

    /**
     * 取到参数值,没有返回null,有去除空格返回.
     * 
     * @param request
     *            当前请求
     * @param paramName
     *            the param name
     * @return 取到参数值,没有返回null,有去除空格返回
     * @deprecated 不推荐使用
     */
    @Deprecated
    public static String getParameterWithTrim(HttpServletRequest request,String paramName){
        String returnValue = RequestUtil.getParameter(request, paramName);
        if (Validator.isNotNullOrEmpty(returnValue)){
            return returnValue.trim();
        }
        return returnValue;
    }

    /**
     * 参数值去除井号,一般用于sendDirect 跳转中带有#标签,参数值取不准确的问题.
     * 
     * @param request
     *            the request
     * @param paramName
     *            the param name
     * @return 参数值去除井号,一般用于sendDirect 跳转中带有#标签,参数值取不准确的问题
     * @deprecated 将来会重构
     */
    @Deprecated
    public static String getParameterWithoutSharp(HttpServletRequest request,String paramName){
        String returnValue = RequestUtil.getParameter(request, paramName);
        if (Validator.isNotNullOrEmpty(returnValue)){
            if (StringUtil.isContain(returnValue, URIComponents.FRAGMENT)){
                returnValue = StringUtil.substring(returnValue, null, URIComponents.FRAGMENT);
            }
        }
        return returnValue;
    }
}
