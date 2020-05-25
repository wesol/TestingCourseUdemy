package pl.mw.testing;

import java.util.Objects;

public class Meal {

    private int price;
    private String name;

    public Meal(int price) {
        this.price = price;
    }

    public Meal(int price, String name) {
        this.price = price;
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getDiscountedPrice(int discount) {

        if (discount > this.price){
            throw new IllegalArgumentException("Discount cannot be higher than the price!");
        }

        return this.price - discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meal)) return false;
        Meal meal = (Meal) o;
        return price == meal.price &&
                Objects.equals(name, meal.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, name);
    }
}
