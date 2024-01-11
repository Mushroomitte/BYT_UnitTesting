package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

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
		// Check if getName returns the correct name for different Bank instances.
        assertEquals("SweBank", SweBank.getName());
        assertEquals("Nordea", Nordea.getName());
        assertEquals("DanskeBank", DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
		// Check if getCurrency returns the correct currency for different Bank instances.
        assertEquals(SEK, SweBank.getCurrency());
        assertEquals(SEK, Nordea.getCurrency());
        assertEquals(DKK, DanskeBank.getCurrency());
	}
	
	
	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		//Ensure that opening an account works as expected
        SweBank.openAccount("Alice");
        assertTrue(SweBank.getBalance("Alice") == 0);

        assertThrows(AccountExistsException.class, () -> SweBank.openAccount("Alice"));

        DanskeBank.openAccount("Yohan");
        assertTrue(DanskeBank.getBalance("Yohan") == 0);
	}

	
	@Test
	 public void testDeposit() throws AccountDoesNotExistException {
		 //Verify that deposits are handled correctly
        SweBank.deposit("Bob", new Money(100, SEK));
        assertEquals(Integer.valueOf(100), SweBank.getBalance("Bob"));

        try {
            SweBank.deposit("NonExistentAccount", new Money(100, SEK));
            fail("Expected AccountDoesNotExistException, but no exception was thrown.");
        } catch (AccountDoesNotExistException ignored) {
        }
    }

	
	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		//Verify that withdraws are handled correctly
        SweBank.deposit("Ulrika", new Money(100, SEK));
        SweBank.withdraw("Ulrika", new Money(50, SEK));
        assertEquals(Integer.valueOf(50), SweBank.getBalance("Ulrika"));

        try {
            SweBank.withdraw("Charlie", new Money(75, SEK));
            fail("Expected AccountDoesNotExistException, but no exception was thrown.");
        } catch (AccountDoesNotExistException ignored) {
        }

        try {
            SweBank.withdraw("NonExistentAccount", new Money(38, SEK));
            fail("Expected AccountDoesNotExistException, but no exception was thrown.");
        } catch (AccountDoesNotExistException ignored) {
        }
    }

	
	@Test
	 public void testGetBalance() throws AccountDoesNotExistException {
		 //Verify that getBalance() shows and processes balance correctly
        SweBank.deposit("Bob", new Money(100, SEK));
        int balanceBobSwebank = SweBank.getBalance("Bob");
        int balanceBobNordea = Nordea.getBalance("Bob");
        assertEquals(100, balanceBobSwebank);
        assertEquals(0, balanceBobNordea);

        try {
            int nonExistentBalance = SweBank.getBalance("NonExistentAccount");
            fail("Expected AccountDoesNotExistException, but no exception was thrown.");
        } catch (AccountDoesNotExistException ignored) {
        }
    }
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		//Check if transfers between accounts and banks are processed correctly
        SweBank.deposit("Bob", new Money(150, SEK));
        SweBank.transfer("Bob", "Ulrika", new Money(75, SEK));
        assertEquals(new Integer(75), SweBank.getBalance("Bob"));
        assertEquals(new Integer(75), SweBank.getBalance("Ulrika"));

        try {
            SweBank.transfer("Alice", Nordea, "Charlie", new Money(60, SEK));
            fail("Expected AccountDoesNotExistException, but no exception was thrown.");
        } catch (AccountDoesNotExistException ignored) {
        }

        try {
            SweBank.transfer("NonExistentAccount", Nordea, "Charlie", new Money(30, SEK));
            fail("Expected AccountDoesNotExistException, but no exception was thrown.");
        } catch (AccountDoesNotExistException ignored) {
        }
    }
	
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		//Check if timed payment is processed correctly 
        SweBank.deposit("Bob", new Money(100, SEK));

        SweBank.addTimedPayment("Bob", "payment1", 2, 0, new Money(20, SEK), SweBank, "Ulrika");

        for (int i = 0; i < 10; i++) {
            SweBank.tick();
        }

        assertEquals(new Integer(20), SweBank.getBalance("Bob"));
        assertEquals(new Integer(80), SweBank.getBalance("Ulrika"));
    }
	}

