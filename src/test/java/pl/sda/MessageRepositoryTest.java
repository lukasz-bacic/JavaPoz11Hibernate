package pl.sda;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class MessageRepositoryTest {


    @BeforeClass
    public static void prepare() {
        User kowalski = User.builder()
                .age(20)
                .firstName("Jan")
                .lastName("Kowalski")
                .email("kowalski_66@gmail.com")
                .password("4")
                .sex(Sex.MALE)
                .userRating(UserRating.builder()
                        .rating(5)
                        .build())
                .build();

        User nowak = User.builder()
                .age(20)
                .firstName("Pawel")
                .lastName("Nowak")
                .email("nowaczek@onet.com")
                .password("4")
                .sex(Sex.MALE)
                .userRating(UserRating.builder()
                        .rating(5)
                        .build())
                .build();

        Long kowalksiId = UserRepository.saveOrUpdate(kowalski);
        Long nowakId = UserRepository.saveOrUpdate(nowak);

        Advertisement advertisement = Advertisement.builder()
                .type(AdvertisementType.FLAT)
                .title("Fajne mieszkanie")
                .address(Address.builder().city("Poznań").build())
                .owner(nowak)
                .createDate(LocalDateTime.now().minusMonths(2))
                .build();

        Long aLong = AdvertisementRepository.saveOrUpdate(advertisement);
    }

    @Test
    public void findByAdvertisementIdAndUserId() {

        Optional<Advertisement> advertisement = AdvertisementRepository.findById(1L);
        Optional<User> kowalski = UserRepository.findById(1L);
        Optional<User> nowak = UserRepository.findById(2L);

        Message message = Message.builder()
                .message("test")
                .advertisement(advertisement.get())
                .createDate(LocalDateTime.now())
                .seller(kowalski.get())
                .buyer(nowak.get())
                .advertisement(Advertisement.builder().title("mam horą córkę można za darmo").build())
                .build();

        Message message2 = Message.builder()
                .message("test")
                .advertisement(advertisement.get())
                .createDate(LocalDateTime.now())
                .seller(kowalski.get())
                .buyer(nowak.get())
                .advertisement(Advertisement.builder().title("spadaj pan").build())
                .build();

        Long id1 = MessageRepository.saveOrUpdate(message);
        Long id2 = MessageRepository.saveOrUpdate(message2);

        List<Message> byAdvertisementIdAndUserId = MessageRepository.findByAdvertisementIdAndUserId(advertisement.get().getId(), nowak.get().getId());
        assertTrue(byAdvertisementIdAndUserId.size() == 2);


    }
}