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

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.Validator;
import com.feilong.core.bean.PropertyUtil;
import com.feilong.servlet.http.entity.CookieEntity;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * {@link javax.servlet.http.Cookie Cookie} 工具类.
 * 
 * <p>
 * 注意:该类创建Cookie仅支持Servlet3以上的版本
 * </p>
 * 
 * <h3>使用说明:</h3>
 * 
 * <h4>
 * case:创建Cookie
 * </h4>
 * 
 * <blockquote>
 * <p>
 * 1.创建一个name名字是shopName,value是feilong的 Cookie (通常出于安全起见,存放到Cookie的值需要加密或者混淆,此处为了举例方便使用原码)<br>
 * 可以调用{@link #addCookie(String, String, HttpServletResponse)}<br>
 * 如:
 * <p>
 * <code>CookieUtil.addCookie("shopName","feilong",response)</code>
 * </p>
 * 注意:该方法创建的cookie,有效期是默认值 -1,即浏览器退出就删除
 * </p>
 * 
 * <p>
 * 2.如果想给该cookie加个过期时间,有效期一天,可以调用 {@link #addCookie(String, String, int, HttpServletResponse)}<br>
 * 如:
 * <p>
 * <code>CookieUtil.addCookie("shopName","feilong", TimeInterval.SECONDS_PER_DAY,response)</code>
 * </p>
 * </p>
 * 
 * <p>
 * 3.如果还想给该cookie加上httpOnly等标识,可以调用 {@link #addCookie(CookieEntity, HttpServletResponse)}<br>
 * 如:
 * 
 * <pre class="code">
 * CookieEntity cookieEntity = new CookieEntity("shopName", "feilong", TimeInterval.SECONDS_PER_DAY);
 * cookieEntity.setHttpOnly(true);
 * CookieUtil.addCookie(cookieEntity, response);
 * </pre>
 * 
 * 此外,如果有特殊需求,还可以对cookieEntity设置 path,domain等属性
 * </p>
 * </blockquote>
 * 
 * <h4>
 * case:获取Cookie
 * </h4>
 * 
 * <blockquote>
 * 
 * <p>
 * 1.可以使用 {@link #getCookie(HttpServletRequest, String)}来获得 {@link Cookie}对象
 * <br>
 * 如:
 * <p>
 * <code>CookieUtil.getCookie(request, "shopName")</code>
 * </p>
 * </p>
 * 
 * <p>
 * 2.更多的时候,可以使用 {@link #getCookieValue(HttpServletRequest, String)}来获得Cookie对象的值
 * <br>
 * 如:
 * <p>
 * <code>CookieUtil.getCookieValue(request, "shopName")</code>
 * </p>
 * 
 * 返回 "feilong" 字符串
 * </p>
 * 
 * 
 * <p>
 * 3.当然,你也可以使用 {@link #getCookieMap(HttpServletRequest)}来获得 所有的Cookie name和value组成的map
 * <br>
 * 如:
 * <p>
 * <code>CookieUtil.getCookieMap(request)</code>
 * </p>
 * 
 * 使用场景,如 {@link com.feilong.servlet.http.RequestLogBuilder#build()}
 * </p>
 * </blockquote>
 * 
 * 
 * <h4>
 * case:删除Cookie
 * </h4>
 * 
 * <blockquote>
 * 
 * <p>
 * 1.可以使用 {@link #deleteCookie(String, HttpServletResponse)}来删除Cookie
 * <br>
 * 如:
 * <p>
 * <code>CookieUtil.deleteCookie(request, "shopName")</code>
 * </p>
 * </p>
 * 
 * <p>
 * 2.特殊时候,由于Cookie原先保存时候设置了path属性,可以使用 {@link #deleteCookie(CookieEntity, HttpServletResponse)}来删除Cookie
 * <br>
 * 如:
 * 
 * <p>
 * 
 * <pre class="code">
 * CookieEntity cookieEntity = new CookieEntity("shopName", "feilong");
 * cookieEntity.setPath("/member/account");
 * CookieUtil.deleteCookie(request, "shopName");
 * </pre>
 * </p>
 * 
 * </p>
 * 
 * </blockquote>
 * 
 * @author feilong
 * @version 2010-6-24 上午08:05:32
 * @version 2012-5-18 14:53
 * @see javax.servlet.http.Cookie
 * @see "org.springframework.web.util.CookieGenerator"
 * @see com.feilong.servlet.http.entity.CookieEntity
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
     * 获得 {@link Cookie}对象.
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
        Validate.notNull(cookieEntity, "cookieEntity can't be null!");

        cookieEntity.setMaxAge(0);// 设置为0为立即删除该Cookie
        addCookie(cookieEntity, response);

        LOGGER.debug("deleteCookie,cookieName:[{}]", cookieEntity.getName());
    }

    //****************************************************************************************************

    /**
     * 创建cookie.
     * 
     * <p>
     * 注意:该方法创建的cookie,有效期是默认值 -1,即浏览器退出就删除
     * </p>
     *
     * @param cookieName
     *            the cookie name
     * @param value
     *            cookie的值,更多说明,参见 {@link CookieEntity#getValue()}
     *            <p style="color:red">
     *            注意:如果值长度超过4K,浏览器会忽略,不会执行记录的操作
     *            </p>
     * @param response
     *            response
     * @see CookieUtil#addCookie(CookieEntity, HttpServletResponse)
     * @since 1.5.0
     */
    public static void addCookie(String cookieName,String value,HttpServletResponse response){
        Validate.notEmpty(cookieName, "cookieName can't be null/empty!");

        CookieEntity cookieEntity = new CookieEntity(cookieName, value);
        addCookie(cookieEntity, response);
    }

    /**
     * 创建cookie.
     *
     * @param cookieName
     *            the cookie name
     * @param value
     *            cookie的值,更多说明,参见 {@link CookieEntity#getValue()}
     *            <p style="color:red">
     *            注意:如果值长度超过4K,浏览器会忽略,不会执行记录的操作
     *            </p>
     * @param maxAge
     *            设置以秒计的cookie的最大存活时间,可以使用 {@link com.feilong.core.TimeInterval TimeInterval}相关常量
     * @param response
     *            response
     * @see CookieUtil#addCookie(CookieEntity, HttpServletResponse)
     * @since 1.5.0
     */
    public static void addCookie(String cookieName,String value,int maxAge,HttpServletResponse response){
        Validate.notEmpty(cookieName, "cookieName can't be null/empty!");

        CookieEntity cookieEntity = new CookieEntity(cookieName, value, maxAge);
        addCookie(cookieEntity, response);
    }

    /**
     * 创建cookie.
     * 
     * @param cookieEntity
     *            cookieEntity
     * @param response
     *            response
     * @see "org.apache.catalina.connector.Response#generateCookieString(Cookie, boolean)"
     */
    public static void addCookie(CookieEntity cookieEntity,HttpServletResponse response){
        //校验
        validateCookieEntity(cookieEntity);

        Cookie cookie = toCookie(cookieEntity);

        if (LOGGER.isDebugEnabled()){
            LOGGER.debug("input cookieEntity info:[{}],response.addCookie", JsonUtil.format(cookieEntity));
        }
        response.addCookie(cookie);
    }

    /**
     * To cookie.
     *
     * @param cookieEntity
     *            the cookie entity
     * @return the cookie
     * @since 1.5.3
     */
    private static Cookie toCookie(CookieEntity cookieEntity){
        Cookie cookie = new Cookie(cookieEntity.getName(), cookieEntity.getValue());

        PropertyUtil.copyProperties(cookie, cookieEntity //
                        , "maxAge"//设置以秒计的cookie的最大存活时间.
                        , "secure"//指定是否cookie应该只通过安全协议,例如HTTPS或SSL,传送给浏览器.
                        , "version"//设置本cookie遵循的cookie的协议的版本
                        , "httpOnly"//@since Servlet 3.0
        );
        PropertyUtil.setPropertyIfValueNotNullOrEmpty(cookie, "comment", cookieEntity.getComment());//指定一个注释来描述cookie的目的.

        //NullPointerException at javax.servlet.http.Cookie.setDomain(Cookie.java:213) ~[servlet-api-6.0.37.jar:na]
        PropertyUtil.setPropertyIfValueNotNullOrEmpty(cookie, "domain", cookieEntity.getDomain());// 指明cookie应当被声明的域.
        PropertyUtil.setPropertyIfValueNotNullOrEmpty(cookie, "path", cookieEntity.getPath());//指定客户端将cookie返回的cookie的路径.
        return cookie;
    }

    /**
     * Validate cookie entity.
     *
     * @param cookieEntity
     *            the cookie entity
     * @since 1.5.0
     */
    private static void validateCookieEntity(CookieEntity cookieEntity){
        Validate.notNull(cookieEntity, "cookieEntity can't be null!");

        String cookieName = cookieEntity.getName();
        Validate.notEmpty(cookieName, "cookieName can't be null/empty!");

        String value = cookieEntity.getValue();

        //如果长度超过4000,浏览器可能不支持
        if (Validator.isNotNullOrEmpty(value) && value.length() > 4000){
            LOGGER.warn(
                            "cookie value:{},length:{},more than 4000!!!some browser may be not support!!!!!,cookieEntity info :{}",
                            value,
                            value.length(),
                            JsonUtil.format(cookieEntity));
        }
    }
}
