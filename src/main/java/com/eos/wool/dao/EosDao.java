package com.eos.wool.dao;

import io.bigbearbro.eos4j.entity.EosAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by pengchaoling on 2018/12/9.
 */
@Service("eosDao")
public class EosDao {

    @Resource
    private JdbcTemplate jdbcTemplate;
    private static final Logger LOG = LoggerFactory.getLogger(EosDao.class);



    public int addAccount(EosAccount eosAccount){
        String sql = "insert into account(pk,accountname) values (?,?)";
        try {
            int res = jdbcTemplate.update(sql,eosAccount.getPk(),eosAccount.getAccountname());
            return res ;
        }catch (Exception e){
            LOG.error("updateWithDraw error!",e);
            return 0;
        }
    }

    public List<EosAccount> getAccountByMysql(){
        String sql = "select * from account";

        try {
            return jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(EosAccount.class));
        }catch (Exception e){
            LOG.error("getAccountByMysql error!");
            return null;
        }

    }

    public List<EosAccount> getTempAccountList(){
        String sql = "select * from account_temp";

        try {
            return jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(EosAccount.class));
        }catch (Exception e){
            LOG.error("getAccountByMysql error!");
            return null;
        }

    }

    public int addToTemp(){
        String delsql = "delete from account_temp";
        String sql = "insert into account_temp select * from account";
        try {
            jdbcTemplate.update(delsql);
            return jdbcTemplate.update(sql);
        }catch (Exception e){
            LOG.error("addToTemp error!");
            return 0;
        }
    }

    public boolean deleteFromTemp(String accountname){
        String sql = "delete from account_temp where accountname=?";
        try {
            int res = jdbcTemplate.update(sql,accountname);
            return res >0;
        }catch (Exception e){
            LOG.error("deleteFromTemp error!",e);
            return false;
        }
    }





}
