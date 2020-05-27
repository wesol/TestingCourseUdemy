package pl.mw.testing;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import pl.mw.testing.extensions.IAExceptionIgnoreExtension;
import pl.mw.testing.order.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

class MealTest {

    @Test
    void shouldReturnDiscountedPrice() {
        // given
        Meal meal = new Meal(35);

        // when
        int discountedPrice = meal.getDiscountedPrice(5);

        // then
        assertEquals(30, discountedPrice);
        System.out.println("ddddddd");
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

    @ExtendWith(IAExceptionIgnoreExtension.class)
    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, 10})
    void mealPricesShouldBeLowerThan10(int price) {  // just for showing
        if (price > 5) {
            throw new IllegalArgumentException();
        }
        assertThat(price, lessThan(20));
    }

    @Tag("fries")
    @TestFactory
    Collection<DynamicTest> calculateMealPrices() {

        Order order = new Order();
        order.addMealToOrder(new Meal(10, "pizza", 2));
        order.addMealToOrder(new Meal(7, "fries", 4));
        order.addMealToOrder(new Meal(10, "cheeseburger", 1));

        Collection<DynamicTest> dynamicTests = new ArrayList<>();

        List<Meal> meals = order.getMeals();
        for (int i = 0; i < meals.size(); i++) {
            Meal meal = meals.get(i);
            int price = meal.getPrice();
            int quantity = meal.getQuantity();

            Executable executable = () -> {
                assertThat(calculatePrice(price, quantity), lessThan(67));
            };
            String name = "Test name: " + i;

            DynamicTest test = DynamicTest.dynamicTest(name, executable);
            dynamicTests.add(test);
        }

        return dynamicTests;
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

    @Test
    void testMealSumPrice() {
        // given
        Meal mealMock = mock(Meal.class);

        given(mealMock.getPrice()).willReturn(15);
        given(mealMock.getQuantity()).willReturn(3);

        given(mealMock.sumPrice()).willCallRealMethod(); // calling real method - it is not recommended!!

        // when
        int result = mealMock.sumPrice();

        // then
        assertThat(result, equalTo(45));
    }

    @Test
    void testMealSumPriceWithSpy() {
        // given
        Meal mealSpy = spy(new Meal(10, "cheeseburger", 1));

        given(mealSpy.getPrice()).willReturn(15);
        given(mealSpy.getQuantity()).willReturn(3);

        // when
        int result = mealSpy.sumPrice();

        // then
        assertThat(result, equalTo(45));
    }

    private int calculatePrice(int price, int quantity) {
        return price * quantity;
    }
}