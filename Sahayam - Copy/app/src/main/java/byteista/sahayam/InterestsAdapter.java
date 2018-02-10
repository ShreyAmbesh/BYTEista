package byteista.sahayam;

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

import byteista.sahayam.MainActivity.EventsModel;
import byteista.sahayam.Utils.InterestModel;

/**
 * Created by Lykan on 10-02-2018.
 */

class InterestsAdapter extends BaseAdapter {
    ArrayList<InterestModel> data;

    private static final String LIKEDISLIKE_URL = "http://35.187.242.68/byteista/LikeDislikeEvent.php";
    public InterestsAdapter(Activity activity, ArrayList<InterestModel> data) {
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

        View vi = inflater.inflate(R.layout.interest_element, null);
        TextView name = vi.findViewById(R.id.name);
        final TextView usn = vi.findViewById(R.id.usn);
        name.setText(data.get(position).NAME);
        usn.setText(data.get(position).USN);


        return vi;
    }
}
