package com.anysoftkeyboard.ui.settings.setup;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class WizardPagesAdapter extends FragmentPagerAdapter {

    private static final boolean MARSHMALLOW = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    private final Fragment[] mFragments;
    public WizardPagesAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new Fragment[MARSHMALLOW ? 5 :4];
        mFragments[0] = new WizardPageIntroFragment();
        mFragments[1] = new WizardPageEnableKeyboardFragment();
        mFragments[2] = new WizardPageSwitchToKeyboardFragment();
        if (MARSHMALLOW) {
            mFragments[3] = new WizardPermissionsFragment();
            mFragments[4] = new WizardPageDoneAndMoreSettingsFragment();
        } else {
            mFragments[3] = new WizardPageDoneAndMoreSettingsFragment();
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments[position];
    }

    @Override
    public int getCount() {
        return mFragments.length;
    }

    public int getItemPosition(Object object) {
        //so "notifyDataSetChanged()" will cause recreation
        return POSITION_NONE;
    }
}
