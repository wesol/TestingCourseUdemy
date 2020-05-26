package pl.mw.testing;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private final List<Meal> meals = new ArrayList<>();

    void addMealToOrder(Meal meal) {
        this.meals.add(meal);
    }

    void removeMealFromOrder(Meal meal) {
        this.meals.remove(meal);
    }

    List<Meal> getMeals() {
        return meals;
    }

    void cancel() {
        this.meals.clear();
    }

    int totalPrice() {

        int sum = this.meals.stream()
                            .mapToInt(Meal::getPrice)
                            .sum();
        if (sum < 0) {
            throw new IllegalStateException("Limit price exceed!");
        }

        return sum;
    }

    @Override
    public String toString() {
        return "Order{" +
                "meals=" + meals +
                '}';
    }
}
