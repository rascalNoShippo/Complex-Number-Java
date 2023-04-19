package range;

import exception.IllegalRangeException;

public class Range {
    private final double start;
    private final double end;
    private final boolean isStartClosed;
    private final boolean isEndClosed;
    private static final IllegalArgumentException IAE = new IllegalArgumentException("Arguments not specified correctly");
    private static final IllegalRangeException IRE = new IllegalRangeException("Absolute value of number is too large");
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

    public static Range valueOf(String rangeStr) {
        String[] rangeStArr = rangeStr.replaceAll("∞", "Infinity").split(",");
        final String startStr = rangeStArr[0].replaceFirst("Range", "").trim();
        final String endStr = rangeStArr[1].trim();
        final boolean isStartClosed = startStr.startsWith("[");
        final boolean isEndClosed = endStr.endsWith("]");
        final double start = Double.valueOf(startStr.replaceFirst("^[(\\[]", "").trim());
        final double end = Double.valueOf(endStr.replaceFirst("[)\\]]$", "").trim());
        return new Range(start, end, isStartClosed, isEndClosed);
    }

    public boolean isContained(double num) {
        return
            (this.isStartClosed ? this.start <= num : this.start < num)
            && (this.isEndClosed ? num <= this.end : num < this.end);
    }
    public boolean isContained(Range range) {
        return
            (isContained(range.start) || this.start == range.start && !range.isStartClosed)
            && (isContained(range.end) || range.end == this.end && !range.isEndClosed);
    }

    public boolean equals(Range range) {
        return isContained(range) && range.isContained(this);
    }

    public long[] toSerialIntArr() {
        if (Double.isInfinite(this.start) || Double.isInfinite(this.end)) {
            throw IRE;
        }
        final long start = Double.valueOf(this.start).longValue() + (isStartClosed && this.start % 1 == 0 ? 0 : 1);
        final long end = Double.valueOf(this.end).longValue() + (this.end % 1 != 0 || isEndClosed ? 0 : -1);
        if (end - start + 1 > Integer.MAX_VALUE) {
            throw IRE;
        }
        long[] resultArr = new long[(int)(end - start + 1)];
        for (int i = 0; i < end - start + 1; i++) {
            resultArr[i] = start + i;
        }
        return resultArr;
    }

}
