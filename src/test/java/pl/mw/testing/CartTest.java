package pl.mw.testing;

import org.junit.jupiter.api.Test;
import pl.mw.testing.order.Order;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTimeout;

class CartTest {

    @Test
    void simulateLargeOrder() {
        // given
        Cart cart = new Cart();

        // when
        // then
        assertTimeout(Duration.ofMillis(100), cart::simulateLargeOrder);
    }

    @Test
    void cartShouldNotBeEmptyAfterAddedOrderToTheCart() {
        // given
        Order order = new Order();
        Cart cart = new Cart();

        // when
        cart.addOrderToCard(order);

        // then
        assertThat(cart.getOrders(), anyOf(
                notNullValue(),
                hasSize(1),
                is(not(empty())),
                is(not(emptyCollectionOf(Order.class)))
        ));

        assertAll( //alternatywa dla allOf z hamcrest
                   () -> assertThat(cart.getOrders(), notNullValue()),
                   () -> assertThat(cart.getOrders().get(0).getMeals(), empty())
        );
    }
}