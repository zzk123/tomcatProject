package com.tomcatwork.ex02.servlet002;

import com.tomcatwork.ex02.servlet001.Response;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * @program: testProject
 * @description: Response外观类。防止被调用内部方法
 * @author: zzk
 * @create: 2020-07-01
 */
public class ResponseFacade implements ServletResponse {

    private ServletResponse response;

    public ResponseFacade(Response response){
        this.response = response;
    }

    @Override
    public String getCharacterEncoding() {
        return response.getCharacterEncoding();
    }

    @Override
    public String getContentType() {
        return response.getContentType();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return response.getOutputStream();
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return response.getWriter();
    }

    @Override
    public void setCharacterEncoding(String s) {
        response.setCharacterEncoding(s);
    }

    @Override
    public void setContentLength(int i) {
        response.setContentLength(i);
    }

    @Override
    public void setContentType(String s) {
        response.setContentType(s);
    }

    @Override
    public void setBufferSize(int i) {
        response.setBufferSize(i);
    }

    @Override
    public int getBufferSize() {
        return response.getBufferSize();
    }

    @Override
    public void flushBuffer() throws IOException {
        response.flushBuffer();
    }

    @Override
    public void resetBuffer() {
        response.resetBuffer();
    }

    @Override
    public boolean isCommitted() {
        return response.isCommitted();
    }

    @Override
    public void reset() {
        response.reset();
    }

    @Override
    public void setLocale(Locale locale) {
        response.setLocale(locale);
    }

    @Override
    public Locale getLocale() {
        return response.getLocale();
    }
}
