package org.sq.zbnss.config.thread_pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * 线程池配置
 *
 * @author mes
 **/
@Configuration
public class ThreadPoolConfig
{
    /**
     * 获取系统处理器个数，作为线程池数量
     */
    private final int CPU_NUM = Runtime.getRuntime().availableProcessors();

    /**
     * 使用Guava的ThreadFactoryBuilder线程工厂 不会导致栈溢出
     */
    private final ThreadFactory THREAD_FACTORY = new ThreadFactoryBuilder()
            .setNameFormat("demo-pool-%d").build();
    // 核心线程池大小
    private int corePoolSize = 50;

    // 最大可创建的线程数
    private int maxPoolSize = 200;

    // 队列最大长度
    private int queueCapacity = 1000;

    // 线程池维护线程所允许的空闲时间
    private int keepAliveSeconds = 300;
    /**
     * 初始化线程池
     */
    @Bean(name = "threadPoolExecutor")
    public ExecutorService threadPoolExecutor() {
        return new ThreadPoolExecutor(
                //设置核心线程数量
                CPU_NUM,
                //设置最大线程数量
                CPU_NUM * 10,
                //设置过期时间20秒
                20,
                TimeUnit.SECONDS,
                //设置列队 最大长度1024
                new LinkedBlockingQueue<Runnable>(1024),
                //设置线程工厂
                THREAD_FACTORY,
                //设置拒绝策略：丢弃任务并抛出RejectedExecutionException异常
                new ThreadPoolExecutor.AbortPolicy());
    }

    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(maxPoolSize);
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        // 线程池对拒绝任务(无线程可用)的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
    }
