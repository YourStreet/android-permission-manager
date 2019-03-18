package in.yourstreet.permissionmanager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements PermissionCommonTabFragment.OnFragmentInteractionListener, AboutTab.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setLogo(R.drawable.ic_logo);
        myToolbar.setTitle(" Permission Manager");
        setSupportActionBar(myToolbar);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);

        // adding a small vertical line between tabs
        LinearLayout linearLayout = (LinearLayout)tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.GRAY);
        drawable.setSize(1, 1);
        linearLayout.setDividerPadding(10);
        linearLayout.setDividerDrawable(drawable);

        for(int tabNum=0;tabNum<ConstantParameters.NumberOfPermissionTabs;tabNum++)
        {
            tabLayout.addTab(tabLayout.newTab().setText(ConstantParameters.PermissionTabName[tabNum]));
        }
        tabLayout.addTab(tabLayout.newTab().setText("About Us"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final  PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //will handle start count from 0
                viewPager.setCurrentItem(tabLayout.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        fetchAppDetails();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_rate_app:
                functionRateApp();
                break;

            case R.id.action_share_app:
                functionShareApp();
                break;

            case R.id.action_about:
                functionAboutApp();
                break;
            case R.id.action_close:
                functionCloseApp();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void functionAboutApp()
    {
        Log.e("TEST","About");
    }

    void functionRateApp()
    {
        Log.e("TEST","Rate App");
    }

    void functionShareApp()
    {
        Log.e("TEST","Share App");
    }

    void functionCloseApp()
    {
        Log.e("TEST","Close");
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    List<String> getGrantedPermissions(final String appPackage) {
        List<String> granted = new ArrayList<String>();
        try {
            PackageInfo pi = getPackageManager().getPackageInfo(appPackage, PackageManager.GET_PERMISSIONS);
            for (int i = 0; i < pi.requestedPermissions.length; i++) {
                if ((pi.requestedPermissionsFlags[i] & PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0) {
                    granted.add(pi.requestedPermissions[i]);
                }
            }
        } catch (Exception e) {
        }
        return granted;
    }
    void fetchAppDetails()
    {
        List<AppItem> allApps = new ArrayList<>();
        for(int pNumber = 0;pNumber<ConstantParameters.NumberOfPermissionTabs;pNumber++)
        {
            MainActivity.permissionSets.add(new HashSet<Integer>());
        }
        List<PackageInfo> packList = getPackageManager().getInstalledPackages(0);
        int i = 0;
        for (PackageInfo packInfo:packList)
        {
           if (  (packInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
            {
                String appName = packInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                Drawable icon = packInfo.applicationInfo.loadIcon(getPackageManager());
                allApps.add(new AppItem(appName,icon,packInfo.packageName,i));
                for(String s:getGrantedPermissions(packInfo.packageName))
                {
                    for(int p_i = 0; p_i < ConstantParameters.NumberOfPermissionTabs; p_i++)
                    {
                        for(String pname:ConstantParameters.PermissionGroupings[p_i])
                        {
                            if(pname.contentEquals(s))
                            {
                                MainActivity.permissionSets.get(p_i).add(i);
                                break;
                            }
                        }
                    }
                }
                i++;
            }
        }
        MainActivity.staticAllApps = allApps;
    }
    public static List<AppItem> staticAllApps;
    public static List<Set<Integer>> permissionSets = new ArrayList<>();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == RecyclerViewAdapter.REQUEST_CODE){
            boolean isPresent= isAppPresent(RecyclerViewAdapter.uninstallingAppName);
            //user Clicked on Cancel
            if(isPresent){

            }
            //user Clicked on Ok
            else{
                // Implementation for removal of app item
            }


        }
    }

    boolean  isAppPresent(String packageName) {
        try{
            ApplicationInfo info = getPackageManager().getApplicationInfo(packageName, 0 );
            return true;

        } catch( PackageManager.NameNotFoundException e ){
            return false;
        }

    }
}
