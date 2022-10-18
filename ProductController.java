import javax.persistence.*;

public class ProductController {
    @PersistenceContext( unitName = "JavaEcommerce")
    private EntityManager em;
    @Resource Usertransaction ut;

    public static void add(EntityManager em, UserTransaction ut, UUID uuid, String name, float price, int quantity) {
        ut.begin();
        Product product = new Product(uuid, name, price, quantity);
        em.persist(product);
        ut.commit();
    }

    public static List<Product> retrieveAll(EntityManager em) {
        Query query = em.createQuery("SELECT p FROM Product p", Product.class);
        List<Product> products = query.getResultList();
        return products;
    }

    public static void update(EntityManager em, UserTransaction ut, UUID uuid, String name, float price, int quantity) throws Exception {
        ut.begin();
        Product product = em.find(Product.class, uuid);
        if (!name.equals(product.getName())) product.setName(name);
        if (price != product.getPrice()) product.setPrice(price);
        if (quantity != product.getQuantity_in_stock()) product.setQuantity_in_stock(quantity);
    }

    public static void destroy(EntitityManager em, UserTransaction ut, UUID uuid) throws Exception {
        ut.begin();
        Product product = em.find(Product.class, uuid);
        em.remove(product);
        ut.commit();
    }

    public static void clearData(EntityManager em, UserTrasaction ut) throws Exception {
        ut.begin();
        Query deleteStatement = em.createQuery("DELETE FROM Product");
        deleteStatement.executeUpdate();
        ut.commit();
    }
}
