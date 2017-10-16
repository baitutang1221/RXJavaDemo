package com.neo.base.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.view.WindowManager;


public class TelephoneManager {

//	private static Context context = NeoApplication.getContext();
	private static Context context = YHContext.getInstance().getContext();

	// 私有的默认构造子
	private TelephoneManager(Context context) {
	}

	// 已经自行实例化
	private static final TelephoneManager tm = new TelephoneManager(context);

	// 静态工厂方法
	public static TelephoneManager getInstance() {
		return tm;
	}

	static TelephonyManager tem = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

	public static String getIMEI() {
		return tem.getDeviceId();
	}

	public String getSoftwareVersion() {
		return tem.getDeviceSoftwareVersion();
	}

	public int getPhoneType() {
		return tem.getPhoneType();
	}
	/**获取手机服务商信息*/
	public static String getIMSI(){
	     String ProvidersName = "";
	        try{  
	        String IMSI = tm.getIMSI();
	        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。  
	        System.out.println(IMSI);
	        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {  
	            ProvidersName = "中国移动";  
	        } else if (IMSI.startsWith("46001")) {  
	            ProvidersName = "中国联通";  
	        } else if (IMSI.startsWith("46003")) {  
	            ProvidersName = "中国电信";  
	        }  
	        }catch(Exception e){
	            e.printStackTrace();  
	        }  
	        return ProvidersName; 
	}

	/**
	 * 获取软件版本号
	 */
	public static int getVersionCode() {
		int versionCode = 0;
		PackageManager manager = context.getPackageManager();
		PackageInfo info;
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
			versionCode = info.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * 获取版本名
	 * 
	 * @Title: getVersionName
	 * @Description: TODO
	 * @return
	 * @return: String
	 */
	public static String getVersionName() {
		String versionName = null;
		PackageManager manager = context.getPackageManager();
		PackageInfo info;
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
			versionName = info.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return versionName;
	}
	/**
     * 获取屏幕分辨率
     * @param context
     * @return
     */
    public static int[] getScreenDispaly(Context context) {
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
		int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
		int result[] = { width, height };
		return result;
	}
	
	/**
	 * 百度统计前缀，表示“版本号|用户手机号”
	* @Title: getEventsLable 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String getEventsLable(){
		String lable = "";
		lable += TelephoneManager.getVersionName()+"|";
//		if(MemberOrm.getLoginMember()!=null&&MemberOrm.getLoginMember().getPhone()!=null){
//			lable += MemberOrm.getLoginMember().getPhone();
//		}else{
//			lable += "null";
//		}
		return lable;
	}
}
