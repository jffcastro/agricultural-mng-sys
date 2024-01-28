package Data_info;

import java.util.Objects;

public class ProductOrder {
    private String productName;

    private double productQuantity;

    private int day;

    private byte feedBack;

    public ProductOrder(String productName, double productQuantity, int day) {
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.day = day;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(double productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public byte getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(byte feedBack) {
        this.feedBack = feedBack;
    }

    @Override
    public boolean equals(Object po1) {
        if (this == po1) return true;
        if (po1 == null || getClass() != po1.getClass()) return false;
        ProductOrder po2 = (ProductOrder) po1;
        return day == po2.day && productName.equals(po2.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, day);
    }
}
