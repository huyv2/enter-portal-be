package hr.lib.factory.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hr.ewallet.adapter.in.api.dto.request.BaseRequestDto;
import hr.ewallet.adapter.in.api.dto.response.BaseResponseDto;
import hr.lib.cache.Cache;
import hr.lib.cache.CacheManager;
import hr.lib.constant.CacheConstant;
import hr.lib.constant.ParamConstant;
import hr.lib.factory.threadpool.ExecutorThreadPoolFactory;
import hr.lib.util.HttpUtil;
import hr.lib.util.JsonParserUtil;

public abstract class BaseApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected final Logger log = LogManager.getLogger(this.getClass());
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//super.doPost(req, resp);
		try {
			ExecutorService executor = ExecutorThreadPoolFactory.getThreadPoolExecutor();
			final AsyncContext asyncContext = req.startAsync();
			executor.execute(new Runnable() {
				
				@Override
				public void run() {
					HttpServletRequest req = null;
					HttpServletResponse res = null;
					PrintWriter responseWriter = null;
					BaseRequestDto requestDto = null;
					String requestBody = "";
					String responseBody = "";
					try {
						req = (HttpServletRequest) asyncContext.getRequest();
						res = (HttpServletResponse)asyncContext.getResponse();
						responseWriter = res.getWriter();
						
						HttpUtil httpUtil = HttpUtil.of(req.getReader());
						requestDto = preProcess(httpUtil, req, res);
						
						requestBody = httpUtil.toString();
						log.info(requestBody);
						
						BaseResponseDto responseDto = executePost(requestDto);
						
						responseBody = JsonParserUtil.toJson(responseDto);
						log.info(responseBody);
					} catch(Exception e) {
						log.error(e);
					} finally {
						responseWriter.print(responseBody);
						responseWriter.flush();
						responseWriter.close();
						
						asyncContext.complete();
					}
				}
			});
		} catch(Exception e) {
			log.error(e);
		}
	}
	
	private BaseRequestDto preProcess(HttpUtil httpUtil, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType(ParamConstant.JSON_CONTENT_TYPE);
		
		BaseRequestDto requestDto = null;
		String requestUri = req.getRequestURI();
		Cache literalCache = (Cache) CacheManager.getInstance().getCache(CacheConstant.LITERAL_REQUEST_CLASS);
		Object tClass = literalCache.get(requestUri);
		if (tClass != null) {
			Object tObject = httpUtil.toObject((Class<?>) tClass);
			requestDto = (BaseRequestDto) tObject;
		}
		
		return requestDto;
	}
	
	protected abstract BaseResponseDto executePost(BaseRequestDto requestBaseDto);
}
