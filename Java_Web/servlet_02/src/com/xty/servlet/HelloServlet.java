package com.xty.servlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author xty
 * @data 2020/7/5
 **/
public class HelloServlet extends Servlet {

    public HelloServlet(){
        System.out.println("1.构造器方法");
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("2.构造器方法");
    }

    @Override
    public ServletConfig getServletConfig() {

        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

        System.out.println("3.hello servlet 被访问了！");

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String method = httpServletRequest.getMethod();
    }

    @Override
    public String getServletInfo() {

        return null;
    }

    @Override
    public void destroy() {

        System.out.println("4.销毁方法");
    }

    public void DoGet(){

    }

    public void DoPost(){

    }

    public static void main(String[] args) {
        System.out.println("hello");
    }

}
