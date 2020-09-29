package com.tomcatwork.ex03.connector.http;

/**
 * @program: testProject
 * @description:
 * @author: zzk
 * @create: 2020-07-01
 */
final class HttpRequestLine {

    public static final int INITIAL_METHOD_SIZE = 8;

    public static final int INITIAL_URI_SIZE = 64;

    public static final int INITIAL_PROTOCOL_SIZE = 8;

    public static final int MAX_METHOD_SIZE = 1024;

    public static final int MAX_URI_SIZE = 32768;

    public static final int MAX_PROTOCOL_SIZE = 1024;


    public char[] method;

    public int methodEnd;

    public char[] uri;

    public int uriEnd;

    public char[] protocol;

    public int protocolEnd;


    public HttpRequestLine(){
        this(new char[INITIAL_METHOD_SIZE], 0, new char[INITIAL_URI_SIZE], 0, new char[INITIAL_PROTOCOL_SIZE], 0);
    }

    public HttpRequestLine(char[] method, int methodEnd, char[] uri, int uriEnd, char[] protocol, int protocolEnd){
        this.method = method;
        this.methodEnd = methodEnd;
        this.uri = uri;
        this.uriEnd = uriEnd;
        this.protocol = protocol;
        this.protocolEnd = protocolEnd;
    }

    public void recycle(){
        methodEnd = 0;
        uriEnd = 0;
        protocolEnd = 0;
    }

    public int indexOf(char[] buf){
        return indexOf(buf, buf.length);
    }

    public int indexOf(char[] buf, int end){
        char firstChar = buf[0];
        int pos = 0;
        while(pos < uriEnd){
            pos = indexOf(firstChar, pos);
            if(pos == -1){
                return -1;
            }
            if((uriEnd - pos) < end){
                return -1;
            }
            for(int i = 0; i < end; i++){
                if(uri[i + pos] != buf[i]){
                    break;
                }
                if(i == (end - 1)){
                    return pos;
                }
            }
            pos++;
        }
        return -1;
    }


    /**
     * Test if the value of the header includes the given string.
     */
    public int indexOf(String str) {
        return indexOf(str.toCharArray(), str.length());
    }


    public int indexOf(char c, int start){
        for(int i=start; i<uriEnd; i++){
            if(uri[i] == c){
                return i;
            }
        }
        return -1;
    }

    public int hashCode(){
        return 0;
    }

    public boolean equals(Object obj){
        return false;
    }

    public char[] getMethod() {
        return method;
    }

    public void setMethod(char[] method) {
        this.method = method;
    }

    public int getMethodEnd() {
        return methodEnd;
    }

    public void setMethodEnd(int methodEnd) {
        this.methodEnd = methodEnd;
    }

    public char[] getUri() {
        return uri;
    }

    public void setUri(char[] uri) {
        this.uri = uri;
    }

    public int getUriEnd() {
        return uriEnd;
    }

    public void setUriEnd(int uriEnd) {
        this.uriEnd = uriEnd;
    }

    public char[] getProtocol() {
        return protocol;
    }

    public void setProtocol(char[] protocol) {
        this.protocol = protocol;
    }

    public int getProtocolEnd() {
        return protocolEnd;
    }

    public void setProtocolEnd(int protocolEnd) {
        this.protocolEnd = protocolEnd;
    }
}
