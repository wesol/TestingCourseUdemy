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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.*;

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

    @Test
    void shouldDoNothingWhenProcessCart() {
        // given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCard(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        given(cartHandler.canHandleCard(cart)).willReturn(true);

        // doNothing().when(cartHandler).sendToPrepare(cart);
        willDoNothing().given(cartHandler).sendToPrepare(cart); //alternative to previous BDD friendly
        // willDoNothing().willThrow(IllegalStateException.class).given(cartHandler).sendToPrepare(cart); //if calling second time throw exc.

        // when
        cartService.processCart(cart);

        // then
        then(cartHandler).should().sendToPrepare(cart);

        assertThat(cart.getOrders(), hasSize(1));
        assertThat(cart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
    }

    @Test
    void shouldAnswerWhenProcessCart() {
        // given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCard(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        /* Four alternative beneath*/
        doAnswer(invocationOnMock -> {
            Cart argumentCart = invocationOnMock.getArgument(0);
            argumentCart.clearCart();
            return true;
        }).when(cartHandler).canHandleCard(cart);

        // when(cartHandler.canHandleCard(cart)).then(invocation ->  {
        //     Cart argumentCart = invocation.getArgument(0);
        //     argumentCart.clearCart();
        //     return true;
        // });
        //
        // willAnswer(invocation -> {
        //     Cart argumentCart = invocation.getArgument(0);
        //     argumentCart.clearCart();
        //     return true;
        // }).given(cartHandler).canHandleCard(cart);
        //
        // given(cartHandler.canHandleCard(cart)).will(invocation -> {
        //     Cart argumentCart = invocation.getArgument(0);
        //     argumentCart.clearCart();
        //     return true;
        // });

        // when
        cartService.processCart(cart);

        // then
        then(cartHandler).should().sendToPrepare(cart);
        assertThat(cart.getOrders(), hasSize(0));
    }

    @Test
    void deliveryShouldBeFreeIfIsMoreThan2Orders() {
        // given
        Cart cart = new Cart();
        cart.addOrderToCard(new Order());
        cart.addOrderToCard(new Order());
        cart.addOrderToCard(new Order());

        given(cartHandler.isDeliveryFree(cart)).willCallRealMethod(); // call to the real method - not recommended

        // when
        boolean isDeliveryFree = cartHandler.isDeliveryFree(cart);

        // then
        assertTrue(isDeliveryFree);
    }
}