package com.cyantree.check.secure;

import org.apache.commons.text.translate.AggregateTranslator;
import org.apache.commons.text.translate.CharSequenceTranslator;
import org.apache.commons.text.translate.EntityArrays;
import org.apache.commons.text.translate.LookupTranslator;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;

// 모든 JSON 응답에 대한 XSS 처리를 위해 사용
// 참조 : https://shinechan.tistory.com/entry/Spring-XSSCross-site-scripting-JSON-%EB%8D%B0%EC%9D%B4%ED%84%B0-XSS-%EC%B2%98%EB%A6%AC-%ED%95%98%EA%B8%B0
public class HTMLCharacterEscapes extends CharacterEscapes {

    private final int[] asciiEscapes;
    private final CharSequenceTranslator translator;

    public HTMLCharacterEscapes() {
        // 1. XSS 방지 처리할 특수 문자 지정
        asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();
        asciiEscapes['<'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['>'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['&'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['\"'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['('] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes[')'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['#'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['\''] = CharacterEscapes.ESCAPE_CUSTOM;

        // XSS 방지 처리 특수 문자 인코딩 값 지정
        translator = new AggregateTranslator(
                new LookupTranslator(EntityArrays.BASIC_ESCAPE), // <, >, &, " 는 여기에 포함됨
                new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE),
                new LookupTranslator(EntityArrays.HTML40_EXTENDED_ESCAPE));

    }

    @Override
    public int[] getEscapeCodesForAscii() {
        return asciiEscapes;
    }

    @Override
    public SerializableString getEscapeSequence(int ch) {
        return new SerializedString(translator.translate(Character.toString((char) ch)));
        // 참고 - 커스터마이징이 필요없다면 아래와 같이 Apache Commons Text에서 제공하는 메서드를 써도 된다.
        // return new
        // SerializedString(StringEscapeUtils.escapeHtml4(Character.toString((char)
        // ch)));
    }

}
