package com.example.keephamchat.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.keephamchat.repository.ChatMessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ChatroomServiceRollbackTest {

    @Autowired
    private ChatroomService chatroomService;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Test
    public void testTransactionRollback() {
        long countBefore = chatMessageRepository.count();

        // This should succeed and increase the count
        chatroomService.test("no-ex");
        long countAfterSuccess = chatMessageRepository.count();
        assertEquals(countBefore + 1, countAfterSuccess);

        // This should fail and rollback the transaction, so count should not increase
        assertThrows(RuntimeException.class, () -> chatroomService.test("ex"));
        long countAfterFailure = chatMessageRepository.count();
        assertEquals(countAfterSuccess, countAfterFailure);
    }
}
