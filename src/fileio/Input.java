package fileio;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

import entity.EntityRegister;
import update.MonthlyUpdate;

public class Input {
    private int numberOfTurns;
    private EntityRegister entityRegister;
    private List<MonthlyUpdate> monthlyUpdates;
    
    public Integer getNumberOfTurns() {
        return numberOfTurns;
    }
    
    public void setNumberOfTurns(Integer numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }
    
    @JsonAlias("initialData")
    public EntityRegister getEntityRegister() {
        return entityRegister;
    }
    
    @JsonAlias("initialData")
    public void setEntityRegister(EntityRegister entityRegister) {
        this.entityRegister = entityRegister;
    }
    
    public List<MonthlyUpdate> getMonthlyUpdates() {
        return monthlyUpdates;
    }
    
    public void setMonthlyUpdates(List<MonthlyUpdate> monthlyUpdates) {
        this.monthlyUpdates = monthlyUpdates;
    }
}
