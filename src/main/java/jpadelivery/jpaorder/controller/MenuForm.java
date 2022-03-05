package jpadelivery.jpaorder.controller;


import lombok.Data;

@Data
public class MenuForm {
    private Long id;
    private int count;
    private Long memberId;
}
