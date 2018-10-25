package pl.sda;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserRepository {

    public static Long saveOrUpdate(User user) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.saveOrUpdate(user);
            transaction.commit();
            return user.getId();
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


    public static Optional<User> findById(Long id) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            User user = session.find(User.class, id);
            return Optional.ofNullable(user);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        } finally {
            if (null != session && session.isOpen()) {
                session.close();
            }
        }
    }

    public static List<User> findAllWithAllComments() {
        Session session = null;
        try {
            session = HibernateUtil.openSession();

            String hql = "SELECT DISTINCT(u) FROM User u JOIN FETCH u.userCommentSet uc";
            Query query = session.createQuery(hql);
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


    public static List<UserIdWithComment> findUserIdAndComments() {
        Session session = null;
        try {
            session = HibernateUtil.openSession();

            String hql = "SELECT new pl.sda.UserIdWithComment(u.id, uc.comment) FROM User u JOIN u.userCommentSet uc";
            Query query = session.createQuery(hql);
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


    public static List<User> findUserWithCommentGreaterThan(Long commentsNumber) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String hql = "SELECT u FROM User u join u.userCommentSet uc " +
                    " GROUP BY uc.user HAVING COUNT(uc.user) > :commentsNumber ";
            Query query = session.createQuery(hql);
            query.setParameter("commentsNumber", commentsNumber);
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

    public static List<User> findUserByLastName(String lastName) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String hql = "SELECT u FROM User u WHERE UPPER(u.lastName) like :lastName";
            Query query = session.createQuery(hql);
            query.setParameter("lastName", "%" + lastName.toUpperCase() + "%");
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

    public static boolean checkIsUserByEmailExist(String email) throws Exception {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String hql = "SELECT COUNT(u) FROM User u WHERE u.email = :email";
            Query query = session.createQuery(hql);
            query.setParameter("email", email);
            query.setParameter("email", email);

            Long userCount = (Long) query.getSingleResult();
            return userCount > 0 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (null != session && session.isOpen()) {
                session.close();
            }
        }
    }

    public static Optional<User> findUserByEmailAndPassword(String email, String password) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String hql = "SELECT u FROM User u WHERE u.email = :email AND u.password = :password";
            Query query = session.createQuery(hql);
            query.setParameter("email", email);
            query.setParameter("password", password);
            Optional<User> userOptional = query.uniqueResultOptional();
            return userOptional;
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            if (null != session && session.isOpen()) {
                session.close();
            }
        }
    }
}
