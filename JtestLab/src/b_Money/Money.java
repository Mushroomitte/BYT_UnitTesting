package b_Money;

import java.math.BigDecimal;

public class Money implements Comparable {
	private int amount;
	private Currency currency;

	/**
	 * New Money
	 * @param amount	The amount of money
	 * @param currency	The currency of the money
	 */
	Money (Integer amount, Currency currency) {
		this.amount = amount;
		this.currency = currency;
	}

	/**
	 * Return the amount of money.
	 * @return Amount of money in Double type.
	 */
	public Integer getAmount() {
		return this.amount;
	}

	/**
	 * Returns the currency of this Money.
	 * @return Currency object representing the currency of this Money
	 */
	public Currency getCurrency() {
		return this.currency;
	}

	/**
	 * Returns the amount of the money in the string form "(amount) (currencyname)", e.g. "10.5 SEK".
	 * Recall that we represent decimal numbers with integers. This means that the "10.5 SEK" mentioned
	 * above is actually represented as the integer 1050
	 *  @return String representing the amount of Money.
	 */
	//Adjusted toString method to represent the amount correctly.
	public String toString() {
	    BigDecimal amountInDecimal = BigDecimal.valueOf(this.amount).scaleByPowerOfTen(-2);
	    return amountInDecimal + " " + this.currency.getName();
	}

    /**
	 * Gets the universal value of the Money, according the rate of its Currency.
	 * @return The value of the Money in the "universal currency".
	 */
	//Implemented method 
	public Integer universalValue() {
		return this.currency.universalValue(this.amount);
	}

	/**
	 * Check to see if the value of this money is equal to the value of another Money of some other Currency.
	 * @param other The other Money that is being compared to this Money.
	 * @return A Boolean indicating if the two monies are equal.
	 */
	//Implemented method
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Money otherMoney = (Money) other;

        return this.getCurrency().equals(otherMoney.getCurrency()) && this.getAmount().equals(otherMoney.getAmount());
    }


    /**
	 * Adds a Money to this Money, regardless of the Currency of the other Money.
	 * @param other The Money that is being added to this Money.
	 * @return A new Money with the same Currency as this Money, representing the added value of the two.
	 * (Remember to convert the other Money before adding the amounts)
	 */
	//Implemented method
    public Money add(Money other) {
        if (!this.getCurrency().equals(other.getCurrency())) {
            int convertedAmount = other.getCurrency().universalValue(other.getAmount());
            int newAmount = this.getAmount() + convertedAmount;
            return new Money(newAmount, this.getCurrency());
        } else {
            int newAmount = this.getAmount() + other.getAmount();
            return new Money(newAmount, this.getCurrency());
        }
    }



    /**
	 * Subtracts a Money from this Money, regardless of the Currency of the other Money.
	 * @param other The money that is being subtracted from this Money.
	 * @return A new Money with the same Currency as this Money, representing the subtracted value.
	 * (Again, remember converting the value of the other Money to this Currency)
	 */
	//Implemented method to subtract money from currecny amount
    public Money sub(Money other) {
        if (!this.getCurrency().equals(other.getCurrency())) {
            int convertedAmount = other.getCurrency().universalValue(other.getAmount());
            int newAmount = this.getAmount() - convertedAmount;
            return new Money(newAmount, this.getCurrency());
        } else {
            int newAmount = this.getAmount() - other.getAmount();
            return new Money(newAmount, this.getCurrency());
        }
    }


    /**
	 * Check to see if the amount of this Money is zero or not
	 * @return True if the amount of this Money is equal to 0.0, False otherwise
	 */
	//Implemented method to check if amount is 0
	public Boolean isZero() {
        return this.amount == 0;
	}
	/**
	 * Negate the amount of money, i.e. if the amount is 10.0 SEK the negation returns -10.0 SEK
	 * @return A new instance of the money class initialized with the new negated money amount.
	 */
	//Implemented method to negate the amount of money
	public Money negate() {
        return new Money(-this.amount, this.currency);
	}

	/**
	 * Compare two Monies.
	 * compareTo is required because the class implements the Comparable interface.
	 * (Remember the universalValue method, and that Integers already implement Comparable).
	 * Also, since compareTo must take an Object, you will have to explicitly downcast it to a Money.
	 * @return 0 if the values of the monies are equal.
	 * A negative integer if this Money is less valuable than the other Money.
	 * A positive integer if this Money is more valuiable than the other Money.
	 */
	//Implemented method 
	public int compareTo(Object other) {
        if (this == other) {
            return 0;
        }
        if (!(other instanceof Money)) {
            throw new IllegalArgumentException("Object must be of type Money");
        }
        Money otherMoney = (Money) other;
        return this.universalValue().compareTo(otherMoney.universalValue());
    }
}