package com.eos.wool.service.impl;

import com.eos.wool.dao.EosDao;
import com.eos.wool.service.EosService;
import io.bigbearbro.eos4j.Eos4j;
import io.bigbearbro.eos4j.ExcelOperate;
import io.bigbearbro.eos4j.api.result.PushTransactionResults;
import io.bigbearbro.eos4j.entity.EosAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private EosDao eosDao;

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

    @Override
    public void fromExcelToMysql(){
        List<EosAccount> eosAccounts = getAccountByExcel();
        for(EosAccount eosAccount : eosAccounts){
            eosDao.addAccount(eosAccount);
        }

    }

    @Override
    public List<EosAccount> getAccountByMysql(){
        return eosDao.getAccountByMysql();
    }

    @Override
    public int addToTemp(){
        return eosDao.addToTemp();
    }

    @Override
    public List<EosAccount> getTempAccountList(){
        return eosDao.getTempAccountList();
    }

    @Override
    public boolean deleteFromTemp(String accountname){
        return eosDao.deleteFromTemp(accountname);
    }



}
