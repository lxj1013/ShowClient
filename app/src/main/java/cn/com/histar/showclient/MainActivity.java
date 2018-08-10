package cn.com.histar.showclient;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import cn.com.histar.showclient.about.AboutFragment;
import cn.com.histar.showclient.help.HelpFragment;
import cn.com.histar.showclient.mould.MouldFragment;
import cn.com.histar.showclient.program.ProgramFragment;
import cn.com.histar.showclient.settings.SettingsFragment;

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
        navigationView.setCheckedItem(R.id.nav_program);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new ProgramFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_add:
//                mainToolbar.setTitle(getResources().getString(R.string.title_add_str));

//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
//                intent.setType("video/*;image/*");
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                startActivityForResult(intent, 1);

                Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
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

        mDrawerLayout.closeDrawers();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                Uri uri = data.getData();
                Toast.makeText(this, "文件路径：" + uri.getPath().toString(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onActivityResult: " + "文件路径：" + uri.getPath().toString());
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
