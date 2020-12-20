package com.NaggroTask.conroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.NaggroTask.exception.ConcurrentLoginException;
import com.NaggroTask.model.AuthenticationRequest;
import com.NaggroTask.model.AuthenticationResponse;
import com.NaggroTask.security.JwtUtil;
import com.NaggroTask.security.StoredToken;
import com.NaggroTask.security.authentication.MyUserDetailsService;
import com.NaggroTask.security.authentication.UserPrinciple;


@RestController
class RequestController {
    
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private StoredToken storedtoken;
	
	
	
	@RequestMapping({ "/hello" })
	public String firstPage() {
		return "Hello World";
	}
	
	
     
	
   
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		
    		authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(),
                    authenticationRequest.getPassword()) );
		  UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());
		  
		  String token = storedtoken.getTokenByUserName(authenticationRequest.getUserName());
		  if (token== null || !jwtTokenUtil.validateToken(token,userDetails)) {
		  
		   String jwt = jwtTokenUtil.generateToken(userDetails);
		   storedtoken.putToken(authenticationRequest.getUserName(), jwt);
		  
		  return ResponseEntity.ok(new AuthenticationResponse(jwt , "Login Successful")); 
	    }
		  
		  throw new ConcurrentLoginException(authenticationRequest.getUserName());
	}
	
	
	 
	
	@RequestMapping(value = "/logOut", method = RequestMethod.GET)
	public ResponseEntity<? extends Object> logout() {
		UserPrinciple user=null;
		user = (UserPrinciple) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		storedtoken.removeTokenByKey(user.getUsername());
		return ResponseEntity.ok(new AuthenticationResponse(null, "Successful LogOut"));
	}
 
	
}