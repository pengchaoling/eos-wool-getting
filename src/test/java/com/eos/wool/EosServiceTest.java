package com.eos.wool;

import com.eos.wool.service.EosService;
import com.eos.wool.utils.common.CommonUtils;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Created by pengchaoling on 2018/9/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EosServiceTest {
    @Resource
    private EosService eosService;



    @Test
    public void arr(){
        int[] myList = {0,1,22,25,26,27,28,29,31,36,39,50,55,57,58,60,68,76,77,85,91,97};
        boolean isHot = ArrayUtils.contains(myList,100);
        System.out.println(isHot);
    }


    @Test
    public void temp(){
        String quantity="24.5001 EOS";
        String res = quantity.replace(" EOS","");
        float eos = CommonUtils.evalFloat(res,0);
        System.out.println(eos);
    }

    @Test
    public void testBet(){
        eosService.mining();
    }

}
