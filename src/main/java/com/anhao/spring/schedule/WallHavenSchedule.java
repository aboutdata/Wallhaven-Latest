package com.anhao.spring.schedule;

import com.anhao.spring.dao.JobPhotosDAO;
import com.anhao.spring.dao.PhotosTagDao;
import com.anhao.spring.dao.TagDao;
import com.anhao.spring.task.crawlTask;
import com.anhao.spring.wallhaven.StorageService;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * 抓取任务调度
 *
 * @author Administrator
 *
 */
@Component
public class WallHavenSchedule {

    Logger logger = LoggerFactory.getLogger(WallHavenSchedule.class);
    /**
     * cron表达式：* * * * * *（共6位，使用空格隔开，具体如下） cron表达式：*(秒0-59) *(分钟0-59) *(小时0-23)
     * *(日期1-31) *(月份1-12或是JAN-DEC) *(星期1-7或是SUN-SAT)
     */
    @Resource
    private ThreadPoolTaskExecutor taskExecutor;

    @Resource
    private JobPhotosDAO jobPhotosDAO;

    @Resource
    private TagDao tagDAO;

    @Resource
    private PhotosTagDao photostagDAO;

    @Resource
    private StorageService storageService;

    private int page = 6778;

    /**
     * 截至时间20150922
     * 
     */
    private int totalPages = 6972;

    // 每20秒执行一次
    @Scheduled(cron = "*/20 * * * * ?")
    public void myTest() {
        System.out.println("=============================================");
        System.out.println("ActiveCount :" + taskExecutor.getActiveCount());
        System.out.println("PoolSize :" + taskExecutor.getPoolSize());
        System.out.println("CorePoolSize :" + taskExecutor.getCorePoolSize());
        System.out.println("=============================================");

        if (taskExecutor.getActiveCount() < 10) {
            System.out.println("当前页:" + page);

            if (page <= totalPages) {
                for (int i = page; i < page + 5; i++) {
                    taskExecutor.execute(new crawlTask(i, photostagDAO, jobPhotosDAO, tagDAO, storageService));
                }
                page = page + 5;
            } else {
                logger.info("抓取页码结束 {}", page);
            }
        }
    }
}