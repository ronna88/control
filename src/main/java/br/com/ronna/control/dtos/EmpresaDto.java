package br.com.ronna.control.dtos;

import lombok.Data;

@Data
public class EmpresaDto {

    private String empresaNomeFantasia;
    private String empresaCNPJ;
    private String empresaInscricaoEstadual;
    private String empresaEndereco;
    private String empresaEmail;
    private String empresaTelefone;

}
