package com.anhao.spring.task;

import com.anhao.spring.domain.Photos;
import com.anhao.spring.enums.PhotoStatus;
import com.anhao.spring.service.PhotosService;

/**
 * Created by wangkun23 on 2017/12/5.
 */
public class MigrateTask implements Runnable {

    private final Photos photo;
    private final PhotosService photosService;

    public MigrateTask(Photos photo, PhotosService photosService) {
        this.photo = photo;
        this.photosService = photosService;
    }

    @Override
    public void run() {
        //下载图片并上传到阿里云

        photosService.markStatus(photo.getId(), PhotoStatus.INDEXED);
    }
}
