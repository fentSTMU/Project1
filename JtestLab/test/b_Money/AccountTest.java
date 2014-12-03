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
	Money m;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
		m = new Money(100, SEK);
	}
	
	@Test
	//Test passed
	public void testAddRemoveTimedPayment() {
		try{
		setUp();
		} catch(Exception e){
			System.out.println("Error");
		}
		testAccount.addTimedPayment("Hans", 1, 0, m, SweBank, "Alice");
		try{
		testAccount.tick();
		assertEquals(1000100, SweBank.getBalance("Alice"));
		testAccount.tick();
		assertEquals(1000200, SweBank.getBalance("Alice"));
		testAccount.tick();
		assertEquals(1000300, SweBank.getBalance("Alice"));
		} catch(Exception e){
			System.out.println("Error");
		}
		try{
		testAccount.removeTimedPayment("Hans");
		testAccount.tick();
		testAccount.tick();
		assertEquals(1000300, SweBank.getBalance("Alice"));
		} catch(AccountDoesNotExistException e){
			System.out.println("Error");
		}
	}
	
	@Test
	//Test passed
	public void testTimedPayment() throws AccountDoesNotExistException {
		try{
			setUp();
			} catch(Exception e){
				System.out.println("Error");
			}
			testAccount.addTimedPayment("Hans", 1, 0, m, SweBank, "Alice");
			assertEquals(true, testAccount.timedPaymentExists("Hans"));
			assertEquals(false, testAccount.timedPaymentExists("h"));
	}

	@Test
	public void testAddWithdraw() {
		try{
			setUp();
		} catch(Exception e){
			System.out.println("Error");
		}
		
		testAccount.deposit(m);
		assertEquals(10000100, testAccount.getBalance().getAmount());
	}
	
	@Test
	//Test Passed
	public void testGetBalance() {
		try{
			setUp();
		} catch(Exception e){
				System.out.println("Error");
		}
		assertEquals(10000000, testAccount.getBalance().getAmount());
	}
}
