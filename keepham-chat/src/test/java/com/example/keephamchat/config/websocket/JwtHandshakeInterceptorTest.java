package com.example.keephamchat.config.websocket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;

class JwtHandshakeInterceptorTest {

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private JwtHandshakeInterceptor jwtHandshakeInterceptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void beforeHandshake_withValidToken_shouldStoreUserIdInAttributes() throws Exception {
        // Given
        ServletServerHttpRequest request = mock(ServletServerHttpRequest.class);
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(request.getServletRequest()).thenReturn(servletRequest);

        ServerHttpResponse response = mock(ServerHttpResponse.class);
        WebSocketHandler wsHandler = mock(WebSocketHandler.class);
        Map<String, Object> attributes = new HashMap<>();

        String token = "validToken";
        String userId = "user123";

        when(servletRequest.getParameter("token")).thenReturn(token);
        when(valueOperations.get(token)).thenReturn(userId);

        // When
        boolean result = jwtHandshakeInterceptor.beforeHandshake(request, response, wsHandler, attributes);

        // Then
        assertTrue(result);
        assertEquals(userId, attributes.get("userId"));
    }

    @Test
    void beforeHandshake_withInvalidToken_shouldReturnFalse() throws Exception {
        // Given
        ServletServerHttpRequest request = mock(ServletServerHttpRequest.class);
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(request.getServletRequest()).thenReturn(servletRequest);

        ServerHttpResponse response = mock(ServerHttpResponse.class);
        WebSocketHandler wsHandler = mock(WebSocketHandler.class);
        Map<String, Object> attributes = new HashMap<>();

        String token = "invalidToken";

        when(servletRequest.getParameter("token")).thenReturn(token);
        when(valueOperations.get(token)).thenReturn(null);

        // When
        boolean result = jwtHandshakeInterceptor.beforeHandshake(request, response, wsHandler, attributes);

        // Then
        assertFalse(result);
        assertNull(attributes.get("userId"));
        verify(response).setStatusCode(HttpStatus.FORBIDDEN);
    }
}
