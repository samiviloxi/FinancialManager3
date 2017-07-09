package orm;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Uliana on 06.12.2016.
 */
public class ListTransactionRender extends JTextPane implements ListCellRenderer<Record> {


    public Component getListCellRendererComponent(JList<? extends Record> list, Record value, int index, boolean isSelected, boolean cellHasFocus) {
        StringBuilder sb=new StringBuilder();
        Date date=new Date(value.getDateTime());
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd.MM.yyyy");
        sb.append("<html><head></head><body><b>Сумма:" + String.valueOf(value.getSum().setScale(2, BigDecimal.ROUND_HALF_EVEN))
       +"</b><br><i>операция: </i>"+ (value.isIncomming()?"Поподнение":"Снятие") + "<br>" +
        "<i>дата: </i>" + dateFormat.format(date) + "<i>категория: </i>" + value.getrCategory().getName()+ "</body></html>");
        setContentType("text/html");
        setEditable(false);
        setText(sb.toString());
        setBorder(new LineBorder(Color.BLACK,1));
        if(isSelected) {
            setBackground(Color.LIGHT_GRAY);

        }
        else {
            setBackground(Color.WHITE);
        }

        return this;
    }
}
