package byteista.sahayam.Media;

/**
 * Created by shrey on 7/5/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import java.util.ArrayList;

import byteista.sahayam.R;


public class GridViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RecyclerViewFastScroller.BubbleTextGetter/*implements FastScrollerUtil.IHeaderAdapter , ICustomScroller*/ {

    Context context;

    public static ArrayList<Model_images> al_menu = new ArrayList<>();
    int int_position;
    private LayoutInflater inflater;
    public  static final int TYPE_DATE=1;
    public  static final int TYPE_IMAGE=0;



    public GridViewAdapter(Context context, ArrayList<Model_images> al_menu,int int_position) {
        this.al_menu = al_menu;
        this.context = context;
        this.int_position = int_position;
        inflater = LayoutInflater.from(context);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==TYPE_IMAGE)
        {View view = inflater.inflate(R.layout.adapter_photosfolder, parent, false);

        return new ViewHolder(view);}
        else
        {View view = inflater.inflate(R.layout.dateholder, parent, false);

            return new DateHolder(view);}
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder)
        {final ViewHolder viewHolder= (ViewHolder) holder;

        viewHolder.tv_foldersize.setVisibility(View.GONE);

            viewHolder.tv_foldern.setVisibility(View.GONE);

            PhotosActivity.dateAppBar.setText(al_menu.get(int_position).getAl_imagepath().get(position)[1]);


            Glide.with(context).load(al_menu.get(int_position).getAl_imagepath().get(position)[2])
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .thumbnail(0.1f)
                    .into(viewHolder.iv_image);


        }
        else
        {

            final DateHolder dateHolder= (DateHolder) holder;
            dateHolder.date.setText(al_menu.get(int_position).getAl_imagepath().get(position)[1]);
            PhotosActivity.dateAppBar.setText(al_menu.get(int_position).getAl_imagepath().get(position)[1]);

            if (position==0)
                dateHolder.view.setVisibility(View.GONE);
            else
                dateHolder.view.setVisibility(View.VISIBLE);

        }


    }

    @Override
    public int getItemViewType(int position) {
        if(GridViewAdapter.al_menu.get(int_position).getAl_imagepath().get(position)[0].equals("path"))
            return TYPE_IMAGE;
        else
            return TYPE_DATE;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return al_menu.get(int_position).getAl_imagepath().size();
    }

    @Override
    public String getTextToShowInBubble(int pos) {
        return al_menu.get(int_position).getAl_imagepath().get(pos)[1];
    }

    private static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_foldern, tv_foldersize;
        ImageView iv_image;


        public ViewHolder(View itemView) {
            super(itemView);
            tv_foldern= (TextView) itemView.findViewById(R.id.tv_folder);
            tv_foldersize= (TextView) itemView.findViewById(R.id.tv_folder2);
            iv_image= (ImageView) itemView.findViewById(R.id.iv_image);

        }
    }


    private static class DateHolder extends RecyclerView.ViewHolder{
        TextView date;
        View view;


        public DateHolder(View itemView) {
            super(itemView);
            view=itemView;
            date= (TextView) itemView.findViewById(R.id.date);


        }
    }


}
