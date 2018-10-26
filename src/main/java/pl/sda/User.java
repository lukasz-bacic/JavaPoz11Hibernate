package pl.sda;

import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    String email;
    @Column(length = 60, name = "first_name", nullable = false)
    String firstName;
    @Column(nullable = false)
    String lastName;

    @Min(value = 18, message = "User should be Adult")
    @Max(value = 130)
    int age;

    @NotNull(message = "Password is empty")
    @Column(nullable = false)
    String password;

    String phoneNumber;

    @Enumerated(EnumType.STRING)
    Sex sex;

    @Embedded
    //@Valid
    Address address;

    @OneToOne(cascade = CascadeType.ALL)
    UserRating userRating;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    Set<UserComment> userCommentSet;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    Set<Advertisement> advertisementSet;

    public void addUserComment(UserComment userComment) {
        if (userCommentSet == null) {
            userCommentSet = new HashSet<>();
        }

        userComment.setUser(this);
        userCommentSet.add(userComment);
    }
}
