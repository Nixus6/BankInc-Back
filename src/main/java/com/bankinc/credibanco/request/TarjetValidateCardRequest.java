package com.bankinc.credibanco.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TarjetValidateCardRequest {
	
	String name;
	String expiryDate;
	Long idProducto;
}
