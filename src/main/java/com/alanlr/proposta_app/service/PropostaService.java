package com.alanlr.proposta_app.service;

import com.alanlr.proposta_app.dto.PropostaRequestDto;
import com.alanlr.proposta_app.dto.PropostaResponseDto;
import com.alanlr.proposta_app.entity.Proposta;
import com.alanlr.proposta_app.mapper.PropostaMapper;
import com.alanlr.proposta_app.repository.PropostaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropostaService {

    private PropostaRepository repository;

    private NotificacaoRabbitMQService notificacaoRabbitMQService;

    private String exchange;

    public PropostaService(PropostaRepository repository, NotificacaoRabbitMQService notificacaoRabbitMQService,
                           @Value("${rabbitmq.propostapendente.exchange}")
                           String exchange) {
        this.repository = repository;
        this.notificacaoRabbitMQService = notificacaoRabbitMQService;
        this.exchange = exchange;
    }

    public PropostaResponseDto criar(PropostaRequestDto data) {
        Proposta proposta = PropostaMapper.INSTANCE.convertDtoToProposta(data);
        repository.save(proposta);

        notificarRabbitMQ(proposta);

        return PropostaMapper.INSTANCE.convetEntityToDto(proposta);
    }

    private void notificarRabbitMQ(Proposta proposta) {
        try {
            notificacaoRabbitMQService.notificar(proposta, exchange);
        } catch (RuntimeException ex) {
            proposta.setIntegrada(false);
            repository.save(proposta);
        }

    }

    public List<PropostaResponseDto> obterPropostas() {
        return PropostaMapper.INSTANCE.convertListEntityToListDto(repository.findAll());
    }
}

