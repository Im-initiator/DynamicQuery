package com.leminhtien.demoSpringJpaDynamic.dto;

import lombok.Getter;

@Getter
public enum CodeResponse {
    SUCCESS(200,"Get success"),
    BAD_REQUEST(400,"Request fail"),
    REDIRECT(300,"Redirect"),
    NOT_FOUNT(300,"Not found");

    private final int status;
    private final String message;

    CodeResponse(int status, String message){
        this.status = status;
        this.message = message;
    }

}
