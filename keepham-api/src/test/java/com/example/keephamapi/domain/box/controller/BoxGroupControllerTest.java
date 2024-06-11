package com.example.keephamapi.domain.box.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.keephamapi.common.entity.Address;
import com.example.keephamapi.common.entity.Coordinate;
import com.example.keephamapi.domain.box.dto.BoxGroupCreateRequest;
import com.example.keephamapi.domain.box.entity.BoxGroup;
import com.example.keephamapi.domain.box.entity.enums.BoxGroupStatus;
import com.example.keephamapi.domain.box.service.BoxGroupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class BoxGroupControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private BoxGroupService boxGroupService;

    @Autowired
    private ObjectMapper objectMapper;

    private BoxGroupCreateRequest validRequest;
    private BoxGroupCreateRequest invalidRequest;
    private BoxGroup boxGroup;

    @BeforeEach
    void setUp() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        validRequest = BoxGroupCreateRequest.builder()
                .status(BoxGroupStatus.AVAILABLE)
                .address(new Address("Sample City", "Sample Street", "12345"))
                .coordinate(new Coordinate(37.5665, 126.9780))
                .build();

        invalidRequest = BoxGroupCreateRequest.builder()
                .status(null)
                .address(null)
                .coordinate(null)
                .build();

        boxGroup = BoxGroup.builder()
                .status(BoxGroupStatus.AVAILABLE)
                .address(new Address("Sample City", "Sample Street", "12345"))
                .coordinate(new Coordinate(37.5665, 126.9780))
                .build();

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createBoxGroup_WithAdminRole_ShouldReturnOk() throws Exception {
        // Given
        when(boxGroupService.createBoxGroup(any(BoxGroupCreateRequest.class))).thenReturn(boxGroup);

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/box-group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk());

        // Then
        verify(boxGroupService).createBoxGroup(any(BoxGroupCreateRequest.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    void createBoxGroup_WithUserRole_ShouldReturnForbidden() throws Exception {
        // Given
        when(boxGroupService.createBoxGroup(any(BoxGroupCreateRequest.class))).thenReturn(boxGroup);

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/box-group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createBoxGroup_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/box-group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}