package com.anysoftkeyboard.ui.settings;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.menny.android.anysoftkeyboard.R;

import net.evendanan.chauffeur.lib.FragmentChauffeurActivity;
import net.evendanan.chauffeur.lib.experiences.TransitionExperiences;

import java.util.Calendar;

public class AboutAnySoftKeyboardFragment extends Fragment {

    private static final String TAG = "AboutAnySoftKeyboardFragment";
    private Typeface tf1, tf2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.about_anysoftkeyboard, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tf1 = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/ropa_soft_bold.ttf");
        tf2 = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/ropa_soft_light.ttf");
        TextView title = (TextView) view.findViewById(R.id.about_app_title);
        title.setTypeface(tf1);

        TextView copyright = (TextView) view.findViewById(R.id.about_copyright);
        copyright.setText(getString(R.string.about_copyright_text, Calendar.getInstance().get(Calendar.YEAR)));
        copyright.setTypeface(tf2);
        String appVersionName = "";
        int appVersionNumber = 0;
        try {
            PackageInfo info = view.getContext().getPackageManager().getPackageInfo(view.getContext().getPackageName(), 0);
            appVersionName = info.versionName;
            appVersionNumber = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        TextView version = (TextView) view.findViewById(R.id.about_app_version);
        version.setText(getString(R.string.version_text, appVersionName, appVersionNumber));
        version.setTypeface(tf2);

        /*
        view.findViewById(R.id.about_donate_paypal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=KDYBGNUNMMN94&lc=US&item_name=AnySoftKeyboard&currency_code=USD&bn=PP%2dDonationsBF%3abtn_donate_SM%2egif%3aNonHosted"));
                try {
                    getActivity().startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    //this means that there is nothing on the device
                    //that can handle Intent.ACTION_VIEW with "https" schema..
                    //silently swallowing it
                    Log.w(TAG, "Can not open '%' since there is nothing on the device that can handle it.", intent.getData());
                }
            }
        });
        */
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle(R.string.ime_name);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        TextView additionalSoftware = (TextView) getView().findViewById(R.id.about_legal_stuff_link);
        SpannableStringBuilder sb = new SpannableStringBuilder(additionalSoftware.getText());
        sb.clearSpans();//removing any previously (from instance-state) set click spans.
        sb.setSpan(new ClickableSpan() {
                       @Override
                       public void onClick(View widget) {
                           FragmentChauffeurActivity activity = (FragmentChauffeurActivity) getActivity();
                           activity.addFragmentToUi(new AdditionalSoftwareLicensesFragment(), TransitionExperiences.DEEPER_EXPERIENCE_TRANSITION);
                       }
                   },
                0, additionalSoftware.getText().length(),
                Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        additionalSoftware.setMovementMethod(LinkMovementMethod.getInstance());
        additionalSoftware.setText(sb);
        additionalSoftware.setTypeface(tf2);
    }

    public static class AdditionalSoftwareLicensesFragment extends Fragment {

        private Typeface tf1, tf2;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.additional_software_licenses, container, false);

        }

        @Override
        public void onStart() {
            super.onStart();
            getActivity().setTitle(R.string.about_additional_software_licenses);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            tf1 = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/ropa_soft_bold.ttf");
            tf2 = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "fonts/ropa_soft_light.ttf");
            ((TextView) view.findViewById(R.id.tvLegal1)).setTypeface(tf2);
            ((TextView) view.findViewById(R.id.tvLegal2)).setTypeface(tf2);
            ((TextView) view.findViewById(R.id.tvLegal3)).setTypeface(tf2);
            ((TextView) view.findViewById(R.id.tvLegal4)).setTypeface(tf2);
            ((TextView) view.findViewById(R.id.tvLegal5)).setTypeface(tf2);
            ((TextView) view.findViewById(R.id.tvLegal6)).setTypeface(tf2);
            ((TextView) view.findViewById(R.id.tvLegal7)).setTypeface(tf2);
            ((TextView) view.findViewById(R.id.tvLegal8)).setTypeface(tf2);
            ((TextView) view.findViewById(R.id.tvLegal9)).setTypeface(tf2);
            ((TextView) view.findViewById(R.id.tvLegal10)).setTypeface(tf2);
            ((TextView) view.findViewById(R.id.tvLegal11)).setTypeface(tf2);
            ((TextView) view.findViewById(R.id.tvLegal12)).setTypeface(tf2);
            ((TextView) view.findViewById(R.id.tvLegal13)).setTypeface(tf2);
            ((TextView) view.findViewById(R.id.tvLegal14)).setTypeface(tf2);
            ((TextView) view.findViewById(R.id.tvLegal15)).setTypeface(tf2);
            ((TextView) view.findViewById(R.id.tvLegal16)).setTypeface(tf2);
            ((TextView) view.findViewById(R.id.tvLegal17)).setTypeface(tf2);
            ((TextView) view.findViewById(R.id.tvLegal18)).setTypeface(tf2);
            ((TextView) view.findViewById(R.id.tvLegal19)).setTypeface(tf2);
            ((TextView) view.findViewById(R.id.tvLegal20)).setTypeface(tf2);
        }
    }
}
