package cn.gw.demo2.guava;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

public class Base {

    /**
     * 方法     * 描述
     * 例子
     * omitEmptyStrings()    * 移去结果中的空字符串
     * Splitter.on(',').omitEmptyStrings().split("a,,c,d") 返回 "a", "c", "d"
     * <p>
     * trimResults()     * 将结果中的空格删除，等价于trimResults(CharMatcher.WHITESPACE)
     * Splitter.on(',').trimResults().split("a, b, c, d") 返回 "a", "b", "c", "d"
     * <p>
     * trimResults(CharMatcher)     * 移除匹配字符
     * Splitter.on(',').trimResults(CharMatcher.is('_')).split("_a ,_b_ ,c__") 返回 "a ", "b_ ", "c"
     * <p>
     * limit(int)     * 达到指定数目后停止字符串的划分
     * Splitter.on(',').limit(3).split("a,b,c,d") 返回 "a", "b", "c,d"
     */

    //切割字符串
    public final static Splitter SPLITTER = Splitter.on(",").omitEmptyStrings().trimResults();//忽略null, 切割后的每个字符串trim
    //集合变为字符串
    public final static Joiner JOINER = Joiner.on(",").skipNulls();//跳过null


    public static void main(String[] args) {
        Iterable<String> split = SPLITTER.split("a,b , c,d,,,A B");
        String join = JOINER.join(split);
        System.out.println("t");
    }
}
