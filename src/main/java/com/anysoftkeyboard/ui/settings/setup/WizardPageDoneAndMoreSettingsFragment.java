package com.anysoftkeyboard.ui.settings.setup;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.anysoftkeyboard.ui.settings.KeyboardAddOnSettingsFragment;
import com.anysoftkeyboard.ui.settings.KeyboardThemeSelectorFragment;
import com.anysoftkeyboard.ui.settings.MainSettingsAlternativeActivity;
import com.menny.android.anysoftkeyboard.R;

import net.evendanan.chauffeur.lib.experiences.TransitionExperiences;

public class WizardPageDoneAndMoreSettingsFragment extends WizardPageBaseFragment implements View.OnClickListener {

    private Typeface tf1, tf2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.keyboard_setup_wizard_page_additional_settings_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tf1 = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/ropa_soft_bold.ttf");
        tf2 = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/ropa_soft_light.ttf");
        view.findViewById(R.id.show_keyboard_view_action).setOnClickListener(this);
        view.findViewById(R.id.go_to_home_fragment_action).setOnClickListener(this);
        view.findViewById(R.id.go_to_languages_action).setOnClickListener(this);
        view.findViewById(R.id.go_to_theme_action).setOnClickListener(this);
        view.findViewById(R.id.go_to_all_settings_action).setOnClickListener(this);
        ((TextView) view.findViewById(R.id.show_keyboard_view_action)).setTypeface(tf1);
        ((TextView) view.findViewById(R.id.tvThankYouNote)).setTypeface(tf2);
        ((TextView) view.findViewById(R.id.go_to_home_fragment_action)).setTypeface(tf1);
        ((TextView) view.findViewById(R.id.go_to_languages_action)).setTypeface(tf1);
        ((TextView) view.findViewById(R.id.go_to_theme_action)).setTypeface(tf1);
        ((TextView) view.findViewById(R.id.go_to_all_settings_action)).setTypeface(tf1);
        ((TextView) view.findViewById(R.id.tvTitleTrdStep)).setTypeface(tf1);
    }

  /*  @Override
    protected boolean isStepCompleted() {
        return false;//this step is never done! You can always configure more :)
    }

    @Override
    protected boolean isStepPreConditionDone() {
        return
    } */


    @Override
    protected boolean isStepCompleted(Context ctx) {
        return false;
    }

    @Override
    protected boolean isStepPreConditionDone(Context ctx) {
        return SetupSupport.isThisKeyboardSetAsDefaultIME(ctx);
    }

    @Override
    public void onClick(View v) {
        MainSettingsAlternativeActivity activity = (MainSettingsAlternativeActivity) getActivity();
        switch (v.getId()) {
            case R.id.show_keyboard_view_action:
                InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
                break;
            case R.id.go_to_home_fragment_action:
                activity.onNavigateToRootClicked(v);
                break;
            case R.id.go_to_languages_action:
                activity.addFragmentToUi(new KeyboardAddOnSettingsFragment(), TransitionExperiences.DEEPER_EXPERIENCE_TRANSITION);
                break;
            case R.id.go_to_theme_action:
                activity.addFragmentToUi(new KeyboardThemeSelectorFragment(), TransitionExperiences.DEEPER_EXPERIENCE_TRANSITION);
                break;
            case R.id.go_to_all_settings_action:
                activity.onNavigateToRootClicked(v);
                //activity.openDrawer();
                break;
        }
    }

}
