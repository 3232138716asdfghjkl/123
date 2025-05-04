package com.user.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class Auth extends User {
    private String code;
}
