package cn.com.hisistar.showclient.mould;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.luck.picture.lib.adapter.PictureImageGridAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Random;

import cn.com.hisistar.showclient.program.ProgramEditActivity;
import cn.com.hisistar.showclient.R;

public class MouldChooseFragment extends Fragment {

    private static final String TAG = "MouldChooseFragment";

    public static final String MOULD_NAME = "mould_name";
    public static final String MOULD_IMAGE_ID = "mould_image_id";
    public static final String MOULD_POSITION = "mould_position";


    private Mould[] mMoulds;

    private List<Mould> mMouldList = new ArrayList<>();
    private MouldAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.program_fragment, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mMoulds = new Mould[]{
                new Mould(getResources().getString(R.string.landscape_mould_one), R.drawable.landscape_mould_one),
                new Mould(getResources().getString(R.string.landscape_mould_two), R.drawable.landscape_mould_two),
                new Mould(getResources().getString(R.string.landscape_mould_three), R.drawable.landscape_mould_three),
                new Mould(getResources().getString(R.string.landscape_mould_four), R.drawable.landscape_mould_four),

                new Mould(getResources().getString(R.string.portrait_mould_one), R.drawable.portrait_mould_one),
                new Mould(getResources().getString(R.string.portrait_mould_two), R.drawable.portrait_mould_two),
                new Mould(getResources().getString(R.string.portrait_mould_three), R.drawable.portrait_mould_three),
                new Mould(getResources().getString(R.string.portrait_mould_four), R.drawable.portrait_mould_four)

        };
//        initMoulds();
        if (!mMouldList.isEmpty()) {
            mMouldList.clear();
        }
        Collections.addAll(mMouldList, mMoulds);
//        mMouldList.addAll(Arrays.asList(mMoulds));
        RecyclerView recyclerView = getActivity().findViewById(R.id.program_rv);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MouldAdapter(mMouldList);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MouldAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Mould mould = mMouldList.get(position);
                Toast.makeText(getActivity(), "position=" + position + " mould=" + mould.getMouldName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), ProgramEditActivity.class);
                Log.e(TAG, "onItemClick: mould.getMouldId()="+mould.getMouldId());
                intent.putExtra(MOULD_NAME, mould.getMouldName());
                intent.putExtra(MOULD_IMAGE_ID, mould.getMouldId());
                intent.putExtra(MOULD_POSITION, position);
                startActivity(intent);

            }
        });
    }

    private void initMoulds() {
        mMouldList.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(mMoulds.length);
            mMouldList.add(mMoulds[index]);
        }
    }
}
