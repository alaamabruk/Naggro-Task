package com.NaggroTask.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.jsonwebtoken.JwtException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {

	
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = e.getParameterName() + " some parameter is missing";
		return buildResponseEntity(new GeneralError(BAD_REQUEST, error, e));
	}

	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(e.getContentType());
		builder.append("Type is not supported, supported are");
		e.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
		return buildResponseEntity(
				new GeneralError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2), e));
	}


	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Error writing JSON output";
		return buildResponseEntity(new GeneralError(HttpStatus.INTERNAL_SERVER_ERROR, error, e));
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		GeneralError error = new GeneralError(BAD_REQUEST);
		error.setMessage(
				String.format("Could not find the %s method for URL %s", e.getHttpMethod(), e.getRequestURL()));
		return buildResponseEntity(error);
	}
	
	@ExceptionHandler(JwtException.class)
	protected ResponseEntity<Object> handleTokenViolation(JwtException e) {
		GeneralError error = new GeneralError(UNAUTHORIZED);
		
		return buildResponseEntity(error);
	}

	@ExceptionHandler(javax.persistence.EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(javax.persistence.EntityNotFoundException e) {
		GeneralError error = new GeneralError(NOT_FOUND);
		error.setMessage(String.format(e.getMessage()));
		return buildResponseEntity(error);
	}

	@ExceptionHandler(ConcurrentLoginException.class)
	protected ResponseEntity<Object> handleConcurrentLoginException(ConcurrentLoginException e, WebRequest request) {
		GeneralError error = new GeneralError(BAD_REQUEST);
		error.setMessage(String.format(e.getMessage()));
		return buildResponseEntity(error);
	}
	
	@ExceptionHandler(InvalidDateRangeException.class)
	protected ResponseEntity<Object> handleBadInvalidDateRangeException(InvalidDateRangeException e, WebRequest request) {
		GeneralError error = new GeneralError(BAD_REQUEST);
		error.setMessage(String.format(e.getMessage()));
		return buildResponseEntity(error);
	}
	
	
	@ExceptionHandler(InvalidAmountRangeException.class)
	protected ResponseEntity<Object> handleInvalidAmountRangeException(InvalidAmountRangeException e, WebRequest request) {
		GeneralError error = new GeneralError(BAD_REQUEST);
		error.setMessage(String.format(e.getMessage()));
		return buildResponseEntity(error);
	}


	@ExceptionHandler(MissingParamException.class)
	protected ResponseEntity<Object> handleMissingParamException(MissingParamException e, WebRequest request) {
		GeneralError error = new GeneralError(BAD_REQUEST);
		error.setMessage(String.format(e.getMessage()));
		return buildResponseEntity(error);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e,
			WebRequest request) {
		GeneralError error = new GeneralError(BAD_REQUEST);
		error.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
				e.getName(), e.getValue(), e.getRequiredType().getSimpleName()));
		return buildResponseEntity(error);
	}
	
	@ExceptionHandler(UserCanNotFilterParametersException.class)
	protected ResponseEntity<Object> handleAdminCanNotFilterParametersException(UserCanNotFilterParametersException e,
			WebRequest request) {
		GeneralError error = new GeneralError(UNAUTHORIZED);
		error.setMessage(e.getMessage());
		return buildResponseEntity(error);
	}


	@ExceptionHandler(MethodNotAllowedException.class)
	protected ResponseEntity<Object> handleMethodNotAllowedException(MethodNotAllowedException e, WebRequest request) {
		GeneralError error = new GeneralError(METHOD_NOT_ALLOWED);
		error.setMessage(String.format(e.getMessage()));
		return buildResponseEntity(error);
	}

	@ExceptionHandler(AccessDeniedException.class)
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(AccessDeniedException e, WebRequest request) {
		GeneralError error = new GeneralError(UNAUTHORIZED);
		error.setMessage(e.getMessage());
		return buildResponseEntity(error);
	}


	private ResponseEntity<Object> buildResponseEntity(GeneralError error) {
		return new ResponseEntity<>(error, error.getStatus());
	}

}
