package cn.com.hisistar.showclient;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.util.ArrayList;
import java.util.List;

import cn.com.hisistar.showclient.about.AboutFragment;
import cn.com.hisistar.showclient.help.HelpFragment;
import cn.com.hisistar.showclient.mould.MouldChooseFragment;
import cn.com.hisistar.showclient.mould.MouldFragment;
import cn.com.hisistar.showclient.picture_selector.MyPictureSelectorActivity;
import cn.com.hisistar.showclient.program.ProgramFragment;
import cn.com.hisistar.showclient.settings.SettingsFragment;
import cn.com.histar.showclient.R;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private DrawerLayout mDrawerLayout;
    private Toolbar mainToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mainToolbar, 0, 0);
        drawerToggle.syncState();
        mDrawerLayout.addDrawerListener(drawerToggle);
        navigationView.setCheckedItem(R.id.nav_program);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new ProgramFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return true;
    }

    private List<LocalMedia> selectList = new ArrayList<>();

    private void openPictureSelector() {

        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(MainActivity.this);
                } else {
                    Toast.makeText(MainActivity.this,
                            getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });

        PictureSelector.create(MainActivity.this)
                .openGallery(PictureMimeType.ofAll())
                .theme(R.style.picture_QQ_style)
                .maxSelectNum(100)
                .minSelectNum(1)
                .selectionMode(PictureConfig.MULTIPLE)
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_add:
//                openPictureSelector();
                replaceFragment(new MouldChooseFragment());
                mainToolbar.setTitle(getResources().getString(R.string.title_choose_str));


//                mainToolbar.setTitle(getResources().getString(R.string.title_add_str));

//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
//                intent.setType("video/*;image/*");
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                startActivityForResult(intent, 1);

//                Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_program:
                replaceFragment(new ProgramFragment());
                mainToolbar.setTitle(getResources().getString(R.string.title_program_str));
                Toast.makeText(this, "program", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_mould:
                replaceFragment(new MouldFragment());
                mainToolbar.setTitle(getResources().getString(R.string.title_mould_str));
                Toast.makeText(this, "mould", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_settings:
                replaceFragment(new SettingsFragment());
                mainToolbar.setTitle(getResources().getString(R.string.title_settings_str));
                Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_help:
                replaceFragment(new HelpFragment());
                mainToolbar.setTitle(getResources().getString(R.string.title_help_str));
                Toast.makeText(this, "help", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_about:
                replaceFragment(new AboutFragment());
                mainToolbar.setTitle(getResources().getString(R.string.title_about_str));
                Toast.makeText(this, "about", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        menuItem.setChecked(true);
        mDrawerLayout.closeDrawers();
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
//                    selectList = PictureSelector.obtainMultipleResult(data);
//                    for (int i = 0; i < selectList.size(); i++)
//                        Log.e(TAG, "onActivityResult: " + selectList.get(i).getPath());
                    data.setClass(MainActivity.this, MyPictureSelectorActivity.class);
                    startActivity(data);
                    break;
            }
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_area, fragment);
        fragmentTransaction.commit();
    }

}
