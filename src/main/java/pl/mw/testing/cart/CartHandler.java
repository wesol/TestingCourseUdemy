package pl.mw.testing.cart;

public interface CartHandler {

    boolean canHandleCard(Cart cart);

    void sendToPrepare(Cart cart);

    default boolean isDeliveryFree(Cart cart) {
        return cart.getOrders().size() > 2;
    }
}
