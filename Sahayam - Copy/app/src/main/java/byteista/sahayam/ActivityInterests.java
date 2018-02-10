package byteista.sahayam;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import byteista.sahayam.Utils.InterestModel;

public class ActivityInterests extends AppCompatActivity {
    Button pubadmn, enterp, poems, dance, sing, litre, coding, hacking, modelling, evntmanagement, speaking, acting, art, photography;
    private String GETInterest = "http://35.187.242.68/byteista/GetInterests.php";
    public static ArrayList<InterestModel> data;

    private Toolbar app_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);
        app_bar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(app_bar);
        pubadmn = findViewById(R.id.pubadmn);
        enterp = findViewById(R.id.enterp);
        poems = findViewById(R.id.poems);
        dance = findViewById(R.id.dance);
        sing = findViewById(R.id.sing);
        litre = findViewById(R.id.litre);
        coding = findViewById(R.id.coding);
        hacking = findViewById(R.id.hacking);
        modelling = findViewById(R.id.modelling);
        evntmanagement = findViewById(R.id.evntmanagement);
        speaking = findViewById(R.id.speaking);
        acting = findViewById(R.id.acting);
        art = findViewById(R.id.art);
        photography = findViewById(R.id.photography);


        data = new ArrayList<>();


        pubadmn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInterest("Public Administration");
            }
        });

        enterp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInterest("Enterprenurship");
            }
        });

        poems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInterest("Poems");
            }
        });


        dance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInterest("Dancing");
            }
        });


        sing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInterest("Singing");
            }
        });


        litre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInterest("Literature");
            }
        });


        coding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInterest("Coding");
            }
        });


        hacking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInterest("Hacking");
            }
        });


        modelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInterest("Modelling");
            }
        });


        evntmanagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInterest("Event Management");
            }
        });


        speaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInterest("Speaking");
            }
        });


        acting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInterest("Acting");
            }
        });


        art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInterest("Art");
            }
        });


        photography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInterest("Photography");
            }
        });
    }

    void getInterest(final String interest) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GETInterest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response string", response);
                        try {
                            JSONArray jsonarray = new JSONArray(response);
                            data=new ArrayList<>();
                            for (int i = 0; i < jsonarray.length(); i++) {
                                final JSONObject jsonobject = jsonarray.getJSONObject(i);
                                data.add(new InterestModel(jsonobject.getString("name"), jsonobject.getString("usn")));
                            }
                        } catch (JSONException e) {

                        }
                        Intent intent=new Intent(ActivityInterests.this,ActivityInterestList.class);
                        startActivity(intent);


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
                params.put("interest", interest);
                return params;
            }

        };

        Cache cache = new DiskBasedCache(getApplicationContext().getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        requestQueue.add(stringRequest);
    }


}
