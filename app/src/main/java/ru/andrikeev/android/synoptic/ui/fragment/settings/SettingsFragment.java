package ru.andrikeev.android.synoptic.ui.fragment.settings;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.View;

import com.evernote.android.job.JobManager;

import javax.inject.Inject;

import ru.andrikeev.android.synoptic.R;
import ru.andrikeev.android.synoptic.application.Settings;
import ru.andrikeev.android.synoptic.di.Injectable;
import ru.andrikeev.android.synoptic.model.sync.FetchWeatherJob;

public class SettingsFragment extends PreferenceFragment implements Injectable {

    @Inject
    protected JobManager jobManager;

    @Inject
    protected Settings settings;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
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

        Preference.OnPreferenceChangeListener onSyncPreferenceChangeListener =
                new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object o) {
                        initScheduledJob();
                        return true;
                    }
                };
        Preference syncPreference = findPreference(getString(R.string.pref_sync_weather_key));
        syncPreference.setOnPreferenceChangeListener(onSyncPreferenceChangeListener);

        Preference syncIntervalPreference = findPreference(getString(R.string.pref_sync_weather_interval_key));
        syncIntervalPreference.setOnPreferenceChangeListener(onSyncPreferenceChangeListener);
    }

    private void initScheduledJob() {
        jobManager.cancelAll();
        if (settings.isSyncEnabled() && jobManager.getAllJobRequestsForTag(FetchWeatherJob.TAG).size() == 0) {
            FetchWeatherJob.scheduleJob(settings.getSyncInterval());
        }
    }
}
