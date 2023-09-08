package hr.ewallet.domain.service;

import hr.ewallet.domain.account.Account;
import hr.lib.factory.bean.Bean;

public interface AccountFundTransfer extends Bean {
	boolean fundTransfer(Account sourceAccount, Account destinationAccount);
}
