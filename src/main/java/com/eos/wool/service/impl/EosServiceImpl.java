package com.eos.wool.service.impl;

import com.eos.wool.service.EosService;
import io.bigbearbro.eos4j.Eos4j;
import io.bigbearbro.eos4j.ExcelOperate;
import io.bigbearbro.eos4j.api.result.PushTransactionResults;
import io.bigbearbro.eos4j.entity.EosAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by pengchaoling on 2018/9/13.
 */
@Service("eosService")
public class EosServiceImpl implements EosService {

    @Value("${eos.accountFile.dir}")
    private String accountDir;

    protected static Logger LOG = LoggerFactory.getLogger(EosServiceImpl.class);

    private ExecutorService executors = Executors.newFixedThreadPool(30);

    @Override
    public void mining() {
        executors.execute(new Runnable() {
            @Override
            public void run() {
                Eos4j eos4j = new Eos4j("https://api.jeda.one");

                PushTransactionResults transactionResults = null;
                try {

                    transactionResults = eos4j.transfer("你的私钥",
                            "eosio.token","你的账户名",
                            "eosfakerroll",
                            "0.5000 EOS", "96|pengchaoling");

                    System.out.println(transactionResults.toString());

                } catch (IOException e) {

                }

            }
        });

    }

    @Override
    public void createExcel(){
        ExcelOperate excelOperate = new ExcelOperate();
        List<EosAccount> eosAccounts = excelOperate.getStudent();
        // 创建Excel表格
        excelOperate.createExcel(eosAccounts, accountDir);
    }

    @Override
    public List<EosAccount> getAccountByExcel(){
        ExcelOperate excelOperate = new ExcelOperate();
        List<EosAccount> eosAccounts = excelOperate.readExcel(accountDir);
        return eosAccounts;
    }





}
