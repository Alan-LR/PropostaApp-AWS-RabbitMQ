package com.alanlr.proposta_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_proposta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Proposta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valorsolicitado")
    private Double valorSolicitado;
    private int prazoPagamento;
    private Boolean aprovado;
    private boolean integrada;
    private String observacao;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;


}
