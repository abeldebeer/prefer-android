package com.cookingfox.android.prefer.impl.pref;

import com.cookingfox.android.prefer.api.pref.PrefMeta;

import static com.cookingfox.guava_preconditions.Preconditions.checkNotNull;

/**
 * Abstract implementation of {@link PrefMeta}.
 */
public abstract class AbstractPrefMeta implements PrefMeta {

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
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    /**
     * Set whether this Pref should be visible.
     *
     * @param show Whether this Pref should be visible.
     */
    public void setShow(boolean show) {
        this.show = show;
    }

    /**
     * Set the Pref summary.
     *
     * @param summary The Pref summary.
     */
    public void setSummary(String summary) {
        this.summary = checkNotNull(summary, "Summary can not be null");
    }

    /**
     * Set the Pref title.
     *
     * @param title The Pref title.
     */
    public void setTitle(String title) {
        this.title = checkNotNull(title, "Title can not be null");
    }

}
