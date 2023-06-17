package common;

import java.io.Serializable;

public class RequiredFile implements Serializable{
	String FileNmae;
	int chunk;
	@Override
	public String toString() {
		return "RequiredFile [FileNmae=" + FileNmae + ", chunk=" + chunk + "]";
	}
	public RequiredFile(String fileNmae, int chunk) {
		super();
		FileNmae = fileNmae;
		this.chunk = chunk;
	}
	public String getFileNmae() {
		return FileNmae;
	}
	public void setFileNmae(String fileNmae) {
		FileNmae = fileNmae;
	}
	public int getChunk() {
		return chunk;
	}
	public void setChunk(int chunk) {
		this.chunk = chunk;
	}

}
