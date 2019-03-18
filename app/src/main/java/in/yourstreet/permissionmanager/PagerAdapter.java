package in.yourstreet.permissionmanager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumberOfTabs;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mNumberOfTabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int i) {
        // handling start count from 0
        if(i < ConstantParameters.NumberOfPermissionTabs)
        {
            return PermissionCommonTabFragment.newInstance(Integer.toString(i));
        }
        if(i == ConstantParameters.NumberOfPermissionTabs)
        {
            AboutTab aboutTab = new AboutTab();
            return  aboutTab;
        }
        return  null;
    }

    @Override
    public int getCount() {
        return mNumberOfTabs;
    }
}
