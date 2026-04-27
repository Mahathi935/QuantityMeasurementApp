/**
 * LengthUnit
 *
 * UC8: Standalone Enum with Conversion Responsibility
 */

public enum LengthUnit {

    FEET(1.0),
    INCH(1.0 / 12.0),
    YARD(3.0),
    CENTIMETER(0.393701 / 12.0);

    private final double toFeetFactor;

    LengthUnit(double factor) {
        this.toFeetFactor = factor;
    }

    // Convert to base unit (feet)
    public double convertToBaseUnit(double value) {
        return value * toFeetFactor;
    }

    // Convert from base unit (feet)
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / toFeetFactor;
    }
}

/**
 * QuantityMeasurementApp
 *
 * UC8: Refactored Design using Standalone LengthUnit
 */

public class QuantityMeasurementApp {

    // -------- VALUE OBJECT --------
    static class Quantity {

        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");

            this.value = value;
            this.unit = unit;
        }

        private double toBase() {
            return unit.convertToBaseUnit(value);
        }

        // -------- CONVERT --------
        public Quantity convertTo(LengthUnit targetUnit) {

            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double baseValue = this.toBase();
            double converted = targetUnit.convertFromBaseUnit(baseValue);

            return new Quantity(converted, targetUnit);
        }

        // -------- UC6 (UNCHANGED) --------
        public Quantity add(Quantity other) {

            if (other == null) {
                throw new IllegalArgumentException("Second operand cannot be null");
            }

            double sumBase = this.toBase() + other.toBase();
            double result = this.unit.convertFromBaseUnit(sumBase);

            return new Quantity(result, this.unit);
        }

        // -------- UC7 (UNCHANGED API) --------
        public Quantity add(Quantity other, LengthUnit targetUnit) {

            if (other == null) {
                throw new IllegalArgumentException("Second operand cannot be null");
            }
            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double sumBase = this.toBase() + other.toBase();
            double result = targetUnit.convertFromBaseUnit(sumBase);

            return new Quantity(result, targetUnit);
        }

        // -------- EQUALITY --------
        @Override
        public boolean equals(Object obj) {

            if (this == obj) return true;
            if (!(obj instanceof Quantity)) return false;

            Quantity other = (Quantity) obj;

            double thisBase = this.toBase();
            double otherBase = other.toBase();

            double epsilon = 0.0001;
            return Math.abs(thisBase - otherBase) < epsilon;
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // -------- MAIN --------
    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);

        System.out.println(q1.add(q2)); // UC6
        System.out.println(q1.add(q2, LengthUnit.INCH)); // UC7
        System.out.println(q1.convertTo(LengthUnit.INCH)); // UC5 style

        Quantity q3 = new Quantity(36.0, LengthUnit.INCH);
        Quantity q4 = new Quantity(1.0, LengthUnit.YARD);

        System.out.println(q3.equals(q4)); // true
    }
}
