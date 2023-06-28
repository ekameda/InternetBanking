package com.banco.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.banco.domain.Titular;
import com.banco.repository.TitularRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TitularService {

	private final TitularRepository titularRepository;

	public List<Titular> listaTitulares() {
		return titularRepository.findAll();
	}
}
