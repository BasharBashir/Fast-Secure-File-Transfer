package common;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * save client details for shared delivery
 * @author
 *
 */
public class clientDetails implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hostName;
	private String ip;
	private String Status;
	/**
	 * group code
	 */
	private int Code;
	private int flag;
	/**
	 * save business user
	 */
	private String additions_names;
	private MessagesClass logoutmsg;
	private MessagesClass loginmsg;
	
	public MessagesClass getLoginmsg() {
		return loginmsg;
	}

	public void setLoginmsg(MessagesClass loginmsg) {
		this.loginmsg = loginmsg;
	}

	public MessagesClass getLogoutmsg() {
		return logoutmsg;
	}

	public void setLogoutmsg(MessagesClass logoutmsg) {
		this.logoutmsg = logoutmsg;
	}

	public String getAdditions_names() {
		return additions_names;
	}

	public void setAdditions_names(String additions_names) {
		this.additions_names = additions_names;
	}


	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public clientDetails(String hostName, String ip, String status) {
		
		this.hostName = hostName;
		this.ip = ip;
		this.Status = status;
	}
	
	public clientDetails(String Firstname,String Status)
	{
		this.hostName=Firstname;
	}
	public clientDetails(String Firstname,int Code,String Status,int flag) 
	{
		this.hostName=Firstname;
	     this.Code=Code;
	     this.Status=Status;
	     this.flag=flag;
	}

	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}

	public int getCode() {
		return Code;
	}

	public void setCode(int code) {
		Code = code;
	}
	
	

}