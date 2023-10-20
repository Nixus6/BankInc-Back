package com.bankinc.credibanco.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankinc.credibanco.model.Transaction;

public interface ITransactionDao extends JpaRepository<Transaction, Integer>{

	
	
}
