package ru.vasire.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;


@Getter
public class Sms {
    @JsonProperty("chat_sessions")
    private List<ChatSession> chatSessions;

    @Override
    public String toString() {
        return "Sms{" +
                "chatSessions=" + Arrays.toString(chatSessions.toArray()) +
                '}';
    }
}
