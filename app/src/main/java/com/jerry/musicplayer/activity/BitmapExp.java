package com.jerry.musicplayer.activity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

import com.jerry.musicplayer.R;
import com.jerry.musicplayer.utils.MMCQ;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerry on 16-1-7.
 */
public class BitmapExp {
    int colors[] = {0xff255779, 0xff3e7492, 0xffa6c0cd};//分别为开始颜色，中间夜色，结束颜色

    GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
//    findViewById(R.id.woshi).setBackground(gd);
//    //        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.colortest);
//    Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.test);
//    List<int[]> result = new ArrayList<int[]>();
//    try {
//        result = MMCQ.compute(icon, 5);
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//    icon.recycle();
////        new Thread(new Runnable() {
////            @Override
////            public void run() {
////                try {
////                    Bitmap micon = BitmapFactory.decodeResource(getResources(), R.drawable.test);
////                    List<int[]> mresult = MMCQ.compute(micon, 5);
//////                    handler.obtainMessage(1, mresult);
////                    micon.recycle();
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
////            }
////        }).start();
//
//
//    int[] dominantColor = result.get(0);
//    findViewById(R.id.dominant_color).setBackgroundColor(
//            Color.rgb(dominantColor[0], dominantColor[1], dominantColor[2]));
//
//    LinearLayout palette = (LinearLayout) findViewById(R.id.palette);
//    for (int i = 1; i < result.size(); i++) {
//        View swatch = new View(this);
//        Resources resources = getResources();
//        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
//        int swatchWidth = (int) (48 * displayMetrics.density + 0.5f);
//        int swatchHeight = (int) (48 * displayMetrics.density + 0.5f);
//        LinearLayout.LayoutParams layoutParams =
//                new LinearLayout.LayoutParams(swatchWidth, swatchHeight);
//        int margin = (int) (4 * displayMetrics.density + 0.5f);
//        layoutParams.setMargins(margin, 0, margin, 0);
//        swatch.setLayoutParams(layoutParams);
//
//        int[] color = result.get(i);
//        int rgb = Color.rgb(color[0], color[1], color[2]);
//        swatch.setBackgroundColor(rgb);
//        palette.addView(swatch);
//    }
}
