package com.cloudurable.docgen.util;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;

public class TokenCounterUtils {


    public static int countTokens(String text) {
        Analyzer analyzer = new StandardAnalyzer();
        int tokenCount = 0;
        try (TokenStream tokenStream = analyzer.tokenStream(null, new StringReader(text))) {
            CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                tokenCount++;
            }
            tokenStream.end();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (int) ((tokenCount * .3) + tokenCount);
    }
}

