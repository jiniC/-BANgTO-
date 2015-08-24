package com.ohhonghong.data;

import java.text.Collator;
import java.util.Comparator;

public class ListDataBank {
	public String getTvTo() {
		return tvTo;
	}

	public void setTvTo(String tvTo) {
		this.tvTo = tvTo;
	}

	public String getTvFrom() {
		return tvFrom;
	}

	public void setTvFrom(String tvFrom) {
		this.tvFrom = tvFrom;
	}

	public String getTvMoney() {
		return tvMoney;
	}

	public void setTvMoney(String tvMoney) {
		this.tvMoney = tvMoney;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int id;
	public String tvTo;
	public String tvFrom;
	public String tvMoney;

}