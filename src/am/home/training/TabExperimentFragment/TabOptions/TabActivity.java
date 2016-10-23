package am.home.training.TabExperimentFragment.TabOptions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by nadya on 7/21/14.
 */
public class TabActivity extends FragmentActivity {
    private List<Stack<Fragment>> tabsStack;
    private List<View> tabButton;
    int containerId;
    int currentTabIndex;

    public void setContainerId(int containerId) {
        this.containerId = containerId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabsStack = new ArrayList<Stack<Fragment>>();
        tabButton = new ArrayList<View>();
        currentTabIndex = 0;
    }

    public void setTabClickListener() {
        for(final View button : tabButton) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switchTab(tabButton.indexOf(view));
                }
            });
        }

    }

    public void addTab(View button,Fragment fragment){
        tabButton.add(button);
        Stack<Fragment> newStackFragment = new Stack<Fragment>();
        newStackFragment.add(fragment);
        tabsStack.add(newStackFragment);

    }

    private void switchTab(int indexOfTab) {
        currentTabIndex = indexOfTab;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(containerId, tabsStack.get(indexOfTab).pop());
        transaction.commit();
    }

    public  void addFragment(Fragment fragment) {


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(tabsStack.get(currentTabIndex).peek());
        transaction.show(fragment);
        transaction.commit();
        tabsStack.get(currentTabIndex).add(fragment);

    }

    @Override
    public void onBackPressed() {
        if(tabsStack.get(currentTabIndex).isEmpty()) {
            super.onBackPressed();
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.remove(tabsStack.get(currentTabIndex).pop());
        transaction.show(tabsStack.get(currentTabIndex).peek());
        transaction.commit();
    }
}
