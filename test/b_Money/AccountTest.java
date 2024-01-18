package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea, DanskeBank, SweBank;
	Account testAccount;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		SweBank = new Bank("SweBank", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		Nordea = new Bank("Nordea", SEK);

		SweBank.openAccount("Alice");
		DanskeBank.openAccount("Bob");
		Nordea.openAccount("Charlie");

		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));
		SweBank.deposit("Alice", new Money(1000000, SEK));
	}

	@Test
	public void testAddRemoveTimedPayment() {
		// Tests adding and removing a timed payment.
		String paymentId = "rent";
		testAccount.addTimedPayment(paymentId, 30, 10, new Money(5000, SEK), DanskeBank, "Bob");
		assertTrue("Timed payment should be added", testAccount.timedPaymentExists(paymentId));

		testAccount.removeTimedPayment(paymentId);
		assertFalse("Timed payment should be removed", testAccount.timedPaymentExists(paymentId));
	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		// Tests the deduction of a timed payment after a tick.
		String paymentId = "subscription";
		testAccount.addTimedPayment(paymentId, 30, 0, new Money(1000, SEK), Nordea, "Charlie");
		testAccount.tick();
		assertEquals("Timed payment should be deducted", 9999000, testAccount.getBalance().getAmount().intValue());
	}

	@Test
	public void testAddWithdraw() {
		// Tests the functionality of depositing and withdrawing money.
		Money depositAmount = new Money(5000, SEK);
		Money withdrawAmount = new Money(3000, SEK);

		testAccount.deposit(depositAmount);
		assertEquals("Balance should increase after deposit", 10005000, testAccount.getBalance().getAmount().intValue());

		testAccount.withdraw(withdrawAmount);
		assertEquals("Balance should decrease after withdrawal", 10002000, testAccount.getBalance().getAmount().intValue());
	}

	@Test
	public void testGetBalance() {
		// Tests if the getBalance method returns the correct balance.
		assertEquals("Balance should be correctly reported", 10000000, testAccount.getBalance().getAmount().intValue());
	}
}
