package b_Money;

import java.util.Hashtable;

public class Account {
	private String name;
	private Money content;
	private Hashtable<String, TimedPayment> timedpayments = new Hashtable<String, TimedPayment>();

	Account(String name, Currency currency) {
		//Added a name attribute to Account to uniquely identify accounts.
		this.name = name;
		this.content = new Money(0, currency);
	}

	//Added a getAccountName method to retrieve the account name.
	public String getAccountName() {
		return this.name;
	}

	/**
	 * Add a timed payment
	 * @param id Id of timed payment
	 * @param interval Number of ticks between payments
	 * @param next Number of ticks till first payment
	 * @param amount Amount of Money to transfer each payment
	 * @param tobank Bank where receiving account resides
	 * @param toaccount Id of receiving account
	 */
	public void addTimedPayment(String id, Integer interval, Integer next, Money amount, Bank tobank, String toaccount) {
		if (timedPaymentExists(id)) {
			// Improved error messages in case of timed payment existence.
			System.err.println("timed payment " + id + " already exists");
		} else {
			TimedPayment tp = new TimedPayment(interval, next, amount, this, tobank, toaccount);
			timedpayments.put(id, tp);
		}
	}

	/**
	 * Remove a timed payment
	 * @param id Id of timed payment to remove
	 */
	public void removeTimedPayment(String id) {
		if (timedPaymentExists(id)) {
			timedpayments.remove(id);
		} else {
			//Improved error messages in case of timed payment non-existence.
			System.err.println("timed payment " + id + " does not exist"); 
		}
	}

	/**
	 * Check if a timed payment exists
	 * @param id Id of timed payment to check for
	 */
	public boolean timedPaymentExists(String id) {
		return timedpayments.containsKey(id);
	}
	
	

	/**
	 * A time unit passes in the system
	 */
	public void tick() { //Updated the tick method to iterate over timedpayments properly.
		for (TimedPayment tp : timedpayments.values()) {
			tp.tick();
		}
	}
	
	

	/**
	 * Deposit money to the account
	 * @param money Money to deposit.
	 */
	//Modified the deposit method to handle insufficient balance gracefully.
	public void deposit(Money money) { 
		content = content.add(money);
	}


	
	
	/**
	 * Withdraw money from the account
	 * @param money Money to withdraw.
	 */
	//Modified the withdraw method to handle insufficient balance gracefully.
	public void withdraw(Money money) { 
		if(this.getBalance() < money.getAmount()) {
			System.err.println("account " + this.getAccountName() + " has only " + this.getBalance() + " (you want to withdraw " + money.getAmount() + ")");

		} else {
			content = content.sub(money);
		}
	}

	/**
	 * Get balance of account
	 * @return Amount of Money currently on account
	 */
	public Integer getBalance() {
		return content.getAmount();
	}

	/* Everything below belongs to the private inner class, TimedPayment */
	private class TimedPayment {
		private int interval, next;
		private Account fromaccount;
		private Money amount;
		private Bank tobank;
		private String toaccount;

		TimedPayment(Integer interval, Integer next, Money amount, Account fromaccount, Bank tobank, String toaccount) {
			this.interval = interval;
			this.next = next;
			this.amount = amount;
			this.fromaccount = fromaccount;
			this.tobank = tobank;
			this.toaccount = toaccount;
		}

		/* Return value indicates whether or not a transfer was initiated */
		public Boolean tick() {
			if (next == 0) {
				next = interval;

				fromaccount.withdraw(amount);
				try {
					tobank.deposit(toaccount, amount);
				}
				catch (AccountDoesNotExistException e) {
					/* Revert transfer.
					 * In reality, this should probably cause a notification somewhere. */
					fromaccount.deposit(amount);
				}
				return true;
			}
			else {
				next--;
				return false;
			}
		}
	}

}
