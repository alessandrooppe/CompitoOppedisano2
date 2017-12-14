package itspiemonte.compitooppedisano;

/**
 * Created by alessandro on 14/12/17.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class CompitinoAdapter extends RecyclerView.Adapter<CompitinoAdapter.ArticleViewHolder> {
    private final Context context;
    private final ArrayList items;

    public CompitinoAdapter(Activity activity, ArrayList items) {
        this.context = activity;
        this.items = items;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        return new ArticleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, final int position) {
        holder.textView.setText((String) items.get(position));
        holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.thumb));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ArticleActivity.class);
                i.putExtra("articleTitle", (String) items.get(position));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView textView;// init the item view's
        ImageView imageView;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.title);
            imageView = (ImageView) itemView.findViewById(R.id.thumb);
        }
    }
}
