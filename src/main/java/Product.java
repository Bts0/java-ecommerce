import java.util.UUID;
import javax.persistence.*;

@Entity @Table(name = "products")
public class Product {
    @Id private String uuid;
    private String name;
    private float price;
    private int quantity;
    private boolean in_stock;

    public Product() {
        String uuid;
        String name;
        float price;
        int quantity;
        boolean in_stock = false;
    }

    public Product(UUID uuid, String name, float price, int q) {
        this.setUuid(uuid);
        this.setName(name);
        this.setPrice(price);
        this.setQuantity(q);
        if(q > 0) {
            this.setIn_stock(true);
        } else if (q == 0) {
            this.setIn_stock(false);
        }
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(UUID u) {
        uuid = u.toString();
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int qis) {
        if (qis == 0) {
            setIn_stock(false);
        }
        else {
            setIn_stock(true);
        }
        quantity = qis;
    }

    public boolean isIn_stock() {
        return in_stock;
    }

    private void setIn_stock(boolean is) {
        in_stock = is;
    }
}
