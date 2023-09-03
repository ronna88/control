package br.com.ronna.control.models;

import br.com.ronna.control.enums.ClienteStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "TB_CLIENTES")
public class ClienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "varbinary(36)")
    private UUID clienteId;

    @Column(nullable = false)
    private String clienteNome;

    @Column(nullable = false)
    private String clienteTelefone;

    @Column(nullable = false)
    private String clienteEmail;

    @Column(nullable = false)
    private String clienteEndereco;

    @Column(nullable = false)
    private String clienteNumero;

    @Column(nullable = false)
    private String clienteBairro;

    @Column(nullable = false)
    private String clienteCidade;

    @Column(nullable = false)
    private String clienteUF;

    @Column(nullable = false)
    private String clienteCEP;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClienteStatus clienteStatus;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime clienteDataCriacao;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime clienteDataAtualizacao;


    @ManyToOne(optional = false)
    private EmpresaModel empresa;

    @OneToOne
    private ContratoModel contrato;
}
