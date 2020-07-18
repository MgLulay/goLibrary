package com.mobility.library.info;

public class CategoryInfo {

    private String systemkey;
    private String code;
    private String name;
    private String createddate;
    private String modifieddate;
    private String remark;
    private int deletedstatus;

    public CategoryInfo(){
        this.systemkey = "";
        this.code = "";
        this.name = "";
        this.remark = "";
        this.createddate = "";
        this.modifieddate = "";
        this.deletedstatus = 0;
    }

    public CategoryInfo(String systemkey, String code, String name, String createddate, String modifieddate,String remark, int deletedstatus) {
        this.systemkey = systemkey;
        this.code = code;
        this.name = name;
        this.createddate = createddate;
        this.modifieddate = modifieddate;
        this.deletedstatus = deletedstatus;
        this.remark = remark;
    }

    public String getSystemkey() {
        return systemkey;
    }

    public void setSystemkey(String systemkey) {
        this.systemkey = systemkey;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }

    public String getModifieddate() {
        return modifieddate;
    }

    public void setModifieddate(String modifieddate) {
        this.modifieddate = modifieddate;
    }

    public int getDeletedstatus() {
        return deletedstatus;
    }

    public void setDeletedstatus(int deletedstatus) {
        this.deletedstatus = deletedstatus;
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
