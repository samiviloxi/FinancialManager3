package orm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Uliana on 08.07.2016.
 */
public class  Account <T> extends User {
    private int aId;
    private  String aDescription;
    private  String aNumber;
    private BigDecimal aBalance=new BigDecimal(0);
    private  int userID;
    private List <Record> recordList;
    Record record;


    public Account(String aNumber, int userID, String aDescription,  List<Record> records)
    {
        this.aNumber=aNumber;
       //
        this.aId=-1;
        this.aDescription=aDescription;
        this.userID=userID;
        this.recordList=records;
        for(Record record: records) {
            if(record.isIncomming()) {
                aBalance=aBalance.add(record.getSum());

            } else {
                aBalance=aBalance.subtract(record.getSum());
            }
        }

    }

    public Account(int accountAmount, String login, String uName, String password, int id, String aDescription,int aId, String aNumber, BigDecimal aBalance, List<Record> record) {
        super(accountAmount, login, uName, password, id);
        this.aDescription = aDescription;
        this.aId=aId;
        this.aNumber = aNumber;
        this.aBalance = aBalance;
        this.userID=userID;



    }

    public Account(int aId, String accountnumber, int userID, String description, List<Record> records) {
        super();
        this.aId=aId;
        this.aNumber=accountnumber;
        this.aDescription=description;
        this.userID=userID;

        this.recordList=records;

    }


    public String getaDescription() {
        return aDescription;
    }

    public String getaNumber() {
        return aNumber;
    }

    public BigDecimal getaBalance() {

                    return aBalance;
    }

    public int getUserID() {
        return userID;
    }

    public List<Record> getRecordList() {
        return recordList;
    }

    public int getaId() {
        return aId;
    }

    public int getuserID() {
        return userID;
    }


    public void setaDescription(String aDescription) {
        this.aDescription = aDescription;
    }

    public void setaNumber(String aNumber) {
        this.aNumber = aNumber;
    }

    public void setaBalance(BigDecimal aBalance) {
        this.aBalance = aBalance;
    }

    public void setaId(int aId) {
        this.aId = aId;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setRecordList(List<Record> recordList) {
        this.recordList = recordList;
    }

    public void setuserID(int userID) {
        this.userID = userID;
    }



}
