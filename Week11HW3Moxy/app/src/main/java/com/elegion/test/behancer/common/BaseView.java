package com.elegion.test.behancer.common;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Vladislav Falzan.
 */

public interface BaseView extends MvpView {
    @StateStrategyType(value = SkipStrategy.class)
    void showRefresh();
    @StateStrategyType(value = SkipStrategy.class)
    void hideRefresh();
    @StateStrategyType(value = SingleStateStrategy.class)
    void showError();
}
