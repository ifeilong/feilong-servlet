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

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.feilong.core.UncheckedIOException;
import com.feilong.core.Validator;
import com.feilong.core.lang.CharsetType;
import com.feilong.io.MimeType;

/**
 * {@link javax.servlet.http.HttpServletResponse HttpServletResponse} 工具类.
 * 
 * <h3>关于 {@link RequestDispatcher#forward(javax.servlet.ServletRequest, javax.servlet.ServletResponse) RequestDispatcher.forward} 和
 * {@link HttpServletResponse#sendRedirect(String) HttpServletResponse.sendRedirect}</h3>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4">
 * <tr style="background-color:#ccccff">
 * <th align="left">{@link RequestDispatcher#forward(javax.servlet.ServletRequest, javax.servlet.ServletResponse) RequestDispatcher.forward}
 * </th>
 * <th align="left">{@link HttpServletResponse#sendRedirect(String) HttpServletResponse.sendRedirect}</th>
 * </tr>
 * <tr valign="top">
 * <td>只能将请求转发给同一个Web应用中的组件；</td>
 * <td>可以定向到应用程序外的其他资源。</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>重定向后URL不会改变；</td>
 * <td>URL会改变</td>
 * </tr>
 * <tr valign="top">
 * <td>在服务器端内部将请求转发给另一个资源， 浏览器只知道发出请求并得到相应结果，并不知在服务器内部发生的转发行为</td>
 * <td>对浏览器的请求直接作出响应，响应的结果告诉浏览器重新发出对另外一个URL的访问请求</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>调用者与被调用者之间共享相同的request、response对象，它们属于同一个访问请求和相应过程；</td>
 * <td>调用者和被调用者使用各自的request、response对象，它们属于两个独立的访问请求和相应过程</td>
 * </tr>
 * <tr>
 * <td>适用于一次请求响应过程由Web程序内部的多个资源来协同完成， 需要在同一个Web程序内部资源之间跳转， 使用 {@link HttpServletRequest#setAttribute(String, Object)}方法将预处理结果传递给下一个资源。</td>
 * <td>适用于不同Web程序之间的重定向。</td>
 * </tr>
 * </table>
 * </blockquote>
 * 
 * 
 * <h3>关于 {@link HttpServletResponse#sendRedirect(String)}:</h3>
 * 
 * <blockquote>
 * <p>
 * 用于生成302响应码和Location响应头，从而通知客户端重新访问Location响应头指定的URL。
 * </p>
 * 
 * <p>
 * 在 {@link HttpServletResponse#sendRedirect(String)}之后，<span style="color:red">应该紧跟一句return;</span> <br>
 * 我们已知道 {@link HttpServletResponse#sendRedirect(String)}是通过浏览器来做转向的，所以只有在页面处理完成后，才会有实际的动作。<br>
 * 既然您已要做转向了，那么后的输出更有什么意义呢？而且有可能会因为后面的输出导致转向失败。
 * </p>
 * </blockquote>
 *
 * @author feilong
 * @version 1.0 2011-11-3 下午02:26:14
 * @see javax.servlet.http.HttpServletResponse
 * @since 1.0.0
 */
public final class ResponseUtil{

    /** Don't let anyone instantiate this class. */
    private ResponseUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 设置不缓存并跳转.
     * 
     * <p>
     * {@link HttpServletResponse#sendRedirect(String)}方法用于生成302响应码和Location响应头，从而通知客户端重新访问Location响应头指定的URL。
     * </p>
     * 
     * <p>
     * 在 {@link HttpServletResponse#sendRedirect(String)}之后，<span style="color:red">应该紧跟一句return;</span> <br>
     * 我们已知道 {@link HttpServletResponse#sendRedirect(String)}是通过浏览器来做转向的，所以只有在页面处理完成后，才会有实际的动作。<br>
     * 既然您已要做转向了，那么后的输出更有什么意义呢？而且有可能会因为后面的输出导致转向失败。
     * </p>
     * 
     * @param response
     *            HttpServletResponse
     * @param url
     *            跳转路径
     * @see #setNoCacheHeader(HttpServletResponse)
     * @see #sendRedirect(HttpServletResponse, String)
     */
    public static void setNoCacheAndRedirect(HttpServletResponse response,String url){
        setNoCacheHeader(response);
        sendRedirect(response, url);
    }

    /**
     * 跳转.
     * <p>
     * {@link HttpServletResponse#sendRedirect(String)}方法用于生成302响应码和Location响应头，从而通知客户端重新访问Location响应头指定的URL。
     * </p>
     * <p>
     * 在 {@link HttpServletResponse#sendRedirect(String)}之后，<span style="color:red">应该紧跟一句return;</span>; <br>
     * 我们已知道 {@link HttpServletResponse#sendRedirect(String)}是通过浏览器来做转向的，所以只有在页面处理完成后，才会有实际的动作。<br>
     * 既然您已要做转向了，那么后的输出更有什么意义呢？而且有可能会因为后面的输出导致转向失败。
     * </p>
     *
     * @param response
     *            the response
     * @param url
     *            the redirect location URL
     * @see HttpServletResponse#sendRedirect(String)
     * @since 1.2.2
     */
    public static void sendRedirect(HttpServletResponse response,String url){
        try{
            response.sendRedirect(url);
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }

    /**
     * 设置页面不缓存.
     *
     * @param response
     *            HttpServletResponse
     */
    public static void setNoCacheHeader(HttpServletResponse response){
        // 当HTTP1.1服务器指定 CacheControl = no-cache时，浏览器就不会缓存该网页。
        // 旧式 HTTP1.0 服务器不能使用 Cache-Control 标题

        // 为了向后兼容 HTTP1.0 服务器，IE使用Pragma:no-cache 标题对 HTTP提供特殊支持
        // 如果客户端通过安全连接 (https://)/与服务器通讯，且服务器响应中返回 Pragma:no-cache 标题，则 Internet Explorer不会缓存此响应。
        // 注意：Pragma:no-cache 仅当在安全连接中使用时才防止缓存，如果在非安全页中使用，处理方式与 Expires:-1相同，该页将被缓存，但被标记为立即过期
        response.setHeader(HttpHeaders.PRAGMA, "No-cache");

        // Cache-control值为“no-cache”时，访问此页面不会在Internet临时文章夹留下页面备份。
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");

        //In other words Expires: 
        //0 not always leads to immediate resource expiration, 
        //therefore should be avoided and Expires: -1 or Expires: [some valid date in the past] should be used instead.
        response.setDateHeader(HttpHeaders.EXPIRES, -1);
    }

    //   [start] PrintWriter

    /**
     * 以json的方式输出.
     *
     * @param response
     *            HttpServletResponse
     * @param json
     *            json字符串
     * @see #write(HttpServletResponse, Object, String, String)
     * @since 1.0.9
     */
    public static void writeJson(HttpServletResponse response,Object json){
        writeJson(response, json, CharsetType.UTF8);
    }

    /**
     * 以json的方式输出.
     *
     * @param response
     *            HttpServletResponse
     * @param json
     *            json字符串
     * @param characterEncoding
     *            the character encoding
     * @see #write(HttpServletResponse, Object, String, String)
     * @since 1.0.9
     */
    public static void writeJson(HttpServletResponse response,Object json,String characterEncoding){
        String contentType = MimeType.JSON.getMime() + ";charset=" + characterEncoding;
        write(response, json, contentType, characterEncoding);
    }

    /**
     * 输出.
     *
     * @param response
     *            HttpServletResponse
     * @param content
     *            相应内容
     * @see javax.servlet.ServletResponse#getWriter()
     * @see java.io.PrintWriter#print(Object)
     * @see java.io.PrintWriter#flush()
     * @see #write(HttpServletResponse, Object, String, String)
     */
    public static void write(HttpServletResponse response,Object content){
        String contentType = null;
        String characterEncoding = null;
        write(response, content, contentType, characterEncoding);
    }

    /**
     * 输出.
     *
     * @param response
     *            HttpServletResponse
     * @param content
     *            相应内容
     * @param contentType
     *            the content type
     * @param characterEncoding
     *            the character encoding
     * @see javax.servlet.ServletResponse#getWriter()
     * @see java.io.PrintWriter#print(Object)
     * @see java.io.PrintWriter#flush()
     * @since 1.0.9
     */
    public static void write(HttpServletResponse response,Object content,String contentType,String characterEncoding){
        //编码 需要在 getWriter之前设置
        if (Validator.isNotNullOrEmpty(contentType)){
            response.setContentType(contentType);
        }
        if (Validator.isNotNullOrEmpty(characterEncoding)){
            response.setCharacterEncoding(characterEncoding);
        }

        try{
            PrintWriter printWriter = response.getWriter();
            printWriter.print(content);
            printWriter.flush();

            //http://www.iteye.com/problems/56543
            //你是用了tomcat，jetty这样的容器，就不需要 printWriter.close();
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }

    // [end]
}
