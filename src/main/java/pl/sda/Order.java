package pl.sda;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity {

    String test;

    @Embedded
    Address address;

    public Order() {
    }


}
