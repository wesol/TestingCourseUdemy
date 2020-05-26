package pl.mw.testing.cart;

public interface CartHandler {

    boolean canHandleCard(Cart cart);

    void sendToPrepare(Cart cart);
}
