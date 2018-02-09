package byteista.sahayam.UserRegistration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import byteista.sahayam.R;


/**
 * Created by Lykan on 9-02-2018.
 */

public class FragmentOpening extends Fragment {
    RegistrationPager viewPager;

    static FragmentOpening newInstance(RegistrationPager pager) {
        FragmentOpening f = new FragmentOpening();
        Bundle args = new Bundle();
        args.putSerializable("pager",pager);
        f.setArguments(args);

        return f;
    }

    public FragmentOpening() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null)
            viewPager= (RegistrationPager) savedInstanceState.getSerializable("pager");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_opening, container, false);

        return v;
    }
    @Override
    public void onDestroy() {

        Runtime.getRuntime().gc();
        super.onDestroy();

    }
}
