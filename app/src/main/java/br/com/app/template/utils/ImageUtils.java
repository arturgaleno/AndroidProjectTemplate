package br.com.app.template.utils;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Hint: Prefer Glide, Fresco, Picasso etc, to load images. They cover most of use cases.
 * But, here goal some image utilities.
 */
@EBean
public class ImageUtils {

	@RootContext
	Context context;

	/**
	 * @param dips the dips values
	 * @return the values in pixels
	 */
	public int convertDipToPixels(float dips){
		 Resources r = context.getResources();
		 return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dips, r.getDisplayMetrics());
	}

	/**
	 * @param options BitmapFactory with the options
	 * @param reqWidth resquested width
	 * @param reqHeight resquested height
	 * @return total sample size pixels
	 */
	public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	
	    if (height > reqHeight || width > reqWidth) {
	        final int heightRatio = Math.round((float) height / (float) reqHeight);
	        final int widthRatio = Math.round((float) width / (float) reqWidth);
	        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
	    }
	    final float totalPixels = width * height;
	    final float totalReqPixelsCap = reqWidth * reqHeight * 2;

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

	    return inSampleSize;
	}
	
	public Bitmap decodeBitmapFromPath(String filePath){
		Bitmap scaledBitmap = null;
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		
		options.inSampleSize = calculateInSampleSize(options, convertDipToPixels(150), convertDipToPixels(200));
		options.inDither = false;
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inJustDecodeBounds = false;
		
		scaledBitmap = BitmapFactory.decodeFile(filePath, options);		
		return scaledBitmap;
	}

	/**
	 * @param imageUri uri from image
	 * @param outPath out path for output
	 * @param maxHeight max height of compressed height
	 * @param maxWidth  max maxWidth of compressed height
	 * @return output path
	 */
	public String compressImage(String imageUri, String outPath, float maxHeight, float maxWidth) {

		String filePath = getRealPathFromURI(imageUri);
		Bitmap scaledBitmap = null;

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(outPath,options);

		int actualHeight = options.outHeight;
		int actualWidth = options.outWidth;
		float imgRatio = actualWidth / actualHeight;
		float maxRatio = maxWidth / maxHeight;

		if (actualHeight > maxHeight || actualWidth > maxWidth) {
			if (imgRatio < maxRatio) {
				imgRatio = maxHeight / actualHeight;
				actualWidth = (int) (imgRatio * actualWidth);
				actualHeight = (int) maxHeight;
			} else if (imgRatio > maxRatio) {
				imgRatio = maxWidth / actualWidth;
				actualHeight = (int) (imgRatio * actualHeight);
				actualWidth = (int) maxWidth;
			} else {
				actualHeight = (int) maxHeight;
				actualWidth = (int) maxWidth;

			}
		}

		options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
		options.inJustDecodeBounds = false;
		options.inDither = false;
		options.inTempStorage = new byte[16*1024];

		try{
			bmp = BitmapFactory.decodeFile(filePath, options);
		}
		catch(OutOfMemoryError exception){
			exception.printStackTrace();

		}
		try{
			scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
		}
		catch(OutOfMemoryError exception){
			exception.printStackTrace();
		}

		float ratioX = actualWidth / (float) options.outWidth;
		float ratioY = actualHeight / (float)options.outHeight;
		float middleX = actualWidth / 2.0f;
		float middleY = actualHeight / 2.0f;

		Matrix scaleMatrix = new Matrix();
		scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

		Canvas canvas = new Canvas(scaledBitmap);
		canvas.setMatrix(scaleMatrix);
		canvas.drawBitmap(bmp, middleX - bmp.getWidth()/2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        canvas.setMatrix(null);
        canvas.setBitmap(null);

		ExifInterface exif;
		try {
			exif = new ExifInterface(filePath);

			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
			Log.d("EXIF", "Exif: " + orientation);
			Matrix matrix = new Matrix();
			if (orientation == 6) {
				matrix.postRotate(90);
				Log.d("EXIF", "Exif: " + orientation);
			} else if (orientation == 3) {
				matrix.postRotate(180);
				Log.d("EXIF", "Exif: " + orientation);
			} else if (orientation == 8) {
				matrix.postRotate(270);
				Log.d("EXIF", "Exif: " + orientation);
			}
			if (scaledBitmap != null) {
				scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		FileOutputStream out = null;

		try {
			out = new FileOutputStream(outPath);
			if (scaledBitmap != null) {
				scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (scaledBitmap != null) {
            scaledBitmap.recycle();
        }

        return outPath;

	}

    /**
     * @param contentURI content uri
     * @return real path from uri
     */
	private String getRealPathFromURI(String contentURI) {
		Uri contentUri = Uri.parse(contentURI);
		Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, null);
		if (cursor == null) {
			return contentUri.getPath();
		} else {
			cursor.moveToFirst();
			int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			return cursor.getString(idx);
		}
	}

}
