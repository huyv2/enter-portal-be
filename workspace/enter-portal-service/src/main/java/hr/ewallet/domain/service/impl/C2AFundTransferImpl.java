package hr.ewallet.domain.service.impl;

import hr.ewallet.domain.account.Account;
import hr.ewallet.domain.card.Card;
import hr.ewallet.domain.service.C2AFundTransfer;

public class C2AFundTransferImpl implements C2AFundTransfer {

	@Override
	public boolean fundTransfer(Card sourceCard, Account destinationAccount) {
		return false;
	}

}
