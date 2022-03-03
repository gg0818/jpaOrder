package jpadelivery.jpaorder.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String password;

    private String phone;

    @Embedded
    private Address address;

    public Member createMember(String email, String password, String city, String street, String zipcode, String phone){
        Member member = new Member();
        member.setEmail(email);
        member.setPassword(password);
        member.setAddress(new Address(city, street, zipcode));
        member.setPhone(phone);
        return member;
    }

}
