package pl.mw.testing;

import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.io.IOException;

class OrderBackupTest {

    private static OrderBackup orderBackup;

    @BeforeAll
    static void setup() throws FileNotFoundException {
        orderBackup = new OrderBackup();
        orderBackup.createFile();
    }

    @BeforeEach
    void appendAtTheStartOfLine() throws IOException {
        orderBackup.getWriter().append("NewOrder: ");
    }

    @AfterEach
    void appendAtTheEndOfLine() throws IOException {
        orderBackup.getWriter().append(" backed up.");
    }

    @AfterAll
    static void tearDown() throws IOException {
        orderBackup.closeFile();
    }


    @Test
    void backUpOrderWithOneMeal() throws IOException {
        // given
        Meal meal = new Meal(7, "Fries");
        Order order = new Order();
        order.addMealToOrder(meal);

        // when
        orderBackup.backupOrder(order);

        // then
        System.out.println("Order: " + order.toString() + " backed up.");
    }
}