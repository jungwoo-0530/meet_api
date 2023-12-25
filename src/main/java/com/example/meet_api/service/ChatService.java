package com.example.meet_api.service;

import com.example.meet_api.domain.Chat;
import com.example.meet_api.dto.Chat.ChatCreateDto;
import com.example.meet_api.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;


    @Transactional
    public void addChat(ChatCreateDto dto){

        chatRepository.save(dto.toEntity());

    }

    @Transactional
    public void updateChatStatus(Long locationId, String status){

        chatRepository.findByLocationId(locationId).ifPresent(chat -> {
            chat.setStatus(status);
        });

    }

    @Transactional(readOnly = true)
    public List<Chat> getChatListByLoginId(String loginId){

        return chatRepository.findAllByLoginId(loginId);

    }


}
