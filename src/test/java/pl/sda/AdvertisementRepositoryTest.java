package pl.sda;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class AdvertisementRepositoryTest {

    @Test
    public void countByAdvertisementType() {
        Advertisement advertisement = Advertisement.builder()
                .type(AdvertisementType.FLAT)
                .title("Fajne mieszkanie")
                .build();

        AdvertisementRepository.saveOrUpdate(advertisement);

        Long aLong = AdvertisementRepository.countByAdvertisementType(AdvertisementType.FLAT);

        Assert.assertTrue(aLong.equals(1L));


    }

    @Test
    public void findTop5ByCityNameWithUserRatingGreaterThan() {
        List<Advertisement> advertisementList = new ArrayList<>();

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

        Long userId = UserRepository.saveOrUpdate(kowalski);

        Optional<User> kowalskiOptional = UserRepository.findById(userId);


        Advertisement advertisement = Advertisement.builder()
                .type(AdvertisementType.FLAT)
                .title("Fajne mieszkanie")
                .address(Address.builder().city("Poznań").build())
                .owner(kowalskiOptional.get())
                .build();

        AdvertisementRepository.saveOrUpdate(advertisement);

        advertisementList = AdvertisementRepository.findTop5ByCityNameWithUserRatingGreaterThanNative("Poznań", 4);


        Assert.assertTrue(advertisementList.size() == 1);


    }

}