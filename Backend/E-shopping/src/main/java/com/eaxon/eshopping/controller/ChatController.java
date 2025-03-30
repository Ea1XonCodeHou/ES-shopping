package com.eaxon.eshopping.controller;
import com.eaxon.eshopping.model.dto.ChatRequestDTO;
import com.eaxon.eshopping.model.dto.ChatResponseDTO;
import com.eaxon.eshopping.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/send")
    public ResponseEntity<ChatResponseDTO> sendMessage(@RequestBody ChatRequestDTO chatRequest) {
        log.info("Deepseek-API");
        log.info(chatRequest.getMessage());
        ChatResponseDTO response = chatService.sendMessage(chatRequest);
        return ResponseEntity.ok(response);
    }
}
