package cn.com.hisistar.showclient.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.com.hisistar.showclient.ProgressDialogActivity;
import cn.com.hisistar.showclient.transfer.FileTransfer;
import cn.com.hisistar.showclient.transfer.SettingsTransfer;

import static cn.com.hisistar.showclient.ProgressDialogActivity.EXTRA_MEDIA_LIST;
import static cn.com.hisistar.showclient.ProgressDialogActivity.EXTRA_SETTINGS;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class FileSendService extends IntentService {

    private static final String TAG = "FileSendService";

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_SEND = "cn.com.hisistar.filesendservice.action.SEND";

    // TODO: Rename parameters
    private static final String EXTRA_PORT = "cn.com.hisistar.filesendservice.extra.PORT";
    private static final String EXTRA_IP = "cn.com.hisistar.filesendservice.extra.IP";
    private static final String EXTRA_NAME = "cn.com.hisistar.filesendservice.extra.NAME";
    private static final String EXTRA_SRC_PATH = "cn.com.hisistar.filesendservice.extra.SRC.PATH";
    private static final String EXTRA_DST_PATH = "cn.com.hisistar.filesendservice.extra.DST.PATH";

    private static int BUF_SIZE = 1024 * 10;


    private Socket socket;
    private OutputStream outputStream;
    private FileInputStream fileInputStream;
    private byte[] buf;
    private BufferedReader bufferedReader;
    private ObjectOutputStream objectOutputStream;
    private Handler mHandler;


    private ScheduledExecutorService callbackService;
    private String fileName = "";
    private long startTime = 0;
    private long fileSize = 0;
    private long sendSize = 0;
    private long totalFilesSize = 0;
    private long sendFilesSize = 0;
    private long sendTempSize = 0;
    private int totalFilesNum = 0;
    private int sendFilesNum = 0;
    private long totalTime;
    //计算瞬时传输速率的间隔时间
    private static final int PERIOD = 1;

    public FileSendService() {
        super("FileSendService");

    }

    public class FileSendBinder extends Binder {
        public FileSendService getService() {
            return FileSendService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new FileSendService.FileSendBinder();
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionSend(Context context, String port, String ip, String name, String srcPath, String dstPath) {
        Intent intent = new Intent(context, FileSendService.class);
        intent.setAction(ACTION_SEND);
        intent.putExtra(EXTRA_PORT, port);
        intent.putExtra(EXTRA_IP, ip);
        intent.putExtra(EXTRA_NAME, name);
        intent.putExtra(EXTRA_SRC_PATH, srcPath);
        intent.putExtra(EXTRA_DST_PATH, dstPath);

        context.startService(intent);
    }

    public static void startActionSend(Context context, List<FileTransfer> fileTransferList) {
        Log.e(TAG, "startActionSend: ");
        Intent intent = new Intent(context, FileSendService.class);
        intent.putExtra(EXTRA_MEDIA_LIST, (Serializable) fileTransferList);
        intent.setAction(ACTION_SEND);

        context.startService(intent);
    }

    public static void startActionSend(Context context, List<FileTransfer> fileTransferList, SettingsTransfer settingsTransfer) {
        Log.e(TAG, "startActionSend: ");
        Intent intent = new Intent(context, FileSendService.class);
        intent.putExtra(EXTRA_MEDIA_LIST, (Serializable) fileTransferList);
        intent.putExtra(EXTRA_SETTINGS, (Serializable) settingsTransfer);
        intent.setAction(ACTION_SEND);

        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(TAG, "onHandleIntent: ");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SEND.equals(action)) {
//                final String port = intent.getStringExtra(EXTRA_PORT);
//                final String ip = intent.getStringExtra(EXTRA_IP);
//                final String name = intent.getStringExtra(EXTRA_NAME);
//                final String srcPath = intent.getStringExtra(EXTRA_SRC_PATH);
//                final List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(intent);
//                final String dstPath = intent.getStringExtra(EXTRA_DST_PATH);
//                handleActionSend(port, ip, name, srcPath, dstPath);
                List<FileTransfer> fileTransferList = ProgressDialogActivity.getFileTransferList(intent);
                totalFilesNum = fileTransferList.size();
                for (int i = 0; i < fileTransferList.size(); i++) {
                    totalFilesSize += fileTransferList.get(i).getFileSize();
                    Log.e(TAG, "onHandleIntent: " + fileTransferList.get(i).toString());
                }
                SettingsTransfer settingsTransfer = ProgressDialogActivity.getSettingsTransfer(intent);
                handleActionSend(fileTransferList, settingsTransfer);

            }
        }
    }


    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSend(String port, String ip, String name, String srcPath, String dstPath) {
        int portInt = Integer.parseInt(port);
        try {
            socket = new Socket();
            socket.bind(null);
            socket.connect(new InetSocketAddress(ip, portInt), 20000);
            outputStream = socket.getOutputStream();
            outputStream.write((name + "*" + dstPath + "*").getBytes());
            File file = new File(srcPath);
            fileInputStream = new FileInputStream(file);
            int num = (int) file.length() / 1024;
            if (num <= 0) {
                num = 1;
            } else if (num > 100) {
                num = 100;
            }
            buf = new byte[1024 * num];
            int len;
            int i = 0;
            while ((len = fileInputStream.read(buf)) != -1) {
//                Log.e(TAG, "handleActionSend: " + len + "   : " + i++);
                outputStream.write(buf, 0, len);
            }
            socket.shutdownOutput();
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String serverBack = bufferedReader.readLine();

            Log.e(TAG, "handleActionSend: " + "backMsg = " + serverBack);
            clean();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleActionSend(List<FileTransfer> fileTransferList) {
        try {
            socket = new Socket();
            socket.bind(null);
            socket.connect(new InetSocketAddress("192.168.43.1", 14563), 10000);
            outputStream = socket.getOutputStream();
//            outputStream.write(("" + "*" + "" + "*").getBytes());
//            File file = new File("");
//            fileInputStream = new FileInputStream(file);
//            int num = (int) file.length() / 1024;
//            if (num <= 0) {
//                num = 1;
//            } else if (num > 100) {
//                num = 100;
//            }
//            buf = new byte[1024 * num];
//            int len;
//            int i = 0;
//            while ((len = fileInputStream.read(buf)) != -1) {
////                Log.e(TAG, "handleActionSend: " + len + "   : " + i++);
//                outputStream.write(buf, 0, len);
//            }
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(fileTransferList);
            Log.e(TAG, "handleActionSend: " + "writeObject");
            buf = new byte[BUF_SIZE];
            int len;
            for (int i = 0; i < fileTransferList.size(); i++) {
                fileInputStream = new FileInputStream(new File(fileTransferList.get(i).getSrcFilePath()));
                Log.e(TAG, "handleActionSend: " + fileTransferList.get(i).toString());
                int size = 0;
                int j = 0;
                while ((len = fileInputStream.read(buf)) != -1) {
                    size += len;
                    j++;
//                    Log.e(TAG, "handleActionSend: " + "len = " + len + " size = " + size);
                    outputStream.write(buf, 0, len);
                }
                Log.e(TAG, "handleActionSend: " + "size=" + size + " j=" + j);
//                outputStream.flush();
            }
            socket.shutdownOutput();
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String serverBack = bufferedReader.readLine();

            Log.e(TAG, "handleActionSend: " + "backMsg = " + serverBack);
            mHandler = new Handler(getMainLooper());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Transfer completed!", Toast.LENGTH_SHORT).show();

                }
            });
            clean();

        } catch (Exception e) {

            final String eMessage = e.getMessage();
            mHandler = new Handler(getMainLooper());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "failed to connect to target!", Toast.LENGTH_SHORT).show();

                }
            });

            Log.e(TAG, "handleActionSend: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleActionSend(List<FileTransfer> fileTransferList, SettingsTransfer settingsTransfer) {
        try {
            socket = new Socket();
            socket.bind(null);
            socket.connect(new InetSocketAddress("192.168.43.1", 14563), 10000);
            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            if (mSendProgressChangListener != null) {
                Log.e(TAG, "handleActionSend: startSendFilesAndSettingsMsg");
                mSendProgressChangListener.startSendFilesAndSettingsMsg();
            }
            objectOutputStream.writeObject(fileTransferList);
            objectOutputStream.writeObject(settingsTransfer);
            Log.e(TAG, "handleActionSend: " + "writeObject");
//            fileName = fileTransferList.get(0).getFileName();
            startCallback();
            buf = new byte[BUF_SIZE];
            int len;
            for (int i = 0; i < fileTransferList.size(); i++) {
                String filePath = fileTransferList.get(i).getSrcFilePath();
                fileName = filePath.substring(filePath.lastIndexOf("/"), filePath.length());
                fileSize = fileTransferList.get(i).getFileSize();
                sendSize = 0;
                sendFilesNum = i + 1;
                fileInputStream = new FileInputStream(new File(fileTransferList.get(i).getSrcFilePath()));
                Log.e(TAG, "handleActionSend: " + fileTransferList.get(i).toString());
                int size = 0;
                int j = 0;
                while ((len = fileInputStream.read(buf)) != -1) {
                    size += len;
                    sendSize += len;
                    sendFilesSize += len;
                    j++;
//                    Log.e(TAG, "handleActionSend: " + "len = " + len + " size = " + size);
                    outputStream.write(buf, 0, len);
                }
                Log.e(TAG, "handleActionSend: " + "size=" + size + " j=" + j);
//                outputStream.flush();
            }
            socket.shutdownOutput();
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String serverBack = bufferedReader.readLine();

            Log.e(TAG, "handleActionSend: " + "backMsg = " + serverBack);
            mHandler = new Handler(getMainLooper());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Transfer completed!", Toast.LENGTH_SHORT).show();
                }
            });
            stopCallback();
            if (mSendProgressChangListener != null) {
                mSendProgressChangListener.onProgressChanged(fileName, totalTime, totalFilesSize / 1024.0 / 1024.0, 100, 100, totalFilesNum, sendFilesNum, 0, 0);
                mSendProgressChangListener.onTransferSucceed();
            }
            //            clean();

        } catch (Exception e) {

            if (mSendProgressChangListener != null) {
                mSendProgressChangListener.onTransferFailed(e);
            }
            mHandler = new Handler(getMainLooper());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "failed to connect to target!", Toast.LENGTH_SHORT).show();

                }
            });

            Log.e(TAG, "handleActionSend: Exception=" + e.getMessage());
            e.printStackTrace();
        } finally {
            clean();
        }
    }

    private void clean() {

        if (socket != null) {
            try {
                socket.close();
                socket = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (outputStream != null) {
            try {
                outputStream.flush();
                outputStream.close();
                outputStream = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (fileInputStream != null) {
            try {
                fileInputStream.close();
                fileInputStream = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
                bufferedReader = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (objectOutputStream != null) {
            try {
                objectOutputStream.close();
                objectOutputStream = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        stopCallback();
        fileName = "";
        startTime = 0;
        fileSize = 0;
        sendSize = 0;
        totalFilesSize = 0;
        sendFilesSize = 0;
        sendTempSize = 0;
        totalFilesNum = 0;
        sendFilesNum = 0;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: ");
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e(TAG, "onStart: ");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clean();
        Log.e(TAG, "onDestroy: ");
    }


    private void startCallback() {
        Log.e(TAG, "startCallback: ");
        startTime = System.currentTimeMillis();
        if (callbackService != null) {
            if (!callbackService.isShutdown()) {
                callbackService.shutdown();
            }
            callbackService = null;
        }
        callbackService = Executors.newScheduledThreadPool(2);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (!fileName.equals("")) {
                    //过去 PERIOD 秒内文件的瞬时传输速率（Kb/s）
                    long instantSpeed = ((sendFilesSize - sendTempSize) / 1024 / PERIOD);
                    //根据瞬时速率计算的-预估的剩余完成时间（）
                    long instantRemainingTime = ((totalFilesSize - sendFilesSize) / 1024 / instantSpeed);
                    //当前传输进度（%）
                    int currentProgress = (int) (sendSize * 100 / fileSize);
                    //传输总进度（%）
                    int totalProgress = (int) (sendFilesSize * 100 / totalFilesSize);

                    double totalSize = totalFilesSize / 1024.0 / 1024.0;

                    totalTime = (System.currentTimeMillis() - startTime) / 1000;
                    sendTempSize = sendFilesSize;
                    if (mSendProgressChangListener != null) {
                        mSendProgressChangListener.onProgressChanged(fileName, totalTime, totalSize, currentProgress, totalProgress, totalFilesNum, sendFilesNum, instantSpeed, instantRemainingTime);
                    }
                }
            }
        };
        //1秒钟之后每隔 PERIOD 秒钟执行一次任务 runnable（定时任务内部要捕获可能发生的异常，否则如果异常抛出到上层的话，会导致定时任务停止）
        callbackService.scheduleAtFixedRate(runnable, 1, PERIOD, TimeUnit.SECONDS);
    }

    private void stopCallback() {
        if (callbackService != null) {
            if (!callbackService.isShutdown()) {
                callbackService.shutdown();
            }
            callbackService = null;
        }
    }


    public interface OnSendProgressChangListener {

        void startSendFilesAndSettingsMsg();

        void onProgressChanged(String fileName, long totalTime, double totalSize, int currentProgress, int totalProgress, int totalFilesNum, int sendFilesNum, long instantSpeed, long instantRemainingTime);

        void onTransferSucceed();

        void onTransferFailed(Exception e);

    }

    private OnSendProgressChangListener mSendProgressChangListener;

    public void setSendProgressChangListener(OnSendProgressChangListener sendProgressChangListener) {
        mSendProgressChangListener = sendProgressChangListener;
    }


}
