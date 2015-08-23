package com.ohhonghong.data;

public class ListDataMember {
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public String getMember_bank() {
		return member_bank;
	}
	public void setMember_bank(String member_bank) {
		this.member_bank = member_bank;
	}
	public String getMember_account() {
		return member_account;
	}
	public void setMember_account(String member_account) {
		this.member_account = member_account;
	}
	// 이름
	public String member_name;
	// 은행
	public String member_bank;
	// 계좌번호
	public String member_account;
}
