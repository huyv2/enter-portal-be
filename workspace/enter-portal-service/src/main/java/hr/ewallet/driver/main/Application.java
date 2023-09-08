package hr.ewallet.driver.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hr.ewallet.adapter.in.api.dto.request.BaseRequestDto;
import hr.ewallet.common.constant.ParamConstant;
import hr.lib.configuration.Configuration;
import hr.lib.main.LibApplication;

public class Application {
	public final static Logger log = LogManager.getLogger(Application.class);
	
	public static void main(String[] args) {
		Configuration.getInstance().configureRequestDtoLoading(ParamConstant.DTO_PACKAGE, BaseRequestDto.class);
		LibApplication.run();
	}
}
