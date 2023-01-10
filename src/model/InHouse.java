package model;
/**
 * InHouse subclass
 */
/**
 * @author James Badke
 */
public class InHouse extends Part {

    private int machineID;

    /**
     * Constructor for InHouse part
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineID
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);

        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        setMachineID(machineID);

    }

    /**
     * Getters and setters
     */
    public int getMachineID() {
        return machineID;
    }

    public void setMachineID(int id) {
        this.machineID = id;
    }

}