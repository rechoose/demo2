package cn.gw.demo2.math;

import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * 超过64位得数字无法计算  long_max = 9223372036854775807
 * 设计本类,用于计算超大数字计算,仅限制于整数
 * 用int数组表示,每个值仅0~9
 * 123
 * 1234
 */
public class BigMath {

    private static final int[] DEFAULT_ZERO = {0};
    private static final int[] DEFAULT_EMPTY = {};
    private int MAX_INDEX = 100;
    private int MIN_INDEX = 1;
    private int size = 1;
    private int[] figure;
    private boolean upper;//0或者正数:true

    public BigMath(String numStr) {
        if (StringUtils.isEmpty(numStr)) {
            new BigMath();
        } else {
            figure = DEFAULT_EMPTY;
            size = 0;
            int upperIndex = numStr.indexOf("-");
            this.upper = upperIndex != 0;
            char[] sb = numStr.toCharArray();
            figure = Arrays.copyOf(figure, sb.length);
            for (int i = 0; i < sb.length; i++) {
                char aChar = sb[i];
                int value = aChar - '0';
                if (value >= 0 && value <= 9) {
                    figure[size++] = value;
                }
            }
            figure = Arrays.copyOf(figure, size);
        }
    }

    public BigMath() {
        this.upper = true;
        this.figure = DEFAULT_ZERO;
    }

    private BigMath(int[] data, boolean upper) {
        size = data.length;
        this.upper = upper;
        this.figure = data;
    }

    public boolean isUpper() {
        return upper;
    }

    public String getValue() {
        if (upper) {
            return getValueAbs();
        } else {
            return "-" + getValueAbs();
        }
    }

    public String getValueAbs() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < size; i++) {
            buffer.append(figure[i]);
        }
        return buffer.toString();
    }

    private int[] getFigure() {
        return figure;
    }

    public int getSize() {
        return size;
    }

    private void setUpper(boolean upper) {
        this.upper = upper;
    }

    public BigMath minus(BigMath math) {
        BigMath bigMath = new BigMath(math.getFigure(), !math.isUpper());
        BigMath plus = this.plus(bigMath);
        return plus;
    }

    public BigMath plus(BigMath math) {
        boolean resultUpper = true;
        boolean upper1 = this.upper;
        boolean upper2 = math.isUpper();
        if (upper1 == upper2) {//--或者++
            if (!upper1) {//--
                resultUpper = false;
            }
            int[] plus = this.plus(math.getFigure());
            return new BigMath(plus, resultUpper);
        } else {//+-
            if (this.getValueAbs().equals(math.getValueAbs())) {
                return new BigMath();
            } else {
                if (upper1) {
                    if (this.size < math.getSize()) {
                        resultUpper = false;
                    }
                    if (this.size == math.getSize()) {
                        resultUpper = max(this.figure, math.getFigure());
                    }
                } else {
                    if (math.getSize() < this.size) {
                        resultUpper = false;
                    }
                    if (math.getSize() == this.size) {
                        resultUpper = max(math.getFigure(), this.figure);
                    }
                }
                int[] plus = this.minus(math.getFigure());
                return new BigMath(plus, resultUpper);
            }
        }
    }

    public BigMath multiple(BigMath math) {
        BigMath start = new BigMath();
        if ("0".equals(this.getValue()) || "0".equals(math.getValue())) {
            return start;
        }
        BigMath max = new BigMath(this.getFigure(), true);
        BigMath min = new BigMath(math.getFigure(), true);
        boolean maxFlag = max(this.figure, math.getFigure());
        if (!maxFlag) {
            min = new BigMath(this.getFigure(), true);
            max = new BigMath(math.getFigure(), true);
        }
        BigMath one = new BigMath("1");
        do {
            start = start.plus(max);
            min = min.minus(one);
        } while (!"0".equals(min.getValueAbs()));
        if (this.upper != math.isUpper()) {//--或者++
            start.setUpper(false);
        }
        return start;
    }

    private boolean max(int[] thisOnes, int[] otherOnes) {
        for (int i = 0; i < thisOnes.length; i++) {
            if (thisOnes[i] > otherOnes[i]) {
                return true;
            }
            if (thisOnes[i] < otherOnes[i]) {
                return false;
            }
        }
        return true;
    }

    //减法
    private int[] minus(int[] other) {
        int[] maxOne = figure;
        int[] minOne = other;
        if (size < other.length) {
            maxOne = other;
            minOne = figure;
        }
        int maxIndex = maxOne.length;
        int minIndex = minOne.length;
        int resultSize = maxIndex;
        int[] result = new int[resultSize];
        int jiewei = 0;
        for (int i = maxIndex - 1, j = minIndex - 1, z = result.length - 1; i >= 0; i--, z--, j--) {
            int maxG = maxOne[i];
            if (j >= 0) {
                int minG = minOne[j];
                int sum = 0;
                if (maxG >= minG + jiewei) {
                    sum = maxG - minG - jiewei;
                    jiewei = 0;
                } else {
                    sum = 10 + maxG - minG - jiewei;
                    jiewei = 1;
                }
                result[z] = sum;
            } else {
                int sum = 0;
                if (maxG >= jiewei) {
                    sum = maxG - jiewei;
                    jiewei = 0;
                } else {
                    sum = 10 + maxG - jiewei;
                    jiewei = 1;
                }
                result[z] = sum;
            }
        }
        int youxiaoIndex = -1;
        for (int i = 0; i < resultSize; i++) {
            if (result[i] == 0) continue;
            youxiaoIndex = i;
            break;
        }
        if (youxiaoIndex <= 0) {
            return result;
        } else {
            int[] ints = new int[resultSize - youxiaoIndex];
            System.arraycopy(result, youxiaoIndex, ints, 0, resultSize - youxiaoIndex);
            return ints;
        }
    }

    //加法
    private int[] plus(int[] other) {
        int[] maxOne = figure;
        int[] minOne = other;
        if (size < other.length) {
            maxOne = other;
            minOne = figure;
        }
        int maxIndex = maxOne.length;
        int minIndex = minOne.length;
        int resultSize = maxIndex + 1;
        int[] result = new int[resultSize];
        int jinwei = 0;
        for (int i = maxIndex - 1, j = minIndex - 1, z = result.length - 1; i >= 0; j--) {
            int maxG = maxOne[i];
            if (j >= 0) {
                int minG = minOne[j];
                int sum = maxG + minG + jinwei;
                int g = sum % 10;
                jinwei = sum / 10;
                result[z] = g;
            } else {
                int sum = maxG + jinwei;
                int g = sum % 10;
                jinwei = sum / 10;
                result[z] = g;
            }
            i--;
            z--;
            if (i < 0) {
                if (jinwei <= 0) {
                    resultSize = resultSize - 1;
                } else {
                    result[z] = jinwei;
                }
            }
        }
        if (resultSize <= maxIndex) {
            int[] ints = new int[resultSize];
            System.arraycopy(result, 1, ints, 0, result.length - 1);
            return ints;
        }
        return result;
    }


    public static void main(String[] args) {
//        String num1 = "9223372036854775807";
        String num1 = "92233728";
        String num2 = "-21";
        BigMath math = new BigMath(num1);
        System.out.println(math.toString());

        BigMath math1 = new BigMath(num2);
        System.out.println(math1.toString());
        System.out.println("+++++++++++++++++++");
        long l0 = System.currentTimeMillis();
        BigMath add1 = math.plus(math1);
        System.out.println(" ( " + num1 + " ) + ( " + num2 + " ) = " + add1.toString() + "   >>>>>耗时:" + (System.currentTimeMillis() - l0));

        long l1 = System.currentTimeMillis();
        BigMath minus = math.minus(math1);
        System.out.println(" ( " + num1 + " ) - ( " + num2 + " ) = " + minus.toString() + "   >>>>>耗时:" + (System.currentTimeMillis() - l1));

        long l2 = System.currentTimeMillis();
        BigMath multiple1 = math.multiple(math1);
        System.out.println(" ( " + num1 + " ) * ( " + num2 + " ) = " + multiple1.toString() + "   >>>>>耗时:" + (System.currentTimeMillis() - l2));

        long l3 = System.currentTimeMillis();
        BigMath multiple2 = math1.multiple(math);
        System.out.println(" ( " + num2 + " ) * ( " + num1 + " ) = " + multiple2.toString() + "   >>>>>耗时:" + (System.currentTimeMillis() - l3));

        long l4 = System.currentTimeMillis();
        BigMath multiple = math.multiple(math);
        System.out.println(" ( " + num1 + " ) * ( " + num1 + " ) = " + multiple.toString() + "   >>>>>耗时:" + (System.currentTimeMillis() - l4));
    }

    @Override
    public String toString() {
        return getValue();
    }
}
