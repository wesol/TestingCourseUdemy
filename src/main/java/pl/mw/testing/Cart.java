package pl.mw.testing;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private final List<Order> orders = new ArrayList<>();

    public List<Order> getOrders() {
        return orders;
    }

    void addOrderToCard(Order order) {
        this.orders.add(order);
    }

    void clearCard() {
        this.orders.clear();
    }

    void simulateLargeOrder() {
        for (int i = 0; i < 1_000; i++) {
            Meal meal = new Meal(10, "Meal no " + i);
            Order order = new Order();
            order.addMealToOrder(meal);
            addOrderToCard(order);
        }
        System.out.println("Cart size: " + orders.size());
        clearCard();
    }
}
