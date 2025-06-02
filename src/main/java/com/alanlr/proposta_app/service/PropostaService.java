package com.alanlr.proposta_app.service;

import com.alanlr.proposta_app.dto.PropostaRequestDto;
import com.alanlr.proposta_app.dto.PropostaResponseDto;
import com.alanlr.proposta_app.entity.Proposta;
import com.alanlr.proposta_app.mapper.PropostaMapper;
import com.alanlr.proposta_app.repository.PropostaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PropostaService {

    private PropostaRepository repository;

    private NotificacaoService notificacaoService;

    public PropostaResponseDto criar(PropostaRequestDto data){
        Proposta proposta = PropostaMapper.INSTANCE.convertDtoToProposta(data);
        repository.save(proposta);

        PropostaResponseDto response = PropostaMapper.INSTANCE.convetEntityToDto(proposta);
        notificacaoService.notificar(response, "proposta-pendente.ex");

        return response;
    }

    public List<PropostaResponseDto> obterPropostas() {
        return PropostaMapper.INSTANCE.convertListEntityToListDto(repository.findAll());
    }
}


