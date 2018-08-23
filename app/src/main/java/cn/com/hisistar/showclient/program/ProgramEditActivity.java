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

import java.util.ArrayList;

import cn.com.hisistar.showclient.mould.MouldChooseFragment;
import cn.com.hisistar.showclient.picture_selector.ProgramSelectorFragment;
import cn.com.histar.showclient.R;

public class ProgramEditActivity extends AppCompatActivity {

    private static final String TAG = "ProgramEditActivity";

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private MyViewPagerAdapter mViewPagerAdapter;
    private ArrayList<String> mTitleList = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

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

        int mouldPosition = intent.getIntExtra(MouldChooseFragment.MOULD_POSITION, 0);
        Log.e(TAG, "init: mouldPosition=" + mouldPosition);
        mTitleList.clear();
        mFragments.clear();
        ProgramSelectorFragment fragment;

        fragment = new ProgramSelectorFragment();
        fragment.setPictureMimeType(PictureConfig.TYPE_ALL);
        mFragments.add(fragment);
        switch (mouldPosition) {
            case 0:
            case 4:
                mTitleList.add(getResources().getString(R.string.program_screen_main));
                mTitleList.add(getResources().getString(R.string.program_music));
                mTitleList.add(getResources().getString(R.string.program_subtitle));
                fragment = new ProgramSelectorFragment();
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
                fragment.setPictureMimeType(PictureConfig.TYPE_IMAGE);
                mFragments.add(fragment);
                fragment = new ProgramSelectorFragment();
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
                fragment.setPictureMimeType(PictureConfig.TYPE_IMAGE);
                mFragments.add(fragment);
                fragment = new ProgramSelectorFragment();
                fragment.setPictureMimeType(PictureConfig.TYPE_IMAGE);
                mFragments.add(fragment);
                fragment = new ProgramSelectorFragment();
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
                fragment.setPictureMimeType(PictureConfig.TYPE_IMAGE);
                mFragments.add(fragment);
                fragment = new ProgramSelectorFragment();
                fragment.setPictureMimeType(PictureConfig.TYPE_IMAGE);
                mFragments.add(fragment);
                fragment = new ProgramSelectorFragment();
                fragment.setPictureMimeType(PictureConfig.TYPE_IMAGE);
                mFragments.add(fragment);
                fragment = new ProgramSelectorFragment();
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
                for (int j = 0; j < mFragments.size() - 1; j++) {
                    ProgramSelectorFragment fragment = (ProgramSelectorFragment) mFragments.get(j);
                    for (int i = 0; i < fragment.getSelectList().size(); i++) {
                        Log.e(TAG, "onClick: " + fragment.getSelectList().get(i).getPath());
                    }
                }
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
}
