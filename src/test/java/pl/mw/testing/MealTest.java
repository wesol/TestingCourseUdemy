package pl.mw.testing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MealTest {

    @Test
    void shouldReturnDiscountedPrice() {
        // given
        Meal meal = new Meal(35);

        // when
        int discountedPrice = meal.getDiscountedPrice(5);

        // then
        assertEquals(30, discountedPrice);
    }


    @Test
    void referenceShouldBeTheSame() {
        // given
        Meal meal = new Meal(21);
        Meal meal1 = meal;

        // then
        assertSame(meal, meal1);
    }


    @Test
    void referenceShouldBeNotTheSame() {
        // given
        Meal meal = new Meal(21);
        Meal meal1 = new Meal(21);

        // then
        assertNotSame(meal, meal1);
    }

    @Test
    void twoMealsShouldBeEqualWhenNameAndPriceAreTheSame() {
        // given
        Meal meal = new Meal(10, "pizza");
        Meal meal1 = new Meal(10, "pizza");

        // then
        assertEquals(meal, meal1);
    }
}