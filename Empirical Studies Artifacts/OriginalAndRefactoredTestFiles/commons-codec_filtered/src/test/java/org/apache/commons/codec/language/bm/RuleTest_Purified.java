package org.apache.commons.codec.language.bm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class RuleTest_Purified {

    private Rule.Phoneme[][] makePhonemes() {
        final String[][] words = { { "rinD", "rinDlt", "rina", "rinalt", "rino", "rinolt", "rinu", "rinult" }, { "dortlaj", "dortlej", "ortlaj", "ortlej", "ortlej-dortlaj" } };
        final Rule.Phoneme[][] phonemes = new Rule.Phoneme[words.length][];
        for (int i = 0; i < words.length; i++) {
            final String[] words_i = words[i];
            final Rule.Phoneme[] phonemes_i = phonemes[i] = new Rule.Phoneme[words_i.length];
            for (int j = 0; j < words_i.length; j++) {
                phonemes_i[j] = new Rule.Phoneme(words_i[j], Languages.NO_LANGUAGES);
            }
        }
        return phonemes;
    }

    @Test
    public void testSubSequenceWorks_1_testMerged_1() {
        final Rule.Phoneme a = new Rule.Phoneme("a", null);
        final Rule.Phoneme b = new Rule.Phoneme("b", null);
        final Rule.Phoneme cd = new Rule.Phoneme("cd", null);
        final Rule.Phoneme ef = new Rule.Phoneme("ef", null);
        assertEquals('a', a.getPhonemeText().charAt(0));
        assertEquals('b', b.getPhonemeText().charAt(0));
        assertEquals('c', cd.getPhonemeText().charAt(0));
        assertEquals('d', cd.getPhonemeText().charAt(1));
        assertEquals('e', ef.getPhonemeText().charAt(0));
        assertEquals('f', ef.getPhonemeText().charAt(1));
        final Rule.Phoneme a_b = new Rule.Phoneme(a, b);
        assertEquals('a', a_b.getPhonemeText().charAt(0));
        assertEquals('b', a_b.getPhonemeText().charAt(1));
        assertEquals("ab", a_b.getPhonemeText().subSequence(0, 2).toString());
        assertEquals("a", a_b.getPhonemeText().subSequence(0, 1).toString());
        assertEquals("b", a_b.getPhonemeText().subSequence(1, 2).toString());
        final Rule.Phoneme cd_ef = new Rule.Phoneme(cd, ef);
        assertEquals('c', cd_ef.getPhonemeText().charAt(0));
        assertEquals('d', cd_ef.getPhonemeText().charAt(1));
        assertEquals('e', cd_ef.getPhonemeText().charAt(2));
        assertEquals('f', cd_ef.getPhonemeText().charAt(3));
        assertEquals("c", cd_ef.getPhonemeText().subSequence(0, 1).toString());
        assertEquals("d", cd_ef.getPhonemeText().subSequence(1, 2).toString());
        assertEquals("e", cd_ef.getPhonemeText().subSequence(2, 3).toString());
        assertEquals("f", cd_ef.getPhonemeText().subSequence(3, 4).toString());
        assertEquals("cd", cd_ef.getPhonemeText().subSequence(0, 2).toString());
        assertEquals("de", cd_ef.getPhonemeText().subSequence(1, 3).toString());
        assertEquals("ef", cd_ef.getPhonemeText().subSequence(2, 4).toString());
        assertEquals("cde", cd_ef.getPhonemeText().subSequence(0, 3).toString());
        assertEquals("def", cd_ef.getPhonemeText().subSequence(1, 4).toString());
        assertEquals("cdef", cd_ef.getPhonemeText().subSequence(0, 4).toString());
        final Rule.Phoneme a_b_cd = new Rule.Phoneme(new Rule.Phoneme(a, b), cd);
        assertEquals('a', a_b_cd.getPhonemeText().charAt(0));
        assertEquals('b', a_b_cd.getPhonemeText().charAt(1));
        assertEquals('c', a_b_cd.getPhonemeText().charAt(2));
        assertEquals('d', a_b_cd.getPhonemeText().charAt(3));
        assertEquals("a", a_b_cd.getPhonemeText().subSequence(0, 1).toString());
        assertEquals("b", a_b_cd.getPhonemeText().subSequence(1, 2).toString());
        assertEquals("c", a_b_cd.getPhonemeText().subSequence(2, 3).toString());
        assertEquals("d", a_b_cd.getPhonemeText().subSequence(3, 4).toString());
        assertEquals("ab", a_b_cd.getPhonemeText().subSequence(0, 2).toString());
        assertEquals("bc", a_b_cd.getPhonemeText().subSequence(1, 3).toString());
        assertEquals("cd", a_b_cd.getPhonemeText().subSequence(2, 4).toString());
        assertEquals("abc", a_b_cd.getPhonemeText().subSequence(0, 3).toString());
        assertEquals("bcd", a_b_cd.getPhonemeText().subSequence(1, 4).toString());
        assertEquals("abcd", a_b_cd.getPhonemeText().subSequence(0, 4).toString());
    }

    @Test
    public void testSubSequenceWorks_7_testMerged_2() {
        final Rule.Phoneme ghi = new Rule.Phoneme("ghi", null);
        assertEquals('g', ghi.getPhonemeText().charAt(0));
        assertEquals('h', ghi.getPhonemeText().charAt(1));
        assertEquals('i', ghi.getPhonemeText().charAt(2));
    }

    @Test
    public void testSubSequenceWorks_10_testMerged_3() {
        final Rule.Phoneme jkl = new Rule.Phoneme("jkl", null);
        assertEquals('j', jkl.getPhonemeText().charAt(0));
        assertEquals('k', jkl.getPhonemeText().charAt(1));
        assertEquals('l', jkl.getPhonemeText().charAt(2));
    }
}
