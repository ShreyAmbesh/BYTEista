package byteista.sahayam.MainActivity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

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

import byteista.sahayam.R;

public class ActivityEvents extends AppCompatActivity {

    private static final String REGISTER_URL = "http://35.187.242.68/byteista/Events.php";
    ArrayList<EventsModel> data;
    ListView events;
    public EventsAdapter adapter;
    Context context;

    private Toolbar app_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        context = this;
        data = new ArrayList<>();
        events=findViewById(R.id.eventList);
        app_bar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(app_bar);
        Thread connect = new Thread(new Runnable() {
            @Override
            public void run() {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("response string", response);
                                try {
                                    JSONArray jsonarray = new JSONArray(response);

                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        final JSONObject jsonobject = jsonarray.getJSONObject(i);
                                        data.add(new EventsModel(jsonobject.getString("name"), jsonobject.getString("date"), jsonobject.getString("description"), jsonobject.getInt("likes")));
                                    }
                                } catch (JSONException e) {

                                }

                                adapter = new EventsAdapter(ActivityEvents.this, data);
                                events.setAdapter(adapter);

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
                        return params;
                    }

                };

                Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
                Network network = new BasicNetwork(new HurlStack());
                RequestQueue requestQueue = new RequestQueue(cache, network);
                requestQueue.start();
                requestQueue.add(stringRequest);


            }
        });
        connect.start();
    }
}
