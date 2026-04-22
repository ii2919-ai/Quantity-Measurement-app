public class Quantity_Measurement_app {

    // Inner class representing Feet measurement
    static class Feet {
        private final double value;

        // Constructor
        public Feet(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        // Overriding equals() method
        @Override
        public boolean equals(Object obj) {

            // Step 1: Same reference check
            if (this == obj) {
                return true;
            }

            // Step 2: Null or different class check
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            // Step 3: Type casting
            Feet other = (Feet) obj;

            // Step 4: Compare using Double.compare()
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // Main method
    public static void main(String[] args) {

        // Test values
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);

        // Equality check
        boolean result = f1.equals(f2);

        // Output
        System.out.println("Input: " + f1.getValue() + " ft and " + f2.getValue() + " ft");
        System.out.println("Output: Equal (" + result + ")");
    }
}
