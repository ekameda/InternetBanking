package com.banco.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.banco.dto.ContaResponseDto;
import com.banco.exceptions.SaldoInsuficienteException;
import com.banco.exceptions.ValorInvalidoException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
//@Table(name = "conta")
@Getter
@Setter
public class Conta implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int agencia;
	private int numero = new Random().nextInt(100000);
	private BigDecimal saldo = BigDecimal.ZERO;
	private Boolean planoExclusivo;
	private String pix;
	@ManyToOne
	private Banco banco;

	@CreationTimestamp
	private LocalDateTime dataCriacao;
	@UpdateTimestamp
	private LocalDateTime dataUltimaAtualizacao;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "nome_coluna_titular_id")
	private Titular titular;

	@ManyToMany
	@JoinTable(name = "conta_tipos_tarifa", joinColumns = @JoinColumn(name = "conta_id"), inverseJoinColumns = @JoinColumn(name = "tipo_tarifa_id"))
	private List<TipoTarifa> tiposTarifa = new ArrayList<>();

	public Conta() {
	}

	// credito, debito
	public void credito(BigDecimal valor) {
		this.validar(valor);
		saldo = saldo.add(valor);
		log.info("Conta {}/{} foi creditada com {} valor.", this.agencia, this.numero, valor);
	}

	public void debito(Boolean planoExclusivo, BigDecimal valor) {
		this.validar(valor);
		BigDecimal taxa = BigDecimal.ZERO;

		if (valor.compareTo(BigDecimal.valueOf(100)) > 0 && valor.compareTo(BigDecimal.valueOf(300)) <= 0) {
			taxa = valor.multiply(BigDecimal.valueOf(0.004));
		} else if (valor.compareTo(BigDecimal.valueOf(300)) > 0) {
			taxa = valor.multiply(BigDecimal.valueOf(0.01));
		}

		log.info(planoExclusivo.toString()+" : "+taxa.toString());
		if (planoExclusivo) {
			taxa = BigDecimal.ZERO;
		}

		if ((valor.subtract(taxa)).compareTo(saldo) >= 0) {
			throw new SaldoInsuficienteException("Conta não tem saldo para atender a solicitacao");
		}
			

		saldo = saldo.subtract(valor.subtract(taxa));
		log.info("Conta {}/{} foi debitada com {} valor.", this.agencia, this.numero, valor);
	}

	private void validar(BigDecimal valor) {
		final String mensagem = String.format("O valor %s é inválido.", valor);
		if (valor == null) {
			throw new ValorInvalidoException(mensagem);
		}

		if (this.valorIncorreto(valor)) {
			throw new ValorInvalidoException(mensagem);
		}
	}

	private boolean valorIncorreto(BigDecimal valor) {
		// NPE
		return valor.compareTo(BigDecimal.ZERO) <= 0;
	}

	public ContaResponseDto toContaDto() {
		ContaResponseDto dto = new ContaResponseDto();
		dto.setAgencia(this.getAgencia());
		dto.setNumero(this.getNumero());
		dto.setPlanoExclusivo(this.planoExclusivo);
		dto.setSaldo(this.getSaldo());
		dto.setChavePix(this.getPix());
		return dto;
	}

}