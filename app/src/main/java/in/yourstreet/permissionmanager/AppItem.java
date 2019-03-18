package in.yourstreet.permissionmanager;


import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class AppItem {

    private String appName;
    Drawable appIcon;
    private String packageName;
    private int appIndex;

    public int getAppIndex() {
        return appIndex;
    }

    public void setAppIndex(int appIndex) {
        this.appIndex = appIndex;
    }
    /*
    0 -> do not show text
    1 -> show permission as granted
    2 -> show permission as denied
     */

    public AppItem(String appName, Drawable appIcon, String packageName, int appIndex) {
        this.appName = appName;
        this.appIcon = appIcon;
        this.packageName = packageName;
        this.appIndex = appIndex;
    }

    public String getAppName() {
        return appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public AppItem() {
    }

}

