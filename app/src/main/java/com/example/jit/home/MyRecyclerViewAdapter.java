package com.example.jit.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.List;

/**
 * Created by iyad on 7/29/2015.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>{

    Context context;
    List<Program> Programs;
    MyRecyclerViewListener listener;
    private ImageLoader imageLoader;
     VolleySingleton volleySingleton;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    public MyRecyclerViewAdapter(Context context, List<Program> Programs){
        this.context = context;
        this.Programs=Programs;
    }
        public void setRecyclerViewListener(MyRecyclerViewListener listener){
        this.listener = listener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_layout, parent, false);
        final MyViewHolder vh = new MyViewHolder(view);
        imageLoader = volleySingleton.getInstance().getImageLoader();
        /////////
        mRequestQueue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });/////////////////
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vh.getAdapterPosition() >=0 && vh.getAdapterPosition()<Programs.size())
                    listener.viewSelected(v, vh.getAdapterPosition());
            }
        });
        return vh;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Program s =Programs.get(position);

        holder.tv1.setText(s.getName());
        holder.tv2.setText(s.getDetail());
        holder.tv3.setText(s.getTime());
       // String urlLogo = s.getPhoto();
       // holder.im.setImageUrl(urlLogo,mImageLoader);
       holder.im.setImageResource(s.getImg());
        holder.c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    MainActivity.Fav_PROG.add(Programs.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return Programs.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv1;
        TextView tv2;
        TextView tv3;
       // NetworkImageView im;
       ImageView im;
        CheckBox c;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.name);
            tv2 = (TextView) itemView.findViewById(R.id.time);
            tv3 = (TextView) itemView.findViewById(R.id.detail);
           //im = (NetworkImageView) itemView.findViewById(R.id.imageView);
            im = (ImageView) itemView.findViewById(R.id.imageView);
            c=(CheckBox)itemView.findViewById(R.id.checkBox);
        }
    }

    public interface MyRecyclerViewListener{
        public void viewSelected(View v, int position);
    }
}
