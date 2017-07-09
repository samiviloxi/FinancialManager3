package orm;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by Uliana on 02.12.2016.
 */
public class AccountJList  extends JList implements IObserver{

    private int userID;
    private JFrame frame;
    List<Account> accounts;
    private int index;

    //Constractor to creat Swing Object of this class with JList behaviour

    public AccountJList(JFrame frame, int userID) {
        super();
        this.userID=userID;
        this.frame=frame;
        setCellRenderer(new ListAccountRender());
        JPopupMenu popupMenu=new JPopupMenu("Счет");
        JMenuItem add=new JMenuItem("Добавить");
        add.addActionListener(e -> new AddEditAccount(frame, "Добавление счета", true, userID).setVisible(true));
        JMenuItem edit=new JMenuItem("Редактировать");
        edit.addActionListener(e-> {
            Account account=getSelectedAccount();
            new AddEditAccount(frame, "Редактирование счета", true, userID, account).setVisible(true);
        });

        JMenuItem delete=new JMenuItem("Удалить");
        delete.addActionListener(e-> remove(getSelectedIndex()));
        popupMenu.add(add);
        popupMenu.add(edit);
        popupMenu.add(delete);
        setComponentPopupMenu(popupMenu);

    }
/* This method checks if there are no items selected in JList ,
we select first, then we ask user by using JOptionPane
of he wants to delete this item, we delete it
 */
public void remove(int index) {
    Account account=getSelectedAccount();
    int answer=JOptionPane.showConfirmDialog(this, "Вы уверены, что хотите удалить счет № " + account.getaNumber() + "\n" ,
    "Подтвердите удаление счета", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

    if(answer==0) {
        AccountService.getInstance().remove(account, userID);
        handleEvent();
    }
}

    public Account getSelectedAccount() {
        if(isSelectionEmpty()) {
            setSelectedIndex(0);
        }
        return (Account) getSelectedValue();
    }

    public void setIndex() {
        index=getSelectedIndex();
    }
    public void handleEvent() {
        AccountService.getInstance().updateList(this, userID);

    }
}
