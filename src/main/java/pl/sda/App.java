package pl.sda;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class App {

    public static void main(String... a) throws Exception {


        Address poznan = Address.builder()
                .city("Poznan")
                .streetName("Gleboka")
                .zipCode("61-512")
                .number(5)
                .build();

        Order order = Order.builder().address(poznan).build();

        Long id = OrderRepository.save(order);

        Optional<Order> orderOptional = OrderRepository.findById(id);

        orderOptional.ifPresent(o -> {
            o.getAddress().setCity("Warszawa");
            OrderRepository.saveOrUpdate(o);
        });


        List<Order> all = OrderRepository.findAll();
        all.stream().forEach(o -> System.out.println(o.getAddress().getCity()));

        List<Order> poznanOrders = OrderRepository.findAllByCityName("Poznan");
        List<Order> warszawaOrders = OrderRepository.findAllByCityName("Warszawa");

        System.out.println("poznanOrders " + poznanOrders.size());
        System.out.println("warszawaOrders " + warszawaOrders.size());


        User kowalski = User.builder()
                .age(20)
                .firstName("Jan")
                .lastName("Kowalski")
                .sex(Sex.MALE)
                .build();

        Long userId = UserRepository.saveOrUpdate(kowalski);

        Optional<User> byId = UserRepository.findById(userId);

        byId.ifPresent(k -> {
                    UserComment superKomentarzKowalskiego = UserComment.builder()
                            .comment("Super komentarz")
                            .user(k)
                            .createTime(LocalDateTime.now()).build();

                    UserCommentRepository.saveOrUpdate(superKomentarzKowalskiego);
                }

        );


        User nowak = User.builder()
                .age(30)
                .firstName("Kasia")
                .lastName("Nowak")
                .sex(Sex.FEMALE)
                .build();

        UserComment superKomentarzNowaka = UserComment.builder()
                .comment("Super komentarz")
                .createTime(LocalDateTime.now()).build();

        UserComment superKomentarzNowaka2 = UserComment.builder()
                .comment("Super komentarz 2")
                .createTime(LocalDateTime.now()).build();


        nowak.addUserComment(superKomentarzNowaka);
        nowak.addUserComment(superKomentarzNowaka2);

        UserRepository.saveOrUpdate(nowak);


        UserRepository.findAllWithAllComments().stream().forEach(u -> {
            Set<UserComment> userCommentSet = u.getUserCommentSet();
            userCommentSet.forEach(comment ->
                    System.out.println(u.getId() +" "+ comment.getComment()));

        });

        UserRepository.findUserIdAndComments().forEach(
                uc -> System.out.println(uc.getUserId()+ " "+ uc.getComment()));


        List<User> userWithCommentGreaterThan0 = UserRepository.findUserWithCommentGreaterThan(0L);
        List<User> userWithCommentGreaterThan1 = UserRepository.findUserWithCommentGreaterThan(1L);
        List<User> userWithCommentGreaterThan2 = UserRepository.findUserWithCommentGreaterThan(2L);

        System.out.println(userWithCommentGreaterThan0.size()+ " "+userWithCommentGreaterThan1.size()
                +" "+userWithCommentGreaterThan2.size());


        List<User> Now = UserRepository.findUserByLastName("Now");
        System.out.println(Now.size());
        List<User> now = UserRepository.findUserByLastName("now");
        System.out.println(now.size());



    }
}
