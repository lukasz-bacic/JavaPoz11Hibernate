package pl.sda;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
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


}
