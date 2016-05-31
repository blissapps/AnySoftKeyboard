package com.anysoftkeyboard.ui.settings.setup;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.menny.android.anysoftkeyboard.R;

public class WizardPageSwitchToKeyboardFragment extends WizardPageBaseFragment {

    private Typeface tf1, tf2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.keyboard_setup_wizard_page_switch_to_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tf1 = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/ropa_soft_bold.ttf");
        tf2 = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/ropa_soft_light.ttf");
        ((TextView) view.findViewById(R.id.tvTitleSndStep)).setTypeface(tf2);
        ((TextView) view.findViewById(R.id.tvExplainSndStep)).setTypeface(tf2);
        view.findViewById(R.id.go_to_switch_keyboard_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.showInputMethodPicker();
            }
        });
        ((TextView) view.findViewById(R.id.go_to_switch_keyboard_action)).setTypeface(tf1);
    }

   /* @Override
    protected boolean isStepCompleted() {
        return SetupSupport.isThisKeyboardSetAsDefaultIME(getActivity());
    }

    @Override
    protected boolean isStepPreConditionDone() {
        return SetupSupport.isThisKeyboardEnabled(getActivity());
    } */

    @Override
    protected boolean isStepCompleted(Context ctx) {
        return SetupSupport.isThisKeyboardSetAsDefaultIME(ctx);
    }

    @Override
    protected boolean isStepPreConditionDone(Context ctx) {
        return SetupSupport.isThisKeyboardEnabled(ctx);
    }
}
