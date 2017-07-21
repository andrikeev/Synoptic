package ru.andrikeev.android.synoptic.ui.fragment.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;

import com.evernote.android.job.JobManager;

import javax.inject.Inject;

import ru.andrikeev.android.synoptic.R;
import ru.andrikeev.android.synoptic.application.Settings;
import ru.andrikeev.android.synoptic.di.Injectable;
import ru.andrikeev.android.synoptic.model.sync.FetchWeatherJob;
import timber.log.Timber;

public class SettingsFragment extends PreferenceFragmentCompat implements Injectable {

    @Inject
    protected JobManager jobManager;

    @Inject
    protected Settings settings;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Preference nightModePreference = findPreference(getString(R.string.pref_night_mode_key));
        nightModePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                getActivity().recreate();
                return true;
            }
        });

        Preference syncPreference = findPreference(getString(R.string.pref_sync_weather_key));
        syncPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                initScheduledJob((boolean) o);
                return true;
            }
        });

        Preference syncIntervalPreference = findPreference(getString(R.string.pref_sync_weather_interval_key));
        syncIntervalPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                initScheduledJob(settings.isSyncEnabled());
                return true;
            }
        });
    }

    private void initScheduledJob(boolean enable) {
        jobManager.cancelAll();
        Timber.d("All jobs canceled");
        if (enable && jobManager.getAllJobRequestsForTag(FetchWeatherJob.TAG).size() == 0) {
            FetchWeatherJob.scheduleJob(settings.getSyncInterval());
            Timber.d("Sync job scheduled");
        }
    }
}
