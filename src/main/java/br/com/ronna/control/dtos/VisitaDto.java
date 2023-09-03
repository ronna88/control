package br.com.ronna.control.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VisitaDto {

    private LocalDateTime visitaInicio;

    private LocalDateTime visitaFinal;

    private String visitaDescricao;

    private Double visitaValorProdutos;
}
