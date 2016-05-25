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

import android.Manifest;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.anysoftkeyboard.PermissionsRequestCodes;
import com.anysoftkeyboard.ui.settings.setup.SetUpKeyboardWizardFragment;
import com.menny.android.anysoftkeyboard.R;

import net.evendanan.chauffeur.lib.experiences.TransitionExperiences;
import net.evendanan.chauffeur.lib.permissions.PermissionsFragmentChauffeurActivity;
import net.evendanan.chauffeur.lib.permissions.PermissionsRequest;
import net.evendanan.pushingpixels.EdgeEffectHacker;

import org.w3c.dom.Text;

import java.lang.reflect.Field;

public class MainSettingsAlternativeActivity extends PermissionsFragmentChauffeurActivity {

    private CharSequence mTitle;
    private Toolbar myToolbar;
    private boolean isMainMenu;
    private Typeface tf1, tf2;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main_ui_alt);
        tf1 = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/ropa_soft_bold.ttf");
        tf2 = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/ropa_soft_light.ttf");
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        mTitle = getTitle();

        Field f = null;

        try {
            f = myToolbar.getClass().getDeclaredField("mTitleTextView");
            f.setAccessible(true);
            ((TextView) f.get(myToolbar)).setTypeface(tf2);
            ((TextView) f.get(myToolbar)).setTextSize(Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (myToolbar != null) {
            myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isMainMenu) {
                        if (getSupportFragmentManager().getBackStackEntryCount() == 2) {
                            getSupportFragmentManager().popBackStackImmediate();
                            myToolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_menu_white_24dp, getTheme()));
                            setTitle(getString(R.string.ime_name));
                            isMainMenu = true;
                        } else {
                            getSupportFragmentManager().popBackStackImmediate();
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        //applying my very own Edge-Effect color
        EdgeEffectHacker.brandGlowEffect(this, ContextCompat.getColor(this, R.color.app_accent));
    }


    @NonNull
    @Override
    protected Fragment createRootFragmentInstance() {
        myToolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_menu_white_24dp, getTheme()));
        isMainMenu = true;
        return new MainSettingsFragment();
    }

    @Override
    protected int getFragmentRootUiElementId() {
        return R.id.main_ui_content;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(mTitle);
    }

    private void exitMainMenu() {
        myToolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_left_white_24dp, getTheme()));
        isMainMenu = false;
    }

    //side menu navigation methods

    public void onOpenMenu() {
        addFragmentToUi(new MainSettingsFragment(), TransitionExperiences.ROOT_FRAGMENT_EXPERIENCE_TRANSITION);
        myToolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_menu_white_24dp, getTheme()));
        setTitle(getString(R.string.ime_name));
        isMainMenu = true;
    }

    public void onNavigateToRootClicked(View v) {
        exitMainMenu();
        setTitle(getString(R.string.ime_name));
        addFragmentToUi(createRootFragmentInstance(), TransitionExperiences.ROOT_FRAGMENT_EXPERIENCE_TRANSITION);
    }

    public void onNavigateToKeyboardAddonSettings(View v) {
        exitMainMenu();
        addFragmentToUi(new KeyboardAddOnSettingsFragment(), TransitionExperiences.SUB_ROOT_FRAGMENT_EXPERIENCE_TRANSITION);
    }

    public void onNavigateToDictionarySettings(View v) {
        exitMainMenu();
        addFragmentToUi(new DictionariesFragment(), TransitionExperiences.SUB_ROOT_FRAGMENT_EXPERIENCE_TRANSITION);
    }

    public void onNavigateToLanguageSettings(View v) {
        exitMainMenu();
        addFragmentToUi(new AdditionalLanguageSettingsFragment(), TransitionExperiences.SUB_ROOT_FRAGMENT_EXPERIENCE_TRANSITION);

    }

    public void onNavigateToKeyboardThemeSettings(View v) {
        exitMainMenu();
        addFragmentToUi(new KeyboardThemeSelectorFragment(), TransitionExperiences.SUB_ROOT_FRAGMENT_EXPERIENCE_TRANSITION);
    }

    public void onNavigateToEffectsSettings(View v) {
        exitMainMenu();
        addFragmentToUi(new EffectsSettingsFragment(), TransitionExperiences.SUB_ROOT_FRAGMENT_EXPERIENCE_TRANSITION);
    }

    public void onNavigateToGestureSettings(View v) {
        exitMainMenu();
        addFragmentToUi(new GesturesSettingsFragment(), TransitionExperiences.SUB_ROOT_FRAGMENT_EXPERIENCE_TRANSITION);
    }

    public void onNavigateToQuickTextSettings(View v) {
        exitMainMenu();
        addFragmentToUi(new QuickTextSettingsFragment(), TransitionExperiences.SUB_ROOT_FRAGMENT_EXPERIENCE_TRANSITION);
    }

    public void onNavigateToUserInterfaceSettings(View v) {
        exitMainMenu();
        addFragmentToUi(new AdditionalUiSettingsFragment(), TransitionExperiences.SUB_ROOT_FRAGMENT_EXPERIENCE_TRANSITION);
    }

    public void onNavigateToConfigureKeyboard(View e) {
        exitMainMenu();
        addFragmentToUi(new SetUpKeyboardWizardFragment(), TransitionExperiences.SUB_ROOT_FRAGMENT_EXPERIENCE_TRANSITION);
    }

    public void onNavigateToAboutClicked(View v) {
        exitMainMenu();
        addFragmentToUi(new AboutAnySoftKeyboardFragment(), TransitionExperiences.SUB_ROOT_FRAGMENT_EXPERIENCE_TRANSITION);
    }

    public void setFullScreen(boolean fullScreen) {
        if (fullScreen) {
            getSupportActionBar().hide();
        } else {
            getSupportActionBar().show();
        }
    }

    /**
     * Will set the title in the hosting Activity's title.
     * Will only set the title if the fragment is hosted by the Activity's manager, and not inner one.
     */
    public static void setActivityTitle(Fragment fragment, CharSequence title) {
        FragmentActivity activity = fragment.getActivity();
        if (activity.getSupportFragmentManager() == fragment.getFragmentManager()) {
            activity.setTitle(title);
        }
    }

    private final DialogInterface.OnClickListener mContactsDictionaryDialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, final int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainSettingsAlternativeActivity.this, Manifest.permission.READ_CONTACTS)) {
                        startContactsPermissionRequest();
                    } else {
                        startAppPermissionsActivity();
                    }
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferencesCompat.EditorCompat.getInstance().apply(
                            sharedPreferences
                                    .edit()
                                    .putBoolean(getString(R.string.settings_key_use_contacts_dictionary), false)
                    );
                    break;
            }
        }
    };

    private final DialogInterface.OnClickListener mLocationDialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, final int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainSettingsAlternativeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        startLocationPermissionRequest();
                    } else {
                        startAppPermissionsActivity();
                    }
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferencesCompat.EditorCompat.getInstance().apply(
                            sharedPreferences
                                    .edit()
                                    .putBoolean(getString(R.string.settings_key_use_location), false)
                    );
                    break;
            }
        }
    };

    private AlertDialog mAlertDialog;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionsRequestCodes.CONTACTS.getRequestCode() && grantResults[0] == PackageManager.PERMISSION_DENIED) {
            //if the result is DENIED and the OS says "do not show rationale", it means the user has ticked "Don't ask me again".
            final boolean userSaysDontAskAgain = !ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS);
            //the user has denied us from reading the Contacts information.
            //I'll ask them to whether they want to grant anyway, or disable ContactDictionary
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setIcon(R.drawable.ic_notification_contacts_permission_required);
            builder.setTitle(R.string.notification_read_contacts_title);
            builder.setMessage(getString(R.string.contacts_permissions_dialog_message));
            builder.setPositiveButton(getString(userSaysDontAskAgain ? R.string.navigate_to_app_permissions : R.string.allow_permission), mContactsDictionaryDialogListener);
            builder.setNegativeButton(getString(R.string.turn_off_contacts_dictionary), mContactsDictionaryDialogListener);

            if (mAlertDialog != null && mAlertDialog.isShowing()) mAlertDialog.dismiss();
            mAlertDialog = builder.create();
            mAlertDialog.show();
        }
        if (requestCode == PermissionsRequestCodes.LOCATION.getRequestCode() && grantResults[0] == PackageManager.PERMISSION_DENIED) {
            //if the result is DENIED and the OS says "do not show rationale", it means the user has ticked "Don't ask me again".
            final boolean userSaysDontAskAgain = !ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS);
            //the user has denied us from reading the Contacts information.
            //I'll ask them to whether they want to grant anyway, or disable ContactDictionary
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setIcon(R.drawable.ic_notification_contacts_permission_required);
            builder.setTitle(R.string.notification_location_title);
            builder.setMessage(getString(R.string.location_permissions_dialog_message));
            builder.setPositiveButton(getString(userSaysDontAskAgain ? R.string.navigate_to_app_permissions : R.string.allow_permission), mLocationDialogListener);
            builder.setNegativeButton(getString(R.string.turn_off_location), mLocationDialogListener);

            if (mAlertDialog != null && mAlertDialog.isShowing()) mAlertDialog.dismiss();
            mAlertDialog = builder.create();
            mAlertDialog.show();
        }
    }

    public void startContactsPermissionRequest() {
        startPermissionsRequest(new PermissionsRequest.PermissionsRequestBase(PermissionsRequestCodes.CONTACTS.getRequestCode(), Manifest.permission.READ_CONTACTS) {
            @Override
            public void onPermissionsGranted() {

            }

            @Override
            public void onPermissionsDenied() {

            }

            @Override
            public void onUserDeclinedPermissionsCompletely() {

            }
        });
    }

    public void startLocationPermissionRequest() {
        startPermissionsRequest(new PermissionsRequest.PermissionsRequestBase(PermissionsRequestCodes.LOCATION.getRequestCode(), Manifest.permission.ACCESS_FINE_LOCATION) {
            @Override
            public void onPermissionsGranted() {

            }

            @Override
            public void onPermissionsDenied() {

            }

            @Override
            public void onUserDeclinedPermissionsCompletely() {

            }
        });
    }
}
