package com.bankinc.credibanco.service;

import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bankinc.credibanco.model.Tarjet;
import com.bankinc.credibanco.model.Transaction;
import com.bankinc.credibanco.model.TransactionState;
import com.bankinc.credibanco.model.User;
import com.bankinc.credibanco.model.dao.ITransactionDao;
import com.bankinc.credibanco.request.TransactionRequest;
import com.bankinc.credibanco.response.TransactionResponse;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
	private static final Logger log = LoggerFactory.getLogger(TransactionServiceTest.class);
	

	@Mock
	ITransactionDao transactionDao;

	@InjectMocks
	TransactionService service;

	private TransactionRequest transactionReq;
	private Transaction transaction;
	private Calendar date = Calendar.getInstance();
	private User user;
	private Tarjet tarjet;

	@BeforeEach
	void setup() {
		transactionReq = TransactionRequest.builder().totalPrice(1000).user_id(user).tarjet_id(tarjet).build();
	}
	

	@Test
	@DisplayName("Test para guardar una transaccion")
	void testSaveTransaction() {
	
		// given
		/*
		 * given(transactionDao.findByUsuario(transaction.getUsuario()))
		 * .willReturn(Optional.empty());
		 */
		//given(transactionDao.save(transaction)).willReturn(transaction);
		transaction = Transaction.builder().totalPrice(transactionReq.getTotalPrice())
				.state(TransactionState.SUCCESSFUL).fechaTransaccion(date).usuario(transactionReq.getUser_id())
				.tarjet(transactionReq.getTarjet_id()).build();
		
		lenient().when(transactionDao.save(transaction)).thenReturn(transaction);
		
		// when
		TransactionResponse transactionSave = service.createTransaction(transactionReq);

		// then
		assertThat(transactionSave).isNotNull();

	}

}
