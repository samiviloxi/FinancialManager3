package orm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by Uliana on 01.08.2016.
 */
public class FinManagerWindow extends JFrame {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 400;
    //private static Logger logger = LoggerFactory.getLogger(FinManagerFrame.class);
    private String login;
    private int userID;
    private JLabel totalJLabel;
    private AccountJList accountJList;
    private TransactionJList recordJList;

    public FinManagerWindow(String login, int userID) {
        this.login = login;
        this.userID = userID;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setBounds(200, 200, getMinimumSize().width, getMinimumSize().height);

        setResizable(true);
        setTitle(" Финансовый помошник: " + this.login);
        setLayout(new BorderLayout(5, 5));

        addMenu();
        addAccountPanel();
        addTransactionPanel();

        addingData();
        addNorthPanel();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                super.windowActivated(e);
                updateNorthPanel();
            }
        });
        setVisible(true);


    }

    private void updateNorthPanel() {
        BigDecimal total = TotalAmount.getInstance().getTotalAmount(userID);
        totalJLabel.setText("Общая сумма на всех Ваших счетах составляет " + total.toPlainString() + " рублей");
    }

    private void addNorthPanel() {
        totalJLabel=new JLabel();
        updateNorthPanel();
        add(totalJLabel, BorderLayout.NORTH);
    }

  private void addMenu() {
      JMenuBar menuBar=new JMenuBar();
      JMenu menu=new JMenu("Финансы");
      JMenuItem changeLoginMenu=new JMenuItem("Сменить пользователя");
      changeLoginMenu.addActionListener(e -> {
new LoginWindow();
          this.dispose();
      });

      menu.add(changeLoginMenu);

      JMenuItem registerUserMenu=new JMenuItem("Зарегистрировать нового\n пользователя");
      registerUserMenu.addActionListener(e-> {
          new RegistrationWindow();
          this.dispose();
      });
      menu.add(registerUserMenu);

      JMenuItem exitMenu=new JMenuItem("Выход");
      exitMenu.addActionListener(e -> System.exit(-1));
      menu.add(new JSeparator());
      menu.add(exitMenu);
      menuBar.add(menu);
  }

    private  void addingData() {
        AccountList.getInstance().notifyObservers();
        TransactionList.getInstance().notifyObservers();
    }

    //adding panel with user's accounts

    private void addAccountPanel() {
        JPanel accountPanel=new JPanel(new BorderLayout());

        //buttons

        JPanel buttonAccountPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addAccountButton=new JButton("+");
        addAccountButton.addActionListener(e->new AddEditAccount(this, "Добавление счета", true, userID).setVisible(true));
        buttonAccountPanel.add(addAccountButton);
        JButton removeAccountButton=new JButton("-");
        removeAccountButton.addActionListener(e-> {
            if(accountJList.isSelectionEmpty()) {
                accountJList.setSelectedIndex(0);
            }
            accountJList.remove(accountJList.getSelectedIndex());
        });
        buttonAccountPanel.add(removeAccountButton);
        accountPanel.add(buttonAccountPanel, BorderLayout.NORTH);
        //listbox with accounts

        accountJList=new AccountJList(this, userID);
        accountJList.addListSelectionListener(e->{
            accountJList.setIndex();
            TransactionList.getInstance().notifyObservers();
        });
        AccountList.getInstance().addObserver(accountJList);
        JScrollPane jScrollPane=new JScrollPane(accountJList);
        accountPanel.add(jScrollPane, BorderLayout.CENTER);

        //adding panel to the form
        accountPanel.setPreferredSize(new Dimension(WIDTH/3, HEIGHT));
        add(accountPanel, BorderLayout.WEST);
    }

    private  void addTransactionPanel(){
        JPanel transactionPanel=new JPanel(new BorderLayout());
        //buttons
        JPanel buttonTransactionPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addTransactionButton=new JButton("+");
        addTransactionButton.addActionListener(e-> new AddEditTransaction(this, "Добавление транзакции", true, accountJList.getSelectedAccount()).setVisible(true));
        buttonTransactionPanel.add(addTransactionButton);
        JButton removeTransactionButton=new JButton("-");
        removeTransactionButton.addActionListener(e-> {
            if(recordJList.isSelectionEmpty()){
                recordJList.setSelectedIndex(0);
            }
            recordJList.remove(recordJList.getSelectedIndex());
        });

        buttonTransactionPanel.add(removeTransactionButton);
        transactionPanel.add(buttonTransactionPanel, BorderLayout.NORTH);

        //listbox with accounts

        recordJList=new TransactionJList(this, accountJList);
        TransactionList.getInstance().addObserver(recordJList);
        JScrollPane jScrollPane=new JScrollPane(recordJList);
        transactionPanel.add(jScrollPane, BorderLayout.CENTER);

        //adding panel to form

        add(transactionPanel, BorderLayout.CENTER);
    }
}



