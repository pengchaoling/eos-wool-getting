package com.eos.wool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eos.wool.service.EosService;
import com.eos.wool.utils.common.HttpHelper;
import io.bigbearbro.eos4j.Eos4j;
import io.bigbearbro.eos4j.ExcelOperate;
import io.bigbearbro.eos4j.api.result.PushTransactionResults;
import io.bigbearbro.eos4j.entity.EosAccount;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EosWoolGettingApplicationTest {

    @Resource
    private EosService eosService;

    @Value("${eos.mainAccount.name}")
    private String mainAccountname;

    @Value("${eos.mainAccount.pk}")
    private String mainAccountPk;


	@Test
	public void contextLoads() {
	}


	@Test
	public void testTransfer() throws IOException {
		Eos4j eos4j = new Eos4j("https://api.jeda.one");

		PushTransactionResults transactionResults = eos4j.transfer("你的私钥",
				"eosio.token","你的账户名",
				"pengchaoling",
				"1.0000 EOS", "1;97;pengchaoling;");
		System.out.println(transactionResults.toString());
		System.out.println("test");
	}

    /**
     * 创建excel模板
     */
    @Test
    public void TestCreateExcel(){
       eosService.createExcel();
    }


    /**
     * 批量下注
     */
    @Test
    public void readExcelAndTransfer(){
        //转给下注的合约账户
        String toAccount = "thebetxowner";
        //每次下注金额
        String quantity = "0.5000 EOS";
        //下注memo
        String memo = "96-pengchaoling-7L7TiEQnvnalt0XZ";

        Eos4j eos4j = new Eos4j("https://api.jeda.one");
        List<EosAccount> eosAccounts = eosService.getAccountByExcel();
        for (EosAccount  eosAccount : eosAccounts){
            System.out.println(eosAccount.toString());

            PushTransactionResults transactionResults = null;
            try {
                transactionResults = eos4j.transfer(eosAccount.getPk(),
                        "eosio.token",eosAccount.getFromAccount(),
                        toAccount,
                        quantity, memo);
                System.out.println(transactionResults.toString());
            } catch (Exception e) {
                System.out.println("执行失败,账号:"+eosAccount.getFromAccount());
                e.printStackTrace();
            }

        }
        System.out.println("执行完毕");
    }


    /**
     * 将eos从大号批量转给小号
     */
    @Test
    public void transferEosToAllSon(){
        //转账数量
        String quantity = "1.0000 EOS";
        //转账memo
        String memo = "";

        Eos4j eos4j = new Eos4j("https://api.jeda.one");
        List<EosAccount> eosAccounts = eosService.getAccountByExcel();
        for (EosAccount  eosAccount : eosAccounts){
            System.out.println(eosAccount.toString());

            PushTransactionResults transactionResults = null;
            try {
                transactionResults = eos4j.transfer(mainAccountPk,
                        "eosio.token",mainAccountname,
                        eosAccount.getFromAccount(),
                        quantity, memo);
            } catch (Exception e) {
                System.out.println("执行失败,账号:"+eosAccount.getFromAccount());
                e.printStackTrace();
            }
            System.out.println(transactionResults.toString());


        }
        System.out.println("执行完毕");

    }



    /**
     * 将所有的小号的代币清空,转给大号
     */
    @Test
    public void clearAllEosToMainAccount(){
        //合约名称
        String code = "eosio.token";
        //代币名称
        String symbol = "EOS";

        Eos4j eos4j = new Eos4j("https://api.jeda.one");

        List<EosAccount> eosAccounts = eosService.getAccountByExcel();
        for (EosAccount  eosAccount : eosAccounts){
            System.out.println(eosAccount.toString());

            PushTransactionResults transactionResults = null;
            try {

                BigDecimal bigDecimal = eos4j.getCurrencyBalance(eosAccount.getFromAccount(),code,symbol);
                String amount = bigDecimal.toString();
                if(amount.equals("")){
                    System.out.println("账号余额已经清零"+eosAccount.getFromAccount());
                    continue;
                }

                String quantity = bigDecimal.toString() +" " + symbol;
                transactionResults = eos4j.transfer(eosAccount.getPk(),
                        "eosio.token",eosAccount.getFromAccount(),mainAccountname,
                        quantity, "");
                System.out.println(transactionResults.toString());
            } catch (Exception e) {
                System.out.println("执行失败,账号:"+eosAccount.getFromAccount()+e.getMessage());

            }

        }
        System.out.println("执行完毕");

    }


    /**
     * 大号给小号批量租赁CPU,租赁0.01eos 7天
     */
    @Test
    public void buyCpuForSon(){

        Eos4j eos4j = new Eos4j("https://api.jeda.one");
        List<EosAccount> eosAccounts = eosService.getAccountByExcel();
        for (EosAccount  eosAccount : eosAccounts){
            System.out.println(eosAccount.toString());

            PushTransactionResults transactionResults = null;
            try {
                transactionResults = eos4j.transfer(mainAccountPk,
                        "eosio.token",mainAccountname,
                        "cpubankeosio",
                        "0.0100 EOS","7d "+eosAccount.getFromAccount()+" cpu");
            } catch (Exception e) {
                System.out.println("执行失败,账号:"+eosAccount.getFromAccount());
                e.printStackTrace();
            }
            System.out.println(transactionResults.toString());
        }
        System.out.println("执行完毕");
    }

    /**
     * 用主账户批量创建新账户,每次十个,记得修改下面的参数
     */
    @Test
    public void CreateAccount(){

        String lastAccunt = "";

        String url = "https://eospark.com/api/contract/eosio.token/trx?page=1&size=10&action=transfer";

        String res = HttpHelper.sendGet(url, "utf-8");
        JSONObject jsonObject = JSON.parseObject(res);
        JSONArray data = jsonObject.getJSONObject("data").getJSONArray("actions");

        for(Object item : data){
            item = (JSONObject) item;
            JSONObject transdata = ((JSONObject) item).getJSONObject("data");
            String eosaccount = transdata.getString("from");
            if(lastAccunt.equals(eosaccount)){
                continue;
            }

            lastAccunt = eosaccount;

            eosaccount = eosaccount.substring(0,eosaccount.length()-2);

            if(eosaccount.equals("crazycapit")||eosaccount.equals("eoscrashma")){
                continue;
            }

            eosaccount = eosaccount+ RandomStringUtils.randomAlphanumeric(2).toLowerCase();

            System.out.println(eosaccount);

            Eos4j eos4j = new Eos4j("https://node.betdice.one");
            try {
                eos4j.createAccount(mainAccountPk,mainAccountname,
                        eosaccount,"EOS6AHzhi6QSJQKhrrLcssyekCkDfaeTQ4SvfxRviRf3xKBYQVCct","EOS6AHzhi6QSJQKhrrLcssyekCkDfaeTQ4SvfxRviRf3xKBYQVCct",

                        4096l,"0.1 EOS","0.05 EOS",1l);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
