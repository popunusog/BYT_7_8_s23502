package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;

	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		// Tests if the getName method returns the correct name for each bank.
		assertEquals("SweBank", SweBank.getName());
		assertEquals("Nordea", Nordea.getName());
		assertEquals("DanskeBank", DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
		// Tests if the getCurrency method returns the correct currency for each bank.
		assertEquals(SEK, SweBank.getCurrency());
		assertEquals(SEK, Nordea.getCurrency());
		assertEquals(DKK, DanskeBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		// Tests the ability to open a new account and if it starts with a zero balance.
		SweBank.openAccount("Alice");
		assertTrue(SweBank.getBalance("Alice") == 0);
	}

	@Test(expected = AccountExistsException.class)
	public void testOpenAccountExists() throws AccountExistsException {
		// Tests that an exception is thrown when trying to open an account that already exists.
		SweBank.openAccount("Ulrika");
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		// Tests if depositing money correctly increases the account balance.
		SweBank.deposit("Ulrika", new Money(5000, SEK));
		assertEquals(5000, SweBank.getBalance("Ulrika").intValue());
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		// Tests if withdrawing money correctly decreases the account balance.
		SweBank.deposit("Ulrika", new Money(5000, SEK));
		SweBank.withdraw("Ulrika", new Money(2000, SEK));
		assertEquals(3000, SweBank.getBalance("Ulrika").intValue());
	}

	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		// Tests if the getBalance method returns the correct balance.
		SweBank.deposit("Ulrika", new Money(5000, SEK));
		assertEquals(5000, SweBank.getBalance("Ulrika").intValue());
	}

	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		// Tests if transferring money between accounts correctly updates both accounts' balances.
		SweBank.deposit("Ulrika", new Money(10000, SEK));
		SweBank.transfer("Ulrika", "Bob", new Money(5000, SEK));
		assertEquals("Expected balance in Ulrika's account after transfer", 5000, SweBank.getBalance("Ulrika").intValue());
		assertEquals("Expected balance in Bob's account after transfer", 5000, SweBank.getBalance("Bob").intValue());
	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		// Tests if a timed payment is correctly deducted from an account after a tick.
		SweBank.deposit("Ulrika", new Money(10000, SEK)); // Ensuring sufficient balance
		SweBank.addTimedPayment("Ulrika", "rent", 30, 0, new Money(6000, SEK), Nordea, "Bob");
		SweBank.tick();
		assertEquals("Expected balance after timed payment", 4000, SweBank.getBalance("Ulrika").intValue());
	}
}
