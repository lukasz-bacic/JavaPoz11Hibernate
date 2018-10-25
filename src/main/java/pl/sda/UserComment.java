package pl.sda;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserComment extends BaseEntity {


    String comment;

    LocalDateTime createTime;

    @ManyToOne
    @JoinColumn
    User user;


}
