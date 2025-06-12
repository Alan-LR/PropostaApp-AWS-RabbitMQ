package com.alanlr.proposta_app.listener;

import com.alanlr.proposta_app.entity.Proposta;
import com.alanlr.proposta_app.repository.PropostaRepository;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PropostaConcluidaListener {

    private PropostaRepository propostaRepository;

    @RabbitListener(queues = "${rabbitmq.queue.proposta.concluida}")
    public void propostaConcluida(Proposta propostaPayload) {
        propostaRepository.findById(propostaPayload.getId()).ifPresentOrElse(proposta -> {
            // Atualize SOMENTE os campos que mudam
            proposta.setAprovado(propostaPayload.getAprovado());
            proposta.setObservacao(propostaPayload.getObservacao());

            // IMPORTANTE: não sobrescreva `usuario` nem outros objetos aninhados com instâncias desanexadas

            propostaRepository.save(proposta); // salva a entidade gerenciada
        }, () -> {
            // Em caso de proposta não encontrada, evite salvar direto o payload desanexado!
            System.out.println("Proposta com id {} não encontrada. Ignorando mensagem." + propostaPayload.getId());
        });
    }


}
