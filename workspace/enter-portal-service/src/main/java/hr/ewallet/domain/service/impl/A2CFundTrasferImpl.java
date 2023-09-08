package hr.ewallet.domain.service.impl;

import hr.ewallet.domain.account.Account;
import hr.ewallet.domain.card.Card;
import hr.ewallet.domain.service.A2CFundTrasfer;

public class A2CFundTrasferImpl implements A2CFundTrasfer {

	@Override
	public boolean fundTransfer(Account sourceAccount, Card destinationCard) {
		return false;
	}

}
