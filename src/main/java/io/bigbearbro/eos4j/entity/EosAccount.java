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
    private String fromAccount;



    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }



    public EosAccount(String pk, String fromAccount, String toAccount) {
        this.pk = pk;
        this.fromAccount = fromAccount;
    }

    public EosAccount() {
    }

    @Override
    public String toString() {
        return "EosAccount{" +
                "pk='" + pk + '\'' +
                ", fromAccount='" + fromAccount + '\'' +
                '}';
    }
}
