package com.example.keephamchat.config.websocket;

import static org.apache.kafka.common.security.oauthbearer.internals.secured.HttpAccessTokenRetriever.AUTHORIZATION_HEADER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;

class JwtHandshakeInterceptorTest {

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations valueOperations;

    @InjectMocks
    private JwtHandshakeInterceptor jwtHandshakeInterceptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        Mockito.doNothing().when(valueOperations).set(anyString(), anyString());
    }

    @Test
    void beforeHandshake_withValidToken_shouldStoreUserIdInAttributes() throws Exception {
        ServletServerHttpRequest request = mock(ServletServerHttpRequest.class);
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(request.getServletRequest()).thenReturn(servletRequest);

        ServletServerHttpResponse response = mock(ServletServerHttpResponse.class);
        WebSocketHandler wsHandler = mock(WebSocketHandler.class);
        Map<String, Object> attributes = new HashMap<>();

        String token = "validToken";
        String userId = "user123";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        when(request.getHeaders()).thenReturn(headers);

        when(valueOperations.get(token)).thenReturn(userId);

        when(servletRequest.getHeader(AUTHORIZATION_HEADER)).thenReturn("Bearer " + token);

        boolean result = jwtHandshakeInterceptor.beforeHandshake(request, response, wsHandler, attributes);

        assertTrue(result);
        assertEquals(userId, attributes.get("userId"));
    }

    @Test
    void beforeHandshake_withInvalidToken_shouldReturnFalse() throws Exception {
        ServletServerHttpRequest request = mock(ServletServerHttpRequest.class);
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(request.getServletRequest()).thenReturn(servletRequest);

        ServletServerHttpResponse response = mock(ServletServerHttpResponse.class);
        WebSocketHandler wsHandler = mock(WebSocketHandler.class);
        Map<String, Object> attributes = new HashMap<>();

        String token = "invalidToken";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        when(request.getHeaders()).thenReturn(headers);

        when(valueOperations.get(token)).thenReturn(null);

        when(servletRequest.getHeader(AUTHORIZATION_HEADER)).thenReturn("Bearer " + token);

        boolean result = jwtHandshakeInterceptor.beforeHandshake(request, response, wsHandler, attributes);

        assertFalse(result);
        assertNull(attributes.get("userId"));
    }
}
