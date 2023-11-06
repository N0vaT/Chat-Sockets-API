package edu.school21.sockets.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private long messageId;
    private String messageText;
    private LocalDateTime messageTime;
    private User messageSender;

    public Message() {
    }

    public Message(String messageText, LocalDateTime messageTime, User messageSender) {
        this.messageText = messageText;
        this.messageTime = messageTime;
        this.messageSender = messageSender;
    }

    public Message(long messageId, String messageText, LocalDateTime messageTime, User messageSender) {
        this.messageId = messageId;
        this.messageText = messageText;
        this.messageTime = messageTime;
        this.messageSender = messageSender;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public LocalDateTime getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(LocalDateTime messageTime) {
        this.messageTime = messageTime;
    }

    public User getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(User messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return messageId == message.messageId && Objects.equals(messageText, message.messageText) && Objects.equals(messageTime, message.messageTime) && Objects.equals(messageSender, message.messageSender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, messageText, messageTime, messageSender);
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", messageText='" + messageText + '\'' +
                ", messageTime=" + messageTime +
                ", messageSender=" + messageSender.getUserName() +
                '}';
    }
}
