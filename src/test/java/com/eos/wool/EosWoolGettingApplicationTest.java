package com.eos.wool;

import io.bigbearbro.eos4j.Eos4j;
import io.bigbearbro.eos4j.api.result.PushTransactionResults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EosWoolGettingApplicationTest {

	@Test
	public void contextLoads() {
	}


	@Test
	public void testTransfer() throws IOException {
		Eos4j eos4j = new Eos4j("https://api.jeda.one");

		PushTransactionResults transactionResults = eos4j.transfer("你的私钥",
				"eosio.token","你的账户名",
				"guessfoxgame",
				"1.0000 EOS", "1;97;pengchaoling;");
		System.out.println(transactionResults.toString());
		System.out.println(11);
	}



}
