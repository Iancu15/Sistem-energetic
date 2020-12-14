package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Contractul dintre un consumator si un distributor
 * @author alex
 *
 */
public final class Contract {
    /**
     * Id-ul consumatorului care participa in contract
     */
    private Integer consumerId;
    /**
     * Rata stabilita prin contract
     */
    private long price;
    /**
     * Numarul de luni ramase pana la expirarea contractului
     */
    private int remainedContractMonths;
    /**
     * Starea contractului
     * false - contractul se desfasoara in conformitate cu cele precizate
     * true - contractul este in asteptare
     */
    private boolean onHold = false;

    public Contract(final int consumerId, final long price, final int remainedContractMonths) {
        this.consumerId = consumerId;
        this.price = price;
        this.remainedContractMonths = remainedContractMonths;
    }

    public Integer getConsumerId() {
        return consumerId;
    }
    public void setConsumerId(final int consumerId) {
        this.consumerId = consumerId;
    }
    public long getPrice() {
        return price;
    }
    public void setPrice(final long price) {
        this.price = price;
    }
    public int getRemainedContractMonths() {
        return remainedContractMonths;
    }
    public void setRemainedContractMonths(final int remainedContractMonths) {
        this.remainedContractMonths = remainedContractMonths;
    }

    @JsonIgnore
    public boolean isOnHold() {
        return onHold;
    }

    public void setOnHold(final boolean onHold) {
        this.onHold = onHold;
    }
}
