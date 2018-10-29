package pl.sda;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AdvertisementFromView extends BaseEntity {

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
