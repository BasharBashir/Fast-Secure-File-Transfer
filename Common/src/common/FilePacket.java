package common;

import java.io.*;

public class FilePacket implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File file;
	private byte[] fileArr;
	private int numberchunks;
	boolean ForShare;
	public FilePacket(  File f) {
		this( f, null,0,false);
	}
	
	public FilePacket( File f , byte[] buff,int numberchunks,boolean ForShare) {
		
		this.setFile(f);
		this.setFileArr(buff);
		setNumberchunks(numberchunks);
		this.ForShare=ForShare;
	}
	
 
	
	public boolean isForShare() {
		return ForShare;
	}

	public void setForShare(boolean forShare) {
		ForShare = forShare;
	}

	public int getNumberchunks() {
		return numberchunks;
	}

	public void setNumberchunks(int numberchunks) {
		this.numberchunks = numberchunks;
	}

	public File getFile() {
		return this.file;
	}
	
	public void setFile(File f) {
		this.file = f;
	}
	
	public byte[] getFileArr() {
		return this.fileArr;
	}
	
	public void setFileArr(byte[] buff) {
		this.fileArr = buff;
	}

}
