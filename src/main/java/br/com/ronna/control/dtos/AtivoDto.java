package br.com.ronna.control.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class AtivoDto {

    private String ativoDescricao;

    private double ativoValorCompra;

    private double ativoValorLocacao;

}
