package hr.ewallet.domain.service;

import hr.ewallet.domain.account.Account;
import hr.ewallet.domain.card.Card;
import hr.lib.factory.bean.Bean;

public interface A2CFundTrasfer extends Bean {
	boolean fundTransfer(Account sourceAccount, Card destinationCard);
}
