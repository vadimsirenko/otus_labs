package ru.vasire.sms;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


@Getter
public class ChatSession {
    @JsonProperty("chat_id")
    private int chatId;
    @JsonProperty("chat_identifier")
    private String chatIdentifier;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("is_deleted")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private boolean isDeleted;
    @JsonProperty("members")
    private List<Member> members;
    @JsonProperty("messages")
    private List<Message> messages;

    @Override
    public String toString() {
        return "ChatSession{" +
                "chatId=" + chatId +
                ", chatIdentifier='" + chatIdentifier + '\'' +
                ", displayName='" + displayName + '\'' +
                ", isDeleted=" + isDeleted +
                ", members=" + Arrays.toString(members.toArray()) +
                ", messages=" + Arrays.toString(messages.toArray()) +
                '}';
    }
}
