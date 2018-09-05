package cn.com.hisistar.showclient.program;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import cn.com.hisistar.showclient.R;

/**
 * @author lxj
 * @date 2018/8/25
 */
public class SubEditFragment extends Fragment {

    private String mSubtitle = "";
    private EditText mSubEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_edit_fragment, container, false);
        mSubEditText = view.findViewById(R.id.program_edit_sub_ev);
        mSubEditText.setText(mSubtitle);
        return view;
    }

    public void setSubtitle(String subtitle) {
        mSubtitle = subtitle;
    }

    public String getSubtitle() {
        if (mSubEditText != null)
            mSubtitle = mSubEditText.getText().toString();
        return mSubtitle;
    }

}
