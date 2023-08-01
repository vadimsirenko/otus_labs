package ru.vasire.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Arrays;


@Setter
@Getter
public class BelongNumber implements Comparable<BelongNumber>, Serializable {

    @JsonProperty("belong_number")
    private String belongNumber;
    @JsonProperty("belongNumberMessages")
    @JacksonXmlElementWrapper(localName = "messages")
    @JacksonXmlProperty(localName = "message")
    private BelongNumberMessages belongNumberMessages = new BelongNumberMessages();

    @Override
    public int compareTo(BelongNumber o) {
        return belongNumber.compareTo(o.belongNumber);
    }

    @Override
    public String toString() {
        return "BelongNumber{" +
                "belongNumber='" + belongNumber + '\'' +
                ", belongNumberMessages=" + belongNumberMessages +
                '}';
    }
}
