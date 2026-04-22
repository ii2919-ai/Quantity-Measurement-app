public class Quantity_Measurement_app {

    // Feet class
    static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;

            if (obj == null || getClass() != obj.getClass()) return false;

            Feet other = (Feet) obj;

            return Double.compare(this.value, other.value) == 0;
        }
    }

    // Inches class
    static class Inches {
        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;

            if (obj == null || getClass() != obj.getClass()) return false;

            Inches other = (Inches) obj;

            return Double.compare(this.value, other.value) == 0;
        }
    }

    // Static method for Feet equality
    public static boolean compareFeet(double v1, double v2) {
        Feet f1 = new Feet(v1);
        Feet f2 = new Feet(v2);
        return f1.equals(f2);
    }

    // Static method for Inches equality
    public static boolean compareInches(double v1, double v2) {
        Inches i1 = new Inches(v1);
        Inches i2 = new Inches(v2);
        return i1.equals(i2);
    }

    // Main method
    public static void main(String[] args) {

        // Inches comparison
        double inch1 = 1.0;
        double inch2 = 1.0;

        boolean inchResult = compareInches(inch1, inch2);
        System.out.println("Input: " + inch1 + " inch and " + inch2 + " inch");
        System.out.println("Output: Equal (" + inchResult + ")");

        // Feet comparison
        double feet1 = 1.0;
        double feet2 = 1.0;

        boolean feetResult = compareFeet(feet1, feet2);
        System.out.println("Input: " + feet1 + " ft and " + feet2 + " ft");
        System.out.println("Output: Equal (" + feetResult + ")");
    }
}