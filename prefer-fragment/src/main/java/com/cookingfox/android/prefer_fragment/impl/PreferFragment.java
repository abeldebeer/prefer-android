package com.cookingfox.android.prefer_fragment.impl;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.v7.app.AlertDialog;

import com.cookingfox.android.prefer.api.exception.PreferException;
import com.cookingfox.android.prefer.api.pref.Pref;
import com.cookingfox.android.prefer.api.pref.PrefGroup;
import com.cookingfox.android.prefer.api.pref.PrefMeta;
import com.cookingfox.android.prefer.api.pref.typed.BooleanPref;
import com.cookingfox.android.prefer.api.pref.typed.IntegerPref;
import com.cookingfox.android.prefer.api.pref.typed.LongPref;
import com.cookingfox.android.prefer.api.pref.typed.StringPref;
import com.cookingfox.android.prefer.api.prefer.Prefer;
import com.cookingfox.android.prefer.impl.pref.AbstractAndroidPref;
import com.cookingfox.android.prefer.impl.pref.AndroidPrefGroup;
import com.cookingfox.android.prefer.impl.prefer.PreferKeySerializer;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link PreferenceFragment} implementation which uses all available Pref groups to generate a
 * {@link PreferenceScreen} with {@link Preference} elements, including hooks for validation. To
 * generate the fragment, use {@link #create(Prefer)}.
 */
public class PreferFragment extends PreferenceFragment {

    //----------------------------------------------------------------------------------------------
    // STATIC PROPERTIES
    //----------------------------------------------------------------------------------------------

    /**
     * The default message to display when an input value is invalid.
     */
    protected static String defaultMessageInvalidInputValue = "Invalid input value";

    //----------------------------------------------------------------------------------------------
    // INSTANCE PROPERTIES
    //----------------------------------------------------------------------------------------------

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
    // STATIC METHODS
    //----------------------------------------------------------------------------------------------

    /**
     * Creates a new Prefer fragment. The actual preferences are generated in its
     * {@link #onCreate(Bundle)} method.
     *
     * @param prefer The Prefer instance to create fragment with.
     * @return The created fragment.
     */
    public static PreferFragment create(Prefer prefer) {
        return new PreferFragment().setPrefer(prefer);
    }

    /**
     * Set the default message that is displayed when an input value is invalid.
     *
     * @param message The default message to display when an input value is invalid.
     */
    public static void setDefaultMessageInvalidInputValue(String message) {
        checkNotNull(message, "Message can not be null");

        PreferFragment.defaultMessageInvalidInputValue = message;
    }

    //----------------------------------------------------------------------------------------------
    // IMPLEMENTATION: PreferenceFragment
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

    /**
     * Set the Prefer instance that contains the Pref groups and objects which will be used to
     * generate {@link Preference} elements.
     *
     * @param prefer The Prefer instance.
     * @return The created fragment.
     */
    public PreferFragment setPrefer(Prefer prefer) {
        this.prefer = checkNotNull(prefer, "Prefer can not be null");
        return this;
    }

    //----------------------------------------------------------------------------------------------
    // PROTECTED METHODS
    //----------------------------------------------------------------------------------------------

    /**
     * Generate {@link PreferenceCategory} items for each Pref group.
     *
     * @param rootScreen The preference screen to add the categories to.
     */
    protected void addCategories(PreferenceScreen rootScreen) {
        for (PrefGroup<? extends Enum> g : prefer.getGroups()) {
            // validate group
            if (!(g instanceof AndroidPrefGroup)) {
                new PreferException(String.format("Can not generate Preference for '%s' - " +
                        "must be an implementation of '%s'", g, AndroidPrefGroup.class))
                        .printStackTrace(); // print exception to console
                continue;
            }

            // cast group
            final AndroidPrefGroup<? extends Enum> group = (AndroidPrefGroup<?>) g;

            // ignore group: should not be displayed on screen
            if (!group.show()) {
                continue;
            }

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

    /**
     * Generate {@link Preference} items for all Prefs in the group and add them to the screen.
     *
     * @param groupScreen The screen for this group.
     * @param group       The Pref group.
     * @return The number of actually generated preferences.
     */
    protected int addPreferences(PreferenceScreen groupScreen, PrefGroup<?> group) {
        int numGenerated = 0;

        for (Pref p : group) {
            // validate pref
            if (!(p instanceof AbstractAndroidPref)) {
                new PreferException(String.format("Can not generate Preference for '%s' - " +
                        "must be an implementation of '%s'", p, AbstractAndroidPref.class))
                        .printStackTrace(); // print exception to console
                continue;
            }

            final AbstractAndroidPref pref = (AbstractAndroidPref) p;

            // ignore pref: should not be displayed on screen
            if (!pref.show()) {
                continue;
            }

            // generate Preference input
            Preference input = createPrefInput(pref);
            populatePreferenceWithMeta(input, pref);

            // TODO: 10/05/16 Add other numeric prefs
            if (pref instanceof IntegerPref || pref instanceof LongPref) {
                input.setDefaultValue(String.valueOf(pref.getDefaultValue()));
            } else {
                input.setDefaultValue(pref.getDefaultValue());
            }

            // set pref key
            input.setKey(PreferKeySerializer.serializeKey(pref.getKey()));

            // add pref on change listener
            input.setOnPreferenceChangeListener(createPreferenceListener(pref));

            // allow the preference implementation to modify the input
            input = pref.modifyPreference(input);

            if (input == null) {
                new PreferException(String.format("Preference modifier for '%s' returned null", pref.getKey()))
                        .printStackTrace();
                continue;
            }

            // add preference to screen
            groupScreen.addPreference(input);

            numGenerated++;
        }

        return numGenerated;
    }

    /**
     * Create the Preference input.
     *
     * @param pref The Pref to create the input for.
     * @return The generated Preference.
     */
    private Preference createPrefInput(AbstractAndroidPref pref) {
        // create correct input for pref type
        if (pref instanceof BooleanPref) {
            return new CheckBoxPreference(getActivity());
        }

        return new EditTextPreference(getActivity());
    }

    /**
     * Create a new "on changed" listener for this Pref.
     *
     * @param pref The Pref to create the listener for.
     * @return The listener.
     */
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

    /**
     * Show dialog when the input validation failed.
     *
     * @param error (Optional) The validation error that occurred.
     */
    protected void showInvalidDialog(Exception error) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setPositiveButton(android.R.string.ok, null);
        dialogBuilder.setTitle(defaultMessageInvalidInputValue);

        if (error == null) {
            dialogBuilder.setMessage(defaultMessageInvalidInputValue);
        } else {
            dialogBuilder.setMessage(error.getMessage());
        }

        dialogBuilder.show();
    }

    /**
     * Validate the user-entered Pref value.
     *
     * @param pref     The Pref to use for validation.
     * @param newValue The value to validate.
     * @return Whether the user input is valid.
     * @throws Exception when the {@link Pref#validate(Object)} method throws.
     */
    @SuppressWarnings("unchecked")
    protected boolean validatePref(Pref pref, Object newValue) throws Exception {
        final String stringValue = String.valueOf(newValue);

        // TODO: 11/05/16 Add other pref implementations
        if (pref instanceof BooleanPref) {
            return pref.validate(Boolean.parseBoolean(stringValue));
        } else if (pref instanceof IntegerPref) {
            return pref.validate(Integer.parseInt(stringValue));
        } else if (pref instanceof LongPref) {
            return pref.validate(Long.parseLong(stringValue));
        } else if (pref instanceof StringPref) {
            return pref.validate(stringValue);
        }

        throw new UnsupportedOperationException("Unsupported Pref implementation: " + pref);
    }

    /**
     * Populate the generated Preference with meta data.
     *
     * @param preference The generated Preference.
     * @param meta       The meta data object.
     */
    protected void populatePreferenceWithMeta(Preference preference, PrefMeta meta) {
        preference.setEnabled(meta.enable());
        preference.setSummary(meta.getSummary());
        preference.setTitle(meta.getTitle());
    }

}
