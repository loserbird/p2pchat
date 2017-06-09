/**
 * 
 */
package po;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**常量类
 * @author bingqin
 * @date 2017年6月8日
 */
public class IpConstant {
	//定义广播的地址
	public static final String broadcastIp = "192.168.1.255";
	public static final int broadcastIpPort = 3333;
	public static final int oneToOnePort = 8888;
	//文件端口
	public static final int filePort = 4444;
	public static  String localIp;
	
	static{
		try {
			localIp = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
