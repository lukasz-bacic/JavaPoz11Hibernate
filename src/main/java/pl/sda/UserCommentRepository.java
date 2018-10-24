package pl.sda;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

public class UserCommentRepository {

    public static void saveOrUpdate(UserComment userComment) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.saveOrUpdate(userComment);
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


    public static Optional<UserComment> findById(Long id) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            UserComment userComment = session.find(UserComment.class, id);
            return Optional.ofNullable(userComment);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        } finally {
            if (null != session && session.isOpen()) {
                session.close();
            }
        }
    }
}
