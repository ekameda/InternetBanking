package com.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banco.domain.Titular;

@Repository
public interface TitularRepository extends JpaRepository<Titular, Long> {

}