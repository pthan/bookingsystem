package com.my.bookingsystem.model.shared;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    public  String name;
    public  String email;
    public  String guid;
    public  String status;

    public String userRole;

}
