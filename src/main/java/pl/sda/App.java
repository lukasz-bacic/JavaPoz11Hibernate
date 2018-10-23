package pl.sda;

import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

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


    }
}
