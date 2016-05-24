package com.anysoftkeyboard.ui.settings.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.preference.CheckBoxPreference;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.TextView;

import com.menny.android.anysoftkeyboard.R;

/**
 * Created by Benny on 24/05/16.
 */
public class SKCheckBoxPreference extends CheckBoxPreference {

    private Typeface tf1, tf2;

    public SKCheckBoxPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SKCheckBoxPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public SKCheckBoxPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SKCheckBoxPreference(Context context) {
        super(context);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        tf1 = Typeface.createFromAsset(getContext().getAssets(), "fonts/ropa_soft_bold.ttf");
        tf2 = Typeface.createFromAsset(getContext().getAssets(), "fonts/ropa_soft_light.ttf");
        ((TextView) view.findViewById(android.R.id.title)).setTypeface(tf1);
        ((TextView) view.findViewById(android.R.id.summary)).setTypeface(tf2);

        AppCompatCheckBox checkboxView = (AppCompatCheckBox) view.findViewById(android.R.id.checkbox);
        checkboxView.setSupportButtonTintList(ResourcesCompat.getColorStateList(getContext().getResources(), android.R.color.white, getContext().getTheme()));
    }

}
