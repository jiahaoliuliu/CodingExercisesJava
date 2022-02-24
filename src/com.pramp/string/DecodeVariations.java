package com.pramp.string;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Decode Variations
 * A letter can be encoded to a number in the following way:
 * 'A' -> '1', 'B' -> '2', 'C' -> '3', ..., 'Z' -> '26'
 *
 * A message is a string of uppercase letters, and it is encoded first using this scheme.
 * For example, 'AZB' -> '1262'
 * Given a string of digits S from 0-9 representing an encoded A message, return the number
 * of ways to decode it.
 * <ul>Example
 *  <li>input:  S = '1262'</li>
 *  <li>output: 3</li>
 *  <li>explanation: There are 3 messages that encode to '1262': 'AZB', 'ABFB', and 'LFB'.</li>
 * </ul>
 *
 */
public class DecodeVariations {

    /**
     * S = string of numbers from 0 - 9
     *   It could be decoded numbers from 1 -> 26
     * variations of combinations that form S
     * 1262
     * -> 1 2 6 2
     * -> 12 6 2
     * -> 1 26 2

     * 12632
     * 1 2 6 3 2
     * 12 6 3 2
     * 1 26 3 2
     * 1 2 63 2 <- Not valid
     * 1 2 6 32 <- Not valid
     dp(i) be the answer for the string S[i:].
     *S[i] == 0, then dp(i) = 0
     There are no ways, since no encoded letter starts with 0.
     * 1062
     x 0 -> 10, 20
     0 non zero
     non zero 0
     non zero non zero
     2 3 1 0 1 1   b ba

     0 0
     * -> 1 0 6 2   -> Not valid -> Contains 0
     * -> 10 6 2 -> Valid
     * -> 1 06 2 -> Not valid
     * -> 1 0 62 -> Not valid -> Because of zero, because of 62
     *
     * 231011
     * 2 3 1 0 1 1 -> Not valid
     * 23 1 0 1 1 -> Not valid
     * 2 3 10 1 1 -> valid        -> 1
     * 2 3 1 01 1 -> No valid
     * 2 3 1 0 11 -> No valid
     * 23 10 1 1 -> Valid         -> 2
     * 23 10 11 -> Valid          -> 3
     //2 3 10 1 1
     * ->  We cannot have more than 3 number combinations
     * ->  because max is 26
     * Sliding window problem, where the window size = 2
     *
     * Corner cases
     * 0 -> 0
     * 1 -> 1
     * 2 -> 1
     * # < 9 -> 1
     *  Zero -> Is not a problem
     * String -> Int = Integer.parseInt(String)

     */
    static int decodeVariations(String codedWord) {
        return helper(codedWord, 0, new ArrayList<>());
    }

    static int helper(String codedWord, int left, List<String> combinations) {
        if (left >= codedWord.length()) {
            if (isCombinationValid(combinations)) {
                return 1;
            } else {
                return 0;
            }
        }

        // One item
        // The pass does not matter
        List<String> oneItemCombination = new ArrayList<>(combinations);
        String nextChar = codedWord.substring(left, left+1);
        // No more combination is possible
        int oneItemSum = nextChar.equals("0") ? 0 : helper(codedWord, left+1, oneItemCombination);

        // Two item
        if (left <= codedWord.length() - 2) {
            String nextTwoChars = codedWord.substring(left, left+2);
            int nextTwoCharsAsInt = Integer.parseInt(nextTwoChars);
            return oneItemSum + (nextTwoCharsAsInt < 10 || nextTwoCharsAsInt > 27 ? 0 :  helper(codedWord, left+2, combinations));
        }

        return oneItemSum;
    }

    static boolean isCombinationValid(List<String> combinations) {
        for (int i = 0; i < combinations.size(); i++) {
            String currentCombination = combinations.get(i);
            if (currentCombination.length() == 1) {
                if (currentCombination.equals("0")) {
                    return false;
                }
            }

            if (currentCombination.length() == 2) {
                int number = Integer.parseInt(currentCombination);
                if (number < 10 || number > 26) {
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    public void test1() {
        // Given
        String input = "12";

        // When
        int combinations = decodeVariations(input);

        // Then
        assertEquals(2, combinations);
    }

    @Test
    public void test2() {
        // Given
        String input = "123";  // <1, 2, 3> <12, 3> <1, 23>

        // When
        int combinations = decodeVariations(input);

        // Then
        assertEquals(3, combinations);
    }

    @Test
    public void test3() {
        // Given
        String input = "1223";  // <1, 2, 2, 3>  <12, 2, 3> <12, 23>  <1, 22, 3> <1, 2, 23>

        // When
        int combinations = decodeVariations(input);

        // Then
        assertEquals(5, combinations);
    }

    @Test
    public void test4() {
        // Given
        String input = "1203";  // <1, 2, 0, 3> No valid
                                // <12, 0, 3>   No valid
                                // <12, 03>     No valid
                                // <1, 20, 3>   Valid
                                // <1, 2, 03>   No valid
        // When
        int combinations = decodeVariations(input);

        // Then
        assertEquals(1, combinations);
    }

}
