package com.voidforce.activiti.config.security;

import com.voidforce.activiti.common.bean.HashMapResult;
import com.voidforce.activiti.util.JsonUtil;
import com.voidforce.activiti.util.SessionUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        HashMapResult result = HashMapResult.success("登录成功", SessionUtil.currentUserDeatils());
        out.write(JsonUtil.toJson(result));
        out.flush();
        out.close();
    }
}
