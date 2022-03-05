package jpadelivery.jpaorder.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private String name;
    private int price;
    private String info;
    @Column(name = "menu_img")
    private String originalFileName;
    @Transient
    private String StoredFilePath;
    @Transient
    private Long fileSize;

}
