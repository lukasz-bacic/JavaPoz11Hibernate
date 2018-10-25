package pl.sda;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Message extends BaseEntity {

    @ManyToOne
    Advertisement advertisement;

    String message;
    LocalDateTime createDate;

    @ManyToOne
    User buyer;

    @ManyToOne
    User seller;

}
