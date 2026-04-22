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

    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite");

            this.value = value;
            this.unit = unit;
        }

        public double getValue() {
            return value;
        }

        public LengthUnit getUnit() {
            return unit;
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        // 🔹 Private utility method (DRY)
        private static QuantityLength addInternal(QuantityLength q1,
                                                  QuantityLength q2,
                                                  LengthUnit targetUnit) {

            double sumFeet = q1.toFeet() + q2.toFeet();
            double resultValue = targetUnit.fromFeet(sumFeet);

            return new QuantityLength(resultValue, targetUnit);
        }

        // ✅ UC6 (default → first operand unit)
        public QuantityLength add(QuantityLength other) {
            if (other == null) {
                throw new IllegalArgumentException("Second operand cannot be null");
            }
            return addInternal(this, other, this.unit);
        }

        // ✅ UC7 (explicit target unit)
        public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
            if (other == null) {
                throw new IllegalArgumentException("Second operand cannot be null");
            }
            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }
            return addInternal(this, other, targetUnit);
        }

        // Static version
        public static QuantityLength add(QuantityLength q1,
                                         QuantityLength q2,
                                         LengthUnit targetUnit) {
            if (q1 == null || q2 == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid input");
            }
            return addInternal(q1, q2, targetUnit);
        }

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

    // Demo method
    public static void demonstrateAddition(QuantityLength q1,
                                           QuantityLength q2,
                                           LengthUnit targetUnit) {

        QuantityLength result = q1.add(q2, targetUnit);

        System.out.println("Input: add(" + q1 + ", " + q2 + ", " + targetUnit + ")");
        System.out.println("Output: " + result);
        System.out.println();
    }

    public static void main(String[] args) {

        demonstrateAddition(
                new QuantityLength(1.0, LengthUnit.FEET),
                new QuantityLength(12.0, LengthUnit.INCH),
                LengthUnit.FEET);

        demonstrateAddition(
                new QuantityLength(1.0, LengthUnit.FEET),
                new QuantityLength(12.0, LengthUnit.INCH),
                LengthUnit.INCH);

        demonstrateAddition(
                new QuantityLength(1.0, LengthUnit.FEET),
                new QuantityLength(12.0, LengthUnit.INCH),
                LengthUnit.YARD);

        demonstrateAddition(
                new QuantityLength(1.0, LengthUnit.YARD),
                new QuantityLength(3.0, LengthUnit.FEET),
                LengthUnit.YARD);

        demonstrateAddition(
                new QuantityLength(36.0, LengthUnit.INCH),
                new QuantityLength(1.0, LengthUnit.YARD),
                LengthUnit.FEET);

        demonstrateAddition(
                new QuantityLength(2.54, LengthUnit.CENTIMETER),
                new QuantityLength(1.0, LengthUnit.INCH),
                LengthUnit.CENTIMETER);
    }
}