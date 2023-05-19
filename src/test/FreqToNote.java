package test;

public class FreqToNote {
    private static final String[] NOTE_VALUES = {"C", "C♯", "D", "E♭", "E", "F", "F♯", "G", "A♭", "A", "B♭", "B"};
    private static final short DEFAULT_FREQUENCY_A4 = 440;

    public static void main(String[] args) {
        System.out.println(freqToNote(550));
    }

    public static String freqToNote(double frequency_Hz) {
        return freqToNote(frequency_Hz, DEFAULT_FREQUENCY_A4);
    }
    
    public static String freqToNote(double frequency_Hz, double frequency_A4) {
        if (frequency_Hz <= 0 || frequency_A4 <= 0) {
            throw new IllegalArgumentException("Argument must be positive number.");
        }
        final double freqC4 = frequency_A4 * Math.pow(2, -3d / 4d);
        final double pitch = 1200 * (log2(frequency_Hz) - log2(freqC4));
        final String noteValue = NOTE_VALUES[(int)Math.floor(mod(pitch + 50, 1200) / 100)];
        final double deviation = mod(pitch + 50, 100) - 50;
        final int octave = (int)Math.floor((pitch + 50) / 1200) + 4;
        return String.format("%s%d %s%f", noteValue, octave, deviation > 0 ? "+" : "", deviation);
    }

    private static double mod(double dividend, double divisor) {
        double calc = dividend % divisor;
        return calc + (calc < 0 ? divisor : 0);
    }
    private static double log2(double x) {
        return Math.log(x) / Math.log(2);
    }
}
