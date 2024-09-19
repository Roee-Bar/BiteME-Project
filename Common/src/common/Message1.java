package common;

import java.io.Serializable;

public class Message1 implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MessageType messageType;
	Object object;
	public Message1(MessageType messageType, Object object) {
		super();
		this.messageType = messageType;
		this.object = object;
	}
	public MessageType getMessageType() {
		return messageType;
	}
	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	
	
}
