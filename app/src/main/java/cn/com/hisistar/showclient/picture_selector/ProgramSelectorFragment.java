package cn.com.hisistar.showclient.picture_selector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import cn.com.hisistar.showclient.picture_selector.adapter.GridImageAdapter;
import cn.com.histar.showclient.R;

/**
 * @author lxj
 * @date 2018/8/23
 */
public class ProgramSelectorFragment extends Fragment {

    private static final String TAG = "ProgramSelectorFragment";

    private static final int MAX_SELECT_NUM = 100;
    private static final int MIN_SELECT_NUM = 1;
    private static final int THEME_ID = R.style.picture_QQ_style;
    private static final int PICTURE_MIME_TYPE = PictureMimeType.ofAll();
    private static final int CHOOSE_MODE = PictureConfig.MULTIPLE;

    private int pictureMimeType = 0;

    private List<LocalMedia> selectList = new ArrayList<>();

    private String screenPath;

    private RecyclerView recyclerView;
    private GridImageAdapter adapter;

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            // 进入相册 以下是例子：不需要的api可以不写
            PictureSelector.create(ProgramSelectorFragment.this)
                    .openGallery(pictureMimeType)
                    .theme(THEME_ID)
                    .maxSelectNum(MAX_SELECT_NUM)
                    .minSelectNum(MIN_SELECT_NUM)
                    .selectionMode(CHOOSE_MODE)
                    .previewImage(true)
                    .previewVideo(true)
//                    .enablePreviewAudio(false) // 是否可播放音频
//                    .isCamera(true)
//                    .enableCrop(false)
//                    .compress(false)
                    .glideOverride(160, 160)
                    .previewEggs(true)
//                    .withAspectRatio(1, 1)
//                    .hideBottomControls(true)
//                    .isGif(false)
//                    .freeStyleCropEnabled(true)
//                    .circleDimmedLayer(false)
//                    .showCropFrame(false)
//                    .showCropGrid(false)
                    .openClickSound(true)
                    .selectionMedia(selectList)
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        }
    };



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.program_selector_fragemnt, container, false);
        recyclerView = view.findViewById(R.id.program_select_rv);
        init();
        return view;
    }

    private void init() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(getActivity(), 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(getActivity(), onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(MAX_SELECT_NUM);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(getActivity()).themeStyle(THEME_ID).openExternalPreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(getActivity()).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(getActivity()).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    selectList = PictureSelector.obtainMultipleResult(data);
                    for (int i = 0; i < selectList.size(); i++)
                        Log.e(TAG, "onActivityResult: " + selectList.get(i).getPath());
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    public int getPictureMimeType() {
        return pictureMimeType;
    }

    public void setPictureMimeType(int pictureMimeType) {
        this.pictureMimeType = pictureMimeType;
    }

    public List<LocalMedia> getSelectList() {
        return selectList;
    }

    public void setSelectList(List<LocalMedia> selectList) {
        this.selectList = selectList;
    }

    public String getScreenPath() {
        return screenPath;
    }

    public void setScreenPath(String screenPath) {
        this.screenPath = screenPath;
    }
}


