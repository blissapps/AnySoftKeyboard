package com.anysoftkeyboard.ui.settings.setup;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.menny.android.anysoftkeyboard.R;

public class WizardPageIntroFragment extends WizardPageBaseFragment {

    private Typeface tf1, tf2;
    private boolean startSetup = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.keyboard_setup_wizard_layout_start, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tf1 = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/ropa_soft_bold.ttf");
        tf2 = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/ropa_soft_light.ttf");
        view.findViewById(R.id.bEnableKeyboard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSetup = true;
            }
        });
        ((TextView) view.findViewById(R.id.tvIntro2)).setTypeface(tf2);
        ((TextView) view.findViewById(R.id.tvIntro1)).setTypeface(tf2);
        ((Button) view.findViewById(R.id.bEnableKeyboard)).setTypeface(tf1);
    }

    @Override
    protected boolean isStepCompleted() {
        return startSetup;
    }

    @Override
    protected boolean isStepPreConditionDone() {
        return true;//the pre-condition is that the App is installed... I guess it does, right?
    }

}
