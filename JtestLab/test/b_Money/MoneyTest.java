package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() {
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
		setUp();
		assertEquals(10000, SEK100.getAmount());
	}

	@Test
	public void testGetCurrency() {
		setUp();
		assertEquals(SEK, SEK100.getCurrency());
	}

	@Test
	public void testToString() {
		setUp();
		assertEquals("10000 SEK", SEK100.toString());
	}

	@Test
	//multiply 10000 by rate of 0.15
	//multiply by 100 to move decimal place
	//cast to int for integer comparison
	public void testUniversalValue() {
		assertEquals((int)(100 * 0.15 * 100.0), SEK100.universalValue());
	}

	@Test
	public void testEqualsMoney() {
		setUp();
		assertEquals(SEK0.universalValue() == EUR0.universalValue(),SEK0.equals(EUR0));
		assertEquals(SEK100.universalValue() == EUR0.universalValue(),SEK100.equals(EUR0));
	}

	@Test
	public void testAdd() {
		setUp();
		int value = (int)((SEK100.universalValue() + EUR10.universalValue())/0.15);
		Money test = new Money(value, SEK);
		assertEquals(test.getAmount(), SEK100.add(EUR10).getAmount());
	}

	@Test
	public void testSub() {
		setUp();
		int value = (int)((SEK100.universalValue() - EUR10.universalValue())/0.15);
		Money test = new Money(value, SEK);
		assertEquals(test.getAmount(), SEK100.sub(EUR10).getAmount());
	}

	@Test
	public void testIsZero() {
		setUp();
		assertEquals(true, SEK0.isZero());
	}

	@Test
	public void testNegate() {
		setUp();
		assertEquals(-10000, SEK100.negate().getAmount());
	}

	@Test
	public void testCompareTo() {
		setUp();
		assertEquals(SEK100.universalValue() - EUR10.universalValue(),SEK100.compareTo(EUR10));
	}
}
