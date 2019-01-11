package com.appprocessors.ecomstore.interfaces;


public interface UiUpdaterListener {
    void error(String errorMessage);
    void updateViews(boolean isLoading);
}
