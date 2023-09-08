package hr.ewallet.domain.service;

import hr.ewallet.domain.card.Card;
import hr.lib.factory.bean.Bean;

public interface CardFundTransfer extends Bean {
	boolean fundTransfer(Card sourceCard, Card destinationCard);
}
