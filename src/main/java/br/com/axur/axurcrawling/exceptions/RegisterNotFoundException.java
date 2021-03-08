package br.com.axur.axurcrawling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RegisterNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -5220996581133717809L;

	public RegisterNotFoundException() {
        super();
    }
	
}
