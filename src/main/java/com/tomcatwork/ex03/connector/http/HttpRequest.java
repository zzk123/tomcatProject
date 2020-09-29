package com.tomcatwork.ex03.connector.http;

import com.tomcatwork.apache.catalina.util.Enumerator;
import com.tomcatwork.apache.catalina.util.ParameterMap;
import com.tomcatwork.apache.catalina.util.RequestUtil;
import com.tomcatwork.ex03.connector.RequestStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @program: testProject
 * @description:
 * @author: zzk
 * @create: 2020-07-01
 */
public class HttpRequest implements HttpServletRequest {

    /**
     * 内容编码类型
     */
    private String contentType;
    /**
     * HTTP消息实体的传输长度
     */
    private int contentLength;
    /**
     * IP地址和域名
     */
    private InetAddress inetAddress;
    private InputStream input;
    /**
     * 请求方法
     */
    private String method;
    private String protocol;
    private String queryString;
    private String requestURI;
    private String serverName;
    private int serverPort;
    private Socket socket;
    private boolean requestedSessionCookie;
    private String requestedSessionId;
    private boolean requestedSessionURL;

    protected HashMap attributes = new HashMap();

    protected String authorization = null;

    protected String contextPath = "";

    protected ArrayList cookies = new ArrayList();

    protected static ArrayList empty = new ArrayList();

    protected SimpleDateFormat formats[] = {
        new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US),
        new SimpleDateFormat("EEEEEE, dd-MMM-yy HH:mm:ss zzz", Locale.US),
        new SimpleDateFormat("EEE MMMM d HH:mm:ss yyyy", Locale.US)
    };

    protected  HashMap headers = new HashMap();

    protected ParameterMap parameterMap = null;

    protected boolean parsed = false;

    protected String pathInfo = null;

    protected BufferedReader reader = null;

    protected ServletInputStream stream = null;

    public HttpRequest(InputStream input){
        this.input = input;
    }

    public void addHeader(String name, String value){
        name = name.toLowerCase();
        synchronized (headers){
            ArrayList values = (ArrayList) headers.get(name);
            if(values == null){
                values = new ArrayList();
                headers.put(name, values);
            }
            values.add(value);
        }
    }

    /**
     * 解析参数
     */
    protected void parseParameters(){
        //ParameterMap不允许随意修改值，根据locked判断
        if(parsed){
            return;
        }
        ParameterMap results = parameterMap;
        if(results == null){
            results = new ParameterMap();
        }
        results.setLocked(false);
        String encoding = getCharacterEncoding();
        if(encoding == null){
            encoding = "ISO-8859-1";
        }
        //参数解析
        String queryString = getQueryString();
        try{
            RequestUtil.parseParameters(results, queryString, encoding);
        }catch (UnsupportedEncodingException e){

        }

        String contentType = getContentType();
        if(contentType == null){
            contentType = "";
        }
        int semicolon = contentType.indexOf(";");
        if(semicolon >= 0){
            contentType = contentType.substring(0, semicolon).trim();
        }else{
            contentType = contentType.trim();
        }
        //解析请求体
        if("POST".equals(getMethod()) && (getContentLength() > 0)
            &&  "application/x-www-form-urlencoded".equals(contentType)){
            try{
                int max = getContentLength();
                int len = 0;
                byte buf[] = new byte[getContentLength()];
                ServletInputStream is = getInputStream();
                while(len < max) {
                    int next = is.read(buf, len, max - len);
                    if (next < 0) {
                        break;
                    }
                    len += next;
                }
                is.close();
                if(len < max){
                    throw new RuntimeException("Content length mismatch");
                }
                RequestUtil.parseParameters(results, buf, encoding);
            }catch (UnsupportedEncodingException e){

            }catch (IOException e){
                throw new RuntimeException("Content read fail");
            }
        }

        results.setLocked(true);
        parsed = true;
        parameterMap = results;
    }

    public void addCookie(Cookie cookie){
        synchronized (cookie){
            cookies.add(cookie);
        }
    }

    public ServletInputStream createInputStream() throws IOException{
        return (new RequestStream(this));
    }


    @Override
    public String getAuthType() {
        return null;
    }

    @Override
    public String getContextPath() {
        return null;
    }


    @Override
    public Object getAttribute(String name) {
        synchronized (attributes) {
            return (attributes.get(name));
        }
    }

    @Override
    public Enumeration getAttributeNames() {
        synchronized (attributes) {
            return (new Enumerator(attributes.keySet()));
        }
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }


    @Override
    public int getContentLength() {
        return 0;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public int getRemotePort() {
        return 0;
    }

    @Override
    public String getLocalName() {
        return null;
    }

    @Override
    public String getLocalAddr() {
        return null;
    }

    @Override
    public int getLocalPort() {
        return 0;
    }

    public InputStream getStream() {
        return input;
    }

    public void setContentLength(int length) {
        this.contentLength = length;
    }

    public void setContentType(String type) {
        this.contentType = type;
    }


    public void setContextPath(String path) {
        if (path == null)
            this.contextPath = "";
        else
            this.contextPath = path;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setPathInfo(String path) {
        this.pathInfo = path;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public void setServerName(String name) {
        this.serverName = name;
    }

    public void setServerPort(int port) {
        this.serverPort = port;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setInet(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    public void setRequestedSessionCookie(boolean flag) {
        this.requestedSessionCookie = flag;
    }

    public void setRequestedSessionId(String requestedSessionId) {
        this.requestedSessionId = requestedSessionId;
    }

    public void setRequestedSessionURL(boolean flag) {
        requestedSessionURL = flag;
    }


    public Cookie[] getCookies() {
        synchronized (cookies) {
            if (cookies.size() < 1)
                return (null);
            Cookie results[] = new Cookie[cookies.size()];
            return ((Cookie[]) cookies.toArray(results));
        }
    }

    public long getDateHeader(String name) {
        String value = getHeader(name);
        if (value == null)
            return (-1L);

        // Work around a bug in SimpleDateFormat in pre-JDK1.2b4
        // (Bug Parade bug #4106807)
        value += " ";

        // Attempt to convert the date header in a variety of formats
        for (int i = 0; i < formats.length; i++) {
            try {
                Date date = formats[i].parse(value);
                return (date.getTime());
            }
            catch (ParseException e) {
                ;
            }
        }
        throw new IllegalArgumentException(value);
    }

    public String getHeader(String name){
        name = name.toLowerCase();
        synchronized (headers){
            ArrayList values = (ArrayList) headers.get(name);
            if(values != null){
                return ((String) values.get(0));
            }else{
                return null;
            }
        }
    }

    public Enumeration getHeaderNames(){
        synchronized (headers){
            return (new Enumerator(headers.keySet()));
        }
    }


    public Enumeration getHeaders(String name) {
        name = name.toLowerCase();
        synchronized (headers) {
            ArrayList values = (ArrayList) headers.get(name);
            if (values != null)
                return (new Enumerator(values));
            else
                return (new Enumerator(empty));
        }
    }


    public ServletInputStream getInputStream() throws IOException {
        if (reader != null)
            throw new IllegalStateException("getInputStream has been called");

        if (stream == null)
            stream = createInputStream();
        return (stream);
    }

    public int getIntHeader(String name) {
        String value = getHeader(name);
        if (value == null)
            return (-1);
        else
            return (Integer.parseInt(value));
    }

    public Locale getLocale() {
        return null;
    }

    public Enumeration getLocales() {
        return null;
    }

    public String getMethod() {
        return method;
    }

    public String getParameter(String name) {
        parseParameters();
        String values[] = (String[]) parameterMap.get(name);
        if (values != null)
            return (values[0]);
        else
            return (null);
    }

    public Map getParameterMap() {
        parseParameters();
        return (this.parameterMap);
    }

    public Enumeration getParameterNames() {
        parseParameters();
        return (new Enumerator(parameterMap.keySet()));
    }

    public String[] getParameterValues(String name) {
        parseParameters();
        String values[] = (String[]) parameterMap.get(name);
        if (values != null)
            return (values);
        else
            return null;
    }

    public String getPathInfo() {
        return pathInfo;
    }

    public String getPathTranslated() {
        return null;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getQueryString() {
        return queryString;
    }

    public BufferedReader getReader() throws IOException {
        if (stream != null)
            throw new IllegalStateException("getInputStream has been called.");
        if (reader == null) {
            String encoding = getCharacterEncoding();
            if (encoding == null)
                encoding = "ISO-8859-1";
            InputStreamReader isr =
                    new InputStreamReader(createInputStream(), encoding);
            reader = new BufferedReader(isr);
        }
        return (reader);
    }

    public String getRealPath(String path) {
        return null;
    }

    public String getRemoteAddr() {
        return null;
    }

    public String getRemoteHost() {
        return null;
    }

    public String getRemoteUser() {
        return null;
    }

    public RequestDispatcher getRequestDispatcher(String path) {
        return null;
    }

    public String getScheme() {
        return null;
    }

    public String getServerName() {
        return null;
    }

    public int getServerPort() {
        return 0;
    }

    public String getRequestedSessionId() {
        return null;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public StringBuffer getRequestURL() {
        return null;
    }

    public HttpSession getSession() {
        return null;
    }

    public HttpSession getSession(boolean create) {
        return null;
    }

    public String getServletPath() {
        return null;
    }

    public Principal getUserPrincipal() {
        return null;
    }

    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    public boolean isRequestedSessionIdFromUrl() {
        return isRequestedSessionIdFromURL();
    }

    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    public boolean isRequestedSessionIdValid() {
        return false;
    }

    public boolean isSecure() {
        return false;
    }

    public boolean isUserInRole(String role) {
        return false;
    }

    public void removeAttribute(String attribute) {
    }

    public void setAttribute(String key, Object value) {
    }

    /**
     * Set the authorization credentials sent with this request.
     *
     * @param authorization The new authorization credentials
     */
    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public void setCharacterEncoding(String encoding) throws UnsupportedEncodingException {
    }
}
