package com.gidis01.CRamirezProgramacionNCapasMarzo25.ML;

import java.util.List;

public class Result {

    public boolean correct; // cuando se ejecuta ok o no ok 
    public String errorMessage; // descripción del error del mensaje
    public Exception ex; // excepciones
    public Object object; // int, string, alumno, lista<Alumno>, ARREGLO, MATRIZ 
    public List<Object> objects;
    
    private boolean success;
    private String message;
    private String error;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
}
