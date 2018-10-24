package pl.sda;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String email;
    @Column(length = 60, name = "first_name")
    String firstName;
    String lastName;
    int age;
    String password;

    @Transient
    String name;

    @Enumerated(EnumType.STRING)
    Sex sex;

    @Embedded
    Address address;

    @OneToOne
    UserRating userRating;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    Set<UserComment> userCommentSet;


    public void addUserComment(UserComment userComment) {
        if (userCommentSet == null) {
            userCommentSet = new HashSet<>();
        }

        userComment.setUser(this);
        userCommentSet.add(userComment);
    }
}
