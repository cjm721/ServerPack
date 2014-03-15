package com.gmail.cjmiller721.ServerPack.Teleport;

public class Request {
	
	public enum Type {TPA, TPAHERE};
	
	public String fromUsername;
	public String toUsername;
	public long sendTime;
	public Type type;
	
	public Request(String from, String to, Type t){
		this.fromUsername = from;
		this.toUsername = to;
		this.type = t;
		this.sendTime = System.nanoTime();
	}
}
