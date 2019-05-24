package com.whosssmade.bluetoothterminal.base;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by Administrator on 2019/4/16.
 */

//Activity堆栈管理
public class ActivityManager {

    private static Stack<Activity> activityStack;

    //添加
    public static void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    //结束指定Activity
    public static void finishActivity(Activity activity) {
        if (activity != null && activityStack != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    //结束指定类名的Activity
    public static void finishActivity(Class<?> cls) {
        if (activityStack != null) {
            for (Activity activity :
                    activityStack) {
                if (activityStack.getClass().equals(cls)) {
                    finishActivity(activity);
                }
            }
        }
    }
}
