package com.eos.wool.schedule;

import com.eos.wool.service.EosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author: pengchaoling
 * Data: 2018/9/18
 * description:
 */
@Component
public class EosSchedule {

    @Resource
    private EosService eosService;
    private static final Logger LOG = LoggerFactory.getLogger(EosSchedule.class);
    private ExecutorService executors = Executors.newFixedThreadPool(100);


    //@Scheduled(fixedRate = 1000)
    public void heroBet(){
        LOG.info("start to eosTransferSch");
            executors.execute(new Runnable() {
                @Override
                public void run() {
                    eosService.mining();
                }
            });
    }


}
