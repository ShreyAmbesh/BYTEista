package byteista.sahayam.MainActivity;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import byteista.sahayam.R;

/**
 * Created by Lykan on 10-02-2018.
 */

class EventsAdapter extends BaseAdapter {
    ArrayList<EventsModel> data;

    private static final String LIKEDISLIKE_URL = "http://35.187.242.68/byteista/LikeDislikeEvent.php";
    public EventsAdapter(Activity activity, ArrayList<EventsModel> data) {
        this.activity = activity;
        this.data = data;
        this.inflater = activity.getLayoutInflater();
    }

    Activity activity;
    private LayoutInflater inflater = null;

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi = inflater.inflate(R.layout.events_list_element, null);
        TextView title = vi.findViewById(R.id.title);
        final TextView date = vi.findViewById(R.id.date);
        TextView discription = vi.findViewById(R.id.discription);
        TextView likeNo = vi.findViewById(R.id.likeNo);
        final ImageButton likeButton = vi.findViewById(R.id.likeButton);
        title.setText(data.get(position).NAME);
        date.setText(data.get(position).DATE);
        discription.setText(data.get(position).DISCRIPTION);
        likeNo.setText(""+data.get(position).LIKES);
        if (data.get(position).LIKED)
        {
            likeButton.setImageResource(R.drawable.ic_thumbs_down);

        }
        else
        {
            likeButton.setImageResource(R.drawable.ic_thumbs_up);
        }

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).LIKED)
                {

                    --data.get(position).LIKES;
                    notifyDataSetChanged();

                    data.get(position).LIKED=false;
                    likeButton.setImageResource(R.drawable.ic_thumbs_down);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, LIKEDISLIKE_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("response string", response);


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
                            params.put("type","0");
                            params.put("name",data.get(position).NAME);
                            return params;
                        }

                    };

                    Cache cache = new DiskBasedCache(activity.getCacheDir(), 1024 * 1024);
                    Network network = new BasicNetwork(new HurlStack());
                    RequestQueue requestQueue = new RequestQueue(cache, network);
                    requestQueue.start();
                    requestQueue.add(stringRequest);
                }
                else
                {
                    ++data.get(position).LIKES;
                    notifyDataSetChanged();

                    data.get(position).LIKED=true;
                    likeButton.setImageResource(R.drawable.ic_thumbs_up);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, LIKEDISLIKE_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("response string", response);


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
                            params.put("type","1");
                            params.put("name",data.get(position).NAME);
                            return params;
                        }

                    };

                    Cache cache = new DiskBasedCache(activity.getCacheDir(), 1024 * 1024);
                    Network network = new BasicNetwork(new HurlStack());
                    RequestQueue requestQueue = new RequestQueue(cache, network);
                    requestQueue.start();
                    requestQueue.add(stringRequest);
                }
            }
        });


        return vi;
    }
}
