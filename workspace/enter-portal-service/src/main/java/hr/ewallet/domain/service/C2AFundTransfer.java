package hr.ewallet.domain.service;

import hr.ewallet.domain.account.Account;
import hr.ewallet.domain.card.Card;
import hr.lib.factory.bean.Bean;

public interface C2AFundTransfer extends Bean {
	boolean fundTransfer(Card sourceCard, Account destinationAccount);
}
