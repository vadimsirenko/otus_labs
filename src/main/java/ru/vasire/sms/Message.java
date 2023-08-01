package ru.vasire.sms;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

@Getter
public class Message {
    @JsonProperty("ROWID")
    private int rowId;
    @JsonProperty("attributedBody")
    private String attributedBody;
    @JsonProperty("belong_number")
    private String belongNumber;
    @JsonProperty("date")
    private BigInteger date;
    @JsonProperty("date_read")
    private BigInteger dateRead;
    @JsonProperty("guid")
    private UUID guid;
    @JsonProperty("handle_id")
    private int handleId;
    @JsonProperty("has_dd_results")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private boolean hasDdRresults;
    @JsonProperty("first")
    private int first;
    @JsonProperty("is_deleted")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private boolean isDeleted;
    @JsonProperty("is_from_me")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private boolean isFromMe;
    @JsonProperty("send_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss", timezone = "Europe/Moscow")
    private Date sendDate; //"03-31-2023 14:09:19"
    @JsonProperty("send_status")
    private int sendStatus;
    @JsonProperty("service")
    private String service;
    @JsonProperty("text")
    private String text;

    @Override
    public String toString() {
        return "BelongNumberMessage{" +
                "rowId=" + rowId +
                ", attributedBody='" + attributedBody + '\'' +
                ", belongNumber='" + belongNumber + '\'' +
                ", date=" + date +
                ", dateRead=" + dateRead +
                ", guid=" + guid +
                ", handleId=" + handleId +
                ", hasDdRresults=" + hasDdRresults +
                ", first=" + first +
                ", isDeleted=" + isDeleted +
                ", isFromMe=" + isFromMe +
                ", sendDate=" + sendDate +
                ", sendStatus=" + sendStatus +
                ", service='" + service + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
