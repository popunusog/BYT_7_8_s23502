package b_Money;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, EUR;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		// Test to ensure the correct name is returned for each currency.
		assertEquals("SEK", SEK.getName());
		assertEquals("DKK", DKK.getName());
		assertEquals("EUR", EUR.getName());
	}

	@Test
	public void testGetRate() {
		// Test to verify that the correct exchange rate is returned for each currency.
		assertEquals(0.15, SEK.getRate(), 0.001);
		assertEquals(0.20, DKK.getRate(), 0.001);
		assertEquals(1.5, EUR.getRate(), 0.001);
	}

	@Test
	public void testSetRate() {
		// Test to verify that the exchange rate can be changed and retrieved correctly.
		SEK.setRate(0.16);
		assertEquals(0.16, SEK.getRate(), 0.001);

		EUR.setRate(1.6);
		assertEquals(1.6, EUR.getRate(), 0.001);
	}

	@Test
	public void testGlobalValue() {
		// Test to check if the universal value calculation is correct.
		int universalValueSEK = SEK.universalValue(100); // 100 SEK in universal currency
		assertEquals((int)(100 * 0.15), universalValueSEK);

		int universalValueEUR = EUR.universalValue(100); // 100 EUR in universal currency
		assertEquals((int)(100 * 1.5), universalValueEUR);
	}

	@Test
	public void testValueInThisCurrency() {
		// Test to verify conversion of an amount from one currency to another.
		int valueInSEK = SEK.valueInThisCurrency(100, EUR); // 100 EUR in SEK
		assertEquals((int)(100 * 1.5 / 0.15), valueInSEK);

		int valueInEUR = EUR.valueInThisCurrency(100, DKK); // 100 DKK in EUR
		assertEquals((int)(100 * 0.20 / 1.5), valueInEUR);
	}
}
