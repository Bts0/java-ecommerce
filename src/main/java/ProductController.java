
//import javax.annotation.Resource;

//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.HibernateException;

import java.util.List;
import java.util.UUID;

public class ProductController {
    //@PersistenceContext( unitName = "JavaEcommerce")

    public void add(Session session, UUID uuid, String name, float price, int quantity) throws Exception {
        Transaction tx = session.beginTransaction();
        Product product = new Product(uuid, name, price, quantity);
        session.save(product);
        tx.commit();
    }

    public List<Product> retrieveAll(Session session) {
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("FROM Product");
        return query.list();
    }

    public void update(Session session, UUID uuid, String name, float price, int quantity) throws Exception {
        Transaction tx = session.beginTransaction();
        Product product = session.find(Product.class, uuid);
        if (!name.equals(product.getName())) product.setName(name);
        if (price != product.getPrice()) product.setPrice(price);
        if (quantity != product.getQuantity()) product.setQuantity(quantity);
        tx.commit();
    }

    public void destroy(Session session, UUID uuid) throws Exception {
        Transaction tx = session.beginTransaction();
        Product product = session.find(Product.class, uuid);
        session.remove(product);
        tx.commit();
    }

    public static void clearData(Session session, Transaction tx) throws Exception {
        //tx.begin();
        Query deleteStatement = session.createQuery("DELETE FROM Product p");
        deleteStatement.executeUpdate();
        tx.commit();
    }

    public static void createTestData(Session session, Transaction tx) throws Exception {
        Product product = null;
        //tx.begin();
        ProductController.clearData(session, tx);
        tx.begin();
        product = new Product(UUID.randomUUID(), "Test1", 10, 1);
        session.save(product);
        product = new Product(UUID.randomUUID(), "Test2", 17, 9);
        session.save(product);
        product = new Product(UUID.randomUUID(), "Test3", 5, 11);
        session.save(product);
        tx.commit();
    }
}
class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = build();
        }
        return sessionFactory;
    }

    private static SessionFactory build() {
        try {
            return new Configuration().configure().buildSessionFactory();
        }
        catch  (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
}

class ProductTestDrive {
    public static void main(String[] args) throws Exception {
        //ProductController pc = new ProductController();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        ProductController.createTestData(session, tx);
        System.out.println("Test data created.");
        session.close();
    }
}

