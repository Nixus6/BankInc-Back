package com.bankinc.credibanco.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankinc.credibanco.model.Transaction;
import com.bankinc.credibanco.model.User;

import java.util.List;
import java.util.Calendar;




public interface ITransactionDao extends JpaRepository<Transaction, Integer>{
	List<Transaction> findByUsuarioOrderByFechaTransaccionDesc(User usuario);
	//JpaRepository<Transaction,Integer> findById(Integer id);
	//List<Transaction> findByFechaTransaccion(Calendar fechaTransaccion);
}
