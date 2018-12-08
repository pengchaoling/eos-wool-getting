package com.eos.wool.service;

import io.bigbearbro.eos4j.entity.EosAccount;

import java.util.List;

/**
 * Created by pengchaoling on 2018/9/13.
 */
public interface EosService {


    /**
     * 挖矿
     */
    void mining();

    /**
     * 创建excel模板
     */
    void createExcel();

    /**
     * 读取excel文件,组装实体
     * @return
     */
    List<EosAccount> getAccountByExcel();



}
