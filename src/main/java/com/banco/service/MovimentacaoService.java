package com.banco.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.banco.domain.Conta;
import com.banco.domain.Movimentacao;
import com.banco.repository.MovementacaoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MovimentacaoService {

	private final ContaService contaService;
	private final MovementacaoRepository movimentacaoRepository;

	public Movimentacao debita(Long idConta, BigDecimal valor, BigDecimal valorAnterior) {

		Movimentacao movimentacao = new Movimentacao();
		movimentacao.setTipoMovimentacao("Debita");
		movimentacao.setDataMovimentacao(new Date());
		movimentacao.setIdConta(idConta);
		movimentacao.setValorMovimentacao(valor);
		movimentacao.setValorContaAnterior(valorAnterior);
		Conta conta = contaService.procuraConta(idConta);
		movimentacao.setValorContaApos(conta.getSaldo());

		return movimentacaoRepository.save(movimentacao);
	}

	public BigDecimal valorAnterio(Long idConta) {

		Conta conta = contaService.procuraConta(idConta);
		return conta.getSaldo();
	}

	public Movimentacao creditaConta(Long idConta, BigDecimal valor, BigDecimal valorAnterior) {

		Movimentacao movimentacao = new Movimentacao();
		movimentacao.setTipoMovimentacao("Credita");
		movimentacao.setDataMovimentacao(new Date());
		movimentacao.setIdConta(idConta);
		movimentacao.setValorMovimentacao(valor);
		movimentacao.setValorContaAnterior(valorAnterior);
		Conta conta = contaService.procuraConta(idConta);
		movimentacao.setValorContaApos(conta.getSaldo());

		return movimentacaoRepository.save(movimentacao);
	}

	public List<Movimentacao> procuraEntreDatas(Date dataInicio, Date dataFim) {
		return movimentacaoRepository.findByDataMovimentacaoBetween(dataInicio, dataFim);
	}

}
