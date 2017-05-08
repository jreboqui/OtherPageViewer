package com.example.junnel_lalaine.otherpageviewer;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.graphics.BitmapFactory.decodeFile;

public class ImageAdapter extends PagerAdapter {
    Context context;

   private int[] GalImages = new int[] {
            R.drawable.image1,
           R.drawable.image2
   };

    private ArrayList<String> listArray;

    ImageAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return listArray.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Bitmap bitmap;
        ImageView imageView = new ImageView(context);
        int padding = context.getResources().getDimensionPixelSize(R.dimen.padding_medium);
     //   imageView.setPadding(padding, padding, padding, padding);
     //   Bitmap bmp = BitmapFactory.decodeFile(listArray.get(position));
       // imageView.setImageBitmap(bmp);

        String imageLoc;
        File imageFile;

        imageLoc = listArray.get(position);
        imageFile = new File(imageLoc);
        bitmap = decodeFile(imageLoc);

        imageView.setImageBitmap(bitmap);



        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


        //   imageView.setImageResource(GalImages[position]);
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    public ArrayList<String> getAllShownImagesPath(Activity activity) {

        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(absolutePathOfImage);
         //   System.out.println(absolutePathOfImage);
          //  listArray.add(absolutePathOfImage);

            listArray = listOfAllImages;
        }
        return listOfAllImages;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }


}