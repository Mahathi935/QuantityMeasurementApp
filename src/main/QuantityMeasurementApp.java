/**
 * WeightUnit
 *
 * UC9: Standalone Enum for Weight
 */

public enum WeightUnit {

    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double toKgFactor;

    WeightUnit(double factor) {
        this.toKgFactor = factor;
    }

    // Convert to base unit (kg)
    public double convertToBaseUnit(double value) {
        return value * toKgFactor;
    }

    // Convert from base unit (kg)
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / toKgFactor;
    }
}
/**
 * QuantityWeight
 *
 * UC9: Weight Measurement Class
 */

public class QuantityWeight {

    private final double value;
    private final WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");

        this.value = value;
        this.unit = unit;
    }

    private double toBase() {
        return unit.convertToBaseUnit(value);
    }

    // -------- CONVERT --------
    public QuantityWeight convertTo(WeightUnit targetUnit) {

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double base = this.toBase();
        double converted = targetUnit.convertFromBaseUnit(base);

        return new QuantityWeight(converted, targetUnit);
    }

    // -------- ADD (UC6 STYLE) --------
    public QuantityWeight add(QuantityWeight other) {

        if (other == null) {
            throw new IllegalArgumentException("Second operand cannot be null");
        }

        double sumBase = this.toBase() + other.toBase();
        double result = this.unit.convertFromBaseUnit(sumBase);

        return new QuantityWeight(result, this.unit);
    }

    // -------- ADD (UC7 STYLE) --------
    public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {

        if (other == null) {
            throw new IllegalArgumentException("Second operand cannot be null");
        }
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double sumBase = this.toBase() + other.toBase();
        double result = targetUnit.convertFromBaseUnit(sumBase);

        return new QuantityWeight(result, targetUnit);
    }

    // -------- EQUALITY --------
    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityWeight other = (QuantityWeight) obj;

        double epsilon = 0.0001;
        return Math.abs(this.toBase() - other.toBase()) < epsilon;
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}
public class QuantityMeasurementApp {

    public static void main(String[] args) {

        // -------- WEIGHT --------
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);

        System.out.println(w1.equals(w2)); // true

        System.out.println(w1.convertTo(WeightUnit.GRAM)); // 1000 g

        System.out.println(w1.add(w2)); // 2 kg

        System.out.println(w1.add(w2, WeightUnit.GRAM)); // 2000 g

        QuantityWeight w3 = new QuantityWeight(2.20462, WeightUnit.POUND);

        System.out.println(w1.equals(w3)); // true (~)
    }
}