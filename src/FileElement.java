
public class FileElement {
	
	public String filename;
	public long filesize;
	public boolean[] block_complete ;

	public FileElement(String filename2, long length, int chunkSize) {
		this.filename = filename;
		this.filesize = filesize;
		this.block_complete = new boolean[(int) (Math.ceil(filesize / chunkSize) + 1)];
	}
}
