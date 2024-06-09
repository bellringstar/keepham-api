//package com.example.keephamapi.domain.chatroom.service;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.catchThrowable;
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.anyLong;
//import static org.mockito.Mockito.anyString;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import com.example.keephamapi.common.entity.Address;
//import com.example.keephamapi.common.entity.Coordinate;
//import com.example.keephamapi.common.error.ErrorCode;
//import com.example.keephamapi.common.exception.ApiException;
//import com.example.keephamapi.domain.box.dto.BoxResponse;
//import com.example.keephamapi.domain.box.entity.BoxGroup;
//import com.example.keephamapi.domain.box.entity.enums.BoxGroupStatus;
//import com.example.keephamapi.domain.box.service.UnitBoxViewService;
//import com.example.keephamapi.domain.chatroom.dto.create.ChatRoomCreateRequest;
//import com.example.keephamapi.domain.chatroom.dto.create.ChatRoomCreateResponse;
//import com.example.keephamapi.domain.chatroom.entity.ChatRoom;
//import com.example.keephamapi.domain.chatroom.repository.ChatRoomRepository;
//import com.example.keephamapi.domain.member.entity.Member;
//import com.example.keephamapi.domain.member.service.MemberViewService;
//import com.example.keephamapi.domain.store.dto.StoreResponse;
//import com.example.keephamapi.domain.store.entity.Store;
//import com.example.keephamapi.domain.store.service.StoreViewService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//public class ChatRoomServiceTest {
//
//    @Mock
//    private ChatRoomRepository chatRoomRepository;
//
//    @Mock
//    private MemberViewService memberViewService;
//
//    @Mock
//    private StoreViewService storeViewService;
//
//    @Mock
//    private UnitBoxViewService boxViewService;
//
//    @InjectMocks
//    private ChatRoomService chatRoomService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void createChatRoom_shouldCreateAndReturnChatRoom() {
//        // Given
//        ChatRoomCreateRequest request = new ChatRoomCreateRequest();
//        request.setTitle("Test Chat Room");
//        request.setMaxPeople(10);
//        request.setSuperUserId("loginId");
//        request.setLocked(false);
//        request.setPassword("password");
//        request.setStoreId(1L);
//        request.setBoxId(1L);
//
//        Member member = Member.builder().build();
//        Store store = Store.builder()
//                .name("Test Store")
//                .address(new Address("Street", "City", "Zip"))
//                .coordinate(new Coordinate(0.0, 0.0))
//                .build();
//        BoxGroup boxGroup = BoxGroup.builder()
//                .status(BoxGroupStatus.AVAILABLE)
//                .address(new Address("Street", "City", "Zip"))
//                .coordinate(new Coordinate(0.0, 0.0))
//                .build();
//
//        when(memberViewService.findMemberByLoginId(anyString())).thenReturn(member);
//        when(storeViewService.findStoreById(anyLong())).thenReturn(store);
//        when(boxViewService.getAvailableBoxById(anyLong())).thenReturn(boxGroup);
//
//        // When
//        ChatRoomCreateResponse response = chatRoomService.createChatRoom(request, "loginId");
//
//        // Then
//        assertThat(response).isNotNull();
//        assertThat(response.getTitle()).isEqualTo(request.getTitle());
//        assertThat(response.getMaxPeople()).isEqualTo(request.getMaxPeople());
//        assertThat(response.getSuperUserId()).isEqualTo(request.getSuperUserId());
//        assertThat(response.isLocked()).isEqualTo(request.isLocked());
//        assertThat(response.getPassword()).isEqualTo(request.getPassword());
//        assertThat(response.getStore()).isEqualTo(StoreResponse.toResponse(store));
//        assertThat(response.getBox()).isEqualTo(BoxResponse.toResponse(boxGroup));
//
//        verify(memberViewService, times(1)).findMemberByLoginId(anyString());
//        verify(storeViewService, times(1)).findStoreById(anyLong());
//        verify(boxViewService, times(1)).getAvailableBoxById(anyLong());
//        verify(chatRoomRepository, times(1)).save(any(ChatRoom.class));
//    }
//
//    @Test
//    public void createChatRoom_shouldThrowExceptionIfMemberNotFound() {
//        // Given
//        ChatRoomCreateRequest request = new ChatRoomCreateRequest();
//        request.setTitle("Test Chat Room");
//        request.setMaxPeople(10);
//        request.setSuperUserId("superUser");
//        request.setLocked(false);
//        request.setPassword("password");
//        request.setStoreId(1L);
//        request.setBoxId(1L);
//
//        when(memberViewService.findMemberByLoginId(anyString())).thenThrow(
//                new ApiException(ErrorCode.BAD_REQUEST, "존재하지 않는 유저"));
//
//        // When
//        Throwable thrown = catchThrowable(() -> {
//            chatRoomService.createChatRoom(request, "loginId");
//        });
//
//        // Then
//        assertThat(thrown)
//                .isInstanceOf(ApiException.class)
//                .hasMessage("존재하지 않는 유저");
//
//        verify(memberViewService, times(1)).findMemberByLoginId(anyString());
//        verify(storeViewService, times(0)).findStoreById(anyLong());
//        verify(boxViewService, times(0)).getAvailableBoxById(anyLong());
//        verify(chatRoomRepository, times(0)).save(any(ChatRoom.class));
//    }
//
//    @Test
//    public void createChatRoom_shouldThrowExceptionIfStoreNotFound() {
//        // Given
//        ChatRoomCreateRequest request = new ChatRoomCreateRequest();
//        request.setTitle("Test Chat Room");
//        request.setMaxPeople(10);
//        request.setSuperUserId("superUser");
//        request.setLocked(false);
//        request.setPassword("password");
//        request.setStoreId(1L);
//        request.setBoxId(1L);
//
//        Member member = Member.builder().build();
//        when(memberViewService.findMemberByLoginId(anyString())).thenReturn(member);
//        when(storeViewService.findStoreById(anyLong())).thenThrow(
//                new ApiException(ErrorCode.BAD_REQUEST, "존재하지 않는 가게"));
//
//        // When
//        Throwable thrown = catchThrowable(() -> {
//            chatRoomService.createChatRoom(request, "loginId");
//        });
//
//        // Then
//        assertThat(thrown)
//                .isInstanceOf(ApiException.class)
//                .hasMessage("존재하지 않는 가게");
//
//        verify(memberViewService, times(1)).findMemberByLoginId(anyString());
//        verify(storeViewService, times(1)).findStoreById(anyLong());
//        verify(boxViewService, times(0)).getAvailableBoxById(anyLong());
//        verify(chatRoomRepository, times(0)).save(any(ChatRoom.class));
//    }
//
//    @Test
//    public void createChatRoom_shouldThrowExceptionIfBoxNotFound() {
//        // Given
//        ChatRoomCreateRequest request = new ChatRoomCreateRequest();
//        request.setTitle("Test Chat Room");
//        request.setMaxPeople(10);
//        request.setSuperUserId("superUser");
//        request.setLocked(false);
//        request.setPassword("password");
//        request.setStoreId(1L);
//        request.setBoxId(1L);
//
//        Member member = Member.builder().build();
//        Store store = Store.builder()
//                .name("Test Store")
//                .address(new Address("Street", "City", "Zip"))
//                .coordinate(new Coordinate(0.0, 0.0))
//                .build();
//
//        when(memberViewService.findMemberByLoginId(anyString())).thenReturn(member);
//        when(storeViewService.findStoreById(anyLong())).thenReturn(store);
//        when(boxViewService.getAvailableBoxById(anyLong())).thenThrow(
//                new ApiException(ErrorCode.BAD_REQUEST, "존재하지 않는 박스"));
//
//        // When
//        Throwable thrown = catchThrowable(() -> {
//            chatRoomService.createChatRoom(request, "loginId");
//        });
//
//        // Then
//        assertThat(thrown)
//                .isInstanceOf(ApiException.class)
//                .hasMessage("존재하지 않는 박스");
//
//        verify(memberViewService, times(1)).findMemberByLoginId(anyString());
//        verify(storeViewService, times(1)).findStoreById(anyLong());
//        verify(boxViewService, times(1)).getAvailableBoxById(anyLong());
//        verify(chatRoomRepository, times(0)).save(any(ChatRoom.class));
//    }
//
//    @Test
//    public void createChatRoom_shouldThrowExceptionIfBoxInUse() {
//        // Given
//        ChatRoomCreateRequest request = new ChatRoomCreateRequest();
//        request.setTitle("Test Chat Room");
//        request.setMaxPeople(10);
//        request.setSuperUserId("superUser");
//        request.setLocked(false);
//        request.setPassword("password");
//        request.setStoreId(1L);
//        request.setBoxId(1L);
//
//        Member member = Member.builder()
//                .loginId("loginId")
//                .build();
//        Store store = Store.builder()
//                .name("Test Store")
//                .address(new Address("Street", "City", "Zip"))
//                .coordinate(new Coordinate(0.0, 0.0))
//                .build();
//        BoxGroup boxGroup = BoxGroup.builder()
//                .status(BoxGroupStatus.IN_USE)
//                .address(new Address("Street", "City", "Zip"))
//                .coordinate(new Coordinate(0.0, 0.0))
//                .build();
//
//        when(memberViewService.findMemberByLoginId(anyString())).thenReturn(member);
//        when(storeViewService.findStoreById(anyLong())).thenReturn(store);
//        when(boxViewService.getAvailableBoxById(anyLong())).thenThrow(new ApiException(ErrorCode.BAD_REQUEST, "사용할 수 없는 박스"));
//
//        // When
//        Throwable thrown = catchThrowable(() -> {
//            chatRoomService.createChatRoom(request, "loginId");
//        });
//
//        // Then
//        assertThat(thrown)
//                .isInstanceOf(ApiException.class)
//                .hasMessage("사용할 수 없는 박스");
//
//        verify(memberViewService, times(1)).findMemberByLoginId(anyString());
//        verify(storeViewService, times(1)).findStoreById(anyLong());
//        verify(boxViewService, times(1)).getAvailableBoxById(anyLong());
//        verify(chatRoomRepository, times(0)).save(any(ChatRoom.class));
//    }
//
//}