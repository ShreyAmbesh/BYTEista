package byteista.sahayam.UserRegistration;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import byteista.sahayam.MainActivity.MainActivity;
import byteista.sahayam.R;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import byteista.sahayam.Utils.DatabaseHelper;
import byteista.sahayam.Utils.Display;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class ProfileSetup extends AppCompatActivity {

    public static com.pkmmte.view.CircularImageView imageView;
    private final int REQUESTCODE = 1;
    String path;
    private static final String MY_PREFERENCES = "my_preferences";
    SharedPreferences reader;
    EditText status_edit_text;
    private View.OnClickListener listener;
    boolean updateprofilepic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);
        reader = this.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = reader.edit();
        imageView = (com.pkmmte.view.CircularImageView) findViewById(R.id.contact_profile_picture);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        Display display = new Display(this);
        layoutParams.height = (display.getHeight()) / 3;
        //((LinearLayout)findViewById(R.id.profile_pic)).setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        imageView.setLayoutParams(layoutParams);
        //imageView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUESTCODE);
            }
        });
        updateprofilepic = false;
        Button upload = (Button) findViewById(R.id.buttonContinue);
        try {
            DatabaseHelper helper = new DatabaseHelper(this);
            SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
            final Cursor cursor = sqLiteDatabase.query(true, "my_profile", new String[]{"profile_pic", "status"}, "jabber_id=?", new String[]{reader.getString("jabber_id", null)}, null, null, null, null);
            Log.d("bolean load", "" + getIntent().getBooleanExtra("load", false));
            if (cursor != null && cursor.getCount() > 0 && getIntent().getBooleanExtra("load", false)) {
                cursor.moveToFirst();

                if (cursor.getString(0) != null) {
                    FileInputStream img = this.openFileInput(cursor.getString(0));
                    Bitmap theImage = BitmapFactory.decodeStream(img);
                    imageView.setImageBitmap(scaleBitmap(Bitmap.createScaledBitmap(theImage, theImage.getWidth() / 2, theImage.getHeight() / 2, false)));
                }
                imageView.setAlpha(254);
                status_edit_text.setText(cursor.getString(1));
                status_edit_text.setSelection(status_edit_text.getText().length());

            }
            sqLiteDatabase.close();

            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    /*if (updateprofilepic)
                        //new UploadProfilePic(path, reader.getString("jabber_id", null), ProfileSetup.this).execute();
                        //UserRegistration.registrationInfo.USERNAME=FragmentUserName.userName.getText().toString();
                    else
                        ProfileSetup.this.finish();*/
                    Intent intent=new Intent(ProfileSetup.this, MainActivity.class);
                    startActivity(intent);


                }
            });

        } catch (SQLException e) {
            Log.d("sql error", e.getMessage());
        } catch (IllegalStateException e) {
            Log.d("sql error", e.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        editor.putBoolean("is_first", false);
        editor.commit();





    }











    @Override

    protected void onPause() {

        super.onPause();


    }


    @Override

    public void onBackPressed() {



    }


    @Override
    protected void onDestroy() {
        // Store our shared preference

        Runtime.getRuntime().gc();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);


        if (requestCode == REQUESTCODE) {
            if (resultCode == RESULT_OK) {
                final String filePath = imageReturnedIntent.getData().toString();
                path = filePath;
                if (path != null)
                    updateprofilepic = true;

                Glide.with(this).load(filePath)
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                // you can do something with loaded bitmap here

                                imageView.setImageBitmap(scaleBitmap(Bitmap.createScaledBitmap(resource, resource.getWidth() / 2, resource.getHeight() / 2, false)));
                                imageView.setAlpha(254);
                            }
                        });
            }

        }
    }


    private Bitmap scaleBitmap(Bitmap srcBmp) {
        Bitmap dstBmp;
        if (srcBmp.getWidth() <= srcBmp.getHeight()) {
            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    0,
                    srcBmp.getHeight() / 2 - srcBmp.getWidth() / 2,
                    srcBmp.getWidth(),
                    srcBmp.getWidth()
            );


        } else {

            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    srcBmp.getWidth() / 2 - srcBmp.getHeight() / 2,
                    0,
                    srcBmp.getHeight(),
                    srcBmp.getHeight()
            );
        }
        return dstBmp;

    }

}
