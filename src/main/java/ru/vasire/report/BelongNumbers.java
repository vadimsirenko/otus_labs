package ru.vasire.report;

import ru.vasire.sms.ChatSession;
import ru.vasire.sms.Member;
import ru.vasire.sms.Message;
import ru.vasire.sms.Sms;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;


public class BelongNumbers extends HashSet<BelongNumber> implements Serializable {
    public static BelongNumbers SmsToBelongNumbers(Sms sms) {
        BelongNumbers belongNumbers = new BelongNumbers();
        BelongNumber belongNumber;

        for (ChatSession session : sms.getChatSessions()) {
            for (Message message : session.getMessages()) {

                if (!belongNumbers.containsNumber(message.getBelongNumber())) {
                    belongNumber = belongNumbers.addBelongNumber(message.getBelongNumber());
                } else {
                    belongNumber = belongNumbers.getBelongNumber(message.getBelongNumber());
                }
                BelongNumberMessage belongNumberMessage = new BelongNumberMessage(
                        session.getChatIdentifier(),
                        session.getMembers().stream().map(Member::getLast).filter(Objects::nonNull).toList(),
                        message.getSendDate(),
                        message.getText());
                belongNumber.getBelongNumberMessages().add(belongNumberMessage);
            }
        }
        return belongNumbers;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.toArray());
    }

    public boolean containsNumber(String belongNumber) {
        return this.stream().anyMatch(bn -> bn.getBelongNumber().equals(belongNumber));
    }

    public BelongNumber getBelongNumber(String belongNumber) {
        return this.stream().filter(bn -> bn.getBelongNumber().equals(belongNumber)).findFirst().orElse(null);
    }

    public BelongNumber addBelongNumber(String number) {
        BelongNumber belongNumber = new BelongNumber();
        belongNumber.setBelongNumber(number);
        this.add(belongNumber);
        return belongNumber;
    }
}
