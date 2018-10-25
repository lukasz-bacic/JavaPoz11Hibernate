package pl.sda;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.*;

import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

public class UserRepositoryTest {

    @Test
    public void registerNewUserTest() throws Exception {

        User user = User.builder()
                .age(40)
                .firstName("Pawel")
                .lastName("Wysocki")
                .email("test5@gmail.com")
                .sex(Sex.MALE)
                .password("test")
                .phoneNumber("557588")
                .build();


        Long userId = UserService.registerNewUser(user);
        Assert.assertTrue(userId > 0);

    }

    @Test(expected = DuplicateEmailException.class)
    public void registerNewUserWithDuplicateEmailTest() throws Exception {

        User user = User.builder()
                .age(40)
                .firstName("Pawel")
                .lastName("Wysocki")
                .email("test@gmail.com")
                .sex(Sex.MALE)
                .password("test")
                .phoneNumber("555888999")
                .build();

        User user2 = User.builder()
                .age(40)
                .firstName("Pawel")
                .lastName("Wysocki")
                .email("test@gmail.com")
                .sex(Sex.MALE)
                .password("test")
                .phoneNumber("555888999")
                .build();

        Long aLong = UserService.registerNewUser(user);
        Long aLong2 = UserService.registerNewUser(user2);

    }

    @Test
    public void registerUserWithoutPassword() throws Exception {
        User userWIthoutPassword = User.builder()
                .age(40)
                .firstName("Pawel")
                .lastName("Wysocki")
                .email("test8@gmail.com")
                .sex(Sex.MALE)
                .phoneNumber("555888999")
                .build();


        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> validateUser = validator.validate(userWIthoutPassword);

        Assert.assertTrue(validateUser.size() == 1);
        Assert.assertTrue(validateUser.stream().findFirst().get().getMessageTemplate().equals("Password is empty"));
    }

    @Test
    public void registerUserWithUnderage() throws Exception {
        User userWIthoutPassword = User.builder()
                .age(8)
                .firstName("Pawel")
                .lastName("Wysocki")
                .email("test8@gmail.com")
                .password("tajne")
                .sex(Sex.MALE)
                .phoneNumber("555888999")
                .build();


        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> validateUser = validator.validate(userWIthoutPassword);

        Assert.assertTrue(validateUser.size() == 1);
    }

    //@Test
    public void registerUserWithWrongZipCode() throws Exception {
        User zipCodeTest = User.builder()
                .age(50)
                .firstName("Pawel")
                .lastName("Wysocki")
                .email("test8@gmail.com")
                .password("tajne")
                .sex(Sex.MALE)
                .phoneNumber("555888999")
                .address(Address.builder()
                .number(5)
                .zipCode("44-464")
                .build())
                .build();


        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> validateUser = validator.validate(zipCodeTest);

        Assert.assertTrue(validateUser.size() == 1);
        UserService.registerNewUser(zipCodeTest);
    }

    @Test
    public void findUserByEmailAndPassword() throws Exception {

        User user = User.builder()
                .age(40)
                .firstName("Pawel")
                .lastName("Wysocki")
                .email("pawel@gmail.com")
                .sex(Sex.MALE)
                .password("test")
                .phoneNumber("557588")
                .build();


        Long userId = UserService.registerNewUser(user);

        Optional<User> test = UserRepository.findUserByEmailAndPassword("pawel@gmail.com", "test");
        Assert.assertTrue(test.isPresent());
    }

    @Test
    public void findNotExistUserByEmailAndPassword() throws Exception {

        Optional<User> test = UserRepository.findUserByEmailAndPassword("maciej@gmail.com", "test");
        Assert.assertFalse(test.isPresent());
    }

}