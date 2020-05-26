package pl.mw.testing.account;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

class AccountServiceWithStubTest {

    @Test
    void name() {
        // given
        AccountRepositoryStub accountRepositoryStub = new AccountRepositoryStub();
        AccountService accountService = new AccountService(accountRepositoryStub);

        // when
        List<Account> accountList = accountService.getAllActiveAccounts();

        // then
        assertThat(accountList, hasSize(2));
    }
}