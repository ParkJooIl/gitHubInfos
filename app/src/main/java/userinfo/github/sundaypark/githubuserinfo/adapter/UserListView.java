package userinfo.github.sundaypark.githubuserinfo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import userinfo.github.sundaypark.githubuserinfo.ImageManager;
import userinfo.github.sundaypark.githubuserinfo.R;
import userinfo.github.sundaypark.githubuserinfo.core.item_core;
import userinfo.github.sundaypark.githubuserinfo.core.item_repo;
import userinfo.github.sundaypark.githubuserinfo.core.item_userinfo;

public class UserListView extends RecyclerView.Adapter <RecyclerView.ViewHolder>{
    Context mContext;
    ArrayList<Object> itemCores ;
    public UserListView(Context context , ArrayList<Object> cores) {
        mContext = context;
        itemCores = cores;
    }

    @Override
    public int getItemViewType(int position) {
        return ((item_core)itemCores.get(position)).getTYPE();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case item_core.ITEMCODE_USER: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_userinfo, parent, false);
                return new ViewHolder_user(view);
            }
            case item_core.ITEMCODE_REPO: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_repo, parent, false);
                return new ViewHolder_repo(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(((item_core)itemCores.get(position)).getTYPE() == item_core.ITEMCODE_USER){
            ((ViewHolder_user)holder).bind(position);
        }else{
            ((ViewHolder_repo)holder).bind(position);
        }
    }


    @Override
    public int getItemCount() {
        return itemCores.size();
    }

    public class ViewHolder_repo extends RecyclerView.ViewHolder{
        TextView name;
        TextView description;
        TextView stargazers_count;
        public ViewHolder_repo(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.reponame);
            description = (TextView) itemView.findViewById(R.id.repodes);
            stargazers_count = (TextView) itemView.findViewById(R.id.count);

        }
        public void bind(int position){
            item_repo itemRepo = (item_repo)itemCores.get(position);
            name.setText(itemRepo.getname());
            description.setText(itemRepo.getdescription());
            stargazers_count.setText(itemRepo.getstargazers_count());
        }
    }
    public class ViewHolder_user extends RecyclerView.ViewHolder{

        TextView textView_name;
        ImageView Thum;
        public ViewHolder_user(View itemView) {
            super(itemView);
            textView_name = (TextView)itemView.findViewById(R.id.textView_name);
            Thum = (ImageView)itemView.findViewById(R.id.imageView);
        }
        public void bind(int position){
            item_userinfo userinfo = (item_userinfo)itemCores.get(position);
            textView_name.setText(userinfo.getlogin());
            Glide.with(mContext).load(userinfo.getavatar_url()).into(Thum);
        }
    }
    class ImageLoader extends AsyncTask<String, Integer , Bitmap> {
        ImageView imageView;

        public ImageLoader(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... objects) {
            ImageManager imageLoader = new ImageManager(mContext);
            return imageLoader.getImageFile(objects[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap != null){
                imageView.setImageBitmap(bitmap);
            }
            super.onPostExecute(bitmap);
        }
    }
}
