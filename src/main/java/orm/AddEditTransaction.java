package orm;

import javax.print.attribute.DateTimeSyntax;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;

/**
 * Created by Uliana on 06.12.2016.
 */
public class AddEditTransaction extends JDialog implements ActionListener {
    private Account account;
    private Record transaction;
    private int operID;

    private JRadioButton isCheckin;
    JFormattedTextField amountTF;
    private CategoryJComponent categoryJComponent;

   private MyDateTimeTicker dateTimeTF;
    private JTextArea descrTA;
    private JButton cancelButton;
    private JButton okButton;
    private  Category category;

    public AddEditTransaction(JFrame owner, String title, boolean modal, Account account) {
        super(owner, title, modal);
        this.account=account;
        operID=0;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setBounds(100, 200, 1500, 400);
        Box mainBox=Box.createHorizontalBox();

        Box firstRow=Box.createHorizontalBox();
        isCheckin=new JRadioButton("Пополнение", false);
        firstRow.add(isCheckin);
        mainBox.add(firstRow);

        Box secondRow=Box.createHorizontalBox();
        secondRow.add(new JLabel("Сумма:"));
        secondRow.add(Box.createHorizontalStrut(10));

        NumberFormat balanceFormat =NumberFormat.getNumberInstance();
        balanceFormat.setMaximumFractionDigits(2);
        balanceFormat.setMinimumFractionDigits(2);
        balanceFormat.setMaximumIntegerDigits(15);
        balanceFormat.setMinimumIntegerDigits(1);
        amountTF=new JFormattedTextField(balanceFormat);
        amountTF.setColumns(15);

        secondRow.add(amountTF);
        mainBox.add(secondRow);

        Box thirdRow=Box.createHorizontalBox();
        thirdRow.add(new JLabel("Дата:"), Box.LEFT_ALIGNMENT);

        dateTimeTF=new MyDateTimeTicker();
        thirdRow.add(dateTimeTF, Box.RIGHT_ALIGNMENT);
        mainBox.add(thirdRow);

        Box fourthRow=Box.createHorizontalBox();
        fourthRow.add(new JLabel("Категория:"));

        categoryJComponent=new CategoryJComponent(this);
        fourthRow.add(categoryJComponent);
        mainBox.add(fourthRow);
Box fifthRow=Box.createHorizontalBox();
        fifthRow.add(new JLabel("Описание"));
        descrTA=new JTextArea(3, 15);
        descrTA.setLineWrap(true);
        descrTA.setWrapStyleWord(true);
        fifthRow.add(descrTA);
        mainBox.add(fifthRow);

        Box buttonBox=Box.createHorizontalBox();
        okButton=new JButton("Сохранить");
        okButton.setActionCommand("save");
        okButton.addActionListener(this);
        buttonBox.add(okButton, Box.RIGHT_ALIGNMENT);

        cancelButton=new JButton("Отмена");
        cancelButton.setActionCommand("cancel");
        cancelButton.addActionListener(this);
       buttonBox.add(cancelButton, Box.RIGHT_ALIGNMENT);

        mainBox.add(buttonBox);
        setContentPane(mainBox);

mainBox.revalidate();



    }

    public  AddEditTransaction(JFrame owner, String title, boolean modal, Account account, Record transaction){
        this(owner, title, modal, account);
        this.transaction=transaction;
        operID=1;
        isCheckin.setSelected(transaction.isIncomming());
        amountTF.setValue(transaction.getSum().setScale(2, BigDecimal.ROUND_HALF_EVEN));
        dateTimeTF.setDate(new Date(transaction.getDateTime()));
        categoryJComponent.setCategory(transaction.getrCategory());
        descrTA.setText(transaction.getrDescription());
    }


    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "amount ": {
                break;
            }
            case "cancel": {
                this.dispose();
                break;
            }
            case "save": {
                if(category==null) {
                    category=categoryJComponent.getCategory();
                }
                if(transaction==null) {
                    transaction=new Record(account.getaId(), isCheckin.isSelected(),
                            new BigDecimal(amountTF.getValue().toString()), category, dateTimeTF.getDate().getTime(), descrTA.getText());

                }
                else {
                    transaction.setSum(new BigDecimal(amountTF.getValue().toString()).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                    transaction.setrCategory(category);
                    transaction.setIncomming(isCheckin.isSelected());
                    transaction.setrDescription(descrTA.getText());
                }

                TransactionService.getInstance().addOrUpdateTransaction(transaction, operID);
                AccountService.getInstance().addOrUpdateAccount(account, account.getUserID(), 1);

                AccountList.getInstance().notifyObservers();
                this.dispose();
                break;
            }

        }

    }
}
