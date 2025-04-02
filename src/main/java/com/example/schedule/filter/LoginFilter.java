package com.example.schedule.filter;

import com.example.schedule.common.Const;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;


public class LoginFilter implements Filter {
    // 인증을 하지 않아도될 URL Path 배열
    private static final String[] WHITE_LIST = {"/schedules, /schedules/{id}", "/users/signup", "/users/login"};

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        //로그인이 필요한 url인지 확인
        if(!isWhiteList(requestURI)){

            // GET 요청이면 로그인 체크 없이 진행
            if (method.equalsIgnoreCase("GET")) {
                filterChain.doFilter(request, response);
                return;
            }

            //session에 있는 값 가져옴 없으면 null
            HttpSession session = httpRequest.getSession(false);


            if (session == null || session.getAttribute(Const.LOGIN_USER) == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"로그인이 필요한 기능입니다.");
            }

        }

        filterChain.doFilter(httpRequest, httpResponse);
    }

    // 로그인 여부를 확인하는 URL인지 체크하는 메서드
    private boolean isWhiteList(String requestURI) {

        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
