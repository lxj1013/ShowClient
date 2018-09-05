package cn.com.hisistar.showclient.program;

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

import com.luck.picture.lib.entity.LocalMedia;

import org.litepal.LitePal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.com.hisistar.showclient.R;
import cn.com.hisistar.showclient.database.LocalMediaTable;
import cn.com.hisistar.showclient.database.ProgramTable;
import cn.com.hisistar.showclient.database.SettingsTable;
import cn.com.hisistar.showclient.transfer.SettingsTransfer;


public class ProgramFragment extends Fragment {

    private static final String TAG = "ProgramFragment";

    public static final String PROGRAM_TABLE_ID = "program_table_id";
    public static final String MOULD_NAME = "mould_name";
    public static final String MOULD_IMAGE_ID = "mould_image_id";
    public static final String MOULD_POSITION = "mould_position";

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
    private List<ProgramTable> mProgramTableList = new ArrayList<>();
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
        mAdapter.setOnItemClickListener(new ProgramAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Program program = mProgramList.get(position);
                Toast.makeText(getActivity(), "programName=" + program.getProgramName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ProgramEditActivity.class);
                intent.putExtra(PROGRAM_TABLE_ID, program.getId());
                intent.putExtra(MOULD_NAME, program.getProgramName());
                intent.putExtra(MOULD_IMAGE_ID, program.getMouldImg());
                intent.putExtra(MOULD_POSITION, program.getMouldPosition());
                startActivity(intent);
            }
        });
    }

    private void initPrograms() {
        mProgramList.clear();
        mProgramTableList.clear();

        mProgramTableList = LitePal.order("id desc").find(ProgramTable.class);

        mProgramList = getProgramList(mProgramTableList);

        /*for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(mPrograms.length);
            mProgramList.add(mPrograms[index]);
        }*/
    }

    private List<Program> getProgramList(List<ProgramTable> programTableList) {
        List<Program> programList = new ArrayList<>();
        ProgramTable programTable;
        Program program;
        if ((programTableList != null) && (!programTableList.isEmpty())) {
            for (int i = 0; i < programTableList.size(); i++) {
                programTable = programTableList.get(i);
                program = new Program();
                program.setId(programTable.getId());
                program.setProgramName(programTable.getProgramName());
                program.setMouldImg(programTable.getMouldImg());
                program.setMouldPosition(programTable.getMouldPosition());
                programList.add(program);
            }
        }
        return programList;
    }

}
