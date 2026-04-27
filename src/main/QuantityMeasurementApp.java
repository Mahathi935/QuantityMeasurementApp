/**
 * QuantityMeasurementApp
 *
 * UC6: Addition of Length Units
 *
 * Adds two quantities (same category) and returns result
 * in unit of first operand.
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

        private double toFeet() {
            return unit.toFeet(value);
        }

        // -------- ADD METHOD --------
        public Quantity add(Quantity other) {

            if (other == null) {
                throw new IllegalArgumentException("Second operand cannot be null");
            }

            // Step 1: convert both to base (feet)
            double sumInFeet = this.toFeet() + other.toFeet();

            // Step 2: convert back to THIS unit
            double resultValue = this.unit.fromFeet(sumInFeet);

            return new Quantity(resultValue, this.unit);
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

        System.out.println(q1.add(q2)); // 2 feet

        Quantity q3 = new Quantity(12.0, LengthUnit.INCH);
        Quantity q4 = new Quantity(1.0, LengthUnit.FEET);

        System.out.println(q3.add(q4)); // 24 inches

        Quantity q5 = new Quantity(1.0, LengthUnit.YARD);
        Quantity q6 = new Quantity(3.0, LengthUnit.FEET);

        System.out.println(q5.add(q6)); // 2 yards

        Quantity q7 = new Quantity(2.54, LengthUnit.CENTIMETER);
        Quantity q8 = new Quantity(1.0, LengthUnit.INCH);

        System.out.println(q7.add(q8)); // ~5.08 cm
    }
}