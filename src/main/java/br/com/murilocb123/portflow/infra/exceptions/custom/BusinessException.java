package br.com.murilocb123.portflow.infra.exceptions.custom;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private String title;

    public BusinessException(String title, String message) {
        super(message);
    }

    public BusinessException(String message) {
        super(message);
    }
}
