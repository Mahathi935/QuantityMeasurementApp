/**
 * QuantityMeasurementApp
 *
 * UC2: Feet and Inches Equality (with cross-unit comparison)
 *
 * Supports:
 * 1. Feet vs Feet
 * 2. Inches vs Inches
 * 3. Feet vs Inches (conversion)
 *
 * @author Mahathi
 * @version 1.0
 */

public class QuantityMeasurementApp {

    // -------- FEET CLASS --------
    static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        public double toInches() {
            return value * 12;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;

            if (obj instanceof Feet) {
                Feet other = (Feet) obj;
                return Double.compare(this.value, other.value) == 0;
            }

            if (obj instanceof Inches) {
                Inches other = (Inches) obj;
                return Double.compare(this.toInches(), other.value) == 0;
            }

            return false;
        }
    }

    // -------- INCHES CLASS --------
    static class Inches {
        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        public double toFeet() {
            return value / 12;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;

            if (obj instanceof Inches) {
                Inches other = (Inches) obj;
                return Double.compare(this.value, other.value) == 0;
            }

            if (obj instanceof Feet) {
                Feet other = (Feet) obj;
                return Double.compare(this.value, other.toInches()) == 0;
            }

            return false;
        }
    }

    // -------- STATIC METHODS --------
    public static boolean compareFeet(double v1, double v2) {
        return new Feet(v1).equals(new Feet(v2));
    }

    public static boolean compareInches(double v1, double v2) {
        return new Inches(v1).equals(new Inches(v2));
    }

    public static boolean compareFeetAndInches(double feet, double inches) {
        return new Feet(feet).equals(new Inches(inches));
    }

    // -------- MAIN --------
    public static void main(String[] args) {

        System.out.println("Feet vs Feet: " + compareFeet(1.0, 1.0));
        System.out.println("Inches vs Inches: " + compareInches(1.0, 1.0));
        System.out.println("Feet vs Inches (1 ft vs 12 in): " +
                compareFeetAndInches(1.0, 12.0));
    }
}