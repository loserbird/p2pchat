/**
 * 
 */
package po;

/**自定义消息交换格式。采用json
 * @author bingqin
 * @date 2017年6月8日
 */
public class Msg {
	public User user;
	public int socketStatus;
	public String content;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getSocketStatus() {
		return socketStatus;
	}
	public void setSocketStatus(int socketStatus) {
		this.socketStatus = socketStatus;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
