package com.hacking.MemeService.exceptions;

import lombok.Getter;

public class ForbiddenIndexException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 6783384938876035272L;

    @Getter
    private String index;

    public ForbiddenIndexException(final String message, final String index) {
        super(message);
        this.index = index;
    }

}
