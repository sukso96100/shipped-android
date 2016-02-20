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
        this.Name = name;
    }

    public void setReceiver(String receiver) {
        this.Receiver = receiver;
    }

    public void setSender(String sender) {
        this.Sender = sender;
    }

    public void setUrl(String url) {
        this.Url = url;
    }

    public void setStatusArray(String statusArray) {
        this.StatusArray = statusArray;
    }

    public void setTimeArray(String timeArray) {
        this.TimeArray = timeArray;
    }

    public void setCarrierVal(String carrierVal) {
        this.CarrierVal = carrierVal;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public void setNumber(String number) {
        this.Number = number;
    }

    public void setOrigin(String origin) {
        this.Origin = origin;
    }

    public void setDestination(String destination) {
        this.Destination = destination;
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
    
    public String getOrigin() {
        return Origin;
    }

    public String getDestination() {
        return Destination;
    }


}
