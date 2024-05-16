package com.example.keephamapi.security.filter;

import com.example.keephamapi.common.api.Api;
import com.example.keephamapi.common.error.ErrorCode;
import com.example.keephamapi.common.error.jwt.JwtErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            sentErrorResponse(response, ex);
        }
    }

    private void sentErrorResponse(HttpServletResponse response, Exception ex) {

        // todo: 에러별로 상세화 필요
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Api<Object> apiResponse = Api.ERROR(ErrorCode.SERVER_ERROR, ex.getLocalizedMessage());

        try {
            String json = objectMapper.writeValueAsString(apiResponse);
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
