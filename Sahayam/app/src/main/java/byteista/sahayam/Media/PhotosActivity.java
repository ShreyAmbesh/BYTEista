package byteista.sahayam.Media;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import java.net.URI;

import byteista.sahayam.R;
import byteista.sahayam.Utils.ClickListener;
import byteista.sahayam.Utils.MessageInfo;
import byteista.sahayam.Utils.RecyclerTouchListener;


/**
 * Created by shrey on 20/3/17.
 */

public class PhotosActivity extends AppCompatActivity {
    int int_position;
    private RecyclerView gridView;
    GridViewAdapter adapter;
    public static Toolbar app_bar;
    public static TextView dateAppBar;
    public static GridLayoutManager grid;
    private static final int REQUEST_PERMISSIONS = 100;
    private static final int REQUEST_Code = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        gridView = (RecyclerView) findViewById(R.id.gv_folder);
        final RecyclerViewFastScroller fastScroller = (RecyclerViewFastScroller) findViewById(R.id.fastscroller);
        int_position = getIntent().getIntExtra("value", 0);
        grid=new GridLayoutManager(this,2);
        app_bar=(Toolbar)findViewById(R.id.gallery_app_bar);
        setSupportActionBar(app_bar);

        dateAppBar=app_bar.findViewById(R.id.date_app_bar);


        gridView.addOnItemTouchListener(new RecyclerTouchListener(this, gridView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                if(GridViewAdapter.al_menu.get(int_position).getAl_imagepath().get(position)[0].equals("path"))
                {
                    Intent intent = new Intent();

                        intent.putExtra("TYPE",MessageInfo.TYPE_IMAGE);
                    intent.setData(Uri.parse(GridViewAdapter.al_menu.get(int_position).getAl_imagepath().get(position)[2].replace("file://", "")));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        grid.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch(adapter.getItemViewType(position)){
                    case GridViewAdapter.TYPE_IMAGE:
                        return 1;
                    case GridViewAdapter.TYPE_DATE:
                        return 2;
                    default:
                        return -1;
                }


            }
        });



        gridView.setLayoutManager(grid);
        adapter = new GridViewAdapter(this,GalleryActivity.al_images,int_position);
        gridView.setAdapter(adapter);
        fastScroller.needBubble=true;
        fastScroller.setRecyclerView(gridView);
        fastScroller.setViewsToUse(R.layout.scrollbar, R.id.fastscroller_bubble, R.id.fastscroller_handle);
        gridView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {

            }


           });

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);


        if(requestCode==REQUEST_Code) {
            if (resultCode == RESULT_OK) {
                Intent intent=new Intent();
                intent.putExtra("TYPE",imageReturnedIntent.getCharExtra("TYPE",'0'));
                intent.setData(imageReturnedIntent.getData());
                setResult(RESULT_OK, intent);
                finish();

            }

        }
    }
}