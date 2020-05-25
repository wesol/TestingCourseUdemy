package pl.mw.testing;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class AddressTest {

    @ParameterizedTest
    @CsvSource({"Fabryczna, 10", "Armii Krajowej, 57/3", "'Romka, Tomka, Atomka', 10"})
    void givenAddressesShouldNotBeEmptyAndHaveProperNames(String street, String streetNumber) {
        assertThat(street, notNullValue());
        assertThat(street.length(), greaterThanOrEqualTo(3));
        assertThat(streetNumber, notNullValue());
        assertThat(streetNumber.length(), lessThanOrEqualTo(8));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/addresses.csv")
    void givenFromFileAddressesShouldNotBeEmptyAndHaveProperNames(String street, String streetNumber) {
        assertThat(street, notNullValue());
        assertThat(street.length(), greaterThanOrEqualTo(3));
        assertThat(streetNumber, notNullValue());
        assertThat(streetNumber.length(), lessThanOrEqualTo(8));
    }


}