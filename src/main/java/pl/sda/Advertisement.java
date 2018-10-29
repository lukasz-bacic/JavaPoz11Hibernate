package pl.sda;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
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

    @OneToMany(mappedBy = "advertisement")
    Set<Message> messageSet;


    public static Advertisement toEntity(ResultSet rs) throws SQLException {
        Advertisement advertisement = new Advertisement();
        advertisement.setId(rs.getLong("id"));
        advertisement.setPrice(rs.getBigDecimal("price"));
        advertisement.setTitle(rs.getString("title"));
        Address address = new Address();
        address.setCity(rs.getString("city"));
        address.setStreetName(rs.getString("streetName"));
        address.setNumber(rs.getInt("number"));
        address.setZipCode(rs.getString("zipCode"));
        advertisement.setAddress(address);
        return advertisement;
    }
}
