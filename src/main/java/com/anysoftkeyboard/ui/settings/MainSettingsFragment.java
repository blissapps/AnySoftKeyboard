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
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceCategory;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.preference.PreferenceFragment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.anysoftkeyboard.keyboards.KeyboardAddOnAndBuilder;
import com.anysoftkeyboard.keyboards.KeyboardFactory;
import com.anysoftkeyboard.ui.settings.setup.SetUpKeyboardWizardFragment;
import com.anysoftkeyboard.ui.settings.widget.AddOnCheckBoxPreference;
import com.anysoftkeyboard.utils.Log;
import com.menny.android.anysoftkeyboard.R;

import net.evendanan.chauffeur.lib.FragmentChauffeurActivity;
import net.evendanan.chauffeur.lib.experiences.TransitionExperiences;

import java.util.List;

public class MainSettingsFragment extends Fragment {

    private Typeface tf1, tf2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.side_menu_layout_alt_2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tf1 = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/ropa_soft_bold.ttf");
        tf2 = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/ropa_soft_light.ttf");
        ((TextView) view.findViewById(R.id.tvMenuLanguages)).setTypeface(tf1);
        ((TextView) view.findViewById(R.id.tvMenuLanguagesKeyboards)).setTypeface(tf2);
        ((TextView) view.findViewById(R.id.tvMenuLanguagesDictionaries)).setTypeface(tf2);
        ((TextView) view.findViewById(R.id.tvMenuLanguagesMoreSettings)).setTypeface(tf2);
        ((TextView) view.findViewById(R.id.tvMenuUI)).setTypeface(tf1);
        ((TextView) view.findViewById(R.id.tvMenuUIEffects)).setTypeface(tf2);
        ((TextView) view.findViewById(R.id.tvMenuUIGestures)).setTypeface(tf2);
        ((TextView) view.findViewById(R.id.tvMenuUIQuickText)).setTypeface(tf2);
        ((TextView) view.findViewById(R.id.tvMenuUIGeneral)).setTypeface(tf2);
    }
}
