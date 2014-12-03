package b_Money;

import static org.junit.Assert.*;
import b_Money.Currency;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp(){
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	//declare c1 as currency with "Euro" as name
	//use assertEquals to compare "Euro" with c1.getName()
	public void testGetName() {
		Currency c1 = new Currency("Euro", 1);
		assertEquals("Euro", c1.getName());
	}
	
	@Test
	//Declare c1 as currency with 1.37 as rate
	//Use assertEquals to compare 1.37 with c1.getRate(), with 0.1 as delta
	public void testGetRate() {
		Currency c1 = new Currency("Euro", 1.37);
		assertEquals(1.37, c1.getRate(), 0.1);
		
	}
	
	@Test
	//Declare c1 as currency with rate of 1.0
	//Change rate to 1.5 using setRate(), assertEquals to compare 1.5 with c1.getRate()
	//0.1 is delta
	public void testSetRate() {
		Currency c1 = new Currency("Euro", 1.0);
		c1.setRate(1.5);
		assertEquals(1.5, c1.getRate(), 0.1);
	}
	
	@Test
	/*
	 *Declare new currency and set values
	 *multiply amount by a rate of 1.5
	 *multiply by 100 hundred to move the decimal point to the end of the number
	 *cast to double to keep precision from double multiplication
	 *then cast to int to compare with return value of C1.universalValue(amoount)
	 */
	public void testUniversalValue() {
		Currency C1 = new Currency("Euro", 1.5);
		int amount = 6;
		assertEquals((int)((double)amount*1.5), C1.universalValue(amount));
	}
	
	@Test
	public void testValueInThisCurrency() {
		/*
		 * declare an amount of 5 get its universal value
		 * then divide by EUR's rate to convert to EUROS
		 * cast to int for an integer comparison
		 * */
		setUp();
		int amount = 5;
		assertEquals((int)(SEK.universalValue(amount)/EUR.getRate()), EUR.valueInThisCurrency(amount, SEK));
		
	}

}
