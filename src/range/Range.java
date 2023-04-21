package range;

import exception.IllegalRangeException;

/**
 * 実数（厳密には拡大実数）範囲クラス
 * @author kurosawanao
 */
public class Range {
    private final double start;
    private final double end;
    private final boolean isStartClosed;
    private final boolean isEndClosed;
    private static final IllegalArgumentException IAE = new IllegalArgumentException("Arguments not specified correctly");
    private static final IllegalRangeException IRE = new IllegalRangeException("Absolute value of number is too large");
    public static final double INF = Double.POSITIVE_INFINITY;

    /**
     * 実数の範囲（閉区間）
     * @param start 始点
     * @param end 終点
     */
    public Range(double start, double end) {
        if (start > end) {
            throw IAE;
        }
        this.start = start;
        this.end = end;
        this.isStartClosed = true;
        this.isEndClosed = true;
    }

    /**
     * 実数の範囲（開区間または閉区間）
     * @param start 始点
     * @param end 終点
     * @param isIntervalClosed 閉区間：TRUE　開区間：FALSE（端点を含まない）
     */
    public Range(double start, double end, boolean isIntervalClosed) {
        if (start > end || start == end && !isIntervalClosed) {
            throw IAE;
        }
        this.start = start;
        this.end = end;
        this.isEndClosed = isIntervalClosed;
        this.isStartClosed = isIntervalClosed;
    }

    /**
     * 実数の範囲（半開区間）
     * @param start 始点
     * @param end 終点
     * @param isStartClosed 始点を含むか
     * @param isEndClosed 終点を含むか
     */
    public Range(double start, double end, boolean isStartClosed, boolean isEndClosed) {
        if (start > end || start == end && !isStartClosed && !isEndClosed) {
            throw IAE;
        }
        this.start = start;
        this.end = end;
        this.isStartClosed = isStartClosed;
        this.isEndClosed = isEndClosed;
    }

    /**
     * 文字列化
     * 例：a ≦ x < b の範囲の場合は文字列 “Range [a, b)” を返す
     * @return 文字列
     */
    @Override
    public String toString() {
        final String Start = String.valueOf(this.start).trim().replaceFirst(".0$", "");
        final String End = String.valueOf(this.end).trim().replaceFirst(".0$", "");
        final String startSymbol = this.isStartClosed ? "[" : "(";
        final String endSymbol = this.isEndClosed ? "]" : ")";
        return "Range %s%s, %s%s".formatted(startSymbol, Start, End, endSymbol);
    }

    /**
     * 文字列で表された範囲をRangeに変換
     * @param rangeStr 変換したい文字列
     * @return Range
     */
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

    /**
     * 引数で指定した数値が範囲に含まれるか
     * @param num 判定する数値
     * @return 含まれる場合は：TRUE
     */
    public boolean isContained(double num) {
        return
            (this.isStartClosed ? this.start <= num : this.start < num)
            && (this.isEndClosed ? num <= this.end : num < this.end);
    }

    /**
     * 引数で指定したrangeはthisに含まれるか（部分集合であるか）
     * @param range 判定したいRangeクラス
     * @return range ⊆ this の真偽値
     */
    public boolean isContained(Range range) {
        return
            isContained(range.start) && isContained(range.end);
    }

    /**
     * 2つの範囲が一致するか
     * @param obj 比較したい範囲
     * @return 一致する場合：TRUE（開または閉が異なる場合はFALSE）
     */
    @Override
    public boolean equals(Object obj) {
        return
            obj instanceof Range range && isContained(range) && range.isContained(this);
    }

    /**
     * 始点から終点までの配列（整数列）
     * @param isOrderAsc 昇順：TRUE　降順：FALSE
     * @return long[]
     */
    public long[] toSerialIntArr(boolean isOrderAsc) {
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
            resultArr[i] = (isOrderAsc ? start + i : end - i);
        }
        return resultArr;
    }

    /**
     * toSerialIntArr() 引数省略パターン（昇順）
     * @return long[]
     */
    public long[] toSerialIntArr() {
        return toSerialIntArr(true);
    }

    public Range and(Range range) {
        if (!isContained(range.start) && !range.isContained(this.start)) {
            return null;
        }
        final double start = isContained(range.start) ? range.start : this.start;
        double end = isContained(range.end) ? range.end : this.end;
        boolean isStartClosed = isContained(range.start) ? range.isStartClosed : this.isStartClosed;
        boolean isEndClosed = isContained(range.end) ? range.isEndClosed : this.isEndClosed;
        return new Range(start, end, isStartClosed, isEndClosed);
    }

}
