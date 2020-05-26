package pl.mw.testing;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderBackupExecutionOrderTest {

    @Test
    void callingBackupWithoutCreatingAFileFirstShouldThrowException() {
        // given
        OrderBackup orderBackup = new OrderBackup();

        // then
        assertThrows(IOException.class, () -> orderBackup.backupOrder(new Order()));
    }
}
