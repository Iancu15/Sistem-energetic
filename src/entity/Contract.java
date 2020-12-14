package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Contract {
    private Integer consumerId;
    private long price;
    private int remainedContractMonths;
    private boolean onHold = false;
    
    public Contract(int consumerId, long price, int remainedContractMonths) {
        this.consumerId = consumerId;
        this.price = price;
        this.remainedContractMonths = remainedContractMonths;
    }
    
    public Integer getConsumerId() {
        return consumerId;
    }
    public void setConsumerId(int consumerId) {
        this.consumerId = consumerId;
    }
    public long getPrice() {
        return price;
    }
    public void setPrice(long price) {
        this.price = price;
    }
    public int getRemainedContractMonths() {
        return remainedContractMonths;
    }
    public void setRemainedContractMonths(int remainedContractMonths) {
        this.remainedContractMonths = remainedContractMonths;
    }
    
    @JsonIgnore
    public boolean isOnHold() {
        return onHold;
    }

    public void setOnHold(boolean onHold) {
        this.onHold = onHold;
    }
}
