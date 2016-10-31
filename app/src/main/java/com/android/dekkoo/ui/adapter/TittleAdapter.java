package com.android.dekkoo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.dekkoo.R;
import com.android.dekkoo.data.model.Response;
import com.android.dekkoo.util.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TittleAdapter extends RecyclerView.Adapter<TittleAdapter.CharacterHolder> {
    public List<Response> mResponseList;
    private ItemClickListener mItemClickListener;

    @Inject
    public TittleAdapter() {
        this.mResponseList = new ArrayList<>();
    }

    @Override
    public CharacterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_tittle, parent, false);
        return new CharacterHolder(view);
    }

    @Override
    public void onBindViewHolder(final CharacterHolder holder, final int position) {
        final Context context = holder.itemView.getContext();
        final Response response = mResponseList.get(position);
        holder.nameText.setText(response.getTitle().toUpperCase());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.tittleItemSelected(position, response.getValues(), response.getTitle());
            }
        });
    }

    public Response getItemForPosition(int position) {
        return mResponseList.get(position);
    }

    @Override
    public int getItemCount() {
        return mResponseList.size();
    }

    public void setTittles(List<Response> responseList, ItemClickListener itemClickListener) {
        mResponseList = responseList;
        mItemClickListener = itemClickListener;
        notifyDataSetChanged();
    }

    class CharacterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_view)
        TextView nameText;

        public CharacterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}