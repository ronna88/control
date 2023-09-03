package br.com.ronna.control.dtos;

import br.com.ronna.control.enums.FuncionarioStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FuncionarioDto {

    private String funcionarioNome;

    private LocalDate funcionarioNascimento;

    private String funcionarioCPF;

    private String funcionarioEmail;

    private String funcionarioTelefone;

    private String funcionarioEndereco;

    private String funcionarioEndereco2;

    private String funcionarioBairro;

    private String funcionarioCEP;

    private LocalDate funcionarioAdmissao;

    private FuncionarioStatus funcionarioStatus;
}
