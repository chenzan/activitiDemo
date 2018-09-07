package com.act.demo.domain;

import lombok.Data;

import javax.validation.constraints.Email;
import java.util.Date;

@Data
public class SysLeave {
    @Email
    private Integer id;

    private Integer userId;

    private String leaveDay;

    private String leaveReason;

    private Date leaveStartTime;

    private Date leaveEndTime;

    private Integer state;

    private String assigneeName;
}