package com.anhao.spring;

/**
 * Created by wangkun23 on 2017/12/6.
 */
public class AppTest {
    public static void main(String[] args) {
        String path = "group1/M00/08/B7/eznwC1Ykm2CAVE4YAAOR4jSBPrA079.jpg";
        String[] content = path.split("group1/M00");

        System.out.println(content[1]);

    }
}
