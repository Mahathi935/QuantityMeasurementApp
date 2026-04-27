/**
 * QuantityMeasurementApp
 *
 * UC3: Generic Quantity Class using DRY Principle
 *
 * Supports multiple units using enum and conversion.
 *
 * @author Mahathi
 * @version 1.0
 */

public class QuantityMeasurementApp {

    // -------- ENUM FOR UNITS --------
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0); // base = feet

        private final double toFeetFactor;

        LengthUnit(double factor) {
            this.toFeetFactor = factor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    // -------- GENERIC QUANTITY CLASS --------
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {

            // same reference
            if (this == obj) return true;

            // null or different type
            if (obj == null || this.getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            // compare after conversion
            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }
    }

    // -------- MAIN --------
    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);

        System.out.println("1 ft == 12 in ? " + q1.equals(q2));

        Quantity q3 = new Quantity(1.0, LengthUnit.INCH);
        Quantity q4 = new Quantity(1.0, LengthUnit.INCH);

        System.out.println("1 in == 1 in ? " + q3.equals(q4));
    }
}