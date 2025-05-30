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

    public PropostaResponseDto criar(PropostaRequestDto data){
        Proposta proposta = PropostaMapper.INSTANCE.convertDtoToProposta(data);
        repository.save(proposta);

        return PropostaMapper.INSTANCE.convetEntityToDto(proposta);
    }

    public List<PropostaResponseDto> obterPropostas() {
        return PropostaMapper.INSTANCE.convertListEntityToListDto(repository.findAll());
    }
}


