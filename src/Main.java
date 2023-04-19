import complex.Complex;
import range.Range;

public class Main {
    public static void main(String[] args) {
        final Complex a = new Complex(1, 2);
        final Range r = new Range(1, 2);
        System.out.println(r.isContained(2));
    }
}