package pl.sda;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Advertisement extends BaseEntity {

    @Column(length = 80)
    String title;
    @Column(length = 1000)
    String description;
    BigDecimal price;
    @Enumerated
    AdvertisementType type;
    LocalDateTime createDate;
    @Embedded
    Address address;
    Float size;
    @Enumerated
    Contract contract;
    @ManyToOne
    @JoinColumn
    User owner;
    Boolean isFurnished;
    Integer rooms;
}
