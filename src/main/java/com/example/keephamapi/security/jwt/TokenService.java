package com.example.keephamapi.security.jwt;

import com.example.keephamapi.common.error.jwt.JwtErrorCode;
import com.example.keephamapi.common.exception.ApiException;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    private final RedisTemplate<String, String> redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;

    private static final long REFRESH_TOKEN_VALIDITY_SECONDS = 86400;

    public void storeToken(String refreshToken, String loginId) {
        redisTemplate.opsForValue().set(refreshToken, loginId, Duration.ofSeconds(REFRESH_TOKEN_VALIDITY_SECONDS));
    }

    public String validateRefreshToken(String refreshToken) {
        String userId = redisTemplate.opsForValue().get(refreshToken);
        if (userId != null) {
            jwtTokenProvider.validateToken(refreshToken);
            return userId;
        }
        throw new ApiException(JwtErrorCode.INVALID_TOKEN);
    }

    public void deleteRefreshToken(String refreshToken) {
        redisTemplate.delete(refreshToken);
    }

}
