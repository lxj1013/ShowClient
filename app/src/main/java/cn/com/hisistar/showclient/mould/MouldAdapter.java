package cn.com.hisistar.showclient.mould;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import cn.com.histar.showclient.R;

public class MouldAdapter extends RecyclerView.Adapter<MouldAdapter.ViewHolder> {

    private Context mContext;
    private List<Mould> mMouldList;

    private static final String TAG = "MouldAdapter";

    public MouldAdapter(List<Mould> mouldList) {
        mMouldList = mouldList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.program_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        if (mItemClickListener != null) {
            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    mItemClickListener.onItemClick(position, v);

//                    Mould mould = mMouldList.get(position);
//                    Log.e(TAG, "onClick: position = " + position + "   " + mould.getMouldName());
//                    Toast.makeText(mContext, "position = " + position + "   " + mould.getMouldName(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Mould mould = mMouldList.get(position);
        holder.mMouldTv.setText(mould.getMouldName());
        RequestOptions requestOptions = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(mContext).load(mould.getMouldId()).apply(requestOptions).into(holder.mMouldIv);
    }


    @Override
    public int getItemCount() {
        return mMouldList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView mCardView;
        ImageView mMouldIv;
        TextView mMouldTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCardView = (CardView) itemView;
            mMouldIv = itemView.findViewById(R.id.program_image);
            mMouldTv = itemView.findViewById(R.id.program_name);
        }
    }

    private OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
}
