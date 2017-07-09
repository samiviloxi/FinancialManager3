package orm;

import javafx.util.converter.BigDecimalStringConverter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;
import javax.swing.text.html.parser.Parser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by Uliana on 06.12.2016.
 */
public class AddEditAccount extends JDialog implements ActionListener {
    private  int userID;
    private int operID;
    private Color color;
    private Box fieldsPanel;
    private JTextField numberTF;
    private JTextArea descrTA;
    private JFormattedTextField  balanceTA;

    private JPanel buttonPanel;
    private JButton cancelButton;
    private JButton okButton;
    private  Account account;


    public AddEditAccount(Frame owner, String title, boolean modal, int userID) {
        super(owner, title, modal);
        this.userID=userID;
        operID=0;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(200, 200);
        JPanel panel=new JPanel(new BorderLayout(5, 5));
        fieldsPanel=Box.createVerticalBox();
        numberTF=new JTextField(20);
        numberTF.setBorder(new TitledBorder("Номер счета"));
        fieldsPanel.add(numberTF);
        fieldsPanel.add(Box.createHorizontalStrut(5));

        NumberFormat balanceFormat =NumberFormat.getNumberInstance();
        balanceFormat.setMaximumFractionDigits(2);
        balanceFormat.setMinimumFractionDigits(2);
        balanceFormat.setMaximumIntegerDigits(15);
        balanceFormat.setMinimumIntegerDigits(1);
        balanceTA =new JFormattedTextField(balanceFormat);
        balanceTA .setColumns(15);
        fieldsPanel.revalidate();
        balanceTA.setBorder(new TitledBorder("Сумма"));
        fieldsPanel.add(balanceTA);
        fieldsPanel.add(Box.createHorizontalStrut(5));
        descrTA=new JTextArea(3, 20);
        descrTA.setLineWrap(true);
        descrTA.setWrapStyleWord(true);
        descrTA.setBorder(new TitledBorder("Описание счета"));
        fieldsPanel.add(descrTA);
        panel.add(fieldsPanel, BorderLayout.CENTER);

        buttonPanel=new JPanel((new FlowLayout(FlowLayout.RIGHT)));
        cancelButton=new JButton("Отмена");
        cancelButton.setActionCommand("cancel");
        cancelButton.addActionListener(this);
        buttonPanel.add(cancelButton);
        okButton=new JButton("Сохранить");
        okButton.setActionCommand("save");
        okButton.addActionListener(this);
        buttonPanel.add(okButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);
        color=numberTF.getBackground();

    }

public AddEditAccount(Frame owner, String title, boolean modal, int userID, Account account) {
    this(owner, title, modal, userID);
    this.account=account;
    operID=1;
    numberTF.setText(account.getaNumber());
    descrTA.setText(account.getaDescription());
 //
     balanceTA.setValue(account.getaBalance().setScale(2, BigDecimal.ROUND_HALF_EVEN));


}




    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {

            case "cancel": {
                this.dispose();
                break;
            }
            case "save": {
                if (checkFields()) {
                    if (account != null) {
                        account.setaNumber(numberTF.getText());
                        account.setaDescription(descrTA.getText());
                       account.setaBalance(new BigDecimal(balanceTA.getValue().toString()).setScale(2, BigDecimal.ROUND_HALF_EVEN));

                    } else {
                       // BigDecimal ex= new BigDecimal(balanceTA.getValue().toString()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                        account = new Account(numberTF.getText(), userID, descrTA.getText(), new ArrayList<Record>());

                    }
                    AccountService.getInstance().addOrUpdateAccount(account, userID, operID);
                    AccountList.getInstance().notifyObservers();
                    this.dispose();
                }
                break;
            }

            }
        }

    private boolean checkFields() {
        int count=0;
        if(checkComponent(numberTF)) {
            count++;
        }
        if(checkComponent(descrTA)) {
            count++;
        }
        if(count!=0) return false;
        return true;
    }

    private boolean checkComponent(JTextComponent textComponent) {
        if(textComponent.getText().trim().isEmpty()) {
            backLightComponent(textComponent);
            return true;
        }
        else {
            textComponent.setBackground(color);
        }
        return false;
    }

    private void backLightComponent(JTextComponent component) {
        component.setBackground(Color.ORANGE);
    }
}
