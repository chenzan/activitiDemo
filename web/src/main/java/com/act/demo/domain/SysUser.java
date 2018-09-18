package com.act.demo.domain;

import lombok.Data;

@Data
public class SysUser {
    private Integer id;

    private String username;

    private String password;

    private Short position;

}