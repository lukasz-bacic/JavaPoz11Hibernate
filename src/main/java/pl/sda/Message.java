package pl.sda;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message extends BaseEntity {

    @ManyToOne
    @JoinColumn
    Advertisement advertisement;

    String message;
    LocalDateTime createDate;

    @ManyToOne
    @JoinColumn
    User buyer;

    @ManyToOne
    @JoinColumn
    User seller;

}
