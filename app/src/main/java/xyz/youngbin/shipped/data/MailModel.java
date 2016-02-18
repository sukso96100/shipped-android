package xyz.youngbin.shipped.data;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by youngbin on 16. 2. 11.
 */
public class MailModel extends RealmObject {

    private String Name;
    private String Receiver;
    private String Sender;
    private String Url;

    private String StatusArray;
    private String TimeArray;

    @Required
    private String Carrier;
    private String Nat;
    private String Number;

    //Setters

    public void setName(String name) {
        Name = name;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public void setStatusArray(String statusArray) {
        StatusArray = statusArray;
    }

    public void setTimeArray(String timeArray) {
        TimeArray = timeArray;
    }

    public void setCarrier(String carrier) {
        Carrier = carrier;
    }

    public void setNat(String nat) {
        Nat = nat;
    }

    public void setNumber(String number) {
        Number = number;
    }

    //Getters

    public String getName() {
        return Name;
    }

    public String getReceiver() {
        return Receiver;
    }

    public String getSender() {
        return Sender;
    }

    public String getUrl() {
        return Url;
    }

    public String getStatusArray() {
        return StatusArray;
    }

    public String getTimeArray() {
        return TimeArray;
    }

    public String getCarrier() {
        return Carrier;
    }

    public String getNat() {
        return Nat;
    }

    public String getNumber() {
        return Number;
    }

}
