package byteista.sahayam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

public class ActivityInterestList extends AppCompatActivity {

    ListView interest;

    private Toolbar app_bar;
    InterestsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_list);
        app_bar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(app_bar);
        interest=findViewById(R.id.interestList);
        adapter=new InterestsAdapter(this,ActivityInterests.data);
        interest.setAdapter(adapter);
    }


}
