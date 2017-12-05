/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anhao.spring.service;

import com.anhao.spring.domain.Photos;
import com.anhao.spring.enums.PhotoStatus;
import com.anhao.spring.rest.Page;
import com.anhao.spring.rest.Pageable;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author Administrator
 */
public interface PhotosService {

    public Page<Photos> find(Pageable pageable);

    public void markStatus(String id, PhotoStatus status);

    public void process(Document doc);

    public void upload(Photos photos) throws IOException;
}
