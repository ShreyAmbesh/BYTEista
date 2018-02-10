package byteista.sahayam.UserRegistration;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.io.Serializable;

/**
 * Created by Lykan on 09-02-2018.
 */
public class RegistrationPager extends ViewPager implements Serializable
{

    public RegistrationPager(Context context) {
        super(context);
    }

    public RegistrationPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
