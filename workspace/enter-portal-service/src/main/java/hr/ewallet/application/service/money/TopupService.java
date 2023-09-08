package hr.ewallet.application.service.money;

import hr.ewallet.application.port.in.command.request.momo.money.TopupRequestCommand;
import hr.ewallet.application.port.in.command.response.momo.money.TopupResponseCommand;
import hr.ewallet.application.port.in.momo.money.TopupUseCase;
import hr.ewallet.common.constant.ParamConstant;
//import hr.ewallet.domain.card.Activity;
//import hr.ewallet.domain.card.Card;
//import hr.ewallet.domain.account.Account;
//import hr.ewallet.domain.account.Activity;

public class TopupService implements TopupUseCase {

	@Override
	public TopupResponseCommand topup(TopupRequestCommand topupRequestCommand) {
		
		TopupResponseCommand topupResponseCommand = new TopupResponseCommand();
		
		String type = topupRequestCommand.getType();
		
		if (type.equals(ParamConstant.ACCOUNT_TYPE)) {
			//Account account = new Account();
			//Activity accountActivity = Activity.createActivity();
			
			// Proccess to move money here using Account, Activity and something to do then return true or false
		} else if (type.equals(ParamConstant.CARD_TYPE)) {
			//Card card = new Card();
			//Activity cardActivity = Activity.createActivity();
			
			// Proccess to move money here using Account, Activity and something to do then return true or false
		}
		topupResponseCommand.setResult(true);
		
		return topupResponseCommand;
	}

}
