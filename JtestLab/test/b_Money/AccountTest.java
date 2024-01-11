package b_Money;	

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}

	//Check if the timed payment is added and removed properly
	@Test
	public void testAddRemoveTimedPayment() {
		assertFalse(testAccount.timedPaymentExists("payment"));
		testAccount.addTimedPayment("payment", 6, 3, new Money(10000, SEK), SweBank, "Alice");
		assertTrue(testAccount.timedPaymentExists("payment"));
		testAccount.removeTimedPayment("payment");
		assertFalse(testAccount.timedPaymentExists("payment"));
	}

	//Verify that the tick method advances timed payments as expected.
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {

		testAccount.addTimedPayment("payment", 3, 1, new Money(500, SEK), SweBank, "Alice");


		testAccount.tick();
		testAccount.tick();
		testAccount.tick();
		testAccount.tick();
		testAccount.tick();
		testAccount.tick();

		assertEquals(Integer.valueOf(9999000), testAccount.getBalance());
		assertEquals(Integer.valueOf(1001000), SweBank.getBalance("Alice"));

		testAccount.removeTimedPayment("payment");

		testAccount.tick();
		testAccount.tick();
		testAccount.tick();
		testAccount.tick();
		testAccount.tick();
		testAccount.tick();

		assertEquals(Integer.valueOf(9999000), testAccount.getBalance());
		assertEquals(Integer.valueOf(1001000), SweBank.getBalance("Alice"));
	}

	
	//Ensure that the withdraw method handles operations correctly.
	@Test
	public void testAddWithdraw() {
		testAccount.deposit(new Money(9500, SEK));
		assertEquals(Integer.valueOf(10009500), testAccount.getBalance());
		testAccount.withdraw(new Money(300, SEK));
		assertEquals(Integer.valueOf(10009200), testAccount.getBalance());
	}
	
	

	@Test
	public void testGetBalance() {
		assertEquals(Integer.valueOf(10000000), testAccount.getBalance());
		testAccount.deposit(new Money(2341000, SEK));
		assertEquals(Integer.valueOf(12341000), testAccount.getBalance());
	}
}
