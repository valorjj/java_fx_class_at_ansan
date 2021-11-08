package domain;

public class Reply {

	private int r_no;
	private String r_contents;
	private String r_write;
	private String r_date;
	private int r_b_no;

	public Reply() {
	}

	public Reply(int r_no, String r_contents, String r_write, String r_date, int r_b_no) {
		this.r_no = r_no;
		this.r_contents = r_contents;
		this.r_write = r_write;
		this.r_date = r_date;
		this.r_b_no = r_b_no;
	}

	public Reply(String r_contents, String r_write, int r_b_no) {
		this.r_contents = r_contents;
		this.r_write = r_write;
		this.r_b_no = r_b_no;
	}

	public int getR_no() {
		return r_no;
	}

	public void setR_no(int r_no) {
		this.r_no = r_no;
	}

	public String getR_contents() {
		return r_contents;
	}

	public void setR_contents(String r_contents) {
		this.r_contents = r_contents;
	}

	public String getR_write() {
		return r_write;
	}

	public void setR_write(String r_write) {
		this.r_write = r_write;
	}

	public String getR_date() {
		return r_date;
	}

	public void setR_date(String r_date) {
		this.r_date = r_date;
	}

	public int getR_b_no() {
		return r_b_no;
	}

	public void setR_b_no(int r_b_no) {
		this.r_b_no = r_b_no;
	}
	
	

}
