package xyz.youngbin.mailtracker.data;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by youngbin on 16. 2. 11.
 */
public class MailModel extends RealmObject {

    String Name;
    String Receiver;
    String Sender;
    String Url;

    String[] Status;
    String[] Time;

    @Required
    String Carrier;
    String Nat;
    String Number;

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

    public void setStatus(String[] status) {
        Status = status;
    }

    public void setTime(String[] time) {
        Time = time;
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

    public String[] getStatus() {
        return Status;
    }

    public String[] getTime() {
        return Time;
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
