package byteista.sahayam.UserRegistration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import byteista.sahayam.R;

public class FragmentPhoneNumber extends Fragment {

    EditText phoneNumberEditText;


    RegistrationPager viewPager;

    static FragmentPhoneNumber newInstance(RegistrationPager pager) {
        FragmentPhoneNumber f = new FragmentPhoneNumber();
        Bundle args = new Bundle();
        args.putSerializable("pager",pager);
        f.setArguments(args);

        return f;
    }

    public FragmentPhoneNumber() {
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
        View v=inflater.inflate(R.layout.fragment_phone_number, container, false);



        // attaching data adapter to spinner

        Button btnContinue= (Button) v.findViewById(R.id.buttonContinue);
        phoneNumberEditText= (EditText) v.findViewById(R.id.phone_number_edit);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestOtp();


            }
        });
        return v;
    }

    private void requestOtp() {
        UserRegistration.registrationInfo.PHONENUMBER=phoneNumberEditText.getText().toString();
        UserRegistration.registrationPager.setCurrentItem(2);
    }


    @Override
    public void onDestroy() {

            Runtime.getRuntime().gc();
            super.onDestroy();

    }

}
