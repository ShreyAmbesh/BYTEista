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
    SharedPreferences reader;
    SQLiteDatabase database;
    private static final String MY_PREFERENCES = "my_preferences";
    public static final String KEY_REGISTRATIONDATA = "registrationdata";
    private static final String REGISTER_URL = "http://35.187.242.68/byteista/user_registration.php";
    Gson gson;

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
        gson =new Gson();
        continu=v.findViewById(R.id.buttonContinue);
        DatabaseHelper helper=new DatabaseHelper(getContext());
        database=helper.getWritableDatabase();
        reader = getContext().getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);

        continu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    registerUser();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return v;
    }

    private void registerUser() throws JSONException {

        Thread connect=new Thread(new Runnable() {
            @Override
            public void run() {

                final String registrationData=gson.toJson(UserRegistration.registrationInfo);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("response string",response);


                                try {

                                    JSONObject jsonResponse=new JSONObject(response);
                                    UserRegistration.registrationInfo.USN=jsonResponse.getString("usn");
                                    UserRegistration.registrationInfo.SEMESTER=jsonResponse.getString("semester");
                                    UserRegistration.registrationInfo.DATEOFBIRTH=jsonResponse.getString("dob");
                                    UserRegistration.registrationInfo.BRANCH=jsonResponse.getString("branch");
                                    UserRegistration.registrationInfo.GENDER=jsonResponse.getString("gender");
                                    UserRegistration.registrationInfo.PHONENUMBER=jsonResponse.getString("phone");
                                    UserRegistration.registrationInfo.USERNAME=jsonResponse.getString("name");
                                    UserRegistration.registrationInfo.INTERESTS=jsonResponse.getString("interests");
                                    UserRegistration.registrationInfo.HOSTELITE=jsonResponse.getString("hostelite");


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                ContentValues values = new ContentValues();

                                values.put("name",UserRegistration.registrationInfo.USERNAME );
                                values.put("date_of_birth",UserRegistration.registrationInfo.DATEOFBIRTH );
                                values.put("gender",UserRegistration.registrationInfo.GENDER );
                                values.put("usn",UserRegistration.registrationInfo.USN );
                                values.put("branch",UserRegistration.registrationInfo.BRANCH );
                                values.put("interests",UserRegistration.registrationInfo.INTERESTS );
                                values.put("phone_no",UserRegistration.registrationInfo.PHONENUMBER );
                                values.put("semester",UserRegistration.registrationInfo.SEMESTER );
                                values.put("hostelite",UserRegistration.registrationInfo.HOSTELITE );
                                try {
                                    database.insert("my_profile",null,values);
                                    database.close();
                                }catch (SQLException e)
                                {
                                    Log.d("sql error",e.getMessage());
                                }



                                final SharedPreferences.Editor editor = reader.edit();

                                editor.putString("name", UserRegistration.registrationInfo.USERNAME);
                                editor.putString("date_of_birth", UserRegistration.registrationInfo.DATEOFBIRTH);
                                editor.putString("gender", UserRegistration.registrationInfo.GENDER);
                                editor.putString("usn",UserRegistration.registrationInfo.USN );
                                editor.putString("branch",UserRegistration.registrationInfo.BRANCH);
                                editor.putString("interests",UserRegistration.registrationInfo.INTERESTS);
                                editor.putString("phone_no", UserRegistration.registrationInfo.PHONENUMBER);
                                editor.putString("semester",UserRegistration.registrationInfo.SEMESTER );
                                editor.putString("hostelite",UserRegistration.registrationInfo.HOSTELITE );

                                editor.commit();
                                Intent intent=new Intent(getContext(),ProfileSetup.class);
                                startActivity(intent);
                                getActivity().finish();


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("response string","mes "+error.getMessage()+" mes "+error.getLocalizedMessage());
                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put(KEY_REGISTRATIONDATA,registrationData);
                        return params;
                    }

                };

                Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024);
                Network network = new BasicNetwork(new HurlStack());
                RequestQueue requestQueue = new RequestQueue(cache, network);
                requestQueue.start();
                requestQueue.add(stringRequest);


            }
        });
        connect.start();





    }
}
