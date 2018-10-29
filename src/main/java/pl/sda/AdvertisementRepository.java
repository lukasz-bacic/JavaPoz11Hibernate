package pl.sda;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AdvertisementRepository {

    public static Long saveOrUpdate(Advertisement advertisement){
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.saveOrUpdate(advertisement);
            session.save(advertisement);
            session.persist(advertisement);

            transaction.commit();
            return advertisement.getId();
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

    public static Optional<Advertisement> findById(Long id){
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            Advertisement advertisement = session.find(Advertisement.class, id);
            return Optional.ofNullable(advertisement);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        } finally {
            if (null != session && session.isOpen()) {
                session.close();
            }
        }
    }

    public static List<Advertisement> findAll(){
        Session session = null;
        try {
            session = HibernateUtil.openSession();

            String hql = "SELECT a FROM Advertisement a";
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

    public static List<Advertisement> findByAdvertisementType(AdvertisementType advertisementType){
        Session session = null;
        try {
            session = HibernateUtil.openSession();

            String hql = "SELECT a FROM Advertisement a WHERE a.type = :type";
            Query query = session.createQuery(hql);
            query.setParameter("type", advertisementType);
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

    public static Long countByAdvertisementType(AdvertisementType advertisementType){
        Session session = null;
        try {
            session = HibernateUtil.openSession();

            String hql = "SELECT COUNT(a) FROM Advertisement a WHERE a.type = :type";
            Query query = session.createQuery(hql);
            query.setParameter("type", advertisementType);
            Long count = (Long) query.getSingleResult();
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            if (null != session && session.isOpen()) {
                session.close();
            }
        }
    }

    public static List<Advertisement> findTop5ByCityNameWithUserRatingGreaterThan(String city, Integer rating){
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String hql = "SELECT a FROM Advertisement a WHERE " +
                    "a.owner.userRating.rating > :rating " +
                    "AND a.address.city = :city ORDER BY a.price ASC";
            Query query = session.createQuery(hql);

            query.setParameter("rating", rating);
            query.setParameter("city", city);
            query.setMaxResults(5);
            query.setFirstResult(5);
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

    public static List<Advertisement> findTop5ByCityNameWithUserRatingGreaterThanNative(String city, Integer rating){
        Session session = null;
        try {
            session = HibernateUtil.openSession();

            String sql = "select advertisement.* " +
                    "from advertisement " +
                    "join user ON (user.id = advertisement.owner_id) " +
                    "join userrating ON (userrating.id = user.id) " +
                    "where userrating.rating> :rating and advertisement.city= :city " +
                    "order by advertisement.price ASC limit 5";
            NativeQuery query = session.createNativeQuery(sql);
            query.setParameter("rating", rating);
            query.setParameter("city", city);
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

    public static boolean deleteAllOldAdvertisement(){

        Session session = null;
        try {
            session = HibernateUtil.openSession();
            session.getTransaction().begin();
            String sql = "DELETE FROM advertisement " +
                    "WHERE month(advertisement.createDate) < " +
                    "month(localtime()) AND advertisement.id > 0 ";
            NativeQuery nativeQuery = session.createNativeQuery(sql);
            nativeQuery.executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if(session.getTransaction().isActive()){
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (null != session && session.isOpen()) {
                session.close();
            }
        }

    }

    public static List<Advertisement> searchAdvertisement(AdvertisementSearchParameter advertisementSearchParameter){
        return Collections.emptyList();
    }


}
