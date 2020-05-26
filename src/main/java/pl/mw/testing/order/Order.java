package pl.mw.testing.order;

import pl.mw.testing.Meal;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private final List<Meal> meals = new ArrayList<>();

    public void addMealToOrder(Meal meal) {
        this.meals.add(meal);
    }

    void removeMealFromOrder(Meal meal) {
        this.meals.remove(meal);
    }

    public List<Meal> getMeals() {
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
