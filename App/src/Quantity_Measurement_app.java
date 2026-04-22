public class Quantity_Measurement_app {

    // Base unit: FEET
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.393701 / 12.0);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }

        public double fromFeet(double feetValue) {
            return feetValue / toFeetFactor;
        }
    }

    // Quantity class
    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Value must be finite");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        // Convert to another unit (instance method)
        public QuantityLength convertTo(LengthUnit targetUnit) {
            double feetValue = this.toFeet();
            double convertedValue = targetUnit.fromFeet(feetValue);
            return new QuantityLength(convertedValue, targetUnit);
        }

        // Override equals()
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            return Math.abs(this.toFeet() - other.toFeet()) < 1e-6;
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // Static conversion API
    public static double convert(double value, LengthUnit source, LengthUnit target) {

        if (source == null || target == null) {
            throw new IllegalArgumentException("Units cannot be null");
        }

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be finite");
        }

        double feetValue = source.toFeet(value);
        return target.fromFeet(feetValue);
    }

    // Overloaded methods (demonstration)

    // Method 1
    public static void demonstrateLengthConversion(double value,
                                                   LengthUnit from,
                                                   LengthUnit to) {
        double result = convert(value, from, to);
        System.out.println("Input: convert(" + value + ", " + from + ", " + to + ")");
        System.out.println("Output: " + result);
    }

    // Method 2
    public static void demonstrateLengthConversion(QuantityLength quantity,
                                                   LengthUnit target) {
        QuantityLength converted = quantity.convertTo(target);
        System.out.println("Input: " + quantity + " → " + target);
        System.out.println("Output: " + converted);
    }

    // Equality demo
    public static void demonstrateLengthEquality(QuantityLength q1,
                                                 QuantityLength q2) {
        System.out.println("Comparing: " + q1 + " and " + q2);
        System.out.println("Equal: " + q1.equals(q2));
    }

    // Main method
    public static void main(String[] args) {

        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCH);
        demonstrateLengthConversion(3.0, LengthUnit.YARD, LengthUnit.FEET);
        demonstrateLengthConversion(36.0, LengthUnit.INCH, LengthUnit.YARD);
        demonstrateLengthConversion(1.0, LengthUnit.CENTIMETER, LengthUnit.INCH);
        demonstrateLengthConversion(0.0, LengthUnit.FEET, LengthUnit.INCH);

        System.out.println();

        QuantityLength q = new QuantityLength(2.0, LengthUnit.YARD);
        demonstrateLengthConversion(q, LengthUnit.INCH);

        System.out.println();

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

        demonstrateLengthEquality(q1, q2);
    }
}