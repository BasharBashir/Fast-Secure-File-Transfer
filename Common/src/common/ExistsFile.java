package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExistsFile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String FileName;
	List<String> ipList;
	int ChunksNum;
	String key;
	String hashcode;
	@Override
	public String toString() {
		return "ExistsFile [FileName=" + FileName + ", ipList=" + ipList + ", ChunksNum=" + ChunksNum + "]";
	}
	public ExistsFile(String fileName, List<String> ipLists, int chunksNum,String keys,String hashcodes) {
		super();
		FileName = fileName;
		ipList = ipLists;
		ChunksNum = chunksNum;
		key=keys;
		hashcode=hashcodes;
	}
	public ExistsFile() {
		// TODO Auto-generated constructor stub
	}
	public String getHashcode() {
		return hashcode;
	}
	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public List<String> getIpList() {
		return ipList;
	}
	public void setIpList(List<String> ipList) {
		this.ipList = ipList;
	}
	public int getChunksNum() {
		return ChunksNum;
	}
	public void setChunksNum(int chunksNum) {
		ChunksNum = chunksNum;
	}

}
