package com.example.keephamapi.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.keephamapi.common.api.Api;
import com.example.keephamapi.common.error.ErrorCode;
import com.example.keephamapi.common.exception.ApiException;
import com.example.keephamapi.common.exceptionhandler.ApiExceptionHandler;
import com.example.keephamapi.common.exceptionhandler.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class ApiSpecTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new TestController())
                .setControllerAdvice(new ApiExceptionHandler(), new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("정상 리턴")
    void testGetSuccess() throws Exception {
        mockMvc.perform(get("/api/success")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.resultCode").value(ErrorCode.OK.getErrorCode()))
                .andExpect(jsonPath("$.result.resultMessage").value(ErrorCode.OK.getDescription()))
                .andExpect(jsonPath("$.body").value("Success data"));
    }

    @Test
    @DisplayName("ApiException 발생")
    void testGetError() throws Exception {
        mockMvc.perform(get("/api/error")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result.resultCode").value(ErrorCode.BAD_REQUEST.getErrorCode()))
                .andExpect(jsonPath("$.result.resultMessage").value(ErrorCode.BAD_REQUEST.getDescription()))
                .andExpect(jsonPath("$.result.resultDescription").value("Bad request error"));
    }

    @Test
    @DisplayName("GlobalException 발생")
    void testGlobalException() throws Exception {
        mockMvc.perform(get("/api/global-error")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.result.resultCode").value(ErrorCode.SERVER_ERROR.getErrorCode()))
                .andExpect(jsonPath("$.result.resultMessage").value(ErrorCode.SERVER_ERROR.getDescription()))
                .andExpect(jsonPath("$.result.resultDescription").value("Internal server error"));
    }

    @RestController
    @RequestMapping("/api")
    static class TestController {

        @GetMapping("/success")
        public ResponseEntity<Api<String>> getSuccess() {
            return ResponseEntity.ok(Api.OK("Success data"));
        }

        @GetMapping("/error")
        public ResponseEntity<Api<Object>> getError() {
            throw new ApiException(ErrorCode.BAD_REQUEST, "Bad request error");
        }

        @GetMapping("/global-error")
        public ResponseEntity<Api<Object>> getGlobalError() {
            throw new RuntimeException("Internal server error");
        }
    }
}

