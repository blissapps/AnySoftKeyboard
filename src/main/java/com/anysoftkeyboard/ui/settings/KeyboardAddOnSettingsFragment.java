/*
 * Copyright (c) 2013 Menny Even-Danan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.anysoftkeyboard.ui.settings;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceCategory;
import android.support.v4.preference.PreferenceFragment;

import com.anysoftkeyboard.keyboards.KeyboardAddOnAndBuilder;
import com.anysoftkeyboard.keyboards.KeyboardFactory;
import com.anysoftkeyboard.ui.settings.widget.AddOnCheckBoxPreference;
import com.menny.android.anysoftkeyboard.R;


import java.util.List;

public class KeyboardAddOnSettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        getActivity().setTheme(R.style.Theme_Preference_Switch);
        addPreferencesFromResource(R.xml.prefs_keyboards);
    }

    @Override
    public void onStart() {
        super.onStart();

        PreferenceCategory keyboardsGroup = (PreferenceCategory) findPreference("keyboard_addons_group");
        Activity activity = getActivity();
        MainSettingsAlternativeActivity.setActivityTitle(this, getString(R.string.keyboards_group));
        // getting all keyboards
        final List<KeyboardAddOnAndBuilder> creators = KeyboardFactory.getAllAvailableKeyboards(activity.getApplicationContext());

        keyboardsGroup.removeAll();

        //add pt and pt-pBR keyboards first
        for (final KeyboardAddOnAndBuilder creator : creators) {
            if(creator.getKeyboardLocale().equals("pt") || creator.getKeyboardLocale().equals("pt-pBR")) {
                final AddOnCheckBoxPreference checkBox = new AddOnCheckBoxPreference(activity, null, R.style.Theme_Preference);
                checkBox.setAddOn(creator);
                keyboardsGroup.addPreference(checkBox);
            }
        }

        //add the others afterwards
        for (final KeyboardAddOnAndBuilder creator : creators) {
            if(!creator.getKeyboardLocale().equals("pt") && !creator.getKeyboardLocale().equals("pt-pBR")) {
                final AddOnCheckBoxPreference checkBox = new AddOnCheckBoxPreference(activity, null, R.style.Theme_Preference);
                checkBox.setAddOn(creator);
                keyboardsGroup.addPreference(checkBox);
            }
        }
    }
}
