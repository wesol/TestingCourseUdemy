package pl.mw.testing.cart;

import pl.mw.testing.order.OrderStatus;

public class CartService {

    private final CartHandler cartHandler;

    public CartService(CartHandler cartHandler) {
        this.cartHandler = cartHandler;
    }

    void processCart(Cart cart) {

        if (cartHandler.canHandleCard(cart)) {
            cartHandler.sendToPrepare(cart);
            cart.getOrders().forEach(order -> order.changeOrderStatus(OrderStatus.PREPARING));
        } else {
            cart.getOrders().forEach(order -> order.changeOrderStatus(OrderStatus.REJECTED));
        }

    }
}
