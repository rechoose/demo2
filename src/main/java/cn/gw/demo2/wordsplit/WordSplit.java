package cn.gw.demo2.wordsplit;

import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.SegmentationAlgorithm;
import org.apdplat.word.segmentation.Word;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

//分词
public class WordSplit {

    private List<String> titleHandle(ArrayList<String> titles) {
        ArrayList<String> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(titles)) return result;
        for (String title : titles) {
            List<String> strings = this.wordSplit(title);
            String newWord = strings.get(0);
            if (newWord.length() > 1) {
                if (CollectionUtils.isEmpty(result)) {
                    result.add(newWord);
                } else {
                    for (int i = 0; i < result.size(); i++) {
                        String has = result.get(i);
                        if (has.contains(newWord) && has.length() >= newWord.length()) {
                            result.remove(has);
                            i--;
                        } else if (newWord.contains(has) && newWord.length() >= has.length()) {
                            continue;
                        }
                    }
                    result.add(newWord);
                }
            }
            if (result.size() >= 10) break;
        }
        return result;
    }

    private List<String> wordSplit(String title) {
        //word分词器
        ArrayList<String> result = new ArrayList<>();
        for (Word word : WordSegmenter.segWithStopWords(title, SegmentationAlgorithm.BidirectionalMaximumMatching)) {
            result.add(word.getText());
        }
        return result;
    }

    /*public static void main(String[] args) throws IOException {
        StringBuilder result = new StringBuilder();
        String text="每种模式的分词结果都可能不相同，第一个方法忽略分词器模式，返回所有模式的所有不重复分词结果";
        StringReader reader=new StringReader(text);
        //ik分词
        IKSegmenter ik = new IKSegmenter(reader, true);
        try {
            Lexeme word = null;
            while((word=ik.next())!=null) {
                result.append(word.getLexemeText()).append(" ");
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println(result);
        //word分词器
        StringBuilder result1 = new StringBuilder();
        for(Word word : WordSegmenter.segWithStopWords(text, SegmentationAlgorithm.BidirectionalMaximumMatching)){
            result1.append(word.getText()).append(" ");
        }
        System.out.println(result1);
        return ;
    }*/

    //ik
    //每种  模式 的 分词 结果 都可 能不  相同 第一个    方法 忽略 分词器 模式 返回 所有  模式 的 所有 不 重复 分词 结果    //true
    //每种  模式 的 分词 结果 都可 可能 能不 不相同 不相 相同 第一个 第一 一个 一 个 方法 忽略 分词器 分词 器 模式 返回 所有 有 模式 的 所有 有 不重 重复 分词 结果   /false
    //word
    //每 种 模式 的 分词 结果 都 可能 不 相同 第一 个 方法 忽略 分词器 模式 返回 所 有 模式 的 所有 不 重复 分词 结果   //全切分算法
    //每种  模式 的 分词 结果 都可 能不 相同 第一个   方法 忽略 分词器 模式 返回 所有 模式 的 所有 不重 复 分词 结果    //正向最大匹配算法
    //每种  模式 的 分词 结果 都 可能 不相同 第一个 方法 忽略 分词器 模式 返回 所有 模式 的 所有 不重 复 分词 结果      //BidirectionalMaximumMatching

}
