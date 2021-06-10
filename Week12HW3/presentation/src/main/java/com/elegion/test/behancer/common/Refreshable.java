package com.elegion.test.behancer.common;

import com.elegion.data.uiComponents.RefreshCallbackInterface;

/**
 * @author Azret Magometov
 */
public interface Refreshable {
    void onRefreshData(RefreshCallbackInterface callback);
}
