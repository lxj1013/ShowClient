package cn.com.hisistar.showclient.mould;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import cn.com.hisistar.showclient.R;

public class MouldFragment extends Fragment {

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
