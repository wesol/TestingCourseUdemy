package pl.mw.testing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@ExtendWith(BeforeAfterExtension.class)
class OrderTest {

    private Order order;

    @BeforeEach()
    void initializeOrder() {
        order = new Order();
    }

    @AfterEach
    void cleanUp(){
        order.cancel();
    }

    // test to testing method
    @Test
    void testAssertArrayEquals() {
        // given
        int[] ints1 = {1, 2, 3};
        int[] ints2 = {1, 2, 3};

        // then
        assertArrayEquals(ints1, ints2);
    }

    @Test
    void mealListShouldBeEmptyAfterOrderObjectCreation() {
        // then
        assertThat(order.getMeals(), empty());
    }

    @Test
    void addingMealToOrderShouldIncreaseOrderSize() {
        // given
        Meal meal = new Meal(12, "Kebab");
        Meal meal2 = new Meal(17, "Burger");

        // when
        order.addMealToOrder(meal);
        order.addMealToOrder(meal2);

        // then
        assertThat(order.getMeals(), hasSize(2));
        assertThat(order.getMeals(), contains(meal, meal2));
        assertThat(order.getMeals(), hasItem(meal)); // equals to previous
    }

    @Test
    void removingMealFromOrderShouldDecreaseOrderSize() {
        // given
        Meal meal = new Meal(12, "Kebab");
        Meal meal2 = new Meal(17, "Burger");

        // when
        order.addMealToOrder(meal);
        order.addMealToOrder(meal2);
        order.removeMealFromOrder(meal);

        // then
        assertThat(order.getMeals(), hasSize(1));
        assertThat(order.getMeals(), not(contains(meal)));
        assertThat(order.getMeals(), contains(meal2));
    }


    @Test
    void mealsShouldBeInCorrectOrderAfterAddingThemToOrder() {
        // given
        Meal meal = new Meal(12, "Kebab");
        Meal meal2 = new Meal(17, "Burger");

        // when
        order.addMealToOrder(meal);
        order.addMealToOrder(meal2);

        // then
        assertThat(order.getMeals(), contains(meal, meal2)); //inAnyOrder if it nothing matter
    }

}