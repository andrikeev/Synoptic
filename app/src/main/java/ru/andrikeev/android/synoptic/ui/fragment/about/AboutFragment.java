package ru.andrikeev.android.synoptic.ui.fragment.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.View;

import de.psdev.licensesdialog.LicensesDialog;
import ru.andrikeev.android.synoptic.BuildConfig;
import ru.andrikeev.android.synoptic.R;

public class AboutFragment extends PreferenceFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.about);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Preference emailToDeveloperPreference = findPreference(getString(R.string.about_email_developer_key));
        emailToDeveloperPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, getString(R.string.about_email));
                intent.putExtra(Intent.EXTRA_SUBJECT,
                        String.format("%s %s", getString(R.string.app_name), BuildConfig.VERSION_NAME));
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        Preference licensesPreference = findPreference(getString(R.string.about_view_licenses_key));
        licensesPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                new LicensesDialog.Builder(getActivity())
                        .setTitle(R.string.about_view_licenses_button)
                        .setNotices(R.raw.licenses)
                        .setIncludeOwnLicense(true)
                        .build()
                        .showAppCompat();
                return true;
            }
        });

        Preference versionPreference = findPreference(getString(R.string.about_version_key));
        versionPreference.setSummary(BuildConfig.VERSION_NAME);
    }
}
