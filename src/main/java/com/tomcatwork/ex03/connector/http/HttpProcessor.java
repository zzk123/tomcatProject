package com.tomcatwork.ex03.connector.http;

import com.tomcatwork.apache.catalina.util.RequestUtil;
import com.tomcatwork.apache.catalina.util.StringManager;
import com.tomcatwork.ex03.ServletProcessor;
import com.tomcatwork.ex03.StaticResourceProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @program: testProject
 * @description:
 * @author: zzk
 * @create: 2020-07-01
 */
public class HttpProcessor {

    private HttpConnector connector = null;

    private HttpRequest request;

    private HttpRequestLine requestLine = new HttpRequestLine();

    private HttpResponse response;

    protected String method = null;

    protected String queryString = null;

    protected StringManager sm = StringManager.getManager("com.tomcatwork.ex03.connector.http.config");

    public HttpProcessor(HttpConnector connector) {
        this.connector = connector;
    }

    /**
     * 解析HTTP请求的第一行内容和请求头信息，填充HttpRequest对象
     * 将HttpRequest和HttpResponse对象传递给ServletProcessor或StaticResourceProcessor
     * @param socket
     */
    public void process(Socket socket){
        SocketInputStream input = null;
        OutputStream output = null;
        try{
            input = new SocketInputStream(socket.getInputStream(), 2048);
            output = socket.getOutputStream();

            request = new HttpRequest(input);

            response = new HttpResponse(output);
            response.setRequest(request);

            response.setHeader("Server", "Pyrmont Servlet Container");

            //解析请求和请求头数据
            parseRequest(input, output);
            parseHeaders(input);


            //根据请求的url来判断
            //ServletProcessor 会调用请求的servlet实例的service方法
            //StaticResourceProcessor会将请求的静态资源发送给客户端
            if(request.getRequestURI().startsWith("/servlet/")){
                ServletProcessor processor = new ServletProcessor();
                processor.process(request, response);
            }else{
                StaticResourceProcessor processor = new StaticResourceProcessor();
                processor.process(request, response);
            }

            socket.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 解析请求头
     * @param input
     * @throws IOException
     * @throws ServletException
     */
    private void parseHeaders(SocketInputStream input) throws IOException, ServletException {
        while (true){
            HttpHeader header = new HttpHeader();

            input.readHeader(header);
            if(header.nameEnd == 0){
                if(header.valueEnd == 0){
                    return;
                }else{
                    throw new ServletException
                            (sm.getString("httpProcessor.parseHeaders.colon"));
                }
            }

            String name = new String(header.name, 0, header.nameEnd);
            String value = new String(header.value, 0, header.valueEnd);
            request.addHeader(name, value);

            if(name.equals("cookie")){
                Cookie cookies[] = RequestUtil.parseCookieHeader(value);
                for(int i=0; i<cookies.length; i++) {
                    if (cookies[i].getName().equals("jsessionid")) {
                        if (!request.isRequestedSessionIdFromCookie()) {
                            request.setRequestedSessionId(cookies[i].getValue());
                            request.setRequestedSessionCookie(true);
                            request.setRequestedSessionURL(false);
                        }
                    }
                    request.addCookie(cookies[i]);
                }
            }else if(name.equals("content-length")){
                int n = -1;
                try{
                    n = Integer.parseInt(value);
                }catch (Exception e){
                    throw new ServletException(sm.getString("httpProcessor.parseHeaders.contentLength"));
                }
                request.setContentLength(n);
            }else if(name.equals("conten-type")){
                request.setContentType(value);
            }
        }
    }


    /**
     * 解析请求行
     * 如： GET /myApp/Modernservlet?name=test&pasword=pwd HTTP/1.1
     * @param input
     * @param output
     * @throws IOException
     * @throws ServletException
     */
    private void parseRequest(SocketInputStream input, OutputStream output) throws IOException, ServletException{
        //解析请求行，获取请求方法、url和请求协议版本
        input.readRequestLine(requestLine);
        String method = new String(requestLine.method, 0, requestLine.methodEnd);
        String uri = null;
        String protocol = new String(requestLine.protocol, 0, requestLine.protocolEnd);

        // Validate the incoming request line
        if (method.length() < 1) {
            throw new ServletException("Missing HTTP request method");
        }
        else if (requestLine.uriEnd < 1) {
            throw new ServletException("Missing HTTP request URI");
        }

        //查询字符串处理
        int question = requestLine.indexOf("?");
        if (question >= 0) {
            request.setQueryString(new String(requestLine.uri, question + 1,
                    requestLine.uriEnd - question - 1));
            uri = new String(requestLine.uri, 0, question);
        }
        else {
            request.setQueryString(null);
            uri = new String(requestLine.uri, 0, requestLine.uriEnd);
        }


        //资源路径解析
        if(!uri.startsWith("/")){

            int pos = uri.indexOf("://");
            if(pos != -1){
                pos = uri.indexOf('/', pos + 3);
                if(pos == -1){
                    uri = "";
                }else{
                    uri = uri.substring(pos);
                }
            }
        }

        //会话标识符处理
        String match = ";jsessionid=";
        int semicolon = uri.indexOf(match);
        if(semicolon >= 0){
            String rest = uri.substring(semicolon + match.length());
            int semicolon2 = rest.indexOf(';');
            if(semicolon2 >= 0){
                request.setRequestedSessionId(rest.substring(0, semicolon2));
                rest = rest.substring(semicolon2);
            }else{
                request.setRequestedSessionId(rest);
                rest = "";
            }
            request.setRequestedSessionURL(true);
            uri = uri.substring(0, semicolon) + rest;
        }else{
            request.setRequestedSessionId(null);
            request.setRequestedSessionURL(false);
        }

        //对非正常的URL进行修正
        String normaluzedUri = normalize(uri);

        ((HttpRequest)request).setMethod(method);
        request.setProtocol(protocol);
        if(normaluzedUri != null){
            ((HttpRequest)request).setRequestURI(normaluzedUri);
        } else{
            ((HttpRequest)request).setRequestURI(uri);
        }

        if(normaluzedUri == null){
            throw new ServletException("Invalid URI: " + uri + "'");
        }
    }

    /**
     * 对于非正常的URL进行修正，如出现 “\”会替换成"/"
     * 如果URL本身是正常的或者可以修正的，会返回修正后的URL
     * 如果无法修正，则返回null
     * @param path
     * @return
     */
    protected String normalize(String path){
        if(path == null){
            return null;
        }

        String normalized = path;

        if(normalized.startsWith("/%7E") || normalized.startsWith("/%7e")){
            normalized = "/~" + normalized.substring(4);
        }

        if ((normalized.indexOf("%25") >= 0)
                || (normalized.indexOf("%2F") >= 0)
                || (normalized.indexOf("%2E") >= 0)
                || (normalized.indexOf("%5C") >= 0)
                || (normalized.indexOf("%2f") >= 0)
                || (normalized.indexOf("%2e") >= 0)
                || (normalized.indexOf("%5c") >= 0)) {
            return null;
        }

        if(normalized.equals("/.")){
            return "/";
        }

        if(normalized.indexOf('\\') >= 0){
            normalized = normalized.replace('\\', '/');
        }

        if(!normalized.startsWith("/")){
            normalized = "/" + normalized;
        }

        while(true){
            int index = normalized.indexOf("//");
            if(index < 0){
                break;
            }

            normalized = normalized.substring(0, index) + normalized.substring(index + 1);
        }

        while(true){
            int index = normalized.indexOf("/../");
            if(index < 0){
                break;
            }

            if(index == 0){
                return(null);
            }

            int index2 = normalized.lastIndexOf('/', index - 1);
            normalized = normalized.substring(0, index2) + normalized.substring(index + 3);
        }

        if(normalized.indexOf("/...") >= 0){
            return (null);
        }

        return (normalized);
    }
}
