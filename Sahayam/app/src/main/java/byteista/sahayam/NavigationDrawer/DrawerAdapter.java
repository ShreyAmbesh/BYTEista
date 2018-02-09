package byteista.sahayam.NavigationDrawer;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

import byteista.sahayam.R;
import byteista.sahayam.UserRegistration.ProfileSetup;
import byteista.sahayam.Utils.BitmapManipulator;
import byteista.sahayam.Utils.DatabaseHelper;
import byteista.sahayam.Utils.Display;

/**
 * Created by murli jee on 4/26/2017.
 */

public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private List<DrawerLayoutManager> list = Collections.emptyList();
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;
    Display display;
    int display_height;
    private int action_bar_height;
    private static final String MY_PREFERENCES = "my_preferences";
    SharedPreferences reader;
    SQLiteDatabase sqLiteDatabase;
    String image;
    Context context;

    public DrawerAdapter(Context context, List<DrawerLayoutManager> data,int action_bar_height) {
        list = data;
        this.context=context;
        display=new Display(context);
        display_height=display.getHeight()-25;
        this.action_bar_height=action_bar_height;
        inflater = LayoutInflater.from(context);
        DatabaseHelper helper=new DatabaseHelper(context);
        sqLiteDatabase=helper.getReadableDatabase();
        reader = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        getImagePath();


    }

    public void getImagePath()
    {
        try {
            Cursor cursor=sqLiteDatabase.query(true,"my_profile",new String[]{"profile_pic"},"jabber_id=?",new String[]{reader.getString("jabber_id",null)},null,null,null,null);

            if (cursor != null && cursor.getCount() > 0)
            {cursor.moveToFirst();
                image = cursor.getString(0);}
            sqLiteDatabase.close();
        }catch (SQLException e)
        {
            Log.d("sql error",e.getMessage());
        }
        catch (IllegalStateException e)
        {
            Log.d("sql error",e.getMessage());
        }


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = inflater.inflate(R.layout.drawer_header, parent, false);
            view.setMinimumHeight((display_height*3)/9);
            return new HeaderHolder(view);
        } else {
            View view = inflater.inflate(R.layout.custom_drawer_item_view, parent, false);
            view.setMinimumHeight((display_height*2)/9);
            return new ItemHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderHolder) {
            final HeaderHolder headerHolder= (HeaderHolder) holder;
            try {
                if (image!=null) {
                    FileInputStream img = context.openFileInput(image);
                    Bitmap theImage = BitmapFactory.decodeStream(img);
                    headerHolder.imageView.setImageBitmap(BitmapManipulator.scaleBitmap(Bitmap.createScaledBitmap(theImage, theImage.getWidth() / 2, theImage.getHeight() / 2, false)));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            DrawerLayoutManager current = list.get(position-1);
            ItemHolder itemHolder=(ItemHolder)holder;
            itemHolder.textView.setText(current.title);
            itemHolder.imageView.setImageResource(current.iconId);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)

            return TYPE_HEADER;
        else
            return TYPE_ITEM;
    }



    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        com.pkmmte.view.CircularImageView imageView;

        public HeaderHolder(View itemView) {
            super(itemView);
            imageView = (com.pkmmte.view.CircularImageView) itemView.findViewById(R.id.profile_pic_image_view_drawer);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            layoutParams.height =((display_height*3)/9);
            layoutParams.width=((display_height*3)/9);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            imageView.setLayoutParams(layoutParams);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, ProfileSetup.class).putExtra("load",true));

                }
            });


        }
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ItemHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);

            imageView = (ImageView) itemView.findViewById(R.id.image);
            android.view.ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.width =display.getWidth()-action_bar_height-15;
            layoutParams.height =(int)((display_height*1.5)/9) ;
            imageView.setLayoutParams(layoutParams);




        }
    }
}