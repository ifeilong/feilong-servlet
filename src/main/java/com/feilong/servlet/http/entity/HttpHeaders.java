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

package com.feilong.servlet.http.entity;

/**
 * Constants enumerating the HTTP headers.
 * 
 * <p>
 * All headers defined in {@link <a href="http://tools.ietf.org/html/rfc1945">RFC 1945</a>} (HTTP/1.0),
 * {@link <a href="http://tools.ietf.org/html/rfc2616">RFC2616</a>} (HTTP/1.1), and
 * {@link <a href="http://tools.ietf.org/html/rfc2518">RFC2518</a>} (WebDAV) are listed.
 * </p>
 *
 * @see "org.apache.http.HttpHeaders"
 * @see "org.springframework.http.HttpHeaders"
 * @since 1.0.8
 * @since httpcore 4.1
 * 
 * @see <a href="http://tools.ietf.org/html/rfc1945">RFC1945</a>
 * @see <a href="http://tools.ietf.org/html/rfc2616">RFC2616</a>
 * @see <a href="http://tools.ietf.org/html/rfc2518">RFC2518</a>
 */
public final class HttpHeaders{

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.1 */
    public static final String ACCEPT                      = "Accept";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.2 */
    public static final String ACCEPT_CHARSET              = "Accept-Charset";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.3 */
    public static final String ACCEPT_ENCODING             = "Accept-Encoding";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.4 */
    public static final String ACCEPT_LANGUAGE             = "Accept-Language";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.5 */
    public static final String ACCEPT_RANGES               = "Accept-Ranges";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.6 */
    public static final String AGE                         = "Age";

    /** <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.1, RFC 2616 (HTTP/1.1) Section 14.7 */
    public static final String ALLOW                       = "Allow";

    /** <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.2, RFC 2616 (HTTP/1.1) Section 14.8 */
    public static final String AUTHORIZATION               = "Authorization";

    /**
     * <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.9
     * 
     * <p>
     * 在HTTP/1.1这个标准中引入了一个新的字段Cache-control。Cache-control中有一条指令，叫max-age。<br>
     * Age表示这个Response已经存活了多久了。
     * 而max-age表示response最大的存活时间。所以，max-age表明了这条被cache的消息在多长时间段内是有效的。
     * </p>
     * 
     * <p>
     * Example 1: {@code response.setHeader("Cache-Control", "max-age=3600");}
     * </p>
     * 
     * <p style="color:red">
     * 如果返回的消息中，同时出现了Cache-control: max-age和Expires，那么以Cache-control: max-age为准，Expires的声明将会被覆盖掉。
     * </p>
     * 
     * @see #EXPIRES
     */
    public static final String CACHE_CONTROL               = "Cache-Control";

    /**
     * <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.10
     */
    public static final String CONNECTION                  = "Connection";

    /** <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.3, RFC 2616 (HTTP/1.1) Section 14.11 */
    public static final String CONTENT_ENCODING            = "Content-Encoding";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.12 */
    public static final String CONTENT_LANGUAGE            = "Content-Language";

    /** <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.4, RFC 2616 (HTTP/1.1) Section 14.13 */
    public static final String CONTENT_LENGTH              = "Content-Length";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.14 */
    public static final String CONTENT_LOCATION            = "Content-Location";

    /** The content disposition. */
    public static final String CONTENT_DISPOSITION         = "Content-Disposition";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.15 */
    public static final String CONTENT_MD5                 = "Content-MD5";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.16 */
    public static final String CONTENT_RANGE               = "Content-Range";

    /**
     * RFC 1945 (HTTP/1.0) Section 10.5, RFC 2616 (HTTP/1.1) Section 14.17 <code>{@value}</code><br>
     * 
     * <pre>
     * http://tools.ietf.org/html/rfc2616#section-7.2.1
     * 7.2.1 Type
     * 
     *    When an entity-body is included with a message, the data type of that body is determined via the header fields Content-Type and Content-Encoding. 
     *    These define a two-layer, ordered encoding model:
     * 
     *        entity-body := Content-Encoding( Content-Type( data ) )
     * 
     *    Content-Type specifies the media type of the underlying data.
     *    Content-Encoding may be used to indicate any additional content codings applied to the data, usually for the purpose of data compression, that are a property of the requested resource. There is no default encoding.
     * 
     *    Any HTTP/1.1 message containing an entity-body SHOULD include a Content-Type header field defining the media type of that body. 
     *    If and only if the media type is not given by a Content-Type field, the recipient MAY attempt to guess the media type via inspection of its
     *    content and/or the name extension(s) of the URI used to identify the resource. 
     *    If the media type remains unknown, the recipient SHOULD treat it as type "application/octet-stream".
     * </pre>
     */
    public static final String CONTENT_TYPE                = "Content-Type";

    /** <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.6, RFC 2616 (HTTP/1.1) Section 14.18 */
    public static final String DATE                        = "Date";

    /** <code>{@value}</code> RFC 2518 (WevDAV) Section 9.1 */
    public static final String DAV                         = "Dav";

    /** <code>{@value}</code> RFC 2518 (WevDAV) Section 9.2 */
    public static final String DEPTH                       = "Depth";

    /** <code>{@value}</code> RFC 2518 (WevDAV) Section 9.3 */
    public static final String DESTINATION                 = "Destination";

    /**
     * <code>{@value}</code>
     * 
     * <p>
     * RFC 2616 (HTTP/1.1) Section 14.19
     * </p>
     * 
     * <p>
     * Etag 是 http 1.1 规范引入的一个新的 http 实体头， Etag 在规范中仅仅只 etag 可以用来对同一个资源的其它实体头进行对比，没有做进一步的解释，<br>
     * 其实我们可以把 Etag 理解为一个服务器在某个资源上面做的一个记号，至于这个记号用来做什么要看服务器如何去解析它，因此我们在设计我们自己的应用的时候，可以借助 Etag 来实现客户端缓存和服务器之间的缓存协商控制。
     * </p>
     * 
     * <p>
     * 当浏览器第一次请求一个资源的时候，服务器在响应头里面加入 Etag 的标识， Etag 的值既是当前响应内容经过计算以后的值( http 规范没有对 etag 值的计算方式做规定，可以是 md5 或者其它方式)当第二次浏览器发送请求的时候，浏览器便会用原先请求中
     * Etag 响应头的值作为 if-None-Match 请求头的值，这样服务器接受到此次请求以后，根据 if-None-Match 的取值和当前的内容进行对比，如果相同则返回一个 304 not modified 响应码，这样浏览器收到 304 后就会用客户端本地
     * cache 来完成对本次请求的响应。
     * </p>
     * 
     * @see "org.springframework.web.filter.ShallowEtagHeaderFilter"
     */
    public static final String ETAG                        = "ETag";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.20 */
    public static final String EXPECT                      = "Expect";

    /**
     * <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.7, RFC 2616 (HTTP/1.1) Section 14.21
     * <p>
     * Expires早就存在于HTTP/1.0，是由服务器指定的Response过期时间。<br>
     * 表明在Expires这个时间点之前，返回的response都是足够新的或者说是有效的，Client无需再向Server发送请求。<br>
     * 
     * Expires表示的是一个 <span style="color:red">绝对的时刻</span>，所以每次返回的Expires的时间极有可能都不一样。
     * </p>
     * 
     * <p>
     * 比如 :Example 1: <code>response.setDateHeader("Expires", now + 3600000);</code>
     * </p>
     * 
     * <p>
     * 需要注意的是，这里的Expires指示告诉Client如果这个response没有过期，没有必要再次访问。<br>
     * 但这并不意味着response过期之前，Client不能向Server发送请求。<br>
     * 同时，Expires也不意味在response过期以后，Client需要主动的去Server取最新的消息。
     * </p>
     * 
     * <p style="color:red">
     * 如果返回的消息中，同时出现了Cache-control: max-age和Expires，那么以Cache-control: max-age为准，Expires的声明将会被覆盖掉。
     * </p>
     * 
     * @see #CACHE_CONTROL
     * 
     */
    public static final String EXPIRES                     = "Expires";

    /** <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.8, RFC 2616 (HTTP/1.1) Section 14.22 */
    public static final String FROM                        = "From";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.23 */
    public static final String HOST                        = "Host";

    /** <code>{@value}</code> RFC 2518 (WevDAV) Section 9.4 */
    public static final String IF                          = "If";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.24 */
    public static final String IF_MATCH                    = "If-Match";

    /**
     * <code>{@value}</code>
     * 
     * <p>
     * RFC 1945 (HTTP/1.0) Section 10.9, RFC 2616 (HTTP/1.1) Section 14.25.
     * </p>
     * 
     * <p>
     * 当用户第一次浏览一个网站的时候，服务器会在响应头中增加 Last-modified 这个 http 响应头， Last-modified 的格式如：Last-modified: Fri, 16 Mar 201 04:00:25 GMT<br>
     * 当用户第二次再请求同样的 url 的时候，浏览器会将last-modified 的值附加到 if-modified-since 这个 http 请求头中，服务器端接收到请求后，首先 check 一下 if-modified-since 头信息中的时间是否与当前
     * url 对应的资源的最后修改时间一致，如果一致，则服务器返回 http 304 状态码，这样当浏览器收到 http 304 状态码了以后，就会利用本地缓存的内容来完成对本次用户操作的响应。
     * </p>
     */
    public static final String IF_MODIFIED_SINCE           = "If-Modified-Since";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.26 */
    public static final String IF_NONE_MATCH               = "If-None-Match";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.27 */
    public static final String IF_RANGE                    = "If-Range";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.28 */
    public static final String IF_UNMODIFIED_SINCE         = "If-Unmodified-Since";

    /** <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.10, RFC 2616 (HTTP/1.1) Section 14.29 */
    public static final String LAST_MODIFIED               = "Last-Modified";

    /** <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.11, RFC 2616 (HTTP/1.1) Section 14.30 */
    public static final String LOCATION                    = "Location";

    /** <code>{@value}</code> RFC 2518 (WevDAV) Section 9.5 */
    public static final String LOCK_TOKEN                  = "Lock-Token";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.31 */
    public static final String MAX_FORWARDS                = "Max-Forwards";

    /** <code>{@value}</code> RFC 2518 (WevDAV) Section 9.6 */
    public static final String OVERWRITE                   = "Overwrite";

    /** <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.12, RFC 2616 (HTTP/1.1) Section 14.32 */
    public static final String PRAGMA                      = "Pragma";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.33 */
    public static final String PROXY_AUTHENTICATE          = "Proxy-Authenticate";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.34 */
    public static final String PROXY_AUTHORIZATION         = "Proxy-Authorization";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.35 */
    public static final String RANGE                       = "Range";

    /** <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.13, RFC 2616 (HTTP/1.1) Section 14.36 */
    public static final String REFERER                     = "Referer";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.37 */
    public static final String RETRY_AFTER                 = "Retry-After";

    /** <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.14, RFC 2616 (HTTP/1.1) Section 14.38 */
    public static final String SERVER                      = "Server";

    /** <code>{@value}</code> RFC 2518 (WevDAV) Section 9.7 */
    public static final String STATUS_URI                  = "Status-URI";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.39 */
    public static final String TE                          = "TE";

    /** <code>{@value}</code> RFC 2518 (WevDAV) Section 9.8 */
    public static final String TIMEOUT                     = "Timeout";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.40 */
    public static final String TRAILER                     = "Trailer";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.41 */
    public static final String TRANSFER_ENCODING           = "Transfer-Encoding";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.42 */
    public static final String UPGRADE                     = "Upgrade";

    /**
     * <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.15, RFC 2616 (HTTP/1.1) Section 14.43
     * <p>
     * 它是一个特殊字符串头，使得服务器能够识别客户使用的操作系统及版本、CPU 类型、浏览器及版本、浏览器渲染引擎、浏览器语言、浏览器插件等。
     * </p>
     * 
     * <pre>
    *  userAgent 属性是一个只读的字符串，声明了浏览器用于 HTTP 请求的用户代理头的值。
    *  一般来讲，它是在 navigator.appCodeName 的值之后加上斜线和 navigator.appVersion 的值构成的。
    *  例如：Mozilla/4.0 (compatible; MSIE6.0; Windows NT 5.2; SV1; .NET CLR 1.1.4322)。
    *  注：用户代理头：user-agent header。
    * 
    *  User Agent中文名为用户代理，简称 UA，它是一个特殊字符串头，使得服务器能够识别客户使用的操作系统及版本、CPU 类型、浏览器及版本、浏览器渲染引擎、浏览器语言、浏览器插件等。
    * 
    *  标准格式为： 
    *  浏览器标识 (操作系统标识; 加密等级标识; 浏览器语言) 渲染引擎标识 版本信息
    *  
    *  //Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.57 Safari/537.17
    *  
    *  iphone :Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_0 like Mac OS X; en-us) AppleWebKit/532.9 (KHTML, like Gecko) Version/4.0.5 Mobile/8A293 Safari/6531.22.7
     * 
     * </pre>
     * 
     * @see <a href="http://tools.ietf.org/html/rfc2616#section-14.43">14.43 User-Agent</a>
     */
    public static final String USER_AGENT                  = "User-Agent";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.44 */
    public static final String VARY                        = "Vary";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.45 */
    public static final String VIA                         = "Via";

    /** <code>{@value}</code> RFC 2616 (HTTP/1.1) Section 14.46 */
    public static final String WARNING                     = "Warning";

    /** <code>{@value}</code> RFC 1945 (HTTP/1.0) Section 10.16, RFC 2616 (HTTP/1.1) Section 14.47 */
    public static final String WWW_AUTHENTICATE            = "WWW-Authenticate";

    // ****************************************header**************************************************************************

    /**
     * 1、Origin字段里只包含是谁发起的请求，并没有其他信息 (通常情况下是方案，主机和活动文档URL的端口)。<br>
     * 跟Referer不一样的是，Origin字段并没有包含涉及到用户隐私的URL路径和请求内容，这个尤其重要。 <br>
     * 2、Origin字段只存在于POST请求，而Referer则存在于所有类型的请求。<br>
     * <code>{@value}</code> .
     */
    public static final String ORIGIN                      = "origin";

    /**
     * <code>{@value}</code>.
     * <p>
     * X-Forwarded-For:简称XFF头，它代表客户端，也就是HTTP的请求端真实的IP，只有在通过了HTTP代理或者负载均衡服务器时才会添加该项。<br>
     * 它不是RFC中定义的标准请求头信息，在squid缓存代理服务器开发文档中可以找到该项的详细介绍。 <br>
     * 标准格式如下：<br>
     * X-Forwarded-For: client1, proxy1, proxy2.
     * </p>
     */
    public static final String X_FORWARDED_FOR             = "x-forwarded-for";

    /**
     * <code>{@value}</code>,如果主站使用cdn的话，
     * 
     * @see <a href="http://distinctplace.com/infrastructure/2014/04/23/story-behind-x-forwarded-for-and-x-real-ip-headers/">Story behind
     *      X-Forwarded-For and X-Real-IP headers</a>
     * @see <a href="http://lavafree.iteye.com/blog/1559183">nginx做负载CDN加速获取端真实ip</a>
     * @since 1.4.1
     */
    public static final String X_REAL_IP                   = "X-Real-IP";

    /** <code>{@value}</code>. */
    public static final String PROXY_CLIENT_IP             = "Proxy-Client-IP";

    /** <code>{@value}</code> 这个应该是WebLogic前置HttpClusterServlet提供的属性，一般不需要自己处理，在WebLogic控制台中已经可以指定使用这个属性来覆盖. */
    public static final String WL_PROXY_CLIENT_IP          = "WL-Proxy-Client-IP";

    /** <code>{@value}</code>. */
    public static final String X_REQUESTED_WITH            = "X-Requested-With";

    /** <code>{@value}</code>. */
    public static final String X_REQUESTED_WITH_VALUE_AJAX = "XMLHttpRequest";

    /** Don't let anyone instantiate this class. */
    private HttpHeaders(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }
}
