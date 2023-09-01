package br.com.ronna.control.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContratoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "varbinary(36)")
    private UUID contratoId;

    @Column(nullable = false)
    private String contratoDescricao;

    @Column(nullable = false)
    private double contratoValorVisita;

    @Column(nullable = false)
    private double contratoValorRemoto;

    @Column(nullable = true)
    private Set<AtivoModel> listaAtivos;

    @Column(nullable = false)
    private Set<ClienteModel> listaClientes;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime contratoDataCriacao;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime contratoDataAtualizacao;
}
