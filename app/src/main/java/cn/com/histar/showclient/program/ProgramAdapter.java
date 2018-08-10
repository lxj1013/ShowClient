package cn.com.histar.showclient.program;

import android.content.Context;
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

import java.util.List;

import cn.com.histar.showclient.R;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder> {

    private static final String TAG = "ProgramAdapter";

    private Context mContext;
    private List<Program> mProgramList;

    ProgramAdapter(List<Program> programList) {
        mProgramList = programList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.program_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Log.e(TAG, "onClick: " + position);
                Log.e(TAG, "onClick: " + "size = " + getItemCount());
                Program program = mProgramList.get(position);
                Log.e(TAG, "onClick: position = " + position + "   " + program.getProgramName());
                Toast.makeText(mContext, "position = " + position + "   " + program.getProgramName(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Program program = mProgramList.get(position);
        holder.mProgramNameTv.setText(program.getProgramName());
        Glide.with(mContext).load(program.getProgramId()).into(holder.mProgramIv);
    }


    @Override
    public int getItemCount() {
        return mProgramList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView mCardView;
        ImageView mProgramIv;
        TextView mProgramNameTv;

        ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView;
            mProgramIv = itemView.findViewById(R.id.program_image);
            mProgramNameTv = itemView.findViewById(R.id.program_name);
        }
    }
}
