package com.bankinc.credibanco.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bankinc.credibanco.model.Transaction;
import com.bankinc.credibanco.model.TransactionState;
import com.bankinc.credibanco.model.dao.ITransactionDao;
import com.bankinc.credibanco.request.TransactionRequest;
import com.bankinc.credibanco.response.TransactionResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {
	private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
	//Si tiene mas dependencias mockearlas en las pruebas unitarias
	private final ITransactionDao transactionDao;

	public TransactionResponse createTransaction(TransactionRequest request) {
		log.info("entro createTransaction: ");
		List<Transaction> list = new ArrayList<>();
		Calendar date = Calendar.getInstance();
		try {
			log.info("entro createTransaction: "+ request.getUser_id());      
			Transaction transaction = Transaction.builder()
					.totalPrice(request.getTotalPrice())
					.fechaTransaccion(date)
					.state(TransactionState.SUCCESSFUL)
					.usuario(request.getUser_id())
					.tarjet(request.getTarjet_id())
					.build();
			transactionDao.save(transaction);
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			log.error("error al crear transaccion: ", e.getMessage());
		} 
		return TransactionResponse.builder().transaction(list).build();
	}
}
