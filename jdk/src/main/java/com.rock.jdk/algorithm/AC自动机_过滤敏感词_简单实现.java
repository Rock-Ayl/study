package com.rock.jdk.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * ac自动机 实现 敏感词过滤工具类
 * <p>
 * 其实就是字典树
 *
 * @Author ayl
 * @Date 2025-01-06
 */
public class AC自动机_过滤敏感词_简单实现 {

    /**
     * 敏感词字典树
     */
    private TrieNode root = new TrieNode();

    /**
     * 字典树节点
     */
    private static class TrieNode {

        //下一级节点
        private Map<Character, TrieNode> children = new HashMap<>();

        //该节点是否为某个敏感词的终点
        private boolean isEndOfWord = false;

    }

    /**
     * 加入 敏感词 到字典树
     *
     * @param word 敏感词
     */
    private void addSensitiveWord(String word) {
        //获取当前节点
        TrieNode current = root;
        //循环所有char
        for (char ch : word.toCharArray()) {
            //如果不存在,存入
            current.children.putIfAbsent(ch, new TrieNode());
            //下一个
            current = current.children.get(ch);
        }
        //记录终点为敏感词
        current.isEndOfWord = true;
    }

    /**
     * 匹配句子中的敏感词
     *
     * @param text        句子
     * @param replacement 敏感词替换为
     * @return
     */
    private String filterSensitiveWords(String text, char replacement) {
        //初始化结果字符
        StringBuilder filteredText = new StringBuilder(text);
        //当前节点
        TrieNode current = root;
        //开始节点
        int start = 0;
        //循环
        for (int i = 0; i < text.length(); i++) {
            //获取当前字符
            char ch = text.charAt(i);
            //如果匹配上
            if (current.children.containsKey(ch)) {
                //下一个
                current = current.children.get(ch);
                //如果是
                if (current.isEndOfWord) {
                    //循环
                    for (int j = start; j <= i; j++) {
                        //替换为指定字符
                        filteredText.setCharAt(j, replacement);
                    }
                }
            } else {
                //重置字典树头
                current = root;
                start = i + 1;
            }
        }
        //返回最终结果
        return filteredText.toString();
    }

    public static void main(String[] args) {

        //初始化
        AC自动机_过滤敏感词_简单实现 filter = new AC自动机_过滤敏感词_简单实现();

        //添加敏感词
        filter.addSensitiveWord("猪头");
        filter.addSensitiveWord("苹果");
        filter.addSensitiveWord("香蕉");

        //过滤敏感词
        System.out.println(filter.filterSensitiveWords("猪头,你是不是苹果,我不是香蕉.", '*'));

    }

}