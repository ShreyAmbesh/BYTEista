package byteista.sahayam.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;

import byteista.sahayam.R;
import byteista.sahayam.UserRegistration.UserRegistration;

public class MainActivity extends AppCompatActivity {
    private static final String MY_PREFERENCES = "my_preferences";
    SharedPreferences reader;
    private Toolbar app_bar;
    private DrawerLayout drawerLayout;
    private static final String TAG = "ChatService";

    private boolean mBounded;
    public static Context context;

    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        reader = this.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        editor = reader.edit();
        if (isFirst(getBaseContext())) {

            editor.putBoolean("MainActivity", false);
            editor.apply();
            startActivity(new Intent(MainActivity.this, UserRegistration.class));
            finish();
        } else {
            setContentView(R.layout.activity_main);
            context = this;
            setContentView(R.layout.activity_main);
            editor.putBoolean("is_first", false);
            editor.commit();




            app_bar = (Toolbar) findViewById(R.id.app_bar);
            setSupportActionBar(app_bar);
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
            byteista.sahayam.NavigationDrawer.NavigationDrawerFragment nav = (byteista.sahayam.NavigationDrawer.NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
            nav.setUp(R.id.fragment_navigation_drawer, drawerLayout, app_bar, getActionBarHeight());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);




            editor.putBoolean("active", false);
            editor.commit();





        }
    }
    public boolean isFirst(Context context) {
        final boolean first = reader.getBoolean("is_first", true);


        return first;
    }
    private int getActionBarHeight() {
        int actionBarHeight = getSupportActionBar().getHeight();
        if (actionBarHeight != 0)
            return actionBarHeight;
        final TypedValue tv = new TypedValue();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        } else if (getTheme().resolveAttribute(byteista.sahayam.R.attr.actionBarSize, tv, true))
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        return actionBarHeight;
    }
}
