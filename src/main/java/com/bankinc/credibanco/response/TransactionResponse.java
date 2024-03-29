package com.bankinc.credibanco.response;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.bankinc.credibanco.model.Transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
	
	HttpStatus status;
	HashMap<String, String> metadata;
	List<Transaction> transactionData;

}
