package com.anhao.spring.schedule;

import com.anhao.spring.domain.Photos;
import com.anhao.spring.rest.Page;
import com.anhao.spring.rest.Pageable;
import com.anhao.spring.service.PhotosService;
import com.anhao.spring.task.MigrateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 迁移图片到阿里云存储
 * Created by wangkun23 on 2017/12/5.
 */
@Component
public class MigrateSchedule {
    final Logger logger = LoggerFactory.getLogger(getClass());

    private int page = 0;
    private int pagesize = 24;

    @Resource
    private ThreadPoolTaskExecutor taskExecutor;

    @Resource
    private PhotosService photosService;

    //开始同步数据
    @Scheduled(cron = "*/1 * * * * ?")
    public void build() {
        logger.info("=============================================");
        logger.info("ActiveCount :{}", taskExecutor.getActiveCount());
        logger.info("PoolSize : {}", taskExecutor.getPoolSize());
        logger.info("CorePoolSize :{}", taskExecutor.getCorePoolSize());
        logger.info("=============================================");
        if (taskExecutor.getActiveCount() < 10) {
            Pageable pageable = new Pageable(page, pagesize);
            Page<Photos> pages = photosService.find(pageable);
            for (Photos photo : pages.getContent()) {
                taskExecutor.execute(new MigrateTask(photo, photosService));
            }
        }

    }
}
