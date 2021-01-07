package strategy;

/**
 * Strategy types for distributors to choose their producers
 */
public enum EnergyChoiceStrategyType {
    GREEN("GREEN"), PRICE("PRICE"), QUANTITY("QUANTITY");

    public final String label;

    EnergyChoiceStrategyType(final String label) {
        this.label = label;
    }
}
