package com.bankinc.credibanco.response;

import java.util.HashMap;
import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TarjetValidateResponse {
	
	HashMap<String, String> resValid;
	HashMap<String, HttpStatus> resRequest;
	
}
