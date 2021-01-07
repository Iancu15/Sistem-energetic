package strategy;

/**
 * Fabrica singleton de strategii folosite de distribuitori pentru alegerea
 * producatorilor
 *
 * @author alex
 *
 */
public final class EnergyChoiceStrategyFactory {
    private static EnergyChoiceStrategyFactory instance = null;

    private EnergyChoiceStrategyFactory() {
    }

    /**
     * @return      Intoarce instanta singleton
     */
    public static EnergyChoiceStrategyFactory getInstance() {
        if (instance == null) {
            instance = new EnergyChoiceStrategyFactory();
        }

        return instance;
    }

    /**
     * Creeaza strategia corespunzatoarea label-ului dat de type
     *
     * @param type Parametru tip enum ce contine label-ul corespunzator tipului
     * @return Strategia corespunzatoarea label-ului
     * @throws IllegalArgumentException     In cazul in care am adaugat un nou label la
     * EnergyChoiceStrategyType si nu am actualizat switch-ul din metoda
     */
    public EnergyChoiceStrategy createEnergyChoiceStrategy(final EnergyChoiceStrategyType type)
                                                                throws IllegalArgumentException {
        switch (type) {
        case GREEN:
            return new GreenStrategy();
        case PRICE:
            return new PriceStrategy();
        case QUANTITY:
            return new QuantityStrategy();
        default:
            throw new IllegalArgumentException("Invalid strategy type");
        }
    }
}
