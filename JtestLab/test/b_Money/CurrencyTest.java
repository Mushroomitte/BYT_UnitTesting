package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		// Check if getName returns the correct name for different Currency instances.
        assertEquals("SEK", SEK.getName());
        assertEquals("DKK", DKK.getName());
	}
	
	@Test
	public void testGetRate() {
		// Check if getRate returns the correct rate for different Currency instances.
        assertEquals(0.15, SEK.getRate(), 0.01);
        assertEquals(0.20, DKK.getRate(), 0.01);
	}
	
	@Test
	public void testSetRate() {
		// Check if setRate updates the rate correctly for different Currency instances.
        SEK.setRate(0.20);
        assertEquals(0.20, SEK.getRate(), 0.01);
	}
	
	@Test
	public void testGlobalValue() {
		// Check if globalValue returns the correct value for different Currency instances.
        assertEquals(1500, SEK.universalValue(10000).intValue());
        assertEquals(2000, DKK.universalValue(10000).intValue());
	}
	
	@Test
	public void testValueInThisCurrency() {
		// Check if valueInThisCurrency returns the correct value for different Currency instances.
		Integer SEKToDKK = DKK.valueInThisCurrency(1000, SEK);
		Integer DKKToEUR = EUR.valueInThisCurrency(750, DKK);
		Integer EURToSEK = SEK.valueInThisCurrency(100, EUR);

		assertEquals((Integer) 750, SEKToDKK);
		assertEquals((Integer) 100, DKKToEUR);
		assertEquals((Integer) 1000, EURToSEK);
	}

}
