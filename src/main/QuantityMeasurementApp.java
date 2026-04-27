/**
 * QuantityMeasurementApp
 *
 * UC4: Extended Unit Support (Yards & Centimeters)
 *
 * Demonstrates scalability of generic Quantity design.
 *
 * @author Mahathi
 * @version 1.0
 */

public class QuantityMeasurementApp {

    // -------- ENUM (UPDATED) --------
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),                 // 1 yard = 3 feet
        CENTIMETER(0.393701 / 12); // convert cm → inch → feet

        private final double toFeetFactor;

        LengthUnit(double factor) {
            this.toFeetFactor = factor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    // -------- SAME QUANTITY CLASS --------
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
            if (this == obj) return true;
            if (obj == null || this.getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;
            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }
    }

    // -------- MAIN --------
    public static void main(String[] args) {

        // Yard ↔ Feet
        System.out.println("1 yard == 3 feet: " +
                new Quantity(1.0, LengthUnit.YARD)
                        .equals(new Quantity(3.0, LengthUnit.FEET)));

        // Yard ↔ Inches
        System.out.println("1 yard == 36 inches: " +
                new Quantity(1.0, LengthUnit.YARD)
                        .equals(new Quantity(36.0, LengthUnit.INCH)));

        // CM ↔ Inches
        System.out.println("1 cm == 0.393701 inch: " +
                new Quantity(1.0, LengthUnit.CENTIMETER)
                        .equals(new Quantity(0.393701, LengthUnit.INCH)));

        // Same unit
        System.out.println("2 yards == 2 yards: " +
                new Quantity(2.0, LengthUnit.YARD)
                        .equals(new Quantity(2.0, LengthUnit.YARD)));
    }
}