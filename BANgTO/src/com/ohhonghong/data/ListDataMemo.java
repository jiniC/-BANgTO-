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
}
