package byteista.sahayam.UserRegistration;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import byteista.sahayam.R;
import byteista.sahayam.Utils.DatabaseHelper;


public class FragmentOtp extends Fragment {
    RegistrationPager viewPager;
    Button continu;

    static FragmentOtp newInstance(RegistrationPager pager) {
        FragmentOtp f = new FragmentOtp();
        Bundle args = new Bundle();
        args.putSerializable("pager",pager);
        f.setArguments(args);

        return f;
    }

    public FragmentOtp() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null)
            viewPager= (RegistrationPager) savedInstanceState.getSerializable("pager");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_otp, container, false);
        return v;
    }


}
