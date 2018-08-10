package cn.com.histar.showclient.mould;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import cn.com.histar.showclient.R;
import cn.com.histar.showclient.mould.Mould;

public class MouldFragment extends Fragment {

    private Mould[] mMoulds = {new Mould("Mould one", R.drawable.landscape_mould_one)
            , new Mould("Mould two", R.drawable.landscape_mould_two)};

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

//        initMoulds();
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
