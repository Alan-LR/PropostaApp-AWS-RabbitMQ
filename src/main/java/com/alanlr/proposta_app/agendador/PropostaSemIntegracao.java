package com.alanlr.proposta_app.agendador;

import com.alanlr.proposta_app.entity.Proposta;
import com.alanlr.proposta_app.repository.PropostaRepository;
import com.alanlr.proposta_app.service.NotificacaoRabbitMQService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropostaSemIntegracao {

    private PropostaRepository propostaRepository;

    private NotificacaoRabbitMQService notificacaoRabbitMQService;

    private String exchange;

    private final Logger logger = LoggerFactory.getLogger(PropostaSemIntegracao.class);

    public PropostaSemIntegracao(PropostaRepository propostaRepository, NotificacaoRabbitMQService notificacaoRabbitMQService,
                                 @Value("${rabbitmq.propostapendente.exchange}")
                                 String exchange) {
        this.propostaRepository = propostaRepository;
        this.notificacaoRabbitMQService = notificacaoRabbitMQService;
        this.exchange = exchange;
    }

    public void buscarPropostasSemIntegracao(){
        propostaRepository.findAllByIntegradaIsFalse().forEach(proposta -> {
            try {
                notificacaoRabbitMQService.notificar(proposta, exchange);
                atualizarProposta(proposta);
            } catch (RuntimeException ex) {
                logger.error(ex.getMessage());
            }
        });
    }

    private void atualizarProposta(Proposta proposta){
        proposta.setIntegrada(true);
        propostaRepository.save(proposta);
    }
}
