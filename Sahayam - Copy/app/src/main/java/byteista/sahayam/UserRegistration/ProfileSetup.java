package byteista.sahayam.UserRegistration;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import byteista.sahayam.MainActivity.MainActivity;
import byteista.sahayam.Media.GalleryActivity;
import byteista.sahayam.Network.UploadProfilePic;
import byteista.sahayam.R;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import byteista.sahayam.Utils.DatabaseHelper;
import byteista.sahayam.Utils.Display;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;


public class ProfileSetup extends AppCompatActivity {

    private static final String INTERESTS_URL ="http://35.187.242.68/byteista/SetInterests.php" ;
    public static com.pkmmte.view.CircularImageView imageView;
    private final int REQUESTCODE = 1;
    String path;
    private static final String MY_PREFERENCES = "my_preferences";
    SharedPreferences reader;
    private View.OnClickListener listener;
    boolean updateprofilepic;
    String interests="";
    SQLiteDatabase db;
    Button upload,pubadmn,enterp,poems,dance,sing,litre,coding,hacking,modelling,evntmanagement,speaking,acting,art,photography;
    boolean pubadmna=true,enterpa=true,poemsa=true,dancea=true,singa=true,litrea=true,codinga=true,hackinga=true,modellinga=true,evntmanagementa=true,speakinga=true,actinga=true,arta=true,photographya=true;
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
                Intent intent = new Intent(ProfileSetup.this, GalleryActivity.class);
                intent.putExtra("TYPE", "IMAGES");
                startActivityForResult(intent, REQUESTCODE);
            }
        });

        DatabaseHelper helper =new DatabaseHelper(this);
        db=helper.getWritableDatabase();




        pubadmn=findViewById(R.id.pubadmn);
        enterp=findViewById(R.id.enterp);
        poems=findViewById(R.id.poems);
        dance=findViewById(R.id.dance);
        sing=findViewById(R.id.sing);
        litre=findViewById(R.id.litre);
        coding=findViewById(R.id.coding);
        hacking=findViewById(R.id.hacking);
        modelling=findViewById(R.id.modelling);
        evntmanagement=findViewById(R.id.evntmanagement);
        speaking=findViewById(R.id.speaking);
        acting=findViewById(R.id.acting);
        art=findViewById(R.id.art);
        photography=findViewById(R.id.photography);

        pubadmn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pubadmna)
                {
                    pubadmn.setBackgroundColor(Color.BLUE);
                    pubadmna=false;
                }
                else
                {
                    pubadmn.setBackgroundColor(Color.WHITE);
                    pubadmna=true;
                }
            }
        });

        enterp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enterpa)
                {
                    enterp.setBackgroundColor(Color.BLUE);
                    enterpa=false;
                }
                else
                {
                    enterp.setBackgroundColor(Color.WHITE);
                    enterpa=true;
                }
            }
        });

        poems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (poemsa)
                {
                    poems.setBackgroundColor(Color.BLUE);
                    poemsa=false;
                }
                else
                {
                    poems.setBackgroundColor(Color.WHITE);
                    poemsa=true;
                }
            }
        });


        dance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dancea)
                {
                    dance.setBackgroundColor(Color.BLUE);
                    dancea=false;
                }
                else
                {
                    dance.setBackgroundColor(Color.WHITE);
                    dancea=true;
                }
            }
        });


        sing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (singa)
                {
                    sing.setBackgroundColor(Color.BLUE);
                    singa=false;
                }
                else
                {
                    sing.setBackgroundColor(Color.WHITE);
                    singa=true;
                }
            }
        });


        litre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (litrea)
                {
                    litre.setBackgroundColor(Color.BLUE);
                    litrea=false;
                }
                else
                {
                    litre.setBackgroundColor(Color.WHITE);
                    litrea=true;
                }
            }
        });


        coding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codinga)
                {
                    coding.setBackgroundColor(Color.BLUE);
                    codinga=false;
                }
                else
                {
                    coding.setBackgroundColor(Color.WHITE);
                    codinga=true;
                }
            }
        });


        hacking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hackinga)
                {
                    hacking.setBackgroundColor(Color.BLUE);
                    hackinga=false;
                }
                else
                {
                    hacking.setBackgroundColor(Color.WHITE);
                    hackinga=true;
                }
            }
        });


        modelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modellinga)
                {
                    modelling.setBackgroundColor(Color.BLUE);
                    modellinga=false;
                }
                else
                {
                    modelling.setBackgroundColor(Color.WHITE);
                    modellinga=true;
                }
            }
        });


        evntmanagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (evntmanagementa)
                {
                    evntmanagement.setBackgroundColor(Color.BLUE);
                    evntmanagementa=false;
                }
                else
                {
                    evntmanagement.setBackgroundColor(Color.WHITE);
                    evntmanagementa=true;
                }
            }
        });


        speaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (speakinga)
                {
                    speaking.setBackgroundColor(Color.BLUE);
                    speakinga=false;
                }
                else
                {
                    speaking.setBackgroundColor(Color.WHITE);
                    speakinga=true;
                }
            }
        });



        acting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actinga)
                {
                    acting.setBackgroundColor(Color.BLUE);
                    actinga=false;
                }
                else
                {
                    acting.setBackgroundColor(Color.WHITE);
                    actinga=true;
                }
            }
        });



        art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arta)
                {
                    art.setBackgroundColor(Color.BLUE);
                    arta=false;
                }
                else
                {
                    art.setBackgroundColor(Color.WHITE);
                    arta=true;
                }
            }
        });



        photography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photographya)
                {
                    photography.setBackgroundColor(Color.BLUE);
                    photographya=false;
                }
                else
                {
                    photography.setBackgroundColor(Color.WHITE);
                    photographya=true;
                }
            }
        });










        updateprofilepic = false;
        upload = (Button) findViewById(R.id.buttonContinue);
        try {
            final Cursor cursor = db.query(true, "my_profile", new String[]{"profile_pic", "interests"}, "phone_no=?", new String[]{reader.getString("phone_no", null)}, null, null, null, null);
            Log.d("bolean load", "" + getIntent().getBooleanExtra("load", false));
            if (cursor != null && cursor.getCount() > 0 && getIntent().getBooleanExtra("load", false)) {
                cursor.moveToFirst();

                if (cursor.getString(0) != null) {
                    FileInputStream img = this.openFileInput(cursor.getString(0));
                    Bitmap theImage = BitmapFactory.decodeStream(img);
                    imageView.setImageBitmap(scaleBitmap(Bitmap.createScaledBitmap(theImage, theImage.getWidth() / 2, theImage.getHeight() / 2, false)));
                }
                imageView.setAlpha(254);

            }



        } catch (SQLException e) {
            Log.d("sql error", e.getMessage());
        } catch (IllegalStateException e) {
            Log.d("sql error", e.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        /*editor.putBoolean("is_first", false);
        editor.commit();*/
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!pubadmna)
                    interests=interests+"Public Administration,";
                if (!enterpa)
                    interests=interests+"Enterpreneurship,";
                if (!poemsa)
                    interests=interests+"Poems,";
                if (!dancea)
                    interests=interests+"Dancing,";
                if (!singa)
                    interests=interests+"Singing,";
                if (!litrea)
                    interests=interests+"Literature,";
                if (!codinga)
                    interests=interests+"Coding,";
                if (!hackinga)
                    interests=interests+"Hacking,";
                if (!modellinga)
                    interests=interests+"Modelling,";
                if (!evntmanagementa)
                    interests=interests+"Event Management,";
                if (!speakinga)
                    interests=interests+"Speaking,";
                if (!actinga)
                    interests=interests+"Acting,";
                if (!arta)
                    interests=interests+"Arts,";
                if (!photographya)
                    interests=interests+"Photography,";

                interests=interests.substring(0,interests.length()-1);
                if (updateprofilepic && path!=null)
                    new UploadProfilePic(path, reader.getString("phone", null), ProfileSetup.this).execute();
                setInterests();
                ProfileSetup.this.finish();
                Intent intent=new Intent(ProfileSetup.this, MainActivity.class);
                startActivity(intent);
            }
        });



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

    private void setInterests(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, INTERESTS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response string", response);
                        ContentValues cv= new ContentValues();
                        cv.put("interests",interests);
                        db.update("my_profile",cv,null,null);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("response string", "mes " + error.getMessage() + " mes " + error.getLocalizedMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone",reader.getString("phone_no",""));
                params.put("interests",interests);
                return params;
            }

        };

        Cache cache = new DiskBasedCache(getApplicationContext().getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        requestQueue.add(stringRequest);
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
