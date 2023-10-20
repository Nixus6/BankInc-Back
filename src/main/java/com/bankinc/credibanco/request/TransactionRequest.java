package com.bankinc.credibanco.request;


import com.bankinc.credibanco.model.Tarjet;
import com.bankinc.credibanco.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
	Integer totalPrice;
	User user_id; 
	Tarjet tarjet_id;
}
