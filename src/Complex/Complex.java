package Complex;

/**
 * 複素数クラス
 *
 * @author Nao Kurosawa
 */
public class Complex extends Number {
    private final double re;
    private final double im;
    private static final ArithmeticException ae = new ArithmeticException("/ by zero");
    public static final Complex ONE = Complex.valueOf(1);
    public static final Complex I = new Complex(0, 1);

    /**
     * 実部ゲッター
     *
     * @return 実部
     */
    public double getRealPart() {
        return this.re;
    }

    /**
     * 虚部ゲッター
     *
     * @return 虚部
     */
    public double getImaginaryPart() {
        return this.im;
    }

    public int intValue() {
        return Double.valueOf(this.re).intValue();
    }

    public long longValue() {
        return Double.valueOf(this.re).longValue();
    }

    public float floatValue() {
        return Double.valueOf(this.re).floatValue();
    }

    public double doubleValue() {
        return this.re;
    }

    /**
     * 複素数を生成
     *
     * @param re 実部
     * @param im 虚部
     */
    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    /**
     * 複素数 0 を生成
     */
    public Complex() {
        this.re = 0;
        this.im = 0;
    }

    /**
     * 複素数を “a + bi” の形で文字列化
     *
     * @return 複素数文字列
     */
    @Override
    public String toString() {
        String realStr = this.re == 0 ? ""
                : (this.re % 1 == 0 ? String.valueOf((long) this.re) : String.valueOf(this.re)).replace("-", "- ");
        String imagStr = this.im == 0 ? ""
                : (this.im % 1 == 0 ? String.valueOf((long) this.im) : String.valueOf(this.im)).replace("-", "- ") + "i";
        String operator = " ";
        if (realStr.equals("") && imagStr.equals("")) {
            operator = "0";
        } else if (!realStr.equals("") && !imagStr.equals("") && this.im > 0) {
            operator = " + ";
        }
        return (realStr + operator + imagStr).trim().replace("1i", "i");
    }

    /**
     * 数値型からComplexクラスに変換
     *
     * @param realNum 数値
     * @return Complexクラス（虚部は0）
     */
    public static Complex valueOf(double realNum) {
        return new Complex(realNum, 0);
    }

    /**
     * 文字列で表された複素数をComplexクラスに変換
     *
     * @param complexStr Complexに変換したい文字列
     * @return Complexクラス
     * @throws NumberFormatException 変換できない文字列が与えられた場合
     */
    public static Complex valueOf(String complexStr) {
        try {
            final String trimmedComplexStr = complexStr.trim();
            final String realPartStr = trimmedComplexStr.replaceFirst("[+-]?\\s?(\\d+(\\.\\d+)?)?i", "")
                    .replaceAll("\\s", "");
            final double realPart = realPartStr.isBlank() ? 0 : Double.parseDouble(realPartStr);
            String imagPartStr = trimmedComplexStr.replaceFirst(realPartStr, "")
                    .replaceAll("\\s", "");
            imagPartStr = imagPartStr.replaceFirst("i", imagPartStr.matches("[+-]?i") ? "1" : "");
            final double imagPart = imagPartStr.isBlank() ? 0 : Double.parseDouble(imagPartStr);
            return new Complex(realPart, imagPart);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException("\"%s\" is not parsable string of complex number.".formatted(complexStr));
        }
    }

    /**
     * 複素数の加算
     *
     * @param complex 足す数
     * @return 和
     */
    public Complex plus(Complex complex) {
        return new Complex(this.re + complex.re, this.im + complex.im);
    }

    /**
     * 複素数の減算
     *
     * @param complex 引く数
     * @return 差
     */
    public Complex minus(Complex complex) {
        return new Complex(this.re - complex.re, this.im - complex.im);
    }

    /**
     * 複素数の乗算
     *
     * @param complex かける数
     * @return 積
     */
    public Complex times(Complex complex) {
        final double realResult = this.re * complex.re - this.im * complex.im;
        final double imagResult = this.re * complex.im + this.im * complex.re;
        return new Complex(realResult, imagResult);
    }

    /**
     * 複素数の値が0か
     *
     * @return 0なら：TRUE
     */
    public boolean isZero() {
        return this.re == 0 && isReal();
    }

    /**
     * 複素数が実数かどうか
     *
     * @return 実数なら：TRUE
     */
    public boolean isReal() {
        return this.im == 0;
    }

    /**
     * 複素数が虚数かどうか
     *
     * @return 虚数なら：TRUE
     */
    public boolean isImaginary() {
        return !isReal();
    }

    /**
     * 複素数の除算
     *
     * @param complex 割る数
     * @return 商
     * @throws ArithmeticException 引数に0が指定された場合
     */
    public Complex dividedBy(Complex complex) {
        if (complex.isZero()) {
            throw ae;
        }
        double denominator = (Math.pow(complex.re, 2) + Math.pow(complex.im, 2));
        double realResult = (this.re * complex.re + this.im * complex.im) / denominator;
        double imagResult = (-this.re * complex.im + this.im * complex.re) / denominator;
        return new Complex(realResult, imagResult);
    }

    /**
     * 共役複素数を計算
     *
     * @return 共役複素数
     */
    public Complex conjugate() {
        return new Complex(this.re, -this.im);
    }

    /**
     * 複素数の絶対値を計算
     *
     * @return 絶対値（double）
     */
    public double abs() {
        return Math.sqrt(Math.pow(this.re, 2) + Math.pow(this.im, 2));
    }

    /**
     * 2つの複素数の値が等しいか
     *
     * @param obj 比較対象の複素数
     * @return 値が等しければ：TRUE
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Complex && this.re == ((Complex) obj).re && this.im == ((Complex) obj).im;
    }

    /**
     * 複素数の平方根のうち実部が非負のもの
     *
     * @return Complex
     */
    public Complex sqrt() {
        final double c = this.re, d = this.im;
        final double constant = c + Math.sqrt(Math.pow(c, 2) + Math.pow(d, 2));
        final double a = Math.sqrt(constant / 2),
                b = c <= 0 && d == 0 ? Math.sqrt(-c) + 0 : d / Math.sqrt(2 * constant);
        return new Complex(a, b);
    }

    /**
     * 複素指数関数 e^z
     *
     * @return 複素数
     */
    public Complex exp() {
        final double a = this.re, b = this.im;
        return new Complex(Math.exp(a) * Math.cos(b), Math.exp(a) * Math.sin(b));
    }

    /**
     * 複素数の正弦（サイン）
     *
     * @return 複素数
     */
    public Complex sin() {
        final Complex A = (new Complex(-this.im, this.re)).exp(), // e^(iz)
                B = (new Complex(this.im, -this.re)).exp(); //e^(-iz)
        return new Complex((A.im - B.im) / 2, (B.re - A.re) / 2);
    }

    /**
     * 複素数の余弦（コサイン）
     *
     * @return 複素数
     */
    public Complex cos() {
        final Complex A = (new Complex(-this.im, this.re)).exp(), // e^(iz)
                B = (new Complex(this.im, -this.re)).exp(); //e^(-iz)
        return new Complex((A.re + B.re) / 2, (A.im + B.im) / 2);
    }

    /**
     * 複素数の正接（タンジェント）
     *
     * @return 複素数
     */
    public Complex tan() {
        return sin().dividedBy(cos());
    }

    /**
     * 複素数の整数乗
     *
     * @param a 指数
     * @return 複素数
     */
    public Complex pow(int a) {
        Complex result = ONE;
        for (int i = 1; i <= Math.abs(a); i++) {
            result = a > 0 ? result.times(this) : result.dividedBy(this);
        }
        return result;
    }

    /**
     * 複素数の偏角（ラジアン）
     *
     * @return ラジアン：(-π, π]
     */
    public double compArg() {
        if (isZero()) {
            throw ae;
        }
        final double x = this.re, y = this.im;
        if (x == 0) {
            return Math.signum(y) * Math.PI / 2;
        }
        return Math.atan(y / x) + (1 - Math.signum(x)) * (1 + Math.signum(y) + (y == 0 ? 0 : -1)) * Math.PI / 2;
    }

    /**
     * 複素数の逆数 1/z
     *
     * @return 逆数
     */
    public Complex reciprocal() {
        return ONE.dividedBy(this);
    }

    public static Complex polar(double radius, double angRad) {
        return new Complex(radius * Math.cos(angRad), radius * Math.sin(angRad));
    }
}
