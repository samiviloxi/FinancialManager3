package orm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Uliana on 01.08.2016.
 */
public class RegistrationWindow extends JFrame {
    //private static Logger logger = LoggerFactory.getLogger(RegistrationForm.class);
    private JTextField loginField;
    private JTextField nameField;
    private JPasswordField passwordField;
    private int loginId;
    public RegistrationWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        GridBagLayout layout=new GridBagLayout();
        GridBagConstraints constraints=new GridBagConstraints();
        setTitle("Окно регистрации");
        setLayout(layout);
        setBounds(200, 200, 500, 500);
        setResizable(true);
        JLabel newAccountLabel=new JLabel("Введите данные нового пользователя");
        constraints.gridx=0;
        constraints.gridy=0;
        constraints.gridwidth=2;
        add(newAccountLabel, constraints);

        constraints.fill=GridBagConstraints.HORIZONTAL;

        JLabel loginLabel=new JLabel("Логин");
        constraints.gridx=0;
        constraints.gridy=2;
        constraints.gridwidth=1;
        add(loginLabel, constraints);

        JLabel passwordLabel=new JLabel("Пароль");
        constraints.gridx=0;
        constraints.gridy=3;
        constraints.gridwidth=1;
        add(passwordLabel, constraints);

        loginField=new JTextField();
        constraints.gridx=1;
        constraints.gridy=2;
        add(loginField, constraints);

        passwordField=new JPasswordField();
        constraints.gridx=1;
        constraints.gridy=3;
        add(passwordField, constraints);
    JLabel nameLabel=new JLabel("Имя");
        constraints.gridx=0;
        constraints.gridy=4;
        constraints.gridwidth=1;
        add(nameLabel, constraints);
        nameField=new JTextField();
        constraints.gridx=1;
        constraints.gridy=4;
        add(nameField, constraints);
        JButton inputButton=new JButton("Создать новую учетную запись и войти в программу");
        constraints.gridx=0;
        constraints.gridy=5;
        constraints.gridwidth=1;
        inputButton.addActionListener(e-> {registration();
        });

        add(inputButton, constraints);

        JButton cancelButton=new JButton("Отмена");
        constraints.gridx=1;
        constraints.gridy=5;
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(-2);
            }
        });
        add(cancelButton, constraints);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    UserDaoFactory.getVariable().closeConnection();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    //logger.error("Error on window close, detail: "+e1.getMessage());
                }
            }
        });
        setVisible(true);

    }

    public  void registration() {

           // Connection conn=UserDaoFactory.getVariable().getConnection();
        try {
            this.loginId = SignInOn.getInstance().registration(loginField.getText(), new String(passwordField.getPassword()), nameField.getText());
        }catch (Exception e) {
            System.out.println("Error - "+e.getLocalizedMessage());
            return;
        }
            if(this.loginId!=0) {
                new FinManagerWindow(loginField.getText(), loginId);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(getContentPane(), "Данный логин уже используется", "Registration error", JOptionPane.ERROR_MESSAGE);
            }
       // } catch (SQLException e) {
         //   e.printStackTrace();
            //logger.error("Error in method registration, detail: "+e.getMessage());
       /* } finally {
            try{
                UserDaoFactory.getVariable().closeConnection();
            }catch (SQLException e) {
                e.printStackTrace();
                //logger.error("Error at finalt part in method registration, detail: "+e.getMessage());
            }*/
      //  }
    }
}
