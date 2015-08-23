package com.ohhonghong.data;

import java.text.Collator;
import java.util.Comparator;

import android.graphics.drawable.Drawable;

public class ListDataMoney {
	// 날짜
	public String money_data;
	// 내용
	public String money_content;
	// +
	public String money_plus;
	// -
	public String money_minus;
	// 잔액
	public String money_balance;

	public String getMoney_data() {
		return money_data;
	}

	public void setMoney_data(String money_data) {
		this.money_data = money_data;
	}

	public String getMoney_content() {
		return money_content;
	}

	public void setMoney_content(String money_content) {
		this.money_content = money_content;
	}

	public String getMoney_plus() {
		return money_plus;
	}

	public void setMoney_plus(String money_plus) {
		this.money_plus = money_plus;
	}

	public String getMoney_minus() {
		return money_minus;
	}

	public void setMoney_minus(String money_minus) {
		this.money_minus = money_minus;
	}

	public String getMoney_balance() {
		return money_balance;
	}

	public void setMoney_balance(String money_balance) {
		this.money_balance = money_balance;
	}

}
