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

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.tools.jsonlib.JsonUtil;
import com.feilong.core.util.Validator;
import com.feilong.servlet.http.entity.CookieEntity;

/**
 * {@link javax.servlet.http.Cookie} 工具 类.
 * 
 * 
 * <h3>cookie适用场合:</h3>
 * 
 * cookie机制将信息存储于用户硬盘，因此可以作为全局变量，这是它最大的一个优点。
 * 
 * <blockquote>
 * <ol>
 * <li>保存用户登录状态。<br>
 * 例如将用户id存储于一个cookie内，这样当用户下次访问该页面时就不需要重新登录了，现在很多论坛和社区都提供这样的功能。<br>
 * cookie还可以设置过期时间，当超过时间期限后，cookie就会自动消失。<br>
 * 因此，系统往往可以提示用户保持登录状态的时间： 常见选项有一个月、三个月、一年等。</li>
 * <li>
 * 跟踪用户行为。<br>
 * 例如一个天气预报网站，能够根据用户选择的地区显示当地的天气情况。<br>
 * 如果每次都需要选择所在地是烦琐的，当利用了cookie后就会显得很人性化了，系统能够记住上一次访问的地区，当下次再打开该页面时，它就会自动显示上次用户所在地区的天气情况。<br>
 * 因为一切都是在后台完成 ，所以这样的页面就像为某个用户所定制的一样，使用起来非常方便。</li>
 * <li>定制页面。<br>
 * 如果网站提供了换肤或更换布局的功能，那么可以使用cookie来记录用户的选项，例如：背景色、分辨率等。<br>
 * 当用户下次访问时，仍然可以保存上一次访问的界面风格。</li>
 * <li>创建购物车。<br>
 * 正如在前面的例子中使用cookie来记录用户需要购买的商品一样，在结账的时候可以统一提交。<br>
 * 例如淘宝网就使用cookie记录了用户曾经浏览过的商品，方便随时进行比较。</li>
 * </ol>
 * 
 * </blockquote>
 * 
 * <h3>cookie的缺点主要集中于安全性和隐私保护:</h3>
 * 
 * <blockquote>
 * <ol>
 * <li>cookie可能被禁用。<br>
 * 当用户非常注重个人隐私保护时，他很可能禁用浏览器的cookie功能；</li>
 * <li>cookie是与浏览器相关的。<br>
 * 这意味着即使访问的是同一个页面，不同浏览器之间所保存的cookie也是不能互相访问的；</li>
 * <li>cookie可能被删除。<br>
 * 因为每个cookie都是硬盘上的一个文件，因此很有可能被用户删除；</li>
 * <li>cookie安全性不够高。<br>
 * 所有的cookie都是以纯文本的形式记录于文件中，因此如果要保存用户名密码等信息时，最好事先经过加密处理。</li>
 * </ol>
 * </blockquote>
 *
 * @author feilong
 * @version 2010-6-24 上午08:05:32
 * @version 2012-5-18 14:53
 * @see javax.servlet.http.Cookie
 * @see "org.springframework.web.util.CookieGenerator"
 * @since 1.0.0
 */
public final class CookieUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CookieUtil.class);

    /** Don't let anyone instantiate this class. */
    private CookieUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //********************************************************************

    /**
     * 取到Cookie值.
     * 
     * @param request
     *            HttpServletRequest
     * @param cookieName
     *            cookie名字,{@link Cookie#getName()}
     * @return 如果取不到cookie,返回 <code>null</code>;<br>
     *         否则,返回 {@link Cookie#getValue()}
     * @see #getCookie(HttpServletRequest, String)
     * @see Cookie#getValue()
     */
    public static String getCookieValue(HttpServletRequest request,String cookieName){
        Cookie cookie = getCookie(request, cookieName);
        return null == cookie ? null : cookie.getValue();
    }

    /**
     * 获得cookie.
     * 
     * <p>
     * 循环遍历 {@link HttpServletRequest#getCookies()},找到 {@link Cookie#getName()}是 <code>cookieName</code> 的 {@link Cookie}
     * </p>
     * 
     * @param request
     *            the request
     * @param cookieName
     *            the cookie name
     * @return 如果 {@link HttpServletRequest#getCookies()}是 null,则返回null;<br>
     *         如果通过 <code>cookieName</code> 找不到指定的 {@link Cookie},也返回null
     * @see javax.servlet.http.HttpServletRequest#getCookies()
     * @see javax.servlet.http.Cookie#getName()
     */
    public static Cookie getCookie(HttpServletRequest request,String cookieName){
        Cookie[] cookies = request.getCookies();

        if (Validator.isNullOrEmpty(cookies)){
            LOGGER.info("request's cookies is null or empty!!");
            return null;
        }

        for (Cookie cookie : cookies){
            if (cookie.getName().equals(cookieName)){
                if (LOGGER.isDebugEnabled()){
                    LOGGER.debug("getCookie,cookieName:[{}],cookie info:[{}]", cookieName, JsonUtil.format(cookie));
                }
                return cookie;
            }
        }
        LOGGER.info("can't find the cookie:[{}]", cookieName);
        return null;
    }

    /**
     * 将cookie的 key 和value转成 map(TreeMap).
     *
     * @param request
     *            the request
     * @return 如果没有 {@link HttpServletRequest#getCookies()},返回 {@link Collections#emptyMap()};<br>
     *         否则,返回 loop cookies,取到 {@link Cookie#getName()}以及 {@link Cookie#getValue()} 设置成map 返回
     * @see HttpServletRequest#getCookies()
     * @see javax.servlet.http.Cookie#getName()
     * @see javax.servlet.http.Cookie#getValue()
     */
    public static Map<String, String> getCookieMap(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();

        if (Validator.isNullOrEmpty(cookies)){
            return Collections.emptyMap();
        }

        Map<String, String> map = new TreeMap<String, String>();
        for (Cookie cookie : cookies){
            map.put(cookie.getName(), cookie.getValue());
        }
        return map;
    }

    //*********************************************************************************

    /**
     * 删除cookie.
     * 
     * <p style="color:red">
     * 删除Cookie的时候,path必须保持一致;<br>
     * 如果path不一致,请使用 {@link CookieUtil#deleteCookie(CookieEntity, HttpServletResponse)}
     * </p>
     * 
     * @param cookieName
     *            the cookie name
     * @param response
     *            the response
     * @see #deleteCookie(CookieEntity, HttpServletResponse)
     */
    public static void deleteCookie(String cookieName,HttpServletResponse response){
        CookieEntity cookieEntity = new CookieEntity(cookieName, "");
        deleteCookie(cookieEntity, response);
    }

    /**
     * 删除cookie.
     * 
     * <p style="color:red">
     * 删除 Cookie的时候,path必须保持一致
     * </p>
     * 
     * @param cookieEntity
     *            the cookie entity
     * @param response
     *            the response
     * @see #addCookie(CookieEntity, HttpServletResponse)
     * @since 1.5.0
     */
    public static void deleteCookie(CookieEntity cookieEntity,HttpServletResponse response){
        if (Validator.isNullOrEmpty(cookieEntity)){
            throw new NullPointerException("cookieEntity can't be null/empty!");
        }

        cookieEntity.setMaxAge(0);// 设置为0为立即删除该Cookie
        addCookie(cookieEntity, response);

        LOGGER.debug("deleteCookie,cookieName:[{}]", cookieEntity.getName());
    }

    //****************************************************************************************************

    /**
     * 创建个cookie.
     *
     * @param cookieName
     *            the cookie name
     * @param value
     *            the value
     * @param response
     *            response
     * @see CookieUtil#addCookie(CookieEntity, HttpServletResponse)
     * @since 1.5.0
     */
    public static void addCookie(String cookieName,String value,HttpServletResponse response){
        if (Validator.isNullOrEmpty(cookieName)){
            throw new NullPointerException("cookieName can't be null/empty!");
        }

        CookieEntity cookieEntity = new CookieEntity(cookieName, value);
        addCookie(cookieEntity, response);
    }

    /**
     * 创建个cookie.
     *
     * @param cookieName
     *            the cookie name
     * @param value
     *            the value
     * @param maxAge
     *            the max age
     * @param response
     *            response
     * @see CookieUtil#addCookie(CookieEntity, HttpServletResponse)
     * @since 1.5.0
     */
    public static void addCookie(String cookieName,String value,int maxAge,HttpServletResponse response){
        if (Validator.isNullOrEmpty(cookieName)){
            throw new NullPointerException("cookieName can't be null/empty!");
        }

        CookieEntity cookieEntity = new CookieEntity(cookieName, value, maxAge);
        addCookie(cookieEntity, response);
    }

    /**
     * 创建个cookie.
     * 
     * @param cookieEntity
     *            cookieEntity
     * @param response
     *            response
     * @see "org.apache.catalina.connector.Response#generateCookieString(Cookie, boolean)"
     */
    public static void addCookie(CookieEntity cookieEntity,HttpServletResponse response){

        if (Validator.isNullOrEmpty(cookieEntity)){
            throw new NullPointerException("cookieEntity can't be null/empty!");
        }

        String cookieName = cookieEntity.getName();

        if (Validator.isNullOrEmpty(cookieName)){
            throw new NullPointerException("cookieName can't be null/empty!");
        }

        String value = cookieEntity.getValue();

        //****************************************************************************
        Cookie cookie = new Cookie(cookieName, value);

        cookie.setMaxAge(cookieEntity.getMaxAge());//设置以秒计的cookie的最大存活时间。

        String comment = cookieEntity.getComment();
        if (Validator.isNotNullOrEmpty(comment)){
            cookie.setComment(comment);//指定一个注释来描述cookie的目的。
        }

        // 指明cookie应当被声明的域。
        String domain = cookieEntity.getDomain();
        if (Validator.isNotNullOrEmpty(domain)){ //NullPointerException at javax.servlet.http.Cookie.setDomain(Cookie.java:213) ~[servlet-api-6.0.37.jar:na]
            cookie.setDomain(domain);
        }

        //指定客户端将cookie返回的cookie的路径。
        String path = cookieEntity.getPath();
        if (Validator.isNotNullOrEmpty(path)){
            cookie.setPath(path);
        }

        //指定是否cookie应该只通过安全协议，例如HTTPS或SSL,传送给浏览器。
        cookie.setSecure(cookieEntity.getSecure());

        //设置本cookie遵循的cookie的协议的版本
        cookie.setVersion(cookieEntity.getVersion());

        //HttpOnly cookies are not supposed to be exposed to client-side scripting code, 
        //and may therefore help mitigate certain kinds of cross-site scripting attacks.
        cookie.setHttpOnly(cookieEntity.getHttpOnly()); //@since Servlet 3.0

        if (LOGGER.isDebugEnabled()){
            LOGGER.debug("input cookieEntity info:[{}],response.addCookie", JsonUtil.format(cookieEntity));
        }
        response.addCookie(cookie);
    }
}
