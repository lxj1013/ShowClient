package cn.com.hisistar.showclient.program;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import cn.com.hisistar.showclient.mould.MouldChooseFragment;
import cn.com.histar.showclient.R;

public class ProgramEditActivity extends AppCompatActivity {

    private static final String TAG = "ProgramEditActivity";

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
        Glide.with(this).load(mouldImageId).into(mouldImageView);

        int mouldPosition = intent.getIntExtra(MouldChooseFragment.MOULD_POSITION, 0);
        switch (mouldPosition) {
            case 0:
            case 4:

                break;
            case 1:
                break;
            case 2:
            case 3:
            case 5:
            case 6:
                break;
            case 7:
                break;
            default:
                Log.e(TAG, "onItemClick: " + "error!");
                break;
        }


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
