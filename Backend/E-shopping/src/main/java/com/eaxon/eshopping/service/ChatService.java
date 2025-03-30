package com.eaxon.eshopping.service;


import com.eaxon.eshopping.model.dto.ChatRequestDTO;
import com.eaxon.eshopping.model.dto.ChatResponseDTO;

public interface ChatService {
    ChatResponseDTO sendMessage(ChatRequestDTO request);
} 