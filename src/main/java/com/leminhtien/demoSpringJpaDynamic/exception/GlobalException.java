package com.leminhtien.demoSpringJpaDynamic.exception;

import com.leminhtien.demoSpringJpaDynamic.dto.CodeResponse;
import com.leminhtien.demoSpringJpaDynamic.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ResponseDTO> nullPoint(NullPointerException e){
        return ResponseEntity.ok().body(
                ResponseDTO.failure(e.getMessage(), CodeResponse.NOT_FOUNT)
        );
    }

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<ResponseDTO> customRuntime(CustomRuntimeException e){
        return ResponseEntity.ok(ResponseDTO.failure(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> global(Exception e){
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ResponseDTO.builder()
                        .code(404)
                        .message("Server has some errors")
                        .build()
        );
    }
}
