package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Statistica lunara a unui producator
 * @author alex
 *
 */
public class MonthlyStat {
    /**
     * Luna aferenta statisticii
     */
    private int month;
    /**
     * Lista cu distribuitorii abonati in luna respectiva
     */
    private List<Integer> distributorsIds = new ArrayList<Integer>();

    public MonthlyStat() {
    }

    public MonthlyStat(final int month) {
        this.month = month;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(final int month) {
        this.month = month;
    }

    public List<Integer> getDistributorsIds() {
        return distributorsIds;
    }

    public void setDistributorsIds(final List<Integer> distributorsIds) {
        this.distributorsIds = distributorsIds;
    }
}
