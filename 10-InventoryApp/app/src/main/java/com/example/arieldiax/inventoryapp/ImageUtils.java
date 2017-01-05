package com.example.arieldiax.inventoryapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.InputStream;

public final class ImageUtils {

    /**
     * Creates a new ImageUtils object (no, it won't).
     */
    private ImageUtils() {
        // Required empty private constructor (to prevent instantiation).
    }

    /**
     * Decodes the sampled bitmap from an URI.
     *
     * @param context        Instance of the Context class.
     * @param uri            Instance of the Uri class.
     * @param requiredWidth  Width required.
     * @param requiredHeight Height required.
     * @return The sampled bitmap from an URI decoded.
     */
    public static Bitmap decodeSampledBitmapFromUri(Context context, Uri uri, int requiredWidth, int requiredHeight) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        InputStream inputStream = null;
        try {
            inputStream = context.getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        BitmapFactory.decodeStream(inputStream, null, options);
        options.inSampleSize = calculateInSampleSize(options, requiredWidth, requiredHeight);
        options.inJustDecodeBounds = false;
        inputStream = null;
        try {
            inputStream = context.getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        return BitmapFactory.decodeStream(inputStream, null, options);
    }

    /**
     * Calculates the in sample size.
     *
     * @param options        Instance of the Options class.
     * @param requiredWidth  Width required.
     * @param requiredHeight Height required.
     * @return The in sample size calculated.
     */
    private static int calculateInSampleSize(Options options, int requiredWidth, int requiredHeight) {
        int inSampleSize = 1;
        int width = options.outWidth;
        int height = options.outHeight;
        if (width > requiredWidth || height > requiredHeight) {
            int halfWidth = width / 2;
            int halfHeight = height / 2;
            while ((halfWidth / inSampleSize) >= requiredWidth && (halfHeight / inSampleSize) >= requiredHeight) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
