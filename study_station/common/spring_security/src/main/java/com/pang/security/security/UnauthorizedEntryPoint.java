package com.pang.security.security;

import com.pang.commonutils.R;
import com.pang.commonutils.ResponseUtil;
import lombok.SneakyThrows;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * <p>
 * 未授权的统一处理方式
 * </p>
 */
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

    @SneakyThrows
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        ResponseUtil.out(response, R.error());
    }
}
