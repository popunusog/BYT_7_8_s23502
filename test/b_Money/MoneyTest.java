package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		// Tests if the getAmount method returns the correct amount.
		assertEquals(10000, SEK100.getAmount().intValue());
		assertEquals(0, EUR0.getAmount().intValue());
	}

	@Test
	public void testGetCurrency() {
		// Tests if the getCurrency method returns the correct currency.
		assertEquals(SEK, SEK100.getCurrency());
		assertEquals(EUR, EUR0.getCurrency());
	}

	@Test
	public void testToString() {
		// Tests if the toString method correctly represents the money amount and currency.
		assertEquals("100.0 SEK", SEK100.toString());
		assertEquals("0.0 EUR", EUR0.toString());
	}

	@Test
	public void testGlobalValue() {
		// Tests the universalValue method for correct conversion to a "universal" value.
		assertEquals((int)(10000 * 0.15), SEK100.universalValue().intValue());
		assertEquals((int)(0 * 1.5), EUR0.universalValue().intValue());
	}

	@Test
	public void testEqualsMoney() {
		// Tests equality between two Money instances.
		assertTrue(SEK200.equals(new Money(20000, SEK)));
		assertFalse(SEK100.equals(EUR10));
	}

	@Test
	public void testAdd() {
		// Tests the addition of two Money instances, including currency conversion.
		Money sum = SEK100.add(EUR10);
		assertEquals(SEK.getName(), sum.getCurrency().getName());
		int expectedAmount = SEK100.getAmount() + (int)(EUR10.universalValue() / SEK.getRate());
		assertEquals(expectedAmount, sum.getAmount().intValue());
	}

	@Test
	public void testSub() {
		// Tests the subtraction of two Money instances, including currency conversion.
		Money diff = SEK200.sub(EUR10);
		assertEquals(SEK.getName(), diff.getCurrency().getName());
		int expectedAmount = SEK200.getAmount() - (int)(EUR10.universalValue() / SEK.getRate());
		assertEquals(expectedAmount, diff.getAmount().intValue());
	}

	@Test
	public void testIsZero() {
		// Tests if isZero correctly identifies zero and non-zero amounts.
		assertTrue(EUR0.isZero());
		assertFalse(SEK100.isZero());
	}

	@Test
	public void testNegate() {
		// Tests if negate correctly negates the amount.
		assertEquals(-10000, SEK100.negate().getAmount().intValue());
		assertEquals(0, EUR0.negate().getAmount().intValue());
	}

	@Test
	public void testCompareTo() {
		// Tests the compareTo method for comparing two Money instances.
		assertEquals(0, SEK100.compareTo(new Money(10000, SEK)));
		assertTrue(SEK200.compareTo(EUR10) > 0);
		assertTrue(SEKn100.compareTo(SEK100) < 0);
	}
}