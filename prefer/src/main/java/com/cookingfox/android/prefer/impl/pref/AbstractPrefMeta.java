package com.cookingfox.android.prefer.impl.pref;

import com.cookingfox.android.prefer.api.pref.PrefMeta;

/**
 * Abstract implementation of {@link PrefMeta}.
 *
 * @param <T> Indicates the concrete type that extends this class.
 */
public abstract class AbstractPrefMeta<T extends PrefMeta> implements PrefMeta {

    protected boolean enable = true;
    protected String summary = "";
    protected String title = "";
    protected boolean show = true;

    //----------------------------------------------------------------------------------------------
    // IMPLEMENTATION: PrefMeta
    //----------------------------------------------------------------------------------------------

    @Override
    public boolean enable() {
        return enable;
    }

    @Override
    public String getSummary() {
        return summary;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public boolean show() {
        return show;
    }

    //----------------------------------------------------------------------------------------------
    // SETTERS
    //----------------------------------------------------------------------------------------------

    /**
     * Set whether this Pref is enabled.
     *
     * @param enable Whether this Pref is enabled.
     * @return The current instance, for method chaining.
     */
    public T setEnable(boolean enable) {
        this.enable = enable;
        return (T) this;
    }

    /**
     * Set whether this Pref should be visible.
     *
     * @param show Whether this Pref should be visible.
     * @return The current instance, for method chaining.
     */
    public T setShow(boolean show) {
        this.show = show;
        return (T) this;
    }

    /**
     * Set the Pref summary.
     *
     * @param summary The Pref summary.
     * @return The current instance, for method chaining.
     */
    public T setSummary(String summary) {
        this.summary = summary;
        return (T) this;
    }

    /**
     * Set the Pref title.
     *
     * @param title The Pref title.
     * @return The current instance, for method chaining.
     */
    public T setTitle(String title) {
        this.title = title;
        return (T) this;
    }

}
