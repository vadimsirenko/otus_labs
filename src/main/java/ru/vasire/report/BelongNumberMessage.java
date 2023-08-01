package ru.vasire.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BelongNumberMessage implements Comparable<BelongNumberMessage>, Serializable {
    @JsonProperty("chat_identifier")
    private String chatIdentifier;
    @JsonProperty("last")
    @JacksonXmlElementWrapper(localName = "lasts")
    @JacksonXmlProperty(localName = "last")
    private List<String> membersLast;
    @JsonProperty("send_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss", timezone = "Europe/Moscow")
    private Date sendDate;
    @JsonProperty("text")
    private String text;

    @Override
    public String toString() {
        return "BelongNumberMessage = {" +
                "chatIdentifier=" + chatIdentifier +
                ", members=" + Arrays.toString(membersLast.toArray()) +
                ", sendDate=" + sendDate +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public int compareTo(BelongNumberMessage otherMessage) {
        return sendDate.compareTo(otherMessage.sendDate);
    }
}
