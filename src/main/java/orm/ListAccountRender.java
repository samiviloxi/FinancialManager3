package orm;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.math.BigDecimal;

/**
 * Created by Uliana on 02.12.2016.
 */
public class ListAccountRender extends JTextPane implements ListCellRenderer<Account> {
    public Component getListCellRendererComponent(JList<? extends Account> list, Account value, int index, boolean isSelected, boolean cellHasFocus) {
        StringBuilder sb=new StringBuilder();
        TotalAmount result=new TotalAmount();
        Record record=new Record();
      //  isSelected=record.isIncomming();
       /* sb.append("<html><head></head><body><b>№" + value.getaNumber()+"</b><br><i>баланс:</i>"
        +String.valueOf(value.getaBalance().setScale(2, BigDecimal.ROUND_HALF_EVEN)) + "<br>"
             +"<i>описание:</i>" +value.getaDescription()+"<br>"+
                "<i>транзакции:</i>"+value.getRecordList().size() + "</body></html>") ; */
        sb.append("<html><head></head><body><b>№" + value.getaNumber()+"</b><br><i>баланс:</i>"
                +result.getAccountAmount(value) + "<br>"
                +"<i>описание:</i>" +value.getaDescription()+"<br>"+
                "<i>транзакции:</i>"+value.getRecordList().size() + "</body></html>") ;
        setContentType("text/html");
        setEditable(false);
        setText(sb.toString());
        setBorder(new LineBorder(Color.BLACK, 1));
        if(isSelected) {
            setBackground(Color.LIGHT_GRAY);

        }
        else {setBackground(Color.WHITE); }


        return this;
    }
}
