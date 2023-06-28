package com.banco.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.banco.domain.Movimentacao;
import com.banco.domain.TipoTarifa;

@Repository
public interface MovementacaoRepository extends JpaRepository<Movimentacao, Long> {

	Movimentacao save(Movimentacao movimentacao);


	List<Movimentacao> findByDataMovimentacaoBetween(Date startDate, Date endDate);

//	@Query("FROM Movimentacao m WHERE m.dataMovimentacao BETWEEN :dataInicio AND :dataFim")
//	Movimentacao buscaPorMov(Date dataInicio, Date dataFim);
	
	// select m from Movimentacao m where m.data BETWEEN :pStartData AND :pEndData 		
	//@Query("FROM Movimentacao m WHERE m.dataMovimentacao BETWEEN :dataInicio AND :dataFim")
	//List<Movimentacao> movementacaoRepository.buscaPorMov(dataInicio, dataFim);		
				

}
