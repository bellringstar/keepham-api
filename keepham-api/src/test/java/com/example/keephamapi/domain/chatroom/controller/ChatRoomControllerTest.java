package com.example.keephamapi.domain.chatroom.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.keephamapi.domain.box.entity.BoxGroup;
import com.example.keephamapi.domain.box.service.BoxGroupViewService;
import com.example.keephamapi.domain.chatroom.dto.create.ChatRoomCreateRequest;
import com.example.keephamapi.domain.chatroom.dto.create.ChatRoomCreateResponse;
import com.example.keephamapi.domain.chatroom.entity.ChatRoom;
import com.example.keephamapi.domain.chatroom.entity.enums.ChatRoomStatus;
import com.example.keephamapi.domain.chatroom.service.ChatRoomService;
import com.example.keephamapi.domain.member.entity.Member;
import com.example.keephamapi.domain.member.service.MemberViewService;
import com.example.keephamapi.domain.store.entity.Store;
import com.example.keephamapi.domain.store.service.StoreViewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
class ChatRoomControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatRoomService chatRoomService;

    @MockBean
    private MemberViewService memberViewService;

    @MockBean
    private BoxGroupViewService boxGroupViewService;

    @MockBean
    private StoreViewService storeViewService;

    @Autowired
    private ObjectMapper objectMapper;

    private ChatRoomCreateRequest validRequest;
    private ChatRoomCreateRequest invalidRequest;
    private ChatRoom chatRoom;
    private Member member;
    private Store store;
    private BoxGroup boxGroup;

    @BeforeEach
    void setUp() {
        validRequest = ChatRoomCreateRequest.builder()
                .title("Sample Room")
                .maxPeople(6)
                .locked(false)
                .password(null)
                .storeId(1L)
                .boxGroupId(1L)
                .build();

        invalidRequest = ChatRoomCreateRequest.builder()
                .title("")
                .maxPeople(0)
                .locked(true)
                .password("")
                .storeId(null)
                .boxGroupId(null)
                .build();

        member = Member.builder()
                .loginId("user1")
                .build();

        store = Store.builder()
                .build();

        boxGroup = BoxGroup.builder()
                .build();

        chatRoom = ChatRoom.builder()
                .title("Sample Room")
                .status(ChatRoomStatus.OPEN)
                .maxPeople(6)
                .superUserId("user1")
                .locked(false)
                .password(null)
                .store(store)
                .boxGroup(boxGroup)
                .build();
    }

    @Test
    @WithMockUser(roles = "USER")
    void createChatRoom_WithValidData_ShouldReturnOk() throws Exception {
        // Given
        when(memberViewService.findMemberByLoginId(anyString())).thenReturn(member);
        when(storeViewService.findStoreById(anyLong())).thenReturn(store);
        when(boxGroupViewService.findAvailableBoxById(anyLong())).thenReturn(boxGroup);
        when(chatRoomService.createChatRoom(any(ChatRoomCreateRequest.class), eq(member), eq(store), eq(boxGroup)))
                .thenReturn(ChatRoomCreateResponse.toResponse(chatRoom));

        // When / Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/chatroom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk());

        verify(chatRoomService).createChatRoom(any(ChatRoomCreateRequest.class), eq(member), eq(store), eq(boxGroup));
    }

    @Test
    @WithMockUser(roles = "USER")
    void createChatRoom_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        // When / Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/chatroom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}