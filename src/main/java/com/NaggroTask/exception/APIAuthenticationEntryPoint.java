
package com.NaggroTask.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
public class APIAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final HttpMessageConverter<String> messageConverter;

	private final ObjectMapper mapper;

	public APIAuthenticationEntryPoint(ObjectMapper mapper) {
		this.messageConverter = new StringHttpMessageConverter();
		this.mapper = mapper;
	}

	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException {
		GeneralError error = new GeneralError(UNAUTHORIZED);
        error.setMessage("Invalid Credential. Authentication is required");
		ServerHttpResponse outputMessage = new ServletServerHttpResponse(httpServletResponse);
		outputMessage.setStatusCode(HttpStatus.UNAUTHORIZED);
		messageConverter.write(mapper.writeValueAsString(error), MediaType.APPLICATION_JSON, outputMessage);
	}
}
