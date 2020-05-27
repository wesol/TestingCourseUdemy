package pl.mw.testing.cart;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import pl.mw.testing.order.Order;
import pl.mw.testing.order.OrderStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Test
    void processCartShouldSendToPrepare() {
        // given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCard(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        given(cartHandler.canHandleCard(cart)).willReturn(true);

        // when
        cartService.processCart(cart);

        // then
        verify(cartHandler).sendToPrepare(cart);
        verify(cartHandler, times(1)).sendToPrepare(cart);
        verify(cartHandler, atLeastOnce()).sendToPrepare(cart);
        verify(cartHandler, atMostOnce()).sendToPrepare(cart);

        InOrder inOrder = inOrder(cartHandler);
        inOrder.verify(cartHandler).canHandleCard(cart);
        inOrder.verify(cartHandler).sendToPrepare(cart);

        assertThat(cart.getOrders(), hasSize(1));
        assertThat(cart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
    }


    @Test
    void processCartShouldNotSendToPrepare() {
        // given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCard(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        given(cartHandler.canHandleCard(cart)).willReturn(false);

        // when
        cartService.processCart(cart);

        // then
        verify(cartHandler, never()).sendToPrepare(cart);
        then(cartHandler).should(never()).sendToPrepare(cart); // previous alternative

        assertThat(cart.getOrders(), hasSize(1));
        assertThat(cart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.REJECTED));
    }

    @Test
    void processCartShouldReturnMultipleValues() {
        // given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCard(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        given(cartHandler.canHandleCard(cart)).willReturn(false, true, false);

        // then
        assertThat(cartHandler.canHandleCard(cart), equalTo(false));
        assertThat(cartHandler.canHandleCard(cart), equalTo(true));
        assertThat(cartHandler.canHandleCard(cart), equalTo(false));
    }

    @Test
    void processCartShouldSendToPrepareWithLambdas() {
        // given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCard(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        given(cartHandler.canHandleCard(argThat(c -> c.getOrders().size() > 0))).willReturn(true); // true zostaje zwrócone tylko jeśli warunek z lambdy jest spełniony

        // when
        cartService.processCart(cart);

        // then
        then(cartHandler).should().sendToPrepare(cart);
        assertThat(cart.getOrders(), hasSize(1));
        assertThat(cart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
    }


    @Test
    void canHandleCartShouldThrowException() {
        // given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCard(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        given(cartHandler.canHandleCard(cart)).willThrow(IllegalStateException.class);

        // when
        // then
        assertThrows(IllegalStateException.class, () -> cartService.processCart(cart));
    }


    @Test
    void processCartShouldSendToPrepareWithArgumentCaptor() {
        // given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCard(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        ArgumentCaptor<Cart> argumentCaptor = ArgumentCaptor.forClass(Cart.class);

        given(cartHandler.canHandleCard(cart)).willReturn(true);

        // when
        cartService.processCart(cart);

        // then
        then(cartHandler).should().sendToPrepare(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getOrders(), hasSize(1));

        assertThat(cart.getOrders(), hasSize(1));
        assertThat(cart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
    }
}