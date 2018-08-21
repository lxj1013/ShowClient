package cn.com.hisistar.showclient.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
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
import java.util.ArrayList;
import java.util.List;

import cn.com.hisistar.showclient.FileTransfer;

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
    private static final String ACTION_SEND = "cn.com.histar.filereceivetest.action.SEND";

    // TODO: Rename parameters
    private static final String EXTRA_PORT = "cn.com.histar.filereceivetest.extra.PORT";
    private static final String EXTRA_IP = "cn.com.histar.filereceivetest.extra.IP";
    private static final String EXTRA_NAME = "cn.com.histar.filereceivetest.extra.NAME";
    private static final String EXTRA_SRC_PATH = "cn.com.histar.filereceivetest.extra.SRC.PATH";
    private static final String EXTRA_DST_PATH = "cn.com.histar.filereceivetest.extra.DST.PATH";
    private static final String EXTRA_MEDIA_LIST = "cn.com.histar.filereceivetest.extra.MEDIA.LIST";

    private static int BUF_SIZE = 1024 * 10;


    private Socket socket;
    private OutputStream outputStream;
    private FileInputStream fileInputStream;
    private byte[] buf;
    private BufferedReader bufferedReader;
    private ObjectOutputStream objectOutputStream;
    private Handler mHandler;

    public FileSendService() {
        super("FileSendService");

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
                List<FileTransfer> fileTransferList = getFileTransferList(intent);
                for (int i = 0; i < fileTransferList.size(); i++) {
                    Log.e(TAG, "onHandleIntent: " + fileTransferList.get(i).toString());
                }
                handleActionSend(fileTransferList);

            }
        }
    }

    private List<FileTransfer> getFileTransferList(Intent intent) {
        List<FileTransfer> fileTransferList = (List<FileTransfer>) intent.getSerializableExtra(EXTRA_MEDIA_LIST);
        if (!fileTransferList.isEmpty()) {
            return fileTransferList;
        } else {
            fileTransferList = new ArrayList<>();
            return fileTransferList;
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
                    Toast.makeText(getApplicationContext(),"Transfer completed!", Toast.LENGTH_SHORT).show();

                }
            });
            clean();

        } catch (Exception e) {

            final String eMessage = e.getMessage();
            mHandler = new Handler(getMainLooper());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"failed to connect to target!", Toast.LENGTH_SHORT).show();

                }
            });

            Log.e(TAG, "handleActionSend: " + e.getMessage());
            e.printStackTrace();
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
}
