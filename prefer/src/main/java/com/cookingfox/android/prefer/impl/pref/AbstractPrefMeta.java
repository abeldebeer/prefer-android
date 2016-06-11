package com.cookingfox.android.prefer.impl.pref;

import com.cookingfox.android.prefer.api.pref.PrefMeta;

/**
 * Created by abeldebeer on 10/05/16.
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

    public T setEnable(boolean enable) {
        this.enable = enable;
        return (T) this;
    }

    public T setShow(boolean show) {
        this.show = show;
        return (T) this;
    }

    public T setSummary(String summary) {
        this.summary = summary;
        return (T) this;
    }

    public T setTitle(String title) {
        this.title = title;
        return (T) this;
    }

}
