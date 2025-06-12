package com.alanlr.proposta_app.service;

import com.alanlr.proposta_app.dto.PropostaResponseDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    private SimpMessagingTemplate template;

    public WebSocketService(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void notificar(PropostaResponseDto proposta){
        template.convertAndSend("/proposta", proposta);
    }
}
