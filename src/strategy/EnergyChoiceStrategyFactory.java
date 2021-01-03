package strategy;

import java.io.IOException;

/**
 * Fabrica singleton de strategii folosite de distribuitori pentru alegerea producatorilor
 * @author alex
 *
 */
public class EnergyChoiceStrategyFactory {
    private static EnergyChoiceStrategyFactory instance = null;

    private EnergyChoiceStrategyFactory() {
    }

    public static EnergyChoiceStrategyFactory getInstance() {
        if (instance == null) {
            instance = new EnergyChoiceStrategyFactory();
        }

        return instance;
    }
    
    /**
     * Creeaza strategia corespunzatoarea label-ului dat de type
     * @param type  Parametru tip enum ce contine label-ul corespunzator tipului
     * @return      Strategia corespunzatoarea label-ului
     * @throws IOException
     */
    public EnergyChoiceStrategy createEnergyChoiceStrategy(final EnergyChoiceStrategyType type) {
        switch(type) {
            case GREEN: return new GreenStrategy();
            case PRICE: return new PriceStrategy();
            case QUANTITY: return new QuantityStrategy();
        }

        return null;
    }
}
