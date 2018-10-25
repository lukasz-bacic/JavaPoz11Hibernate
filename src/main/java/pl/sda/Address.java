package pl.sda;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

@Embeddable
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    String city;
    String streetName;
    @Pattern(regexp = "^[0-9]{2}-[0-9]{3}")
    String zipCode;
    Integer number;
    String district;
}
