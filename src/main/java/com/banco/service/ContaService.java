package com.banco.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banco.domain.Banco;
import com.banco.domain.Conta;
import com.banco.domain.Titular;
import com.banco.dto.ContaRequestDto;
import com.banco.exceptions.BancoInexistenteException;
import com.banco.exceptions.ContaExistenteException;
import com.banco.exceptions.ContaInexistenteException;
import com.banco.repository.BancoRepository;
import com.banco.repository.ContaRepository;
import com.banco.repository.TitularRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ContaService {

	private final ContaRepository contaRepository;
	private final TitularRepository titularRepository;
	private final BancoRepository bancoRepository;

	public Conta criarConta(ContaRequestDto requestDto) {

		int codigo = requestDto.getCodigo();
		final Banco banco = bancoRepository.findByCodigo(codigo)
				.orElseThrow(() -> new BancoInexistenteException("Banco não encontrado: " + codigo));

		final Titular titular = new Titular();
		titular.setCpf(requestDto.getCpf());
		titular.setNome(requestDto.getNome());
		titular.setDataNascimento(requestDto.getDataNascimento());
		titularRepository.save(titular);

		var conta = new Conta();
		conta.setPlanoExclusivo(requestDto.getPlanoExclusivo());
		
		conta.setAgencia(requestDto.getAgencia());
		conta.setTitular(titular);
		conta.setBanco(banco);
		conta.setPix(requestDto.getChave());
		validaContaExistente(conta);
		Conta contaSalva = contaRepository.save(conta);
		return contaSalva;
	}

	private void validaContaExistente(Conta conta) {
		Optional<Conta> contaOptional = contaRepository.findByAgenciaAndNumero(conta.getAgencia(), conta.getNumero());

		if (contaOptional.isPresent()) {
			throw new ContaExistenteException();
		}
	}

	public List<Conta> procuraContas() {
		return contaRepository.findAll();
	}

	public Conta procuraConta(Long id) {
		Optional<Conta> contaOptional = contaRepository.findById(id);
		if (contaOptional.isEmpty()) {
			throw new ContaInexistenteException("Essa conta não existe!");
		}
		return contaOptional.get();
	}

	public Conta creditarConta(Long idConta, BigDecimal valor) {
		Conta conta = procuraConta(idConta);
		conta.credito(valor);		
		return contaRepository.save(conta);
	}

	public Conta debitaConta(Long idConta, BigDecimal valor) {
		Conta conta = procuraConta(idConta);
		conta.debito(conta.getPlanoExclusivo(), valor);		
		return contaRepository.save(conta);
	}

}
