package com.stockprocessor.stockprocessor.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Entity Not Found")
public class NotFoundHttpException extends Exception{
}
