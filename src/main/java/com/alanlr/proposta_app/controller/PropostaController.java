package com.alanlr.proposta_app.controller;

import com.alanlr.proposta_app.dto.PropostaRequestDto;
import com.alanlr.proposta_app.dto.PropostaResponseDto;
import com.alanlr.proposta_app.service.PropostaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/proposta")
public class PropostaController {

    private PropostaService service;

    @PostMapping
    public ResponseEntity<PropostaResponseDto> criar(@RequestBody PropostaRequestDto data){
        PropostaResponseDto responseDto = service.criar(data);
        //usado para retornar no headers da requisição mais informações, como location ex:http://localhost:8080/proposta/1
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDto.getId())
                .toUri())
                .body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<PropostaResponseDto>> obterPropostas(){
        return ResponseEntity.ok(service.obterPropostas());
    }
}
