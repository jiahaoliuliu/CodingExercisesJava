package com.pramp.array;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Given an array of integer, find the equilibrium point of it.
 * The equilibrium point is defined as the point which the sum of the left side is equal to the
 * sum of the right side.
 * https://www.youtube.com/watch?v=c9imv2LvgWk
 *
 * Example [1, 2, 6, 4, 0, -1]
 * The equilibrium point is 6, because
 * - on the left side: 1 + 2 = 3
 * - on the right side: 4 + 0 + -1 = 3
 */
public class EquilibriumPoint {

    /**
     * Initial analysis
     * [1, 2, 6, 4, 0, -1]. We are talking about sum of values.
     * All the number on the left side of the position 0 is considered 0
     * All the number on the right side of the last position is considered 0
     * If by given any number, we can find the sum of the all the number on the left side
     * and the sum of all the numbers on the right side -> Sub problem
     * Now, if we have two extra arrays, one is the sum of all the numbers from left,
     * and another one is the sum of all the number from right
     * Given         [1, 2, 6, 4, 0, -1]
     * - SumLeft  =  [0, 1, 3, 9, 13, 13]
     * - SumRight = [11, 9, 3, -1 ,-1, 0]
     *
     * @param array
     * @return
     */
    public int equilibriumPoint(int[] array) {
        if (array.length == 0 ) return -1;
        if (array.length == 1) return 0;

        int[] sumLeft = new int[array.length];
        sumLeft[0] = 0;
        for (int i = 1; i < sumLeft.length; i++) {
            sumLeft[i] = sumLeft[i - 1] + array[i - 1];
        }

        int[] sumRight = new int[array.length];
        sumRight[array.length - 1] = 0;
        for (int i = array.length - 2; i >= 0; i--) {
            sumRight[i] = sumRight[i + 1] + array[i + 1];
        }

        for (int i = 0; i < array.length; i++) {
            if (sumLeft[i] == sumRight[i]) {
                return i;
            }
        }

        return -1;
    }

    @Test
    public void test1() {
        // Given
        int[] array = new int[]{1, 2, 6, 4, 0, -1};

        // When
        int result = equilibriumPoint(array);

        // Then
        assertEquals(2, result);
    }
}
