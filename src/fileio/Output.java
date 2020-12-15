package fileio;

import java.util.List;

import entity.Consumer;
import entity.Distributor;

/**
 * Clasa ce contine listele de entitati ce vor fi afisate la output
 * @author alex
 *
 */
public final class Output {
    private List<Consumer> consumers;
    private List<Distributor> distributors;

    public Output(final List<Consumer> consumers, final List<Distributor> distributors) {
        this.consumers = consumers;
        this.distributors = distributors;
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public void setConsumers(final List<Consumer> consumers) {
        this.consumers = consumers;
    }

    public List<Distributor> getDistributors() {
        return distributors;
    }

    public void setDistributors(final List<Distributor> distributors) {
        this.distributors = distributors;
    }
}
