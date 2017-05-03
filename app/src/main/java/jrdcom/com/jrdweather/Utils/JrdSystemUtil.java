package jrdcom.com.jrdweather.Utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;
import android.util.DisplayMetrics;

/**
 * Created by longcheng on 2017/4/21.
 */

public class JrdSystemUtil {

    /*
    * 判断shortcut是否已经存在
    * */
    public static boolean isHasShortCut(Activity activity, String appName){
        /*查询Launcher*/
        String url = "content://com.android.launcher2.settings/favorites?notify=true";
        ContentResolver resolver = activity.getContentResolver();
        Cursor cursor = resolver.query(Uri.parse(url), null, "title=?", new String[]{appName}, null);
        if (cursor != null && cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }

    /*
    * 在launch创建shortCut
    * */
    public static void createShortCut(Activity activity, String appName, int id){
        Intent intent = new Intent();
        intent.setClass(activity, activity.getClass());
        /*以下两句是为了在卸载应用的时候同时删除桌面快捷方式*/
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");

        Intent shortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        //不允许重复创建
        shortcutIntent.putExtra("duplicate", false);
        //需要现实的名称
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, appName);
        //快捷图片
        Parcelable icon = Intent.ShortcutIconResource.fromContext(activity.getApplicationContext(), id);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        //点击快捷图片，运行的程序主入口
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        //发送广播。OK
        activity.sendBroadcast(shortcutIntent);
    }

    /*
    * 屏幕参数
    * */
    private static DisplayMetrics sMetrics;

    private static DisplayMetrics getDisplayMetrics() {
        if (sMetrics == null) {
            sMetrics = Resources.getSystem().getDisplayMetrics();
        }
        return sMetrics;
    }

    public static float getDesity(){
        return getDisplayMetrics().density;
    }

    public static int px2Dp(float px) {
        final float scale = getDisplayMetrics() != null ? getDisplayMetrics().density : 1;
        return (int) (px / scale + 0.5f);
    }

    public static int dp2Px(float dp) {
        final float scale = getDisplayMetrics() != null ? getDisplayMetrics().density : 1;
        return (int) (dp * scale + 0.5f);
    }

    /*设备屏幕宽度*/
    public static int getScreenWidth() {
        return getDisplayMetrics().widthPixels;
    }

    /*设备屏幕高度*/
    public static int getScreenHeight() {
        return getDisplayMetrics().heightPixels;
    }

}
