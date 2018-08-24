package cn.com.hisistar.showclient.program;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.com.hisistar.showclient.FileTransfer;
import cn.com.hisistar.showclient.SettingsTransfer;
import cn.com.hisistar.showclient.mould.MouldChooseFragment;
import cn.com.hisistar.showclient.picture_selector.MyPictureSelectorActivity;
import cn.com.hisistar.showclient.picture_selector.ProgramSelectorFragment;
import cn.com.hisistar.showclient.service.FileSendService;
import cn.com.histar.showclient.R;

public class ProgramEditActivity extends AppCompatActivity {

    private static final String TAG = "ProgramEditActivity";

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private MyViewPagerAdapter mViewPagerAdapter;
    private ArrayList<String> mTitleList = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private SettingsTransfer mSettingsTransfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_edit);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        String mouldName = intent.getStringExtra(MouldChooseFragment.MOULD_NAME);
        int mouldImageId = intent.getIntExtra(MouldChooseFragment.MOULD_IMAGE_ID, 0);
        Toolbar toolbar = findViewById(R.id.program_edit_toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.program_edit_coll_toolbar);
        ImageView mouldImageView = findViewById(R.id.program_edit_title_iv);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(mouldName);
        Log.e(TAG, "init: mouldImageId=" + mouldImageId);
        Glide.with(this).load(mouldImageId).into(mouldImageView);


        mTabLayout = findViewById(R.id.program_edit_tl);
        mViewPager = findViewById(R.id.program_edit_vp);

        final int mouldPosition = intent.getIntExtra(MouldChooseFragment.MOULD_POSITION, 0);
        Log.e(TAG, "init: mouldPosition=" + mouldPosition);

        mSettingsTransfer = new SettingsTransfer();
        switch (mouldPosition){
            case 0:
                mSettingsTransfer.setMouldLandscapeMode(1);
                break;
            case 1:
                mSettingsTransfer.setMouldLandscapeMode(2);
                break;
            case 2:
                mSettingsTransfer.setMouldLandscapeMode(3);
                break;
            case 3:
                mSettingsTransfer.setMouldLandscapeMode(4);
                break;
            case 4:
                mSettingsTransfer.setMouldPortraitMode(1);
                break;
            case 5:
                mSettingsTransfer.setMouldPortraitMode(2);
                break;
            case 6:
                mSettingsTransfer.setMouldPortraitMode(3);
                break;
            case 7:
                mSettingsTransfer.setMouldPortraitMode(4);
                break;
            default:
                Log.e(TAG, "init: mouldPosition + error!!!");
                break;

        }
        mTitleList.clear();
        mFragments.clear();
        ProgramSelectorFragment fragment;

        fragment = new ProgramSelectorFragment();
        fragment.setScreenPath("/histarProgram/mainScreen");
        fragment.setPictureMimeType(PictureConfig.TYPE_ALL);
        mFragments.add(fragment);
        switch (mouldPosition) {
            case 0:
            case 4:
                mTitleList.add(getResources().getString(R.string.program_screen_main));
                mTitleList.add(getResources().getString(R.string.program_music));
                mTitleList.add(getResources().getString(R.string.program_subtitle));
                fragment = new ProgramSelectorFragment();
                fragment.setScreenPath("/histarProgram/musicProgram");
                fragment.setPictureMimeType(PictureConfig.TYPE_AUDIO);
                mFragments.add(fragment);
                mFragments.add(new ProgramFragment());
                break;
            case 1:
                mTitleList.add(getResources().getString(R.string.program_screen_main));
                mTitleList.add(getResources().getString(R.string.program_screen_two));
                mTitleList.add(getResources().getString(R.string.program_music));
                mTitleList.add(getResources().getString(R.string.program_subtitle));
                fragment = new ProgramSelectorFragment();
                fragment.setScreenPath("/histarProgram/screenTwo");
                fragment.setPictureMimeType(PictureConfig.TYPE_IMAGE);
                mFragments.add(fragment);
                fragment = new ProgramSelectorFragment();
                fragment.setScreenPath("/histarProgram/musicProgram");
                fragment.setPictureMimeType(PictureConfig.TYPE_AUDIO);
                mFragments.add(fragment);
                mFragments.add(new ProgramFragment());
                break;
            case 2:
            case 3:
            case 5:
            case 6:
                mTitleList.add(getResources().getString(R.string.program_screen_main));
                mTitleList.add(getResources().getString(R.string.program_screen_two));
                mTitleList.add(getResources().getString(R.string.program_screen_three));
                mTitleList.add(getResources().getString(R.string.program_music));
                mTitleList.add(getResources().getString(R.string.program_subtitle));
                fragment = new ProgramSelectorFragment();
                fragment.setScreenPath("/histarProgram/screenTwo");
                fragment.setPictureMimeType(PictureConfig.TYPE_IMAGE);
                mFragments.add(fragment);
                fragment = new ProgramSelectorFragment();
                fragment.setScreenPath("/histarProgram/screenThree");
                fragment.setPictureMimeType(PictureConfig.TYPE_IMAGE);
                mFragments.add(fragment);
                fragment = new ProgramSelectorFragment();
                fragment.setScreenPath("/histarProgram/musicProgram");
                fragment.setPictureMimeType(PictureConfig.TYPE_AUDIO);
                mFragments.add(fragment);
                mFragments.add(new ProgramFragment());
                break;
            case 7:
                mTitleList.add(getResources().getString(R.string.program_screen_main));
                mTitleList.add(getResources().getString(R.string.program_screen_two));
                mTitleList.add(getResources().getString(R.string.program_screen_three));
                mTitleList.add(getResources().getString(R.string.program_screen_four));
                mTitleList.add(getResources().getString(R.string.program_music));
                mTitleList.add(getResources().getString(R.string.program_subtitle));
                fragment = new ProgramSelectorFragment();
                fragment.setScreenPath("/histarProgram/screenTwo");
                fragment.setPictureMimeType(PictureConfig.TYPE_IMAGE);
                mFragments.add(fragment);
                fragment = new ProgramSelectorFragment();
                fragment.setScreenPath("/histarProgram/screenTree");
                fragment.setPictureMimeType(PictureConfig.TYPE_IMAGE);
                mFragments.add(fragment);
                fragment = new ProgramSelectorFragment();
                fragment.setScreenPath("/histarProgram/screenFour");
                fragment.setPictureMimeType(PictureConfig.TYPE_IMAGE);
                mFragments.add(fragment);
                fragment = new ProgramSelectorFragment();
                fragment.setScreenPath("/histarProgram/musicProgram");
                fragment.setPictureMimeType(PictureConfig.TYPE_AUDIO);
                mFragments.add(fragment);
                mFragments.add(new ProgramFragment());
                break;
            default:
                Log.e(TAG, "onItemClick: " + "error!");
                break;
        }


        mViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), mTitleList, mFragments);

        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
//  那我们如果真的需要监听tab的点击或者ViewPager的切换,则需要手动配置ViewPager的切换,例如:
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //切换ViewPager
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        FloatingActionButton floatingActionButton = findViewById(R.id.program_edit_send_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<FileTransfer> fileTransferList = new ArrayList<>();
                List<LocalMedia> selectList = new ArrayList<>();
                for (int j = 0; j < mFragments.size() - 1; j++) {
                    ProgramSelectorFragment fragment = (ProgramSelectorFragment) mFragments.get(j);
                    selectList = fragment.getSelectList();
                    String dstPath = fragment.getScreenPath();
                    for (int i = 0; i < selectList.size(); i++) {
                        String file_path = selectList.get(i).getPath();
                        String name = getTime() + i + file_path.substring(file_path.lastIndexOf("."), file_path.length());


                        fileTransferList.add(new FileTransfer(name, file_path, dstPath, new File(file_path).length()));

                        Log.e(TAG, "onClick: " + fragment.getSelectList().get(i).getPath());
                    }
                }
                FileSendService.startActionSend(ProgramEditActivity.this, fileTransferList,mSettingsTransfer);

                Toast.makeText(ProgramEditActivity.this, "fab clicked", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.program_edit_settings_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private String getTime() {
//        long timeStampSec = System.currentTimeMillis() / 1000;
//        @SuppressLint("DefaultLocale") String timestamp = String.format("%010d", timeStampSec);
//        return timestamp;
        return String.valueOf(System.currentTimeMillis());
    }


}
