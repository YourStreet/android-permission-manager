package in.yourstreet.permissionmanager;

public final class ConstantParameters {

    public static final int NumberOfPermissionTabs = 7;
    // 0 index for tab name
    // 1 index for corresponding exact permission name
    public static final String[] PermissionTabName = {
            "Camera",
            "Location",
            "Storage",
            "Contacts",
            "Microphone",
            "SMS",
            "Phone Calls"
        };
    public static final String[][] PermissionGroupings = {
            {"android.permission.CAMERA"}, //Camera
            {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}, //Location
            {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, //Storage
            {"android.permission.READ_CONTACTS", "android.permission.WRITE_CONTACTS"}, //Contacts
            {"android.permission.RECORD_AUDIO"}, //Microphone
            {"android.permission.SEND_SMS", "android.permission.READ_SMS"}, //SMS
            {"android.permission.CALL_PHONE"} //Phone Call
    };

}
