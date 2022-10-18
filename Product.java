import java.util.UUID;
import javax.persistence.*;

@Entity @Table(name = "products")
public class Product {
    @Id private UUID uuid;
    private String name;
    private float price;
    private int quantity_in_stock;
    private boolean in_stock;

    public Product(UUID uuid, String name, float price, int quantity) {
        this.setUuid(uuid);
        this.setName(name);
        this.setQuantity_in_stock(quantity);
        if(quantity > 0) {
            this.setIn_stock(true);
        } else if (quantity == 0) {
            this.setIn_stock(false);
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID u) {
        uuid = u;
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float p) {
        price = p;
    }

    public int getQuantity_in_stock() {
        return quantity_in_stock;
    }

    public void setQuantity_in_stock(int qis) {
        if (qis == 0) {
            setIn_stock(false);
        }
        else {
            setIn_stock(true);
        }
        quantity_in_stock = qis;
    }

    public boolean isIn_stock() {
        return in_stock;
    }

    private void setIn_stock(boolean is) {
        in_stock = is;
    }

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
