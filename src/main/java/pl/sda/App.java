package pl.sda;

import org.hibernate.Session;

public class App {

    public static void main(String ... a) throws Exception {
        Session session = HibernateUtil.openSession();


    }
}
