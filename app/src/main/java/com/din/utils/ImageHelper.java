package com.din.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by Dinesh on 8/6/2015.
 */
public class ImageHelper {



    public byte[] bitmapToByte(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (bitmap != null)


        // Bitmap bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.common)).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] photo = byteArrayOutputStream.toByteArray();
        return photo;
    }

   public Bitmap byteToBitmap(byte[] imgByte){
       ByteArrayInputStream imageStream = new ByteArrayInputStream(imgByte); /*optional*/
       Bitmap theImage= BitmapFactory.decodeStream(imageStream);
        return theImage;
    }
}
