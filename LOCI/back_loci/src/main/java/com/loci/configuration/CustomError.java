package com.loci.configuration;

import javax.servlet.http.HttpServletResponse;

public class CustomError {
    private Integer code;
    private String message;

    public CustomError(HttpServletResponse res, Exception e) {
        this.code = res.getStatus();
        this.message = e.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
