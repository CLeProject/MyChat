package com.le.mychat.core;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.le.mychat.R;


/**
 * Fragment 之间切换
 */
public class FragmentInfoActivity extends BaseActivity {

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        FragmentInfo info = getIntent().getParcelableExtra(FragmentInfo.INFO);
        fragment = getSupportFragmentManager().findFragmentByTag(info.getClazz().getName());
        if (fragment == null) {
            fragment = Fragment.instantiate(getApplicationContext(), info
                    .getClazz().getName(), info.getBundle());
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.setTransitionStyle(FragmentTransaction.TRANSIT_ENTER_MASK
//                | FragmentTransaction.TRANSIT_EXIT_MASK);
//        transaction.setCustomAnimations(R.anim.push_enter, R.anim.push_out,
//                R.anim.pop_enter, R.anim.pop_out);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(R.id.content, fragment, info.getClazz().getName())
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 在Fragment中跳转
     *
     * @param currentFragment
     * @param info
     */
    public static void startFragment(BaseFragment currentFragment,
                                     FragmentInfo info) {
        if (currentFragment.getActivity() instanceof FragmentInfoActivity) {
            Fragment fragment = Fragment.instantiate(
                    currentFragment.getActivity(), info.getClazz().getName(),
                    info.getBundle());
            FragmentTransaction transaction = currentFragment
                    .getFragmentManager().beginTransaction();
            transaction
                    .setTransitionStyle(FragmentTransaction.TRANSIT_ENTER_MASK
                            | FragmentTransaction.TRANSIT_EXIT_MASK);
            transaction.setCustomAnimations(R.anim.push_enter, R.anim.push_out,
                    R.anim.pop_enter, R.anim.pop_out);
            transaction.hide(currentFragment)
                    .add(R.id.content, fragment, info.getClazz().getName())
                    .addToBackStack(null).commit();
        } else {
            Intent intent = new Intent(currentFragment.getActivity(),
                    FragmentInfoActivity.class);
            intent.putExtra(FragmentInfo.INFO, info);
            currentFragment.getActivity().startActivity(intent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (fragment instanceof BaseFragment) {
            if (((BaseFragment) fragment).onKeyDown(keyCode, event)) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 在Activity中的跳转
     *
     * @param context
     * @param info
     */
    public static void startActivity(Context context, FragmentInfo info) {
        Intent intent = new Intent(context, FragmentInfoActivity.class);
        intent.putExtra(FragmentInfo.INFO, info);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public static void startActivityForResult(Fragment fragment,
                                              FragmentInfo info, int requestCode) {
        Intent intent = new Intent(fragment.getActivity(),
                FragmentInfoActivity.class);
        intent.putExtra(FragmentInfo.INFO, info);
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void startActivityForResult(Activity activity,
                                              FragmentInfo info, int requestCode) {
        Intent intent = new Intent(activity, FragmentInfoActivity.class);
        intent.putExtra(FragmentInfo.INFO, info);
        activity.startActivityForResult(intent, requestCode);
    }

}
