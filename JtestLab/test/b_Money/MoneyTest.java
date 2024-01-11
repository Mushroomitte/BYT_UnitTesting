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
		// Check if getAmount() returns the correct amount compared to int value
		assertEquals(Integer.valueOf(10000), SEK100.getAmount());
		assertEquals(Integer.valueOf(1000), EUR10.getAmount());
		assertEquals(Integer.valueOf(20000), SEK200.getAmount());
		assertEquals(Integer.valueOf(2000), EUR20.getAmount());
		assertEquals(Integer.valueOf(0), SEK0.getAmount());
		assertEquals(Integer.valueOf(0), EUR0.getAmount());
		assertEquals(Integer.valueOf(-10000), SEKn100.getAmount());
    }
	

	@Test
	public void testGetCurrency() {
		// Check if getCurrency returns the correct currency for different Money instances.
		assertEquals(SEK, SEK100.getCurrency());
		assertEquals(EUR, EUR10.getCurrency());
		assertEquals(SEK, SEK200.getCurrency());
		assertEquals(EUR, EUR20.getCurrency());
		assertEquals(SEK, SEK0.getCurrency());
		assertEquals(EUR, EUR0.getCurrency());
		assertEquals(SEK, SEKn100.getCurrency());
	}

	@Test
	public void testToString() {
		// Check if toString returns the correct string representation for different Money instances.
		assertEquals("100.00 SEK", SEK100.toString());
		assertEquals("10.00 EUR", EUR10.toString());
		assertEquals("200.00 SEK", SEK200.toString());
		assertEquals("20.00 EUR", EUR20.toString());
		assertEquals("0.00 SEK", SEK0.toString());
		assertEquals("0.00 EUR", EUR0.toString());
		assertEquals("-100.00 SEK", SEKn100.toString());
	}

	@Test
	public void testGlobalValue() {
		// Check if globalValue returns the correct value for different Money instances.
        assertEquals(1500, EUR10.universalValue().intValue());
        assertEquals(0, SEK0.universalValue().intValue());
        assertEquals(-1500, SEKn100.universalValue().intValue());
	}

	@Test
	public void testEqualsMoney() {
		//Validate the equality check for Money objects.
		Money testSek = new Money(10000, SEK);
		Money testEur = new Money(1000, EUR);
		assertTrue(SEK100.equals(testSek));
		assertTrue(EUR10.equals(testEur));
		assertFalse(SEK100.equals(EUR10));
	}

	@Test
	public void testAdd() {
		// Check if add returns the correct Money instance after adding two Money instances.
		Money SEK300 = SEK100.add(SEK200);
		Money EUR30 = EUR10.add(EUR20);
		assertEquals("300.00 SEK", SEK300.toString());
		assertEquals("30.00 EUR", EUR30.toString());
	}

	@Test
	public void testSub() {
		// Check if sub returns the correct Money instance after subtracting two Money instances.
		Money SEK100Minus200 = SEK100.sub(SEK200);
		Money EUR20Minus10 = EUR20.sub(EUR10);
		assertEquals("-100.00 SEK", SEK100Minus200.toString());
		assertEquals("10.00 EUR", EUR20Minus10.toString());
	}

	@Test
	public void testIsZero() {
		// Check if isZero returns the correct result for different Money instances.
        assertFalse(SEK100.isZero());
        assertTrue(SEK0.isZero());
	}

	@Test
	public void testNegate() {
		// Check if negate returns the correct Money instance with negated amount.
        Money result = SEK100.negate();
        assertEquals(-10000, result.getAmount().intValue());
        assertEquals(SEK, result.getCurrency());
	}

	@Test
	public void testCompareTo() {
		// Check if compareTo returns the correct result when comparing two Money instances.
        assertFalse(SEK100.compareTo(EUR10) > 0);
        assertTrue(SEK100.compareTo(SEK200) < 0);
        assertTrue(SEK100.compareTo(SEK100) == 0);
	}
}
