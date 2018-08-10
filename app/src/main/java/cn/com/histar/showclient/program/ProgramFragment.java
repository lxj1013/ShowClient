package cn.com.histar.showclient.program;

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
import java.util.List;
import java.util.Random;

import cn.com.histar.showclient.R;

public class ProgramFragment extends Fragment {

    private Program[] mPrograms = {new Program("Apple", R.drawable.apple)
            , new Program("Banana", R.drawable.banana)
            , new Program("Orange", R.drawable.orange)
            , new Program("Watermelon", R.drawable.watermelon)
            , new Program("Pear", R.drawable.pear)
            , new Program("Grape", R.drawable.grape)
            , new Program("Pineapple", R.drawable.pineapple)
            , new Program("Strawberry", R.drawable.strawberry)
            , new Program("Cherry", R.drawable.cherry)
            , new Program("Mango", R.drawable.mango)};

    private List<Program> mProgramList = new ArrayList<>();
    private ProgramAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.program_fragment, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        initPrograms();

        RecyclerView recyclerView = getActivity().findViewById(R.id.program_rv);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ProgramAdapter(mProgramList);
        recyclerView.setAdapter(mAdapter);

    }

    private void initPrograms() {
        mProgramList.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(mPrograms.length);
            mProgramList.add(mPrograms[index]);
        }
    }
}
