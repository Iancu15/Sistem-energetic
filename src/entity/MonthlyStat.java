package entity;

import java.util.ArrayList;
import java.util.List;

public class MonthlyStat {
    private int month;
    private List<Integer> distributorsIds;
    
    public MonthlyStat() {
        this.distributorsIds = new ArrayList<Integer>();
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<Integer> getDistributorsIds() {
        return distributorsIds;
    }

    public void setDistributorsIds(List<Integer> distributorsIds) {
        this.distributorsIds = distributorsIds;
    }
}