package com.bankinc.credibanco.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bankinc.credibanco.model.Role;
import com.bankinc.credibanco.model.User;
import com.bankinc.credibanco.model.dao.IUserDao;
import com.bankinc.credibanco.request.LoginRequest;
import com.bankinc.credibanco.request.RegisterRequest;
import com.bankinc.credibanco.response.AuthResponse;
/*import com.irojas.demojwt.Jwt.JwtService;
import com.irojas.demojwt.User.Role;
import com.irojas.demojwt.User.User;
import com.irojas.demojwt.User.UserRepository;*/

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private static final Logger log=LoggerFactory.getLogger(AuthService.class);
    private final IUserDao userRepository;
    private final JwtService jwtService ;
    private final PasswordEncoder passwordEncoder ;
    private final AuthenticationManager authenticationManager;
    
    //TODO:Validar errorres
    public AuthResponse login(LoginRequest request){
    	String token="";
    	UserDetails user = null;
    	try {
    	UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
    			request.getUsername(), request.getPassword());
    	authenticationManager.authenticate(authenticationToken);
    	user=userRepository.findByUsername(request.getUsername())
    			.orElseThrow(()->new RuntimeException("User not found"));
    	token=jwtService.getToken(user);
    	}catch (AuthenticationException e) {
			// TODO: handle exception
    		log.error("Error en la autenticacion ", e);
		}
    	return AuthResponse.builder()
    			.user(user)
    			.token(token)
    			.build();
    }

    public AuthResponse register(RegisterRequest request) {
    	log.info("inicio metodo register", request.getUsername());
       User user = User.builder()
               .username(request.getUsername())
               .password(passwordEncoder.encode( request.getPassword()))
               .firstname(request.getFirstname())
               .lastname(request.getLastname())
               .role(Role.USER)
               .build();

        userRepository.save(user);

        return AuthResponse.builder()
            .token(jwtService.getToken(user))
            .build();
        
    }

}