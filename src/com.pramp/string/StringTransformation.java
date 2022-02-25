package com.pramp.string;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Given two strings str1 and str2 of the same length, determine whether you can transform str1
 * into str2 by doing zero or more conversions.
 *
 * In one conversion you can convert all occurrences of one character in str1 to any other lowercase
 * English character.
 *
 * Return true if and only if you can transform str1 into str2.
 *
 * Example 1:
 *
 * Input: str1 = "aabcc", str2 = "ccdee"
 * Output: true
 * Explanation: Convert 'c' to 'e' then 'b' to 'd' then 'a' to 'c'. Note that the order of conversions matter.
 * Example 2:
 *
 * Input: str1 = "leetcode", str2 = "codeleet"      ee->aa->dd  !od not possible
 * Output: false
 * Explanation: There is no way to transform str1 to str2.
 * Note:
 *
 * 1 <= str1.length == str2.length <= 10^4
 * Both str1 and str2 contain only lowercase English letters.
 */
public class StringTransformation {


    /**
     * Initial analysis
     *     leetcode = abbcdefg
     *             codeleet = abcdeccf
     *
     *     str1 = abc -> abb -> dbb -> dee  -> Not for now
     *             str2 = dee
     *
     *     Convert patter from complicated one to simple one
     *     but not convert pattern from simple one to complicated one
     *
     *     aabcc    a2b1c2
     *     ccdee    c2d1e2
     *
     *     aabccaa  a2[b1c2a2]
     *     ccdeecc  c2[d1e2c2]
     *     ccdeeff  c2[d1e2f2]
     *
     *     List<Tuple> ->
     *
     *     Divide and conquer
     *
     *     {c:e   a:c b:d}
     * // You can only use lowercase in english to do the convertion
     *
     *     Input: str1 = "aabcc", str2 = "ccdee"
     *
     *     str1="abc"  str2="cba"    abc  d
     *
     * abc -> abd -> cbd -> cba
     *
     * str1="abc"   str="cba"
     *
     * <c:a, b:b, a:c
     *
     * If two string equals, then return true.
     * If one character a is mapped to 2 different chars, then return false.
     * The order matters, in example 1, a->c, c->e. need to perform c->e first. Otherwise, a->c, becomes ccbcc, then c->e, it becomes eedee, which is not ccdee.
     * Or we need a temp char g a->g->c, first have a->g ggbcc, then c->e, ggbee. Last we have g->c, then ccbee.
     * Inorder to do this, we need one unused char in str2, which makes the size of str2 unique chars smaller than 26.
     * If the str contains all the chars, then the transformation is not possible
     * str1 = 'abcdefghijklmnopqrstuvwxyz', str2 = 'zyxwvutsrqponmlkjihgfedcba'
     *
     * @param str1
     * @param str2
     * @return
     */
    private static boolean canBeTransformed(String str1, String str2) {
        Map<Character, Character> dict = new HashMap<>();
        Set<Character> set = new HashSet<>();

        for (int i = 0; i < str2.length(); i ++) {
            char char2 = str2.charAt(i);
            char char1 = str1.charAt(i);
            set.add(str1.charAt(i));

            if (dict.containsKey(char2)) {
                if (char1 != dict.get(char2)) {
                    return false;
                }
            } else if (dict.containsValue(char2)) {
                return false;
            } else {
                dict.put(char2, char1);
            }
        }

        return set.size() != 26 || dict.size() != 26;
    }

}
