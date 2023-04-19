package range;

public class Range {
    private final double start;
    private final double end;
    private final boolean isStartClosed;
    private final boolean isEndClosed;
    private static final IllegalArgumentException IAE = new IllegalArgumentException();
    public static final double INF = Double.POSITIVE_INFINITY;

    public Range(double start, double end) {
        if (start > end) {
            throw IAE;
        }
        this.start = start;
        this.end = end;
        this.isStartClosed = true;
        this.isEndClosed = true;
    }
    public Range(double start, double end, boolean isIntervalClosed) {
        if (start > end || start == end && !isIntervalClosed) {
            throw IAE;
        }
        this.start = start;
        this.end = end;
        this.isEndClosed = isIntervalClosed;
        this.isStartClosed = isIntervalClosed;
    }
    public Range(double start, double end, boolean isStartClosed, boolean isEndClosed) {
        if (start > end || start == end && !isStartClosed && !isEndClosed) {
            throw IAE;
        }
        this.start = start;
        this.end = end;
        this.isStartClosed = isStartClosed;
        this.isEndClosed = isEndClosed;
    }
    @Override
    public String toString() {
        final String Start = String.valueOf(this.start).trim().replaceFirst(".0$", "");
        final String End = String.valueOf(this.end).trim().replaceFirst(".0$", "");
        final String startSymbol = this.isStartClosed ? "[" : "(";
        final String endSymbol = this.isEndClosed ? "]" : ")";
        return "Range %s%s, %s%s".formatted(startSymbol, Start, End, endSymbol);
    }

    public boolean isContained(double num) {
        return
            (this.isStartClosed ? this.start <= num : this.start < num)
            && (this.isEndClosed ? num <= this.end : num < this.end);
    }

}
