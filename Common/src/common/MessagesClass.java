package common;

import java.io.File;
import java.io.Serializable;

/**
 * Class that connects between client and server
 * use message enum
 * each message handle sql server queries
 * @author asem
 *
 */
public class MessagesClass implements Serializable {
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Messages msgType;
	private Object msgData;
	private Object msgData1;
	private int Id;
	private int Port;

	 


	public int getPort() {
		return Port;
	}



	public void setPort(int port) {
		Port = port;
	}



	public MessagesClass(Messages msgType, Object msgData, int id) {
		super();
		this.msgType = msgType;
		this.msgData = msgData;
		setPort(id);
		Id = id;
	}



	public MessagesClass(Messages msgType, Object msgData) {
		this.msgType = msgType;
		this.msgData = msgData;
	}

 

	@Override
	public String toString() {
		return "MessagesClass [msgType=" + msgType + ", msgData=" + msgData + "]";
	}



	public MessagesClass(Messages msgType, Object msgData, Object msgData1) {
		this.msgType = msgType;
		this.msgData = msgData;
		this.msgData1 = msgData1;	
		}

	public Object getMsgData1() {
		return msgData1;
	}

	public void setMsgData1(Object msgData1) {
		this.msgData1 = msgData1;
	}

	public void setMsgType(Messages msgType) {
		this.msgType = msgType;
	}

	public Messages getMsgType() {
		return msgType;
	}

	public void setMsgData(Object msgData) {
		this.msgData = msgData;
	}

	public Object getMsgData() {
		return msgData;
	}

	public int getId() {
		return Id;
	}

	public void setId(int Id) {
		this.Id = Id;
	}

	
	
}