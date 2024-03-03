package br.com.ronna.control.models;

import br.com.ronna.control.enums.FuncionarioStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "TB_FUNCIONARIOS")
public class FuncionarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "varbinary(36)")
    private UUID funcionarioId;

    @Column(nullable = false)
    private String funcionarioNome;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate funcionarioNascimento;

    @Column(nullable = false)
    private String funcionarioCPF;

    private String funcionarioEmail;

    @Column(nullable = false)
    private String funcionarioTelefone;

    private String funcionarioEndereco;

    private String funcionarioEndereco2;

    private String funcionarioBairro;

    private String funcionarioCidade;

    private String funcionarioCEP;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate funcionarioAdmissao;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime createdDate;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime updatedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FuncionarioStatus funcionarioStatus;

}
