package com.bankinc.credibanco.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bankinc.credibanco.model.Tarjet;
import com.bankinc.credibanco.model.Transaction;
import com.bankinc.credibanco.model.TransactionState;
import com.bankinc.credibanco.model.TypeTarjet;
import com.bankinc.credibanco.model.User;
import com.bankinc.credibanco.model.dao.ITarjetDao;
import com.bankinc.credibanco.model.dao.ITransactionDao;
import com.bankinc.credibanco.model.dao.IUserDao;
import com.bankinc.credibanco.request.TransactionRequest;
import com.bankinc.credibanco.response.TransactionResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {
	private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
	// Si tiene mas dependencias mockearlas en las pruebas unitarias
	private final ITransactionDao transactionDao;
	private final ITarjetDao tarjetDao;
	private final IUserDao userDao;

	public TransactionResponse createTransaction(TransactionRequest request) {
		log.info("entro createTransaction: ");
		HashMap<String, String> metadata = new HashMap<String, String>();
		// List<Transaction> list = new ArrayList<>();

		Calendar date = Calendar.getInstance();
		TransactionState state;
		TypeTarjet type;
		Integer newBalance;
		HttpStatus status;
		try {
			Tarjet tarjetObj = tarjetDao.findByIdProducto(request.getIdProduct());
			if (tarjetObj != null) {
				if (tarjetObj.getSaldo() < request.getTotalPrice()) {
					log.info("entro rejected: ");
					state = TransactionState.REEJECTED;
					type = tarjetObj.getTypeTarget();
				} else {
					log.info("entro succesful: ");
					state = TransactionState.SUCCESSFUL;
					type = tarjetObj.getTypeTarget();
					newBalance = tarjetObj.getSaldo() - request.getTotalPrice();
					tarjetObj.setSaldo(newBalance);
				}

				Transaction transaction = Transaction.builder().totalPrice(request.getTotalPrice())
						.fechaTransaccion(date).state(state).usuario(request.getUser_id())
						.tarjet(request.getTarjet_id()).build();

				Transaction save = transactionDao.save(transaction);

				if (save != null) {
					if (state == TransactionState.REEJECTED && tarjetObj.getTypeTarget() == type.CREDIT) {
						metadata.put("tipo", "Respuesta nok");
						metadata.put("codigo", "01");
						metadata.put("message", "Verify your credit card balance");
					}
					if (state == TransactionState.REEJECTED && tarjetObj.getTypeTarget() == type.DEBIT) {
						metadata.put("tipo", "Respuesta nok");
						metadata.put("codigo", "01");
						metadata.put("message", "Check your debit card balance");
					}
					if (state == TransactionState.SUCCESSFUL) {
						metadata.put("tipo", "Respuesta ok");
						metadata.put("codigo", "00");
						metadata.put("message", "Transaccion Creada");
					}
					// TODO:ESTA FALLANDO POR EL JSON
					// list.add(save);
					status = HttpStatus.OK;
				} else {
					metadata.put("tipo", "Respuesta nok");
					metadata.put("codigo", "-1");
					metadata.put("message", "Transaccion no creada");
					status = HttpStatus.BAD_REQUEST;
					return TransactionResponse.builder().metadata(metadata).status(status).build();
				}
			} else {
				metadata.put("tipo", "Respuesta nok");
				metadata.put("codigo", "-1");
				metadata.put("message", "No existe el numero de producto");
				status = HttpStatus.NOT_FOUND;
				return TransactionResponse.builder().metadata(metadata).status(status).build();
			}

		} catch (Exception e) {
			// TODO: handle exception
			log.error("error al crear transaccion: ", e.getMessage());
			metadata.put("tipo", "Respuesta nok");
			metadata.put("codigo", "-1");
			metadata.put("message", "Transaccion no creada");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			return TransactionResponse.builder().metadata(metadata).status(status).build();
		}

		return TransactionResponse.builder().metadata(metadata).status(status).build();
	}

	public TransactionResponse getTransactionsByIdUser(Integer idUser) {
		List<Transaction> list = new ArrayList<>();
		HashMap<String, String> metadata = new HashMap<String, String>();
		HttpStatus status;
		try {
			User getUser = userDao.findById(idUser).get();
			list = transactionDao.findByUsuarioOrderByFechaTransaccionDesc(getUser);
			if (list != null) {
				metadata.put("tipo", "Respuesta ok");
				metadata.put("codigo", "00");
				metadata.put("message", "Transacciones obtenidas por usuario");
				status = HttpStatus.OK;
			} else {
				metadata.put("tipo", "Respuesta nok");
				metadata.put("codigo", "-1");
				metadata.put("message", "No se encontraron transacciones ligadas a este usuario");
				status = HttpStatus.BAD_REQUEST;
				return TransactionResponse.builder().metadata(metadata).status(status).build();
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("error al obtener transacciones: ", e.getMessage());
			metadata.put("tipo", "Respuesta nok");
			metadata.put("codigo", "-1");
			metadata.put("message", "Error consultar transacciones por usuario");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			return TransactionResponse.builder().metadata(metadata).status(status).build();
		}
		return TransactionResponse.builder().metadata(metadata).status(status).transactionData(list).build();
	}

	public TransactionResponse changeStateTransaction(Integer idTransaction) {
		List<Transaction> list = new ArrayList<>();
		HashMap<String, String> metadata = new HashMap<String, String>();
		HttpStatus status;
		Integer newBalance;
		try {

			Optional<Transaction> transaction = transactionDao.findById(idTransaction);
			if (transaction.isPresent()) {
				transaction.get().setState(TransactionState.CANCELLED);
				Transaction tranUpd = transactionDao.save(transaction.get());
				if (tranUpd != null) {
					Tarjet tarjetObj = tarjetDao.findByIdProducto(transaction.get().getTarjet().getIdProducto());
					/* if (tarjetObj != null) { */
					newBalance = tarjetObj.getSaldo() + transaction.get().getTotalPrice();
					tarjetObj.setSaldo(newBalance);
					Tarjet tarjet = tarjetDao.save(tarjetObj);
					if (tarjet != null) {
						metadata.put("tipo", "Respuesta ok");
						metadata.put("codigo", "00");
						metadata.put("message", "Estado cambiado y saldo devuelto");
						status = HttpStatus.OK;
					} else {
						metadata.put("tipo", "Respuesta nok");
						metadata.put("codigo", "-1");
						metadata.put("message", "No se guardo saldo tarjeta");
						status = HttpStatus.BAD_REQUEST;
					}
				} else {
					metadata.put("tipo", "Respuesta nok");
					metadata.put("codigo", "-1");
					metadata.put("message", "No se actualizo transaccion");
					status = HttpStatus.BAD_REQUEST;
					return TransactionResponse.builder().metadata(metadata).status(status).build();
				}
			} else {
				metadata.put("tipo", "Respuesta nok");
				metadata.put("codigo", "-1");
				metadata.put("message", "No se encontraron transacciones ligadas a este id");
				status = HttpStatus.BAD_REQUEST;
				return TransactionResponse.builder().metadata(metadata).status(status).build();
			}

		} catch (Exception e) {
			// TODO: handle exception
			log.error("error al obtener transacciones: ", e.getMessage());
			metadata.put("tipo", "Respuesta nok");
			metadata.put("codigo", "-1");
			metadata.put("message", "Error consultar transacciones por usuario");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			return TransactionResponse.builder().metadata(metadata).status(status).build();
		}
		return TransactionResponse.builder().metadata(metadata).status(status).transactionData(list).build();
	}

}
