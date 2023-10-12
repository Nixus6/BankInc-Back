package com.bankinc.credibanco.request;

import com.bankinc.credibanco.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TarjetRequest {
	
	String typeTarjet;
	User user;
	
}
