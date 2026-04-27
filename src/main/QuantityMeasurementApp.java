/**
 * QuantityMeasurementApp
 *
 * UC5: Unit-to-Unit Conversion API
 *
 * Supports conversion between FEET, INCH, YARD, CENTIMETER
 *
 * @author Mahathi
 * @version 1.0
 */

public class QuantityMeasurementApp {

    // -------- ENUM --------
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.393701 / 12.0);

        private final double toFeetFactor;

        LengthUnit(double factor) {
            this.toFeetFactor = factor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }

        public double fromFeet(double valueInFeet) {
            return valueInFeet / toFeetFactor;
        }
    }

    // -------- CONVERSION API --------
    public static double convert(double value, LengthUnit source, LengthUnit target) {

        // 🔴 VALIDATION
        if (source == null || target == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid numeric value");
        }

        // Step 1: convert to base (feet)
        double valueInFeet = source.toFeet(value);

        // Step 2: convert to target
        return target.fromFeet(valueInFeet);
    }

    // -------- DEMO METHODS --------

    // Overloaded method 1
    public static void demonstrateLengthConversion(double value, LengthUnit from, LengthUnit to) {
        double result = convert(value, from, to);
        System.out.println("convert(" + value + ", " + from + ", " + to + ") → " + result);
    }

    // Overloaded method 2
    public static void demonstrateLengthConversion(Quantity q, LengthUnit to) {
        double result = convert(q.value, q.unit, to);
        System.out.println("convert(" + q.value + ", " + q.unit + ", " + to + ") → " + result);
    }

    // -------- VALUE OBJECT --------
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
            this.value = value;
            this.unit = unit;
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || this.getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;
            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // -------- MAIN --------
    public static void main(String[] args) {

        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCH);
        demonstrateLengthConversion(3.0, LengthUnit.YARD, LengthUnit.FEET);
        demonstrateLengthConversion(36.0, LengthUnit.INCH, LengthUnit.YARD);
        demonstrateLengthConversion(1.0, LengthUnit.CENTIMETER, LengthUnit.INCH);

        Quantity q = new Quantity(2.0, LengthUnit.YARD);
        demonstrateLengthConversion(q, LengthUnit.FEET);
    }
}