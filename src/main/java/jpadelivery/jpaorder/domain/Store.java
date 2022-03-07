package jpadelivery.jpaorder.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Store {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Column(name="store_name")
    private String name;

    @Enumerated(EnumType.STRING)
    private StoreStatus status;

}
