package cn.com.hisistar.showclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.com.hisistar.showclient.service.FileSendService;
import cn.com.hisistar.showclient.transfer.FileTransfer;
import cn.com.hisistar.showclient.transfer.SettingsTransfer;
import cn.com.hisistar.showclient.R;

public class ProgressDialogActivity extends AppCompatActivity {

    private static final String TAG = "ProgressDialogActivity";

    public static final String EXTRA_MEDIA_LIST = "cn.com.hisistar.progressdialog.extra.MEDIA.LIST";
    public static final String EXTRA_SETTINGS = "cn.com.hisistar.progressdialog.extra.SETTINGS";

    private Window window;
    private WindowManager windowManager;
    private DisplayMetrics displayMetrics;

    private TextView mProgramInfoTv;
    private TextView mFileNameTv;
    private TextView mCurProgressTv;
    private ProgressBar mCurProgressBar;

    private TextView mCurItemTv;
    private TextView mTotalItemTv;
    private TextView mTotalProgressTv;
    private ProgressBar mTotalProgressBar;

    private TextView mTotalTimeTv;
    private TextView mRemainingTv;
    private TextView msendSpeedTv;

    private TextView mFileSendTipsTv;
    private TextView mBackTv;


    private FileSendService mFileSendService;
    private FileSendService.OnSendProgressChangListener mOnSendProgressChangListener = new FileSendService.OnSendProgressChangListener() {
        @Override
        public void startSendFilesAndSettingsMsg() {
            Log.e(TAG, "startSendFilesAndSettingsMsg: ");
           /* runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mFileSendTipsTv.setText("startSendFilesAndSettingsMsg");
                }
            });*/
        }

        @Override
        public void onProgressChanged(final String fileName, final long totalTime, final double totalSize, final int currentProgress, final int totalProgress, final int totalFilesNum, final int sendFilesNum, final long instantSpeed, final long instantRemainingTime) {
            Log.e(TAG, "onProgressChanged: fileName=" + fileName);
            Log.e(TAG, "onProgressChanged: totalTime=" + totalTime);
            Log.e(TAG, "onProgressChanged: totalSize=" + totalSize);
            Log.e(TAG, "onProgressChanged: currentProgress=" + currentProgress);
            Log.e(TAG, "onProgressChanged: totalProgress=" + totalProgress);
            Log.e(TAG, "onProgressChanged: totalFilesNum=" + totalFilesNum);
            Log.e(TAG, "onProgressChanged: sendFilesNum=" + sendFilesNum);
            Log.e(TAG, "onProgressChanged: instantSpeed=" + instantSpeed);
            Log.e(TAG, "onProgressChanged: instantRemainingTime=" + instantRemainingTime);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String programInfoFormatStr = getResources().getString(R.string.progress_program_info_str);
                    String programInfoFinalStr = String.format(programInfoFormatStr, totalFilesNum, totalSize);
                    mProgramInfoTv.setText(programInfoFinalStr);

                    String fileNameFormatStr = getResources().getString(R.string.progress_cur_file_str);
                    String fileNameFinalStr = String.format(fileNameFormatStr, fileName);
                    mFileNameTv.setText(fileNameFinalStr);

                    String curProgressStr = currentProgress + "%";
                    mCurProgressTv.setText(curProgressStr);

                    mCurProgressBar.setProgress(currentProgress);


                    String curItemStr = "" + sendFilesNum;
                    mCurItemTv.setText(curItemStr);

                    String totalItemStr = "" + totalFilesNum;
                    mTotalItemTv.setText(totalItemStr);

                    String totalProgressStr = totalProgress + "%";
                    mTotalProgressTv.setText(totalProgressStr);

                    mTotalProgressBar.setProgress(totalProgress);
                    String totalTimeStr;
                    if (totalTime == 0) {
                        totalTimeStr = 1 + "s";
                    } else {
                        totalTimeStr = totalTime + "s";
                    }
                    mTotalTimeTv.setText(totalTimeStr);
                    String remainingTimeStr = instantRemainingTime + "s";
                    mRemainingTv.setText(remainingTimeStr);

                    String sendSpeedStr = instantSpeed + "Kb/s";
                    msendSpeedTv.setText(sendSpeedStr);

                    String backStr = getResources().getString(R.string.progress_back_btn_tips_str);
                    mBackTv.setClickable(false);
                    mBackTv.setText(backStr);
                }
            });
        }

        @Override
        public void onTransferSucceed() {
            Log.e(TAG, "onTransferSucceed: ");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String backStr = getResources().getString(R.string.progress_back_btn_str);
                    mBackTv.setClickable(true);
                    mBackTv.setText(backStr);
                    String tipsStr = getResources().getString(R.string.progress_file_send_succeed_str);
                    mFileSendTipsTv.setText(tipsStr);
                }
            });
        }

        @Override
        public void onTransferFailed(Exception e) {
            Log.e(TAG, "onTransferFailed: Msg=" + e.getMessage());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String backStr = getResources().getString(R.string.progress_back_btn_str);
                    mBackTv.setClickable(true);
                    mBackTv.setText(backStr);
                    String tipsStr = getResources().getString(R.string.progress_file_send_failed_str);
                    mFileSendTipsTv.setText(tipsStr);
                }
            });
        }
    };

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            FileSendService.FileSendBinder binder = (FileSendService.FileSendBinder) service;
            mFileSendService = binder.getService();
            mFileSendService.setSendProgressChangListener(mOnSendProgressChangListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mFileSendService = null;
            Intent intent = new Intent(ProgressDialogActivity.this, FileSendService.class);
            bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_dialog);
        setWindow();
        initView();
        Intent intent = new Intent(ProgressDialogActivity.this, FileSendService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        startFileSendService(getIntent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFileSendService != null) {
            mFileSendService.setSendProgressChangListener(null);
            mFileSendService = null;
            unbindService(mServiceConnection);
        }
    }

    private void initView() {


        mProgramInfoTv = findViewById(R.id.progress_program_info_tv);
        mFileNameTv = findViewById(R.id.progress_cur_file_tv);
        mCurProgressTv = findViewById(R.id.progress_cur_progress_tv);
        mCurProgressBar = findViewById(R.id.progress_cur_progress_pb);

        mCurItemTv = findViewById(R.id.progress_cur_item_tv);
        mTotalItemTv = findViewById(R.id.progress_total_item_tv);
        mTotalProgressTv = findViewById(R.id.progress_total_progress_tv);
        mTotalProgressBar = findViewById(R.id.progress_total_progress_pb);

        mTotalTimeTv = findViewById(R.id.progress_total_time_tv);
        mRemainingTv = findViewById(R.id.progress_remaining_time_tv);

        msendSpeedTv = findViewById(R.id.progress_file_send_speed_tv);

        mFileSendTipsTv = findViewById(R.id.progress_file_send_tips_tv);

        mBackTv = findViewById(R.id.progress_back_btn);


        String programInfoFormatStr = getResources().getString(R.string.progress_program_info_str);
        String programInfoFinalStr = String.format(programInfoFormatStr, 0, 0.0);
        mProgramInfoTv.setText(programInfoFinalStr);

        String fileNameFormatStr = getResources().getString(R.string.progress_cur_file_str);
        String fileNameFinalStr = String.format(fileNameFormatStr, "");
        mFileNameTv.setText(fileNameFinalStr);

        String curProgressStr = 0 + "%";
        mCurProgressTv.setText(curProgressStr);

        mCurProgressBar.setProgress(0);


        String curItemStr = "0";
        mCurItemTv.setText(curItemStr);

        String totalItemStr = "0";
        mTotalItemTv.setText(totalItemStr);

        String totalProgressStr = 0 + "%";
        mTotalProgressTv.setText(totalProgressStr);

        mTotalProgressBar.setProgress(0);

        String totalTimeStr = 0 + "s";
        mTotalTimeTv.setText(totalTimeStr);

        String remainingTimeStr = 0 + "s";
        mRemainingTv.setText(remainingTimeStr);

        String sendSpeedStr = 0 + "MB";
        msendSpeedTv.setText(sendSpeedStr);

        String backStr = getResources().getString(R.string.progress_back_btn_tips_str);
        mBackTv.setClickable(false);
        mBackTv.setText(backStr);
        mBackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void setWindow() {
        window = this.getWindow();
        windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        displayMetrics = new DisplayMetrics();
        WindowSizeUtil windowSizeUtil = new WindowSizeUtil(window, windowManager, displayMetrics);
        if (windowSizeUtil.getScreenOrientation() == windowSizeUtil.LANDSCAPE) {
            windowSizeUtil.setWindow(0.7f, 0.8f);
        } else if (windowSizeUtil.getScreenOrientation() == windowSizeUtil.PORTRAIT) {
            windowSizeUtil.setWindow(0.8f, 0.5f);
        }
    }

    private void startFileSendService(Intent intent) {
        List<FileTransfer> fileTransferList = ProgressDialogActivity.getFileTransferList(intent);
        SettingsTransfer settingsTransfer = ProgressDialogActivity.getSettingsTransfer(intent);
//        Intent serviceIntent = new Intent(ProgressDialogActivity.this, ProgressDialogActivity.class);
//        serviceIntent.putExtra(EXTRA_MEDIA_LIST, (Serializable) fileTransferList);
//        serviceIntent.putExtra(EXTRA_SETTINGS, (Serializable) settingsTransfer);
        FileSendService.startActionSend(ProgressDialogActivity.this, fileTransferList, settingsTransfer);
    }

    public static List<FileTransfer> getFileTransferList(Intent intent) {
        List<FileTransfer> fileTransferList = (List<FileTransfer>) intent.getSerializableExtra(EXTRA_MEDIA_LIST);
        if (!fileTransferList.isEmpty()) {
            return fileTransferList;
        } else {
            fileTransferList = new ArrayList<>();
            return fileTransferList;
        }
    }

    public static SettingsTransfer getSettingsTransfer(Intent intent) {
        SettingsTransfer settingsTransfer = (SettingsTransfer) intent.getSerializableExtra(EXTRA_SETTINGS);
        if (settingsTransfer != null) {
            return settingsTransfer;
        } else {
            settingsTransfer = new SettingsTransfer();
            return settingsTransfer;
        }
    }

}
