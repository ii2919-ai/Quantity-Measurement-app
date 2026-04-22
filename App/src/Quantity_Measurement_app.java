public class Quantity_Measurement_app {

    // ✅ Standalone Enum (inside same file for single-file requirement)
    enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(1.0 / 30.48);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
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

    // ✅ QuantityLength Class
    static final class QuantityLength {

        private final double value;
        private final LengthUnit unit;
        private static final double EPSILON = 1e-6;

        public QuantityLength(double value, LengthUnit unit) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid numeric value");
            }
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        // Convert to another unit
        public QuantityLength convertTo(LengthUnit targetUnit) {
            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double base = unit.convertToBaseUnit(value);
            double converted = targetUnit.convertFromBaseUnit(base);

            return new QuantityLength(converted, targetUnit);
        }

        // Static conversion method (UC5)
        public static double convert(double value, LengthUnit source, LengthUnit target) {
            if (!Double.isFinite(value) || source == null || target == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            double base = source.convertToBaseUnit(value);
            return target.convertFromBaseUnit(base);
        }

        // Addition with target unit (UC7)
        public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
            if (other == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            double base1 = this.unit.convertToBaseUnit(this.value);
            double base2 = other.unit.convertToBaseUnit(other.value);

            double sumBase = base1 + base2;
            double result = targetUnit.convertFromBaseUnit(sumBase);

            return new QuantityLength(result, targetUnit);
        }

        // Equality check (UC3+)
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            double base1 = this.unit.convertToBaseUnit(this.value);
            double base2 = other.unit.convertToBaseUnit(other.value);

            return Math.abs(base1 - base2) < EPSILON;
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // ✅ Main Method (Testing all UC features)
    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        // Equality
        System.out.println("Equality: " + q1.equals(q2));

        // Conversion
        double inches = QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCHES);
        System.out.println("1 foot in inches = " + inches);

        // Instance conversion
        System.out.println("Converted: " + q1.convertTo(LengthUnit.INCHES));

        // Addition (different target units)
        System.out.println("Add in FEET: " + q1.add(q2, LengthUnit.FEET));
        System.out.println("Add in INCHES: " + q1.add(q2, LengthUnit.INCHES));
        System.out.println("Add in YARDS: " + q1.add(q2, LengthUnit.YARDS));

        // CM example
        QuantityLength cm = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
        QuantityLength inch = new QuantityLength(1.0, LengthUnit.INCHES);

        System.out.println("CM == Inch: " + cm.equals(inch));
        System.out.println("Add in CM: " + cm.add(inch, LengthUnit.CENTIMETERS));
    }
}