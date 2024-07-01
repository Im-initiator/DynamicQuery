package com.leminhtien.demoSpringJpaDynamic.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccountByUserRequest {
    private String username;
    private String password;
}
