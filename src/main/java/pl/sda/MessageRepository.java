package pl.sda;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MessageRepository {

    public static Long saveOrUpdate(Message message){
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.saveOrUpdate(message);
            transaction.commit();
            return message.getId();
        } catch (Exception ex) {
            if (null != session && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            ex.printStackTrace();
            return 0L;
        } finally {
            if (null != session && session.isOpen()) {
                session.close();
            }
        }
    }

    public static Optional<Message> findById(Long id){
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            Message message = session.find(Message.class, id);
            return Optional.ofNullable(message);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        } finally {
            if (null != session && session.isOpen()) {
                session.close();
            }
        }
    }

    public static List<Message> findByAdvertisementIdAndUserId(Long advertisementid, Long userId){
        Session session = null;

        try {
            session = HibernateUtil.openSession();
            String hql = "SELECT m FROM Message m WHERE " +
                    "m.advertisement.id= :advertisementid " +
                    "AND (m.buyer.id = :userId OR m.seller.id = :userId ) ORDER BY m.createDate DESC";
            Query query = session.createQuery(hql);

            query.setParameter("advertisementid", advertisementid);
            query.setParameter("userId", userId);

            List resultList = query.getResultList();
            return resultList;
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
