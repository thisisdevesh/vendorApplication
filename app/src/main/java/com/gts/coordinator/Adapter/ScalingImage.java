package com.gts.coordinator.Adapter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ScalingImage {
    private static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight){
        //Raw height and width of image
        final int rawHeight = options.outHeight;
        final int rawWidth = options.outWidth;
        int sampleSize = 1;
        if(rawHeight>reqHeight || rawWidth>reqWidth){
            int halfHeight = rawHeight/2;
            int halfWidth = rawWidth/2;
            while((halfHeight/sampleSize)>=reqHeight || (halfWidth/sampleSize)>=reqWidth){
                sampleSize = sampleSize * 2;
            }
        }
        return sampleSize;
    }

    public static Bitmap decodeScaledBitmapFromResource(String filePath,int reqWidth,int reqHeight){
        //firstDecodeWith InJustDecodeBounds = true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath,options);
        //calculate in SampleSize
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);
        //decode in sample set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath,options);


        // filePath = compresh file path
    }
}
