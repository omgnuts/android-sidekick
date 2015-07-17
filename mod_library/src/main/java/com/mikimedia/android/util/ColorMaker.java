/*
 *
 *  * Copyright (c) 2015. The MikiMedia Inc
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

package com.mikimedia.android.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ColorMaker {

    public final static ColorMaker DEFAULT;

    public final static ColorMaker MATERIAL_500S;

    public final static ColorMaker MATERIAL_500;

    static {
        DEFAULT = create(Arrays.asList(
                0xfff16364,
                0xfff58559,
                0xfff9a43e,
                0xffe4c62e,
                0xff67bf74,
                0xff59a2be,
                0xff2093cd,
                0xffad62a7,
                0xfff16364,
                0xfff58559,
                0xfff9a43e,
                0xffe4c62e,
                0xff67bf74,
                0xff59a2be,
                0xff2093cd,
                0xffad62a7
        ));

//        MATERIAL = create(Arrays.asList(
//                0xffe57373,
//                0xfff06292,
//                0xffba68c8,
//                0xff9575cd,
//                0xff7986cb,
//                0xff64b5f6,
//                0xff4fc3f7,
//                0xff4dd0e1,
//                0xff4db6ac,
//                0xff81c784,
//                0xffaed581,
//                0xffff8a65,
//                0xffd4e157,
//                0xffffd54f,
//                0xffffb74d,
//                0xffa1887f
//        ));

        MATERIAL_500S = create(Arrays.asList(
                0xFFF44336,
                0xFF9C27B0,
                0xFF2196F3,
                0xFF4CAF50,
                0xFFFFC107,
                0xFF00BCD4,
                0xFFE91E63,
                0xFFFFEB3B
        ));

        // http://www.designskilz.com/colors/
        // Uses the 500 colors
        MATERIAL_500 = create(Arrays.asList(
                0xfff44336,
                0xffe91e63,
                0xff9c27b0,
                0xff673ab7,
                0xff3f51b5,
                0xff2196f3,
                0xff03a9f4,
                0xff00bcd4,
                0xff009688,
                0xff4caf50,
                0xff8bc34a,
                0xffcddc39,
                0xffffeb3b,
                0xffffc107,
                0xffff9800,
                0xffff5722
        ));

    }

    private final List<Integer> mColors;
    private final Random mRandom;
    private final int size;

    public static ColorMaker create(List<Integer> colorList) {
        return new ColorMaker(colorList);
    }

    private ColorMaker(List<Integer> colorList) {
        mColors = colorList;
        mRandom = new Random(System.currentTimeMillis());
        size = mColors.size();
    }

    public int getColor(Object key) {
        // Note get the last four bits of the color index
        return mColors.get(key.hashCode() & (size - 1));
    }

    public int getColor(int key) {
        // Note get the last four bits of the color index
        return mColors.get(key & 15);
    }

    public int getRandomColor() {
        return mColors.get(mRandom.nextInt(size));
    }

    public int getColorX(Object key) {
        return mColors.get(Math.abs(key.hashCode()) % size);
    }

    public ColorMaker createWithAlpha(int alpha) {
        List<Integer> colors = new ArrayList<Integer>();
        for (int color :  mColors) {
            color &= 0x00FFFFFF; // remove current alpha
            color += (alpha << 24); // add current alpha;
            colors.add(color);
        }
        return new ColorMaker(colors);
    }

    public static int getAlphaColor(int alpha, int color) {
        return (color & 0x00FFFFFF) + (alpha << 24);
    }


}