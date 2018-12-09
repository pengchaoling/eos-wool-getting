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

    /**
     * 从excel中将数据存进mysql
     */
    void fromExcelToMysql();

    /**
     * 从mysql中读取数据
     * @return
     */
    List<EosAccount> getAccountByMysql();

    /**
     * 获取临时表中的数据
     * @return
     */
    List<EosAccount> getTempAccountList();
    /**
     * 转移到临时账户
     */
    int addToTemp();

    /**
     * 从temp表中删除指定账户
     * @param accountname
     * @return
     */
    boolean deleteFromTemp(String accountname);



}
