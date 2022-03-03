package jpadelivery.jpaorder.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {
    private String email;
    private String emailChkNum;
    private String password;
    private String phone;
    private String city;
    private String street;
    private String zipcode;
}
