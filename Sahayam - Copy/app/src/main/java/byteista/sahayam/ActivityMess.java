package byteista.sahayam;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;

public class ActivityMess extends AppCompatActivity {

    private Toolbar app_bar;
    Button done;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess);
        context=this;
        CalendarView view= findViewById(R.id.calendarView);
        app_bar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(app_bar);
        view.setDate(System.currentTimeMillis());
        done=findViewById(R.id.buttonContinue);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Your Schedule Is Updated",Toast.LENGTH_LONG).show();
            }
        });
    }
}
