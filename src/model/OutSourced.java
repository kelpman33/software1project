package model;
/**
 * OutSourced subclass
 */
/**
 * @author James Badke
 */
public class OutSourced extends Part {

    private String companyName;

    /**
     * constructor for Outsourced part
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);

        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        setCompanyName(companyName);

    }

    /**
     * Getters and setters
     * @return
     */
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String name) {
        this.companyName = name;
    }
}
