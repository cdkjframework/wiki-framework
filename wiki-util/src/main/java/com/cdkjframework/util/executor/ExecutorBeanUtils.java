package com.cdkjframework.util.executor;

import com.cdkjframework.constant.IntegerConsts;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ProjectName: com.lesmarthome.iot
 * @Package: com.lesmarthome.iot.executor
 * @ClassName: ExecutorBean
 * @Description: 执行bean
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class ExecutorBeanUtils {

    /**
     * 创建线程池管理
     *
     * @return 返回结果
     */
    @Bean("cdkjExecutor")
    public Executor cdkjExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(IntegerConsts.TEN);
        executor.setMaxPoolSize(IntegerConsts.TWENTY);
        executor.setQueueCapacity(IntegerConsts.ONE_HUNDRED * IntegerConsts.TWO);
        executor.setKeepAliveSeconds(IntegerConsts.ONE);
        executor.setThreadNamePrefix("CkdjExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(IntegerConsts.THREE);
        return executor;
    }
}
