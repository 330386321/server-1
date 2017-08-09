package util.upload;

public enum FileUploadTypeEnum {
	IMG("img", "图片"), 
	VIDEO("VIDEO", "视频"), 
	OTHER("OTHER", "其他");

	public String val;
	public String name;

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	FileUploadTypeEnum(String val, String name) {
		this.val = val;
		this.name = name;
	}
	
	

}
