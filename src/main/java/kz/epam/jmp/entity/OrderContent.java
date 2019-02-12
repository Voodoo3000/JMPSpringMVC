package kz.epam.jmp.entity;

public class OrderContent extends Entity {
    private Water water;
    private BottleSize bottleSize;
    private int quantity;
    private int customerOrderId;

    public Water getWater() {
        return water;
    }

    public void setWater(Water water) {
        this.water = water;
    }

    public BottleSize getBottleSize() {
        return bottleSize;
    }

    public void setBottleSize(BottleSize bottleSize) {
        this.bottleSize = bottleSize;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCustomerOrderId() {
        return customerOrderId;
    }

    public void setCustomerOrderId(int customerOrderId) {
        this.customerOrderId = customerOrderId;
    }

    @Override
    public String toString() {
        return "OrderContent{" +
                "water=" + water +
                ", bottleSize=" + bottleSize +
                ", quantity=" + quantity +
                ", customerOrderId=" + customerOrderId +
                '}';
    }
}
