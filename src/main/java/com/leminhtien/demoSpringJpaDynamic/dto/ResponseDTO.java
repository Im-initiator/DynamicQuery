package com.leminhtien.demoSpringJpaDynamic.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {
    private int code;
    private String message;
    //private boolean success;
    private Object data;


    public static ResponseDTO success(String message,Object data){
        return ResponseDTO.builder()
 //               .success(true)
                .message(message)
                .code(CodeResponse.SUCCESS.getStatus())
                .data(data)
                .build();
    }

    public static ResponseDTO success(String message, CodeResponse code, Object data){
        return ResponseDTO.builder()
  //              .success(true)
                .message(message)
                .code(code.getStatus())
                .data(data)
                .build();
    }

    public static ResponseDTO success(String message){
        return ResponseDTO.builder()
     //           .success(true)
                .message(message)
                .code(CodeResponse.SUCCESS.getStatus())
                .build();
    }

    public static ResponseDTO success(Object data){
        return ResponseDTO.builder()
      //          .success(true)
                .message(CodeResponse.SUCCESS.getMessage())
                .code(CodeResponse.SUCCESS.getStatus())
                .data(data)
                .build();
    }

    public static ResponseDTO failure(String message){
       return ResponseDTO.builder()
               .message(message)
               .code(CodeResponse.BAD_REQUEST.getStatus())
       //        .success(false)
               .build();
    }

    public static ResponseDTO failure(String message,CodeResponse status){
        return null;
    }
}
