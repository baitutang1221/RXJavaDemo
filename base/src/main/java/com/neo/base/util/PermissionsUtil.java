package com.neo.base.util;

import android.os.Build;
import android.util.Log;

import pub.devrel.easypermissions.EasyPermissions;

public class PermissionsUtil {




    public static boolean  getPermissions(String[] perms){
        if (EasyPermissions.hasPermissions(YHContext.getInstance().getContext(), perms)){//检查是否获取该权限
            Log.i("permissions", "-----------已获取权限----");
            return true;
        }

        return false;

    }

    public static boolean isPermissions (){
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        return false;
    }


}
