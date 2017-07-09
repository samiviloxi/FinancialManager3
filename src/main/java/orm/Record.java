package orm;

import javafx.util.converter.LocalDateTimeStringConverter;

import javax.print.attribute.DateTimeSyntax;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by Uliana on 13.07.2016.
 */
public class Record {
    private int rId;
  // private int rNumber;
    private int accountID;
    //private int userID;
  private boolean incomming;

   private BigDecimal sum;
   private String rDescription;

   private Category rCategory;
    private long dateTime;


    public Record() {


    }
    public Record( int rId,  boolean incomming,int accountID, long dateTime,  BigDecimal sum,  String rDescription,  Category rCategory) {
       this(accountID, incomming, sum ,rCategory, dateTime, rDescription );
        this.rId=rId;

    }

    public Record(int accountID, boolean incomming, BigDecimal sum,    Category category, long dateTime, String rDescription) {
        this.rId=-1;
       this.accountID=accountID;
        this.incomming=incomming;
        this.rDescription=rDescription;
        this.sum=sum;
        this.incomming=incomming;

this.dateTime=dateTime;

        this.rCategory=category;
    }



    public boolean isIncomming() {
        return incomming;
    }



    public BigDecimal getSum() {
        return  sum;
    }

    public String getrDescription() {
        return rDescription;
    }

    public int getrId() {
        return rId;
    }

    public Category getrCategory() {
        return rCategory;
    }

    public long getDateTime() {
        return dateTime;
    }

    public int getAccountID() {
        return accountID;
    }

    // @Override
    //public int getuserID() {
    //    return userID;
   // }

  /*  public void setrNumber(int rNumber) {
        this.rNumber = rNumber;
    }*/

    public void setIncomming(boolean incomming) {
        this.incomming = incomming;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }


    public void setrDescription(String rDescription) {
        this.rDescription = rDescription;
    }


   /* @Override
    public void setuserID(int userID) {
        this.userID = userID;
    }*/

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public void setrCategory(Category rCategory) {
        this.rCategory = rCategory;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public void setrId(int rId) {
        this.rId = rId;
    }

    @Override
    public String toString() {
        return "Record{" +
                "rId=" + rId +
                ", accountID=" + accountID +
                ", incomming=" + incomming +
                ", sum=" + sum +
                ", rDescription='" + rDescription + '\'' +
                ", rCategory=" + rCategory +
                ", dateTime=" + dateTime +
                '}';
    }
}
