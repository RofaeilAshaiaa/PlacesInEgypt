package rofaeil.ashaiaa.idea.placesinegypt.sightdetails;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import rofaeil.ashaiaa.idea.placesinegypt.R;

/**
 * @author Rofaeil Ashaiaa
 *         Created on 21/10/17.
 */

public class SaveImageToStorageTask extends AsyncTask<Bitmap, Void, File> {

    // to prevent any memory leaks
    private WeakReference<Context> mContext;

    public SaveImageToStorageTask(WeakReference<Context> mContextWeakReference) {
        this.mContext = mContextWeakReference;
    }

    @Override
    protected File doInBackground(Bitmap... bitmaps) {
        Bitmap icon = BitmapFactory.decodeResource(mContext.get().getResources(),
                R.mipmap.ic_launcher);
        Bitmap iconToDraw = Bitmap.createScaledBitmap(
                icon, (icon.getWidth() / 4), (icon.getHeight() / 4), false);
        Bitmap bitmapCopy = Bitmap.createScaledBitmap(
                bitmaps[0], icon.getWidth(), icon.getHeight(), false);

        putOverlay(bitmapCopy, iconToDraw);
        File file = CreateImageFile();
        SaveImageFile(file, bitmapCopy);

        return file;
    }

    @Override
    protected void onPostExecute(File file) {

        if (mContext != null) {
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(file));
            mContext.get().sendBroadcast(intent);
            Toast.makeText(mContext.get(), "Image Saved", Toast.LENGTH_SHORT).show();
        }
    }

    public File CreateImageFile() {

        File imageFile = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                , "Sights of Egypt");
        String timestamp = new SimpleDateFormat(
                "yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String finalPath = imageFile.getPath() + "/" + timestamp + ".jpg";
        if (!imageFile.exists()) {
            imageFile.mkdirs();
        }

        return new File(finalPath);
    }

    public void SaveImageFile(File file, Bitmap bitmap) {
        if (file == null) {
            Log.d("tag",
                    "Error creating media file, check storage permissions: ");
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("tag", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("tag", "Error accessing file: " + e.getMessage());
        }
    }

    public void putOverlay(Bitmap bitmap, Bitmap overlay) {
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(
                overlay,
                canvas.getHeight()/2 ,
                canvas.getWidth() / 2 ,
                paint);
    }

}
