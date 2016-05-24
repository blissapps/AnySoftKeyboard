package com.anysoftkeyboard.ui.settings.setup;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.SharedPreferencesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anysoftkeyboard.PermissionsRequestCodes;
import com.anysoftkeyboard.ui.settings.MainSettingsAlternativeActivity;
import com.anysoftkeyboard.utils.Log;
import com.menny.android.anysoftkeyboard.AnyApplication;
import com.menny.android.anysoftkeyboard.R;

import net.evendanan.chauffeur.lib.permissions.PermissionsRequest;

public class WizardPermissionsFragment extends WizardPageBaseFragment implements View.OnClickListener {

    private MainSettingsAlternativeActivity activity;
    private Typeface tf1, tf2;

    private final PermissionsRequest mContactsPermissionRequest =
            new PermissionsRequest.PermissionsRequestBase(PermissionsRequestCodes.CONTACTS.getRequestCode(),
                    Manifest.permission.READ_CONTACTS) {
                @Override
                public void onPermissionsGranted() {
                    activity.startPermissionsRequest(mLocationPermissionRequest);
                }

                @Override
                public void onPermissionsDenied() {/*no-op*/}

                @Override
                public void onUserDeclinedPermissionsCompletely() {/*no-op - Main-Activity handles this case*/}
            };

    private final PermissionsRequest mLocationPermissionRequest =
            new PermissionsRequest.PermissionsRequestBase(PermissionsRequestCodes.LOCATION.getRequestCode(),
                    Manifest.permission.ACCESS_FINE_LOCATION) {
                @Override
                public void onPermissionsGranted() {
                    activity.startPermissionsRequest(mStoragePermissionRequest);
                }

                @Override
                public void onPermissionsDenied() {/*no-op*/}

                @Override
                public void onUserDeclinedPermissionsCompletely() {/*no-op - Main-Activity handles this case*/}
            };

    private final PermissionsRequest mStoragePermissionRequest =
            new PermissionsRequest.PermissionsRequestBase(PermissionsRequestCodes.STORAGE_WRITE.getRequestCode(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                @Override
                public void onPermissionsGranted() {
                    refreshWizardPager();
                }

                @Override
                public void onPermissionsDenied() {/*no-op*/}

                @Override
                public void onUserDeclinedPermissionsCompletely() {/*no-op - Main-Activity handles this case*/}
            };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.keyboard_setup_wizard_page_permissions_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tf1 = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/ropa_soft_bold.ttf");
        tf2 = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/ropa_soft_light.ttf");
        ((TextView) view.findViewById(R.id.tvTitleTrdStep)).setTypeface(tf2);
        ((TextView) view.findViewById(R.id.tvLocationDialog)).setTypeface(tf2);
        ((TextView) view.findViewById(R.id.tvContactsDialog)).setTypeface(tf2);
        ((TextView) view.findViewById(R.id.tvAccessContacts)).setTypeface(tf2);
        ((TextView) view.findViewById(R.id.tvAccessLocation)).setTypeface(tf2);
        ((TextView) view.findViewById(R.id.tvAccessStorage)).setTypeface(tf2);
        ((TextView) view.findViewById(R.id.ask_for_permissions_action)).setTypeface(tf1);
        ((TextView) view.findViewById(R.id.ask_for_location_permissions_action)).setTypeface(tf1);
        ((TextView) view.findViewById(R.id.ask_for_storage_permissions_action)).setTypeface(tf1);
        ((TextView) view.findViewById(R.id.disable_contacts_dictionary)).setTypeface(tf1);
        ((TextView) view.findViewById(R.id.disable_location)).setTypeface(tf1);
        ((TextView) view.findViewById(R.id.disable_storage)).setTypeface(tf1);
        ((TextView) view.findViewById(R.id.open_permissions_wiki_action)).setTypeface(tf1);
        view.findViewById(R.id.ask_for_permissions_action).setOnClickListener(this);
        view.findViewById(R.id.ask_for_location_permissions_action).setOnClickListener(this);
        view.findViewById(R.id.ask_for_storage_permissions_action).setOnClickListener(this);
        view.findViewById(R.id.disable_contacts_dictionary).setOnClickListener(this);
        view.findViewById(R.id.disable_location).setOnClickListener(this);
        view.findViewById(R.id.disable_storage).setOnClickListener(this);
        view.findViewById(R.id.open_permissions_wiki_action).setOnClickListener(this);
    }

    @Override
    protected boolean isStepCompleted() {
        if (getActivity() != null) {
            return !AnyApplication.getConfig().useContactsDictionary() ||//either the user disabled Contacts
                    ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED || //or the user granted permission
                    !AnyApplication.getConfig().useLocation() ||//either the user disabled location
                    ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || //or the user granted permission
                    !AnyApplication.getConfig().useStorage() ||//either the user disabled Storage access
                    ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED; //or the user granted permission//or the user granted permission
        } else {
            return false;
        }
    }

    @Override
    protected boolean isStepPreConditionDone() {
        return SetupSupport.isThisKeyboardSetAsDefaultIME(getActivity());
    }

    @Override
    public void onClick(View v) {
        activity = (MainSettingsAlternativeActivity) getActivity();
        if (activity == null) return;
        SharedPreferences sharedPreferences = null;
        switch (v.getId()) {
            case R.id.ask_for_permissions_action:
                activity.startPermissionsRequest(mContactsPermissionRequest);
                break;
            case R.id.ask_for_location_permissions_action:
                activity.startPermissionsRequest(mLocationPermissionRequest);
                break;
            case R.id.ask_for_storage_permissions_action:
                activity.startPermissionsRequest(mStoragePermissionRequest);
            case R.id.disable_contacts_dictionary:
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
                SharedPreferencesCompat.EditorCompat.getInstance().apply(
                        sharedPreferences
                                .edit()
                                .putBoolean(getString(R.string.settings_key_use_contacts_dictionary), false)
                );
                refreshWizardPager();
                break;
            case R.id.disable_location:
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
                SharedPreferencesCompat.EditorCompat.getInstance().apply(
                        sharedPreferences
                                .edit()
                                .putBoolean(getString(R.string.settings_key_use_location), false)
                );
                refreshWizardPager();
                break;
            case R.id.disable_storage:
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
                SharedPreferencesCompat.EditorCompat.getInstance().apply(
                        sharedPreferences
                                .edit()
                                .putBoolean(getString(R.string.settings_key_use_storage), false)
                );
                refreshWizardPager();
                break;
            case R.id.open_permissions_wiki_action:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.permissions_wiki_site_url)));
                try {
                    startActivity(browserIntent);
                } catch (ActivityNotFoundException weirdException) {
                    //https://github.com/AnySoftKeyboard/AnySoftKeyboard/issues/516
                    //this means that there is nothing on the device
                    //that can handle Intent.ACTION_VIEW with "https" schema..
                    //silently swallowing it
                    Log.w("WizardPermissionsFragment", "Can not open '%' since there is nothing on the device that can handle it.", browserIntent.getData());
                }
                break;
        }
    }
}
