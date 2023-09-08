package hr.lib.factory.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hr.lib.constant.ParamConstant;
import hr.lib.util.ResourceUtil;

public class ExecutorThreadPoolFactory {
	private final static Logger log = LogManager.getLogger(ExecutorThreadPoolFactory.class);
	
	private static int corePoolSize;
	private static int maxPoolSize;
	private static byte boundQueue;
	private static ThreadPoolExecutor executor = null;
	
	public static void init() {
		try {
			corePoolSize = Integer.parseInt(ResourceUtil.getApplicationConfiguration("executor.corePoolSize"));
			maxPoolSize = Integer.parseInt(ResourceUtil.getApplicationConfiguration("executor.maxPoolSize"));
			boundQueue = Byte.parseByte(ResourceUtil.getApplicationConfiguration("executor.boundQueue"));
		} catch(Exception e) {
			corePoolSize = 1;
			maxPoolSize = 1;
			boundQueue = ParamConstant.IS_MAX_TASK_QUEUE;
		}
		
		try {
			if (boundQueue == ParamConstant.IS_MAX_TASK_QUEUE) {
				executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 0L, TimeUnit.SECONDS,
						new LinkedBlockingQueue<Runnable>());
			} else {
				executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 0L, TimeUnit.SECONDS,
						new LinkedBlockingQueue<Runnable>(boundQueue));
			}
			log.info("Init Factory ExecutorThreadPool successfully");
		} catch(Exception e) {
			log.error(e);
		}
	}
	
	public static ExecutorService getThreadPoolExecutor() {
		return executor;
	}
	
	public static void shutdown() {
		try {
			log.info("Prepare sutting down ThreadPool");
			
			executor.shutdown();
			
			log.info("Please wait for all taks complete");
			while(!executor.awaitTermination(10, TimeUnit.SECONDS)) {
				log.info("Awaiting completation of tasks!");
			}
			log.info("All tasks complete!");
			
			log.info("Shutdown ThreadPool successfully");
		} catch(Exception e) {
			log.error("Shutdown error ", e);
		}
	}
}
