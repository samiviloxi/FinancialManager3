package orm;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Uliana on 20.07.2016.
 */
public class Category  {
    private String name;
    private int cId;

    public Category(String name) {
        this.name=name;
        this.cId=-1;
    }
 public Category(String name, int cId) {
 this(name);
 this.cId=cId;
 }

    public String getName() {
        return name;
    }

    public int getcId() {
        return cId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String toString() {return name;}
}
