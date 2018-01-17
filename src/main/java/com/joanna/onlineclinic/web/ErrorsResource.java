package com.joanna.onlineclinic.web;

import java.util.List;

public class ErrorsResource {

    private List<String> validationErrors;

    public ErrorsResource(List<String> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }
}
