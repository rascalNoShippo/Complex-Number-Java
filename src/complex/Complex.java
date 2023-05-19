package complex;


import java.util.Objects;

/**
 * 複素数クラス
 *
 * @author Nao Kurosawa
 */
public class Complex extends Number {
    private final double re;
    private final double im;
    private static final ArithmeticException AE = new ArithmeticException("/ by zero");
    /**
     * 複素数 “1”
     */
    public static final Complex ONE = Complex.valueOf(1);
    /**
     * 複素数 “-1”
     */
    public static final Complex NEGATIVE_ONE = Complex.valueOf(-1);
    /**
     * 複素数 “i”（虚数単位）
     */
    public static final Complex I = new Complex(0, 1);
    /**
     * 複素数 “0”
     */
    public static final Complex ZERO = new Complex();

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
        return (int) this.re;
    }

    public long longValue() {
        return (long) this.re;
    }

    public float floatValue() {
        return (float) this.re;
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
                : this.re % 1 == 0 ? String.valueOf((long) this.re) : String.valueOf(this.re).replaceFirst("^-", "- ");
        String imagStr = this.im == 0 ? ""
                : (this.im % 1 == 0 ? String.valueOf((long) this.im) : String.valueOf(this.im).replaceFirst("^-", "- ")) + "i";
        String operator = " ";
        if (realStr.equals("") && imagStr.equals("")) {
            operator = "0";
        } else if (!realStr.equals("") && !imagStr.equals("") && this.im > 0) {
            operator = " + ";
        }
        return String.format("Complex (%s)", (realStr + operator + imagStr).trim().replace("1i", "i"));
    }

    /**
     * あらゆるNumber型からComplexクラスに変換
     *
     * @param number 数値
     * @return Complexクラス（numberがComplexならnumber自体、実数（int, floatなど）の場合は虚部を0とした複素数型）を返す
     */
    public static Complex valueOf(Number number) {
        return number instanceof Complex z
            ? z
            : new Complex(number.doubleValue(), 0);
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
     * @param summand 足す数
     * @return 和
     */
    public Complex add(Number summand) {
        final Complex z = Complex.valueOf(summand);
        return new Complex(this.re + z.re, this.im + z.im);
    }

    /**
     * 複素数の減算
     *
     * @param subtrahend 引く数
     * @return 差
     */
    public Complex subtract(Number subtrahend) {
        final Complex z = Complex.valueOf(subtrahend);
        return new Complex(this.re - z.re, this.im - z.im);
    }

    /**
     * 複素数の乗算
     *
     * @param multiplier かける数
     * @return 積
     */
    public Complex multiply(Number multiplier) {
        final Complex z = Complex.valueOf(multiplier);
        final double realResult = this.re * z.re - this.im * z.im;
        final double imagResult = this.re * z.im + this.im * z.re;
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
     * @param divisor 割る数
     * @return 商
     * @throws ArithmeticException 引数に0が指定された場合
     */
    public Complex divide(Number divisor) {
        final Complex z = Complex.valueOf(divisor);
        if (z.isZero()) {
            throw AE;
        }
        double denominator = (Math.pow(z.re, 2) + Math.pow(z.im, 2));
        double realResult = (this.re * z.re + this.im * z.im) / denominator;
        double imagResult = (-this.re * z.im + this.im * z.re) / denominator;
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

    public boolean equals(Object obj) {
        if (obj instanceof Number n) {
            final Complex z = Complex.valueOf(n);
            return this.re == z.re && this.im == z.im;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(re, im);
    }

    /**
     * 複素数の平方根のうち実部が非負のもの
     * 負の実数が与えられた場合は虚部が正のもの
     *
     * @return Complex
     */
    public Complex sqrt() {
        final double c = this.re;
        final double d = this.im;
        final double constant = c + Math.sqrt(Math.pow(c, 2) + Math.pow(d, 2));
        final double a = Math.sqrt(constant / 2);
        final double b = c <= 0 && d == 0 ? Math.sqrt(-c) : d / Math.sqrt(2 * constant);
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
        return sin().divide(cos());
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
            result = a > 0 ? result.multiply(this) : result.divide(this);
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
            throw AE;
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
        return ONE.divide(this);
    }

    /**
     * 極形式で表された複素数をComplexクラスで生成
     * @param radius r
     * @param angRad θ
     * @return Complex
     */
    public static Complex polar(double radius, double angRad) {
        return new Complex(radius * Math.cos(angRad), radius * Math.sin(angRad));
    }

    /**
     * 実部・虚部それぞれの丸めを実行
     * @param decimal 小数点以下何位まで残すかを指定
     * @return 丸められたComplex
     */
    public Complex round(int decimal) {
        final double pow10 = Math.pow(10, decimal);
        return new Complex(Math.round(this.re * pow10) / pow10, Math.round(this.im * pow10) / pow10);
    }
}
