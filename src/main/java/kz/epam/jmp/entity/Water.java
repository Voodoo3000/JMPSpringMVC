package kz.epam.jmp.entity;

public class Water extends Entity {
    private String type;
    private int pricePerLiter;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPricePerLiter() {
        return pricePerLiter;
    }

    public void setPricePerLiter(int pricePerLiter) {
        this.pricePerLiter = pricePerLiter;
    }

}
