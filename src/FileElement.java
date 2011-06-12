import java.io.Serializable;

public class FileElement implements Serializable
{
	private static final long serialVersionUID = 1L;
	public String filename;
	public long filesize;
	public boolean[] block_complete;
	

	public FileElement(String filename, long length, int chunkSize) {
		this.filename = filename;
		this.filesize = length;
		this.block_complete = new boolean[(int) (Math.ceil(length / chunkSize) + 1)];
	}
}
