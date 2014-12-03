package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	Money money;
	
	@Before
	public void setUp() throws Exception{
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
		money = new Money(1000,SEK);
	}

	@Test
	//Test passed
	public void testGetName() {
		try{
		setUp();
		} catch (Exception e) {
			System.out.println("Error");
		}
		assertEquals("SweBank", SweBank.getName());
	}

	@Test
	public void testGetCurrency() {
		try{
			setUp();
		} catch (Exception e) {
			
		}
		assertEquals(SEK, SweBank.getCurrency());
	}

	@Test
	//Test failed
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		try{
			setUp();
		} 
		catch (AccountExistsException e) {
			System.out.println("Account already exists");
		}
		catch (AccountDoesNotExistException e){
			System.out.println("Account does not exist");
		}
		catch(Exception e){
			System.out.println("Error");
		}
		
		try{
			SweBank.openAccount("Bob");
		} catch (AccountExistsException e){
			assertEquals(1,1);
			return;
		} 
		fail();
		
	}

	@Test
	/*
	 * Test Passed
	 * */
	public void testDeposit() throws AccountDoesNotExistException {
		
		try{
			setUp();
			SweBank.deposit("Bob", money);
		}catch(AccountDoesNotExistException e){
			System.out.println("Account does not exist");
		}catch(Exception e){
			
		}
		assertEquals(1000,SweBank.getBalance("Bob"));
	}

	@Test
	/*
	 * Test failed
	 */
	public void testWithdraw() throws AccountDoesNotExistException {
		try{
			setUp();
			SweBank.deposit("Bob", money);
			Money m = new Money(500,SEK);
			SweBank.withdraw("Bob", m);
		}catch(AccountDoesNotExistException e){
			System.out.println("Account does not exist");
		}catch(Exception e){
			System.out.println("Error");
		}
		assertEquals(1000/2,SweBank.getBalance("Bob"));
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		try{
			setUp();
			SweBank.deposit("Bob", money);
		}catch(AccountDoesNotExistException e){
			System.out.println("Account does not exist");
		}catch(Exception e){
			System.out.println("Error");
		}
		assertEquals(1000, SweBank.getBalance("Bob"));
	}
	
	@Test
	//test passed
	public void testTransfer() throws AccountDoesNotExistException {
		try{
			setUp();
			SweBank.deposit("Bob", money);
		}catch(AccountDoesNotExistException e){
			System.out.println("Account does not exist");
		}catch(Exception e){
			System.out.println("Error");
		}
		Money m = new Money(500, SEK);
		SweBank.transfer("Bob", DanskeBank, "Gertrud", m);
		assertEquals((money.sub(m).universalValue()*5), DanskeBank.getBalance("Gertrud"));
	}
	
	@Test
	//Test passed, assuming that the initial tick is necessary for an immediate transfer
	public void testTimedPayment() throws AccountDoesNotExistException {
		try{
			setUp();
			SweBank.deposit("Bob", money);
		}catch(AccountDoesNotExistException e){
			System.out.println("Account does not exist");
		}catch(Exception e){
			System.out.println("Error");
		}
		Money m = new Money(100, SEK);
		
		SweBank.addTimedPayment("Bob", "payment1", 1, 0, m, DanskeBank, "Gertrud");
		SweBank.tick();
		assertEquals(75,DanskeBank.getBalance("Gertrud"));
		SweBank.tick();
		assertEquals(150,DanskeBank.getBalance("Gertrud"));
		SweBank.tick();
		assertEquals(225,DanskeBank.getBalance("Gertrud"));
		SweBank.tick();
		assertEquals(300,DanskeBank.getBalance("Gertrud"));
		SweBank.tick();
		assertEquals(375,DanskeBank.getBalance("Gertrud"));
		
	}
}
