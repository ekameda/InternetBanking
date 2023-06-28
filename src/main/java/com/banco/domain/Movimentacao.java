package com.banco.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.banco.dto.MovimentacaoResponseDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Movimentacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String tipoMovimentacao;
	private Long idConta;
	private BigDecimal valorContaAnterior;
	private BigDecimal valorContaApos;
	private BigDecimal valorMovimentacao;
	private Date dataMovimentacao;
	
	public MovimentacaoResponseDto toMovimentacaoDto() {
		MovimentacaoResponseDto dto = new MovimentacaoResponseDto();
		dto.setDataMovimentacao(this.getDataMovimentacao());
		dto.setIdConta(this.getIdConta());
		dto.setTipoMovimentacao(this.getTipoMovimentacao());
		dto.setValorContaAnterior(this.getValorContaAnterior());
		dto.setValorContaApos(this.getValorContaApos());
		dto.setValorMovimentacao(this.getValorMovimentacao());
		
		return dto;
	}
}
