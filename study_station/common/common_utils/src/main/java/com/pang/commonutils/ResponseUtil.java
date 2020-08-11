package com.pang.commonutils;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ResponseUtil {
    public static void out(HttpServletResponse response, R r)throws Exception{
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();
        out.println(r.toString());
        out.flush();
        out.close();
    }
}
