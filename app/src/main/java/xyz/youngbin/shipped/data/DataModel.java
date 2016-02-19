package xyz.youngbin.shipped.data;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by youngbin on 16. 2. 11.
 */
public class DataModel extends RealmObject {

    private String Name;
    private String Receiver;
    private String Sender;
    private String Origin;
    private String Destination;
    private String Url;

    private String StatusArray;
    private String TimeArray;

    @Required
    private String CarrierVal;
    private String Type;
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

    public void setCarrierVal(String carrierVal) {
        CarrierVal = carrierVal;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getOrigin() {
        return Origin;
    }

    public String getDestination() {
        return Destination;
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

    public String getCarrierVal() {
        return CarrierVal;
    }

    public String getType() {
        return Type;
    }

    public String getNumber() {
        return Number;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }
}
