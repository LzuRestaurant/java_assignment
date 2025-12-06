package com.pegasus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    private Long id;
    private String name;
    private String password;
    private String idCard;
    private String phone;
    private String gender;
    private Date createdAt;
}