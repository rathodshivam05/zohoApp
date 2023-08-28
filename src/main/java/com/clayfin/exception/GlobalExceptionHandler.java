package com.clayfin.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EmployeeException.class)
	public ResponseEntity<ErrorDetails> employeeExceptionHandler(EmployeeException e, WebRequest req) {

		ErrorDetails ed = new ErrorDetails();

		ed.setTimestamp(LocalDateTime.now());
		ed.setMessage(e.getMessage());
		ed.setDescription(req.getDescription(false));

		return new ResponseEntity<>(ed, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(TaskException.class)
	public ResponseEntity<ErrorDetails> taskExceptionHandler(TaskException e, WebRequest req) {

		ErrorDetails ed = new ErrorDetails();

		ed.setTimestamp(LocalDateTime.now());
		ed.setMessage(e.getMessage());
		ed.setDescription(req.getDescription(false));

		return new ResponseEntity<>(ed, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(LeaveException.class)
	public ResponseEntity<ErrorDetails> leaveExceptionHandler(LeaveException e, WebRequest req) {

		ErrorDetails ed = new ErrorDetails();

		ed.setTimestamp(LocalDateTime.now());
		ed.setMessage(e.getMessage());
		ed.setDescription(req.getDescription(false));

		return new ResponseEntity<>(ed, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(RegularizationException.class)
	public ResponseEntity<ErrorDetails> regularizationExceptionHandler(RegularizationException e, WebRequest req) {

		ErrorDetails ed = new ErrorDetails();

		ed.setTimestamp(LocalDateTime.now());
		ed.setMessage(e.getMessage());
		ed.setDescription(req.getDescription(false));

		return new ResponseEntity<>(ed, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(AttendanceException.class)
	public ResponseEntity<ErrorDetails> attendanceExceptionHandler(AttendanceException e, WebRequest req) {

		ErrorDetails ed = new ErrorDetails();

		ed.setTimestamp(LocalDateTime.now());
		ed.setMessage(e.getMessage());
		ed.setDescription(req.getDescription(false));

		return new ResponseEntity<>(ed, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetails> validationHandler(MethodArgumentNotValidException e, WebRequest req) {

		ErrorDetails ed = new ErrorDetails();

		ed.setTimestamp(LocalDateTime.now());
		ed.setMessage("Validation Error");
		ed.setDescription(e.getBindingResult().getFieldError().getDefaultMessage());

		return new ResponseEntity<>(ed, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorDetails> noHandlerFoundHandler(NoHandlerFoundException e, WebRequest req) {

		ErrorDetails ed = new ErrorDetails();

		ed.setTimestamp(LocalDateTime.now());
		ed.setMessage(e.getMessage());
		ed.setDescription(req.getDescription(false));

		return new ResponseEntity<>(ed, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> ExceptionHandler(Exception e, WebRequest req) {

		ErrorDetails ed = new ErrorDetails();

		ed.setTimestamp(LocalDateTime.now());
		ed.setMessage(e.getMessage());
		ed.setDescription(req.getDescription(false));

		return new ResponseEntity<>(ed, HttpStatus.BAD_REQUEST);

	}

}
