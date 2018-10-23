package pl.sda;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    String city;
    String streetName;
    String zipCode;
    Integer number;
}
