package com.act.demo.domain;

import lombok.Data;
import org.activiti.engine.task.Comment;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SysLeave implements Serializable {
    private Integer id;

    private Integer userId;

    private String leaveDay;

    private String leaveReason;

    private Date leaveStartTime;

    private Date leaveEndTime;

    private Integer state;

    private String assigneeName;

    private List<Comment> comments;
}