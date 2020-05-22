package pl.mw.testing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void accountShouldBeNotActiveAfterCreation() {
        //given
        Account account = new Account();

        //then
        assertFalse(account.isActive());
    }

    @Test
    void accountShouldBeActiveAfterActivation() {
        //given
        Account account = new Account();

        //when
        account.activate();

        //then
        assertTrue(account.isActive());
    }

    @Test
    void newlyCreatedAccountShouldNotHaveDefaultDeliveryAddressSet() {
        // given
        Account account = new Account();

        // when
        Address address = account.getDefaultDeliveryAddress();

        // then
        assertNull(address);
    }


    @Test
    void defaultDeliveryAddressShouldBeNotNullAfterSet() {
        // given
        Address address = new Address("Street Name", "Street number");
        Account account = new Account();
        account.setDefaultDeliveryAddress(new Address("Street Name", "Street number"));

        // when
        Address addressGot = account.getDefaultDeliveryAddress();

        // then
        assertNotNull(addressGot);
    }
}