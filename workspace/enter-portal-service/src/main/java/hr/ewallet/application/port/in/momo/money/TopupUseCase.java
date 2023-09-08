package hr.ewallet.application.port.in.momo.money;

import hr.ewallet.application.port.in.command.request.momo.money.TopupRequestCommand;
import hr.ewallet.application.port.in.command.response.momo.money.TopupResponseCommand;
import hr.lib.factory.bean.Bean;

public interface TopupUseCase extends Bean {
	TopupResponseCommand topup(TopupRequestCommand topupRequestCommand);
}
