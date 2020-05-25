package pl.mw.testing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
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

    @Test
    void shouldThrowExceptionIfDiscountIsHigherThanThePrice() {
        // given
        Meal meal = new Meal(10, "pizza");

        // when
        // then
        assertThrows(IllegalArgumentException.class, () -> meal.getDiscountedPrice(40));
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 10, 15, 18})
    void mealPricesShouldBeLowerThan20(int price) {  // just for showing
        assertThat(price, lessThan(20));
    }

    @ParameterizedTest
    @MethodSource("createMealWithNameAndPrice")
    void burgersShouldHaveCorrectNameAndPrice(String name, int price) {
        assertThat(name, containsString("burger"));
        assertThat(price, greaterThanOrEqualTo(10));
    }

    @ParameterizedTest
    @MethodSource("createCakeNames")
    void cakeNamesShouldEndWithCake(String name) {
        assertThat(name, endsWith("cake"));
    }

    private static Stream<Arguments> createMealWithNameAndPrice() {
        return Stream.of(
                Arguments.of("Hamburger", 10),
                Arguments.of("Cheesburger", 12)
        );
    }

    private static Stream<String> createCakeNames() {
        List<String> cakeNames = Arrays.asList("Cheesecake", "Fruitcake", "Cupcake");
        return cakeNames.stream();
    }
}