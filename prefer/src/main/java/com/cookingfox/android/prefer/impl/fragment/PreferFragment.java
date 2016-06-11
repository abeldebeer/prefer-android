package com.cookingfox.android.prefer.impl.fragment;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.v7.app.AlertDialog;

import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.PrefGroup;
import com.cookingfox.android.prefer.api.pref.PrefMeta;
import com.cookingfox.android.prefer.api.pref.typed.BooleanPref;
import com.cookingfox.android.prefer.api.pref.typed.IntegerPref;
import com.cookingfox.android.prefer.api.pref.typed.StringPref;
import com.cookingfox.android.prefer.api.prefer.Prefer;
import com.cookingfox.android.prefer.impl.pref.AbstractAndroidPref;
import com.cookingfox.android.prefer.impl.pref.AndroidPrefGroup;
import com.cookingfox.android.prefer.impl.prefer.PreferKeySerializer;

/**
 * Created by abeldebeer on 11/05/16.
 */
public class PreferFragment extends PreferenceFragment {

    // FIXME: 11/05/16 Should be possible to change
    protected static final String STRING_INVALID_INPUT_VALUE = "Invalid input value";

    protected Prefer prefer;

    //----------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //----------------------------------------------------------------------------------------------

    /**
     * Recommended method of initialization: {@link #create(Prefer)}.
     */
    public PreferFragment() {
    }

    //----------------------------------------------------------------------------------------------
    // STATIC FACTORY METHOD
    //----------------------------------------------------------------------------------------------

    public static PreferFragment create(Prefer prefer) {
        return new PreferFragment().setPrefer(prefer);
    }

    //----------------------------------------------------------------------------------------------
    // IMPLEMENTATION: PreferenceActivity
    //----------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final PreferenceScreen rootScreen = getPreferenceManager()
                .createPreferenceScreen(getActivity());

        addCategories(rootScreen);

        setPreferenceScreen(rootScreen);
    }

    //----------------------------------------------------------------------------------------------
    // PUBLIC METHODS
    //----------------------------------------------------------------------------------------------

    public PreferFragment setPrefer(Prefer prefer) {
        this.prefer = prefer;
        return this;
    }

    //----------------------------------------------------------------------------------------------
    // PROTECTED METHODS
    //----------------------------------------------------------------------------------------------

    protected void addCategories(PreferenceScreen rootScreen) {
        for (PrefGroup<? extends Enum> g : prefer.getGroups()) {
            if (!(g instanceof AndroidPrefGroup) || !((AndroidPrefGroup) g).show()) {
                continue;
            }

            final AndroidPrefGroup<? extends Enum> group = (AndroidPrefGroup<?>) g;

            // create group screen
            final PreferenceScreen groupScreen = getPreferenceManager()
                    .createPreferenceScreen(getActivity());
            populatePreferenceWithMeta(groupScreen, group);

            // create group header (category)
            final PreferenceCategory groupHeader = new PreferenceCategory(getActivity());
            populatePreferenceWithMeta(groupHeader, group);
            groupScreen.addPreference(groupHeader);

            int numPreferences = addPreferences(groupScreen, group);

            // only add screen if it contains preferences
            if (numPreferences > 0) {
                rootScreen.addPreference(groupScreen);
            }
        }
    }

    protected <K extends Enum<K>> int addPreferences(PreferenceScreen groupScreen, PrefGroup<K> group) {
        int numGenerated = 0;

        for (Pref<K, ?> p : group) {
            if (!(p instanceof AbstractAndroidPref) || !((AbstractAndroidPref) p).show()) {
                continue;
            }

            final AbstractAndroidPref pref = (AbstractAndroidPref) p;

            Preference input = createPrefInput(pref);
            populatePreferenceWithMeta(input, pref);

            // TODO: 10/05/16 Add other numeric prefs
            if (pref instanceof IntegerPref) {
                input.setDefaultValue(String.valueOf(pref.getDefaultValue()));
            } else {
                input.setDefaultValue(pref.getDefaultValue());
            }

            input.setKey(PreferKeySerializer.serializeKey(pref.getKey()));

            System.out.println("key: " + input.getKey());

            input.setOnPreferenceChangeListener(createPreferenceListener(pref));

            // allow the preference implementation to modify the input
            input = pref.modifyPreference(input);

            if (input == null) {
                // FIXME: 10/05/16 Handle error: `modifyPreference()` returned null
                continue;
            }

            groupScreen.addPreference(input);

            numGenerated++;
        }

        return numGenerated;
    }

    private Preference createPrefInput(AbstractAndroidPref pref) {
        // create correct input for pref type
        if (pref instanceof BooleanPref) {
            return new CheckBoxPreference(getActivity());
        }

        return new EditTextPreference(getActivity());
    }

    protected OnPreferenceChangeListener createPreferenceListener(final Pref pref) {
        return new OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Exception error = null;
                boolean isValid = false;

                try {
                    // check if valid
                    isValid = validatePref(pref, newValue);
                } catch (Exception e) {
                    error = e;
                }

                // not valid: show dialog
                if (!isValid) {
                    showInvalidDialog(error);
                }

                return isValid;
            }
        };
    }

    protected void showInvalidDialog(Exception error) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setPositiveButton(android.R.string.ok, null);
        dialogBuilder.setTitle(STRING_INVALID_INPUT_VALUE);

        if (error == null) {
            dialogBuilder.setMessage(STRING_INVALID_INPUT_VALUE);
        } else {
            dialogBuilder.setMessage(error.getMessage());
        }

        dialogBuilder.show();
    }

    protected boolean validatePref(Pref pref, Object newValue) throws Exception {
        final String stringValue = String.valueOf(newValue);

        // TODO: 11/05/16 Add other pref implementations
        if (pref instanceof BooleanPref) {
            return pref.validate(Boolean.parseBoolean(stringValue));
        } else if (pref instanceof IntegerPref) {
            return pref.validate(Integer.parseInt(stringValue));
        } else if (pref instanceof StringPref) {
            return pref.validate(stringValue);
        }

        throw new UnsupportedOperationException("Unsupported Pref implementation: " + pref);
    }

    protected void populatePreferenceWithMeta(Preference preference, PrefMeta meta) {
        preference.setEnabled(meta.enable());
        preference.setSummary(meta.getSummary());
        preference.setTitle(meta.getTitle());
    }
}
