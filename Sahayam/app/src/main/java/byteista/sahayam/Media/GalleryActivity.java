package byteista.sahayam.Media;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import byteista.sahayam.R;
import byteista.sahayam.Utils.ClickListener;
import byteista.sahayam.Utils.MessageInfo;
import byteista.sahayam.Utils.RecyclerTouchListener;

public class GalleryActivity extends AppCompatActivity {
    public static ArrayList<Model_images> al_images = new ArrayList<>();
    boolean boolean_folder;
    Adapter_PhotosFolder obj_adapter;
    RecyclerView gv_folder;
    private static final int REQUEST_PERMISSIONS = 100;
    private static final int REQUEST_Code = 2;
    Toolbar app_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        gv_folder = (RecyclerView) findViewById(R.id.gv_folder);
        app_bar=(Toolbar)findViewById(R.id.gallery_app_bar);
        app_bar.setVisibility(View.GONE);

        final RecyclerViewFastScroller fastScroller = (RecyclerViewFastScroller) findViewById(R.id.fastscroller);
        GridLayoutManager grid=new GridLayoutManager(this,2);
        fastScroller.needBubble=false;

        String TYPE=getIntent().getStringExtra("TYPE");
        gv_folder.setLayoutManager(grid);


        gv_folder.addOnItemTouchListener(new RecyclerTouchListener(this, gv_folder, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), PhotosActivity.class);
                intent.putExtra("value",position);
                startActivityForResult(intent,REQUEST_Code);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));



                if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    if ((ActivityCompat.shouldShowRequestPermissionRationale(GalleryActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(GalleryActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE))) {

                    } else {
                        ActivityCompat.requestPermissions(GalleryActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_PERMISSIONS);
                    }
                }else {
                    if (TYPE.equals("IMAGES"))
                    fn_imagespath();
                    else if (TYPE.equals("VIDEOS"))
                        fn_videospath();
                }


        fastScroller.setRecyclerView(gv_folder);
        fastScroller.setViewsToUse(R.layout.scrollbar, R.id.fastscroller_bubble, R.id.fastscroller_handle);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);


        if(requestCode==REQUEST_Code) {
            if (resultCode == RESULT_OK) {
                Intent intent=new Intent();
                intent.putExtra("TYPE",imageReturnedIntent.getCharExtra("TYPE",'0'));
                intent.setData(imageReturnedIntent.getData());
                setResult(RESULT_OK, intent);
                finish();

            }

        }
    }

    public ArrayList<Model_images> fn_videospath() {
        al_images.clear();

        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name,column_index_date;

        String absolutePathOfImage = null;
        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Video.Media.BUCKET_DISPLAY_NAME,MediaStore.Video.Media.DATE_TAKEN};

        final String orderBy = MediaStore.Video.Media.DATE_TAKEN;
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
        column_index_date = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_TAKEN);
        al_images.add(new Model_images());
        al_images.get(0).setStr_folder("All Videos");
        DateFormat format=new SimpleDateFormat("MMMM yyyy");
        while (cursor.moveToNext()) {
            absolutePathOfImage = "file://"+ cursor.getString(column_index_data);
            Date date=new Date(cursor.getLong(column_index_date));
            String dateString=format.format(date);
            if(dateString.equals(al_images.get(0).mdate))
            {
                String[] map ={"path",dateString,absolutePathOfImage,""+ MessageInfo.TYPE_VIDEO};
                al_images.get(0).al_imagepath.add(map);
            }
            else
            {
                String[] map ={"date",dateString};
                al_images.get(0).al_imagepath.add(map);
                al_images.get(0).mdate=dateString;
                al_images.get(0).nmnths++;
                String[] map2 ={"path",dateString,absolutePathOfImage,""+ MessageInfo.TYPE_VIDEO};
                al_images.get(0).al_imagepath.add(map2);
            }


            for (int i = 0; i < al_images.size(); i++) {
                if (al_images.get(i).getStr_folder().equals(cursor.getString(column_index_folder_name))) {
                    boolean_folder = true;
                    int_position = i;
                    break;
                } else {
                    boolean_folder = false;

                }
            }


            if (boolean_folder) {

                if(dateString.equals(al_images.get(int_position).mdate))
                {
                    String[] map ={"path",dateString,absolutePathOfImage,""+ MessageInfo.TYPE_VIDEO};
                    al_images.get(int_position).al_imagepath.add(map);
                }
                else
                {
                    String[] map ={"date",dateString};
                    al_images.get(int_position).al_imagepath.add(map);
                    al_images.get(int_position).mdate=dateString;
                    al_images.get(int_position).nmnths++;
                    String[] map2 ={"path",dateString,absolutePathOfImage,""+ MessageInfo.TYPE_VIDEO};
                    al_images.get(int_position).al_imagepath.add(map2);
                }

            } else {

                al_images.add(new Model_images());
                al_images.get(al_images.size()-1).setStr_folder(cursor.getString(column_index_folder_name));

                String[] map ={"date",dateString};
                al_images.get(al_images.size()-1).al_imagepath.add(map);
                al_images.get(al_images.size()-1).mdate=dateString;
                al_images.get(al_images.size()-1).nmnths++;
                String[] map2 ={"path",dateString,absolutePathOfImage,""+ MessageInfo.TYPE_VIDEO};
                al_images.get(al_images.size()-1).al_imagepath.add(map2);




            }


        }

        if(al_images.size()==1||al_images.size()==0)
        {
            Toast.makeText(this,"No Videos Found",Toast.LENGTH_LONG).show();
            this.finish();
        }
        obj_adapter = new Adapter_PhotosFolder(getApplicationContext(),al_images);
        gv_folder.setAdapter(obj_adapter);
        return al_images;
    }


    public ArrayList<Model_images> fn_imagespath() {
        al_images.clear();

        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name,column_index_date;

        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME,MediaStore.Images.Media.DATE_TAKEN};

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        column_index_date = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN);
        al_images.add(new Model_images());
        al_images.get(0).setStr_folder("All Images");
        DateFormat format=new SimpleDateFormat("MMMM yyyy");
        while (cursor.moveToNext()) {
            absolutePathOfImage = "file://"+ cursor.getString(column_index_data);
            Date date=new Date(cursor.getLong(column_index_date));
            String dateString=format.format(date);
            if(dateString.equals(al_images.get(0).mdate))
            {
                String[] map ={"path",dateString,absolutePathOfImage,""+ MessageInfo.TYPE_IMAGE};
                al_images.get(0).al_imagepath.add(map);
            }
            else
            {
                String[] map ={"date",dateString};
                al_images.get(0).al_imagepath.add(map);
                al_images.get(0).mdate=dateString;
                al_images.get(0).nmnths++;
                String[] map2 ={"path",dateString,absolutePathOfImage,""+ MessageInfo.TYPE_IMAGE};
                al_images.get(0).al_imagepath.add(map2);
            }


            for (int i = 0; i < al_images.size(); i++) {
                if (al_images.get(i).getStr_folder().equals(cursor.getString(column_index_folder_name))) {
                    boolean_folder = true;
                    int_position = i;
                    break;
                } else {
                    boolean_folder = false;

                }
            }


            if (boolean_folder) {

                if(dateString.equals(al_images.get(int_position).mdate))
                {
                    String[] map ={"path",dateString,absolutePathOfImage,""+ MessageInfo.TYPE_IMAGE};
                    al_images.get(int_position).al_imagepath.add(map);
                }
                else
                {
                    String[] map ={"date",dateString};
                    al_images.get(int_position).al_imagepath.add(map);
                    al_images.get(int_position).mdate=dateString;
                    al_images.get(int_position).nmnths++;
                    String[] map2 ={"path",dateString,absolutePathOfImage,""+ MessageInfo.TYPE_IMAGE};
                    al_images.get(int_position).al_imagepath.add(map2);
                }

            } else {

                al_images.add(new Model_images());
                al_images.get(al_images.size()-1).setStr_folder(cursor.getString(column_index_folder_name));

                String[] map ={"date",dateString};
                al_images.get(al_images.size()-1).al_imagepath.add(map);
                al_images.get(al_images.size()-1).mdate=dateString;
                al_images.get(al_images.size()-1).nmnths++;
                String[] map2 ={"path",dateString,absolutePathOfImage,""+ MessageInfo.TYPE_IMAGE};
                al_images.get(al_images.size()-1).al_imagepath.add(map2);




            }


        }

        if(al_images.size()==1||al_images.size()==0)
        {
            Toast.makeText(this,"No Images Found",Toast.LENGTH_LONG).show();
            this.finish();
        }
        obj_adapter = new Adapter_PhotosFolder(getApplicationContext(),al_images);
        gv_folder.setAdapter(obj_adapter);
        return al_images;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        fn_imagespath();
                    } else {

                    }
                }
            }
        }
    }

}
