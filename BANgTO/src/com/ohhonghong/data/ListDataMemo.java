package com.ohhonghong.data;

public class ListDataMemo {
	public byte type = 0;
    public String data1 = null;
    public String data2 = null;
    
    public ListDataMemo(byte parm_type, String parm_data1, String parm_data2)
    {
        type = parm_type;
        data1 = parm_data1;
        data2 = parm_data2;
    }

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getData1() {
		return data1;
	}

	public void setData1(String data1) {
		this.data1 = data1;
	}

	public String getData2() {
		return data2;
	}

	public void setData2(String data2) {
		this.data2 = data2;
	}
    
}
