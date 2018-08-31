package cn.com.hisistar.showclient;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by lixinjian on 2018/3/13.
 */

public class WindowSizeUtil {
    private static final String TAG = WindowSizeUtil.class.getSimpleName();

    final static int LANDSCAPE = 1;
    final static int PORTRAIT = 0;

    int mWindowWidthPixels;
    int mWindowHeightPixels;

    Window window;
    WindowManager windowManager;
    DisplayMetrics displayMetrics;

    public WindowSizeUtil(Window window, WindowManager windowManager, DisplayMetrics displayMetrics) {
        this.window = window;
        this.windowManager = windowManager;
        this.displayMetrics = displayMetrics;
        this.windowManager.getDefaultDisplay().getMetrics(displayMetrics);
    }


    public void setWindow(float widthPercent, float heightPercent) {
        if (widthPercent > 1) {
            widthPercent = 1;
        }
        if (widthPercent < 0) {
            widthPercent = 0;
        }
        if (heightPercent > 1) {
            heightPercent = 1;
        }
        if (heightPercent < 0) {
            heightPercent = 0;
        }
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = (int) (getWindowWidthPixels() * widthPercent);
        layoutParams.height = (int) (getWindowHeightPixels() * heightPercent);
        layoutParams.gravity = Gravity.CENTER;
        window.getDecorView().setPadding(15, 15, 15, 15);
        window.setAttributes(layoutParams);
    }


    public int getScreenOrientation() {
        if (getWindowWidthPixels() >= getWindowHeightPixels()) {
            //Log.d(TAG, "getScreenOrientation: LANDSCAPE");
            return LANDSCAPE;
        } else {
            //Log.d(TAG, "getScreenOrientation: PORTRAIT");
            return PORTRAIT;
        }
    }

    public int getWindowWidthPixels() {
        mWindowWidthPixels = displayMetrics.widthPixels;
        //Log.d(TAG, "getWindowWidthPixels: " + mWindowWidthPixels);
        return mWindowWidthPixels;
    }

    public int getWindowHeightPixels() {
        mWindowHeightPixels = displayMetrics.heightPixels;
        //Log.d(TAG, "getWindowHeightPixels: " + mWindowHeightPixels);
        return mWindowHeightPixels;
    }
/*
    public void getAndroidScreenProperty() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;         // 屏幕宽度（像素）
        screenHeight = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int width = (int) (screenWidth / density);  // 屏幕宽度(dp)
        int height = (int) (screenHeight / density);// 屏幕高度(dp)


        Log.d("h_bl", "屏幕宽度（像素）：" + screenWidth);
        Log.d("h_bl", "屏幕高度（像素）：" + screenHeight);
        Log.d("h_bl", "屏幕密度（0.75 / 1.0 / 1.5）：" + density);
        Log.d("h_bl", "屏幕密度dpi（120 / 160 / 240）：" + densityDpi);
        Log.d("h_bl", "屏幕宽度（dp）：" + width);
        Log.d("h_bl", "屏幕高度（dp）：" + height);
    }
*/

}
