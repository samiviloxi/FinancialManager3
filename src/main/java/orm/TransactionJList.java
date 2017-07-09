package orm;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Uliana on 06.12.2016.
 */
public class TransactionJList  extends JList implements IObserver{

    private JFrame frame;
    List<Record> transactions;
    AccountJList accountJList;

    /* Constractor creats SWING object of this class with Jlist behaviour */

    public TransactionJList(final JFrame frame, final AccountJList accountJList) {
        super();
        this.frame=frame;
        this.accountJList=accountJList;
        setCellRenderer(new ListTransactionRender());
        JPopupMenu popupMenu=new JPopupMenu("Транзакция");
        JMenuItem add=new JMenuItem("Добавить");
        add.addActionListener(e-> new AddEditTransaction(frame, "Добавление транзакции" , true, (Account) accountJList.getSelectedValue()).setVisible(true));
        JMenuItem edit=new JMenuItem("Редактировать");
        edit.addActionListener(e-> { Record transaction =null;
        if(! isSelectionEmpty()) {
            transaction=(Record) getSelectedValue();
        } else {
                setSelectedIndex(0);
                transaction=(Record) getSelectedValue();
            }
            new AddEditTransaction(frame, "Редактировать транзакции", true,
                    (Account) accountJList.getSelectedValue(), transaction).setVisible(true);
        });
        JMenuItem delete=new JMenuItem("Удалить");
        delete.addActionListener(e -> remove(getSelectedIndex()));
        popupMenu.add(add);
        popupMenu.add(edit);
        popupMenu.add(delete);
        setComponentPopupMenu(popupMenu);



    }

    /* this method checks if ther are no items selected in Jlist, then we select
    first and then ask user by using JOptionPane if he wnats to delete this item, we delete it

     */

    public void remove(int index) {

        if(isSelectionEmpty()) {
            setSelectedIndex(0);
        }
        Record transaction=(Record) getSelectedValue();
        Date date=new Date(transaction.getDateTime());
        SimpleDateFormat currentDate=new SimpleDateFormat("dd.mm.yyyy");
        int answer=JOptionPane.showConfirmDialog(this, "Вы уверены, что хотите удалить транзакцию на сумму " + transaction.getSum().setScale(2, BigDecimal.ROUND_HALF_EVEN)+" "+
        "от "+ currentDate.format(date)+ "?", "Подтверждение удаление элемента", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(answer==0) {
            TransactionService.getInstance().remove(transaction);
        }



    }
    public void handleEvent() { TransactionService.getInstance().updateList(this, accountJList);

    }
}
