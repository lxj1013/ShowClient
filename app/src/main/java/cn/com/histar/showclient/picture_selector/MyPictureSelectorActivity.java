package cn.com.histar.showclient.picture_selector;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import cn.com.histar.showclient.R;
import cn.com.histar.showclient.picture_selector.adapter.GridImageAdapter;

public class MyPictureSelectorActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MyPictureSelector";

    private static final int MAX_SELECT_NUM = 10;
    private static final int MIN_SELECT_NUM = 1;
    private static final int THEME_ID = R.style.picture_QQ_style;
    private static int PICTURE_MIME_TYPE = PictureMimeType.ofAll();
    private static int CHOOSE_MODE = PictureConfig.MULTIPLE;

    private List<LocalMedia> selectList = new ArrayList<>();
    private RecyclerView recyclerView;
    private GridImageAdapter adapter;
    private ImageView left_back;
    private TextView sendTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_picture_selector);
        init();
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            // 进入相册 以下是例子：不需要的api可以不写
            PictureSelector.create(MyPictureSelectorActivity.this)
                    .openGallery(PICTURE_MIME_TYPE)
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

    private void init() {
        recyclerView = findViewById(R.id.picture_select_rv);
        left_back = findViewById(R.id.picture_select_back_iv);
        sendTv = findViewById(R.id.picture_select_bottom_send_tv);

        selectList = PictureSelector.obtainMultipleResult(getIntent());

        FullyGridLayoutManager manager = new FullyGridLayoutManager(MyPictureSelectorActivity.this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(MyPictureSelectorActivity.this, onAddPicClickListener);
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
                            PictureSelector.create(MyPictureSelectorActivity.this).themeStyle(THEME_ID).openExternalPreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(MyPictureSelectorActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(MyPictureSelectorActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });

        left_back.setOnClickListener(this);
        sendTv.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.picture_select_back_iv:
                finish();
                break;
            case R.id.picture_select_bottom_send_tv:
                Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
