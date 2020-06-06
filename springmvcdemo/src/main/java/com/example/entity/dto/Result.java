package com.example.entity.dto;

public class Result<T> {
	//本次请求结果的状态码，200表示成功
	private int code;
	//本次请求结果的详情
	private String msg;
	//本次请求返回的结果集
	private T data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
