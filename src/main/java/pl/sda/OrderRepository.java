package pl.sda;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class OrderRepository {

    public static Optional<Order> findById(Long id) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            Order order = session.find(Order.class, id);
            return Optional.ofNullable(order);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        } finally {
            if (null != session && session.isOpen()) {
                session.close();
            }
        }
    }


    public static Long save(Order order) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            Long id = (Long) session.save(order);
            return id;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0L;
        } finally {
            if (null != session && session.isOpen()) {
                session.close();
            }
        }

    }


    public static void saveOrUpdate(Order order) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.saveOrUpdate(order);
            transaction.commit();
        } catch (Exception ex) {
            if (null != session && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            ex.printStackTrace();
        } finally {
            if (null != session && session.isOpen()) {
                session.close();
            }
        }
    }

    public static List<Order> findAll() {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String hql = "SELECT o FROM Order o";
            Query query = session.createQuery(hql);
            List allOrder = query.getResultList();

            return allOrder;

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            if (null != session && session.isOpen()) {
                session.close();
            }
        }


    }
}
