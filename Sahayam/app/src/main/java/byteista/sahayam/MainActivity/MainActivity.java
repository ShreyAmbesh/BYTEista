package byteista.sahayam.MainActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import byteista.sahayam.R;
import byteista.sahayam.UserRegistration.UserRegistration;
import byteista.sahayam.Utils.Display;

public class MainActivity extends AppCompatActivity {
    private static final String MY_PREFERENCES = "my_preferences";
    SharedPreferences reader;
    private Toolbar app_bar;
    private DrawerLayout drawerLayout;
    ImageView barcode;
    TextView name,usn;
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
            editor.putBoolean("is_first", false);
            editor.commit();


            barcode=findViewById(R.id.barcode);
            name=findViewById(R.id.name);
            usn=findViewById(R.id.usn);
            usn.setText(reader.getString("usn",""));
            name.setText(reader.getString("name",""));
            app_bar = (Toolbar) findViewById(R.id.app_bar);
            setSupportActionBar(app_bar);
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
            byteista.sahayam.NavigationDrawer.NavigationDrawerFragment nav = (byteista.sahayam.NavigationDrawer.NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
            nav.setUp(R.id.fragment_navigation_drawer, drawerLayout, app_bar, getActionBarHeight());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            String text=reader.getString("usn",null); // Whatever you need to encode in the QR code
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            Display display=new Display(this);
            try {
                if (text != null) {
                    BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.CODE_128, display.getWidth()-60,(int)(display.getWidth()-60)/3);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    barcode.setImageBitmap(bitmap);
                }

            } catch (WriterException e) {
                e.printStackTrace();
            }


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

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);  // OPEN DRAWER

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
