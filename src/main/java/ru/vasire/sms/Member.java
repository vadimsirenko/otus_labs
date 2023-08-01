package ru.vasire.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
public class Member {
    @JsonProperty("first")
    private String first;
    @JsonProperty("handle_id")
    private int handleId;
    @JsonProperty("image_path")
    private String imagePath;
    @JsonProperty("last")
    private String last;
    @JsonProperty("middle")
    private String middle;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("service")
    private String service;
    @JsonProperty("thumb_path")
    private String thumb_path;

    @Override
    public String toString() {
        return "Member{" +
                "first='" + first + '\'' +
                ", handleId=" + handleId +
                ", imagePath='" + imagePath + '\'' +
                ", last='" + last + '\'' +
                ", middle='" + middle + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", service='" + service + '\'' +
                ", thumb_path='" + thumb_path + '\'' +
                '}';
    }
}
