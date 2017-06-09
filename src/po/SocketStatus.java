package po;


/**
 * 自定义socket状态码，便于信息交换。
 * @author bingqin
 * @date 2017年6月10日
 */
public class SocketStatus {
	//当前程序的状态
	int status = 0x01;
	 public int getStatus() {   
		 return status;   
	}   
	 public void setStatus(int status) {   
		 this.status = status;   
		 }   
	 public final static int RECIVE_IP = 0x01; //接收在线用户的IP       
	 public final static int RECIVE_ALL_MSG = 0x02; //接收群消息       
	 public final static int REFRESH_ONLINE_USER = 0x03; //刷新在线用户，还没用
	 public final static int RECIVE_FILE = 0x04; //接收文件
}
