import java.util.ArrayList;
import java.util.List;

import entity.Consumer;
import entity.Contract;
import entity.Distributor;
import entity.Entity;
import entity.EntityRegister;
import entity.MonthlyStat;
import entity.Producer;
import fileio.Input;
import update.CostChange;
import update.MonthlyUpdate;

/**
 * Se ocupa de actualizarea datelor entitatilor din joc
 *
 * @author alex
 *
 */
public final class Updater {
    /**
     * Actualizeaza consumatorii
     *
     * @param entityRegister Registrul de entitati
     * @return Starea jocului, daca e true inseamna ca s-a oprit
     */
    public boolean updateConsumers(final EntityRegister entityRegister) {
        for (final Consumer consumer : entityRegister.getConsumers()) {
            // sare peste consumatorii falimentati
            if (consumer.isBankrupt()) {
                if (consumer.getDistributor() != null) {
                    consumer.getDistributor().removeContract(consumer);
                }

                continue;
            }

            // se elimina contractul consumatorului daca acesta a expirat
            Contract contract;
            if (consumer.getDistributor() != null) {
                contract = consumer.getDistributor().findContract(consumer.getId());
                if (contract.getRemainedContractMonths() == 0 && consumer.getDebt() == 0) {
                    consumer.getDistributor().removeContract(consumer);
                }
            }

            // ofera cel mai ieftin distribuitor consumatorului daca acesta nu are unul deja
            if (consumer.getDistributor() == null) {
                final Distributor myDistributor = entityRegister.getCheapestDistributor();
                // daca nu s-a gasit niciun distribuitor nefalimentat, atunci se termina jocul
                if (myDistributor == null) {
                    return true;
                }

                myDistributor.addContract(consumer);
            }

            // calculez noul buget al consumatorului dupa achitarea ratei
            consumer.setBudget(consumer.getBudget() + consumer.getMonthlyIncome());
            contract = consumer.getDistributor().findContract(consumer.getId());
            final long newBudget = consumer.getBudget() - contract.getPrice() - consumer.getDebt();
            contract.setRemainedContractMonths(contract.getRemainedContractMonths() - 1);

            // in caz ca nu are destui bani sa achite rata va ramane dator distribuitorului,
            // daca
            // este deja dator se declara falimentat
            // daca are destui bani i se retrag banii din cont
            if (newBudget < 0) {
                if (consumer.getDebt() != 0) {
                    consumer.setBankrupt(true);
                    continue;
                }

                consumer.setDebt(Math.round(Math.floor(1.2 * contract.getPrice())));
                contract.setOnHold(true);
            } else {
                if (consumer.getDebt() != 0) {
                    consumer.setDebt(0);
                }

                consumer.setBudget(newBudget);
            }
        }

        return false;
    }

    /**
     * Actualizeaza distribuitorii
     *
     * @param entityRegister Registrul de entitati
     */
    public void updateDistributors(final EntityRegister entityRegister) {
        for (final Distributor distributor : entityRegister.getDistributors()) {
            if (distributor.isBankrupt()) {
                continue;
            }

            distributor.calculateBudget();
            if (distributor.getBudget() < 0) {
                distributor.setBankrupt(true);
                continue;
            }
        }
    }

    /**
     * Actualizeaza costurile si pretul distribuitorilor si adauga noi consumatori
     * in joc
     *
     * @param input Datele de intrare
     */
    public void addMonthlyUpdate(final Input input) {
        final MonthlyUpdate update = input.getMonthlyUpdates().get(0);
        final EntityRegister entityRegister = input.getEntityRegister();
        final List<Producer> producers = entityRegister.getProducers();
        for (final CostChange costChange : update.getProducerChanges()) {
            final Entity producer = entityRegister.findEntity(costChange.getId(), producers);
            costChange.updateProducer(((Producer) producer));
        }

        final List<Distributor> distributors = entityRegister.getDistributors();
        for (final CostChange costChange : update.getDistributorChanges()) {
            final Entity distributor = entityRegister.findEntity(costChange.getId(), distributors);
            costChange.updateDistributor(((Distributor) distributor));
            ((Distributor) distributor).calculatePrice(entityRegister);
        }

        for (final Consumer consumer : update.getNewConsumers()) {
            entityRegister.getConsumers().add(consumer);
        }

        input.getMonthlyUpdates().remove(0);
    }

    /**
     * Actualizez contractele
     *
     * @param entityRegister
     */
    public void updateContracts(final EntityRegister entityRegister) {
        final List<Consumer> consumers = entityRegister.getConsumers();
        for (final Distributor distributor : entityRegister.getDistributors()) {
            final List<Contract> expiredContracts = new ArrayList<Contract>();
            for (final Contract contract : distributor.getContracts()) {
                final Entity consumer;
                consumer = entityRegister.findEntity(contract.getConsumerId(), consumers);
                if (((Consumer) consumer).isBankrupt()) {
                    expiredContracts.add(contract);
                }
            }

            // elimin contractele facute cu consumatori falimentati
            distributor.getContracts().removeAll(expiredContracts);
        }
    }

    /**
     * Actualizez statisticile lunare ale producatorilor
     * @param entityRegister    Registrul de entitati
     * @param turn              Tura curenta a simularii(0 e prima tura)
     */
    public void updateProducers(final EntityRegister entityRegister, final int turn) {
        for (final Producer producer : entityRegister.getProducers()) {
            final MonthlyStat monthlyStat = new MonthlyStat(turn);
            // ordonez id-urile distribuitorilor crescator
            producer.getDistributorsIds().sort((u, v) -> u - v);
            monthlyStat.getDistributorsIds().addAll(producer.getDistributorsIds());
            producer.getMonthlyStats().add(monthlyStat);
        }
    }
}
