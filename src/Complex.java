public class Complex extends Number{
    private final Double re;
    private final Double im;

    public int intValue() {
        return this.re.intValue();
    }
    public long longValue() {
        return this.re.longValue();
    }
    public float floatValue() {
        return this.re.floatValue();
    }
    public double doubleValue() {
        return this.re;
    }

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }
    public Complex(double re) {
        this.re = re;
        this.im = 0D;
    }
    public Complex() {
        this.re = 0D;
        this.im = 0D;
    }
    @Override
    public String toString() {
        String realStr = this.re.equals(0D) ? ""
                : (this.re % 1 == 0 ? String.valueOf(this.re.longValue()) : String.valueOf(this.re)).replace("-", "- ");
        String imagStr = this.im.equals(0D) ? ""
                : (this.im % 1 == 0 ? String.valueOf(this.im.longValue()) : String.valueOf(this.im)).replace("-", "- ") + "i";
        String operator = " ";
        if (realStr.equals("") && imagStr.equals("")) {
            operator = "0";
        } else if (!realStr.equals("") && !imagStr.equals("") && this.im.compareTo(0D) > 0) {
            operator = " + ";
        }
        return (realStr + operator + imagStr).trim().replace(" 1i", " i");
    }

    public static Complex toComplex(double number) {
        return new Complex(number, 0);
    }

    public Complex plus(Complex complex) {
        return new Complex(this.re + complex.re, this.im + complex.im);
    }
    public Complex minus(Complex complex) {
        return new Complex(this.re - complex.re, this.im - complex.im);
    }
    public Complex times(Complex complex) {
        final double realResult = this.re * complex.re - this.im * complex.im;
        final double imagResult = this.re * complex.im + this.im * complex.re;
        return new Complex(realResult, imagResult);
    }
    public boolean isZero() {
        return this.re.equals(0D) && this.isRealNumber();
    }
    public boolean isRealNumber() {
        return this.im.equals(0D);
    }
    public Complex dividedBy(Complex complex) {
        if (complex.isZero()) {
            throw new ArithmeticException("/ by Zero");
        }
        double denominator = (Math.pow(complex.re, 2) + Math.pow(complex.im, 2));
        double realResult = (this.re * complex.re + this.im * complex.im) / denominator;
        double imagResult = (-this.re * complex.im + this.im * complex.re) / denominator;
        return new Complex(realResult, imagResult);
    }
}
