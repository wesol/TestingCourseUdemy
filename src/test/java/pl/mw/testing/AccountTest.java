package pl.mw.testing;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

class AccountTest {

    @Test
    void accountShouldBeNotActiveAfterCreation() {
        //given
        Account newAccount = new Account();

        //then
        assertFalse(newAccount.isActive());

        assertThat(newAccount.isActive(), equalTo(false)); //alternative version of previous hamcrest
        assertThat(newAccount.isActive(), is(false)); //alternative version of previous hamcrest

        Assertions.assertThat(newAccount.isActive()).isFalse(); //alternative version of previous assert-j
    }

    @Test
    void accountShouldBeActiveAfterActivation() {
        //given
        Account account = new Account();

        //when
        account.activate();

        //then
        assertTrue(account.isActive());
        assertThat(account.isActive(), is(true));  //alternative version of previous hamcrest
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
        assertThat(addressGot, is(notNullValue()));  // alternative version of previous
        Assertions.assertThat(addressGot).isNotNull(); //alternative version of previous assert-j
    }

    @RepeatedTest(2)
    void newAccountWithNoNullAddressShouldBeActive() {
        // given
        Address address = new Address("Tamka", "10a");

        // when
        Account account = new Account(address);

        // then
        assumingThat(address != null, () -> {
            assertTrue(account.isActive());
        });
    }

    @Test
    void invalidEmailShouldThrowException() {
        // given
        Account account = new Account();

        // when
        // then
        assertThrows(IllegalArgumentException.class, () -> account.setEmail("aaaa"));
    }
}