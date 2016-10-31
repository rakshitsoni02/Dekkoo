package com.android.dekkoo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.dekkoo.R;
import com.android.dekkoo.data.model.Response;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.CharacterHolder> {
    private List<Response> mResponses;

    @Inject
    public VideosAdapter() {
        this.mResponses = new ArrayList<>();
    }

    @Override
    public CharacterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_video, parent, false);
        return new CharacterHolder(view);
    }

    @Override
    public void onBindViewHolder(final CharacterHolder holder, final int position) {
        final Context context = holder.itemView.getContext();
        final Response response = mResponses.get(position);
        holder.viewText.setText(response.getTitle());
        Glide.with(context)
                .load(response.getThumbnails().get(0).getUrl())
                .into(holder.subImage);
        Glide.with(context)
                .load(response.getThumbnails().get(0).getUrl())
                .into(new ViewTarget<FrameLayout, GlideDrawable>(holder.containerBackground) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation anim) {
                        FrameLayout myView = this.view;
                        myView.setBackground(resource);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mResponses.size();
    }

    public void setVideos(List<Response> values) {
        mResponses = values;
        notifyDataSetChanged();
    }

    class CharacterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sub_thumbnail)
        ImageView subImage;
        @BindView(R.id.text_view)
        TextView viewText;
        @BindView(R.id.container_card)
        FrameLayout containerBackground;

        public CharacterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}