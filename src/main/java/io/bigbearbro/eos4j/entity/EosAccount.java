package io.bigbearbro.eos4j.entity;

/**
 * @author lsh
 * @date 2018/12/8.
 */
public class EosAccount {
    /**
     * 私钥
     */
    private String pk;
    /**
     * 个人账户
     */
    private String accountname;

    public EosAccount(String pk,String accountname){
        this.pk = pk;
        this.accountname = accountname;
    }

    public EosAccount(){}



    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    @Override
    public String toString() {
        return "EosAccount{" +
                "pk='" + pk + '\'' +
                ", accountname='" + accountname + '\'' +
                '}';
    }
}
