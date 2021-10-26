package com.example.spinrgmvcbasic.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //[status-Line]
        response.setStatus(HttpServletResponse.SC_OK);

        //[response-headers]
        response.setHeader("Content-Type", "text/plain");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("my-header", "hello");
        //요런 식으로도 가능.
//        response.setContentType("text/plain");

        //헤더의 편의 메서드
        content(response);
        cookie(response);
        redirect(response);

        PrintWriter writer = response.getWriter();
        writer.println("ok");
    }

    //content에 대한 편의 메서드
    private void content(HttpServletResponse response){
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
    }

    //cookie에 대한 편의 메서드

    private void cookie(HttpServletResponse response){
        //Set-Cookie: myCookie=good; Max-Age=600;
        //response.setHeadeR("Set-Cookie" ~) 요런 식으로도 가능.
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600);  //600초
        response.addCookie(cookie);
    }

    public void redirect(HttpServletResponse response) throws IOException {
        //Status Code 302
        //Location: /basic/hello-form.html
        response.sendRedirect("/basic/hello-form.html");

        //아래와 같이도 가능
//        response.setStatus(HttpServletResponse.SC_FOUND); //302
//        response.setHeader("Location", "/basic/hello-form.html");
    }
}
