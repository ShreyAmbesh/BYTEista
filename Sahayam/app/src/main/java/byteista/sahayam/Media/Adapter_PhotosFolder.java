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


public class Adapter_PhotosFolder extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    Context context;

    ArrayList<Model_images> al_menu = new ArrayList<>();


    public Adapter_PhotosFolder(Context context, ArrayList<Model_images> al_menu) {
        this.al_menu = al_menu;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_photosfolder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder= (ViewHolder) holder;
        viewHolder.tv_foldern.setText(al_menu.get(position).getStr_folder());
        viewHolder.tv_foldersize.setText(al_menu.get(position).getSize()+"");

        Glide.with(context).load(al_menu.get(position).getAl_imagepath().get(1)[2])
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .thumbnail(0.1f)
                .into(viewHolder.iv_image);


    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return al_menu.size();
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


}