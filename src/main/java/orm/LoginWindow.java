package orm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Created by Uliana on 01.08.2016.
 */
public class LoginWindow extends JFrame {
    //private static Logger logger = LoggerFactory.getLogger(LoginJFrame.class);
    private int loginId;

    public LoginWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constrains = new GridBagConstraints();
        setTitle("Форма входа");
        setLayout(layout);
        setBounds(200, 200, 350, 350);
        setResizable(true);
        JLabel loginLabel = new JLabel("логин");
        loginLabel.setSize(100, 20);
        constrains.gridx = 0;
        constrains.gridy = 0;
        add(loginLabel, constrains);
        final JTextField loginField = new JTextField();
        loginField.setHorizontalAlignment(SwingConstants.CENTER);
        constrains.gridx=1;
        constrains.fill=GridBagConstraints.HORIZONTAL;
        add(loginField, constrains);
        JLabel passwordLabel=new JLabel("Пароль");
        passwordLabel.setSize(100, 20);
        constrains.gridx = 0;
        constrains.gridy = 1;
        add(passwordLabel, constrains);

        final JPasswordField passwordFiled=new JPasswordField();
        passwordFiled.setHorizontalAlignment(SwingConstants.CENTER);
        passwordFiled.addActionListener(e->authorization(loginField.getText(), passwordFiled.getText()));
        constrains.gridx=1;
        add(passwordFiled, constrains);

        JButton inputButton=new JButton("Вход");
        constrains.gridx=0;
        constrains.gridy=2;
        inputButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                authorization(loginField.getText(), passwordFiled.getText());
            }
        });
        add(inputButton, constrains);

        JButton canselButton=new JButton("Отмена");
        constrains.gridx=1;
       canselButton.addActionListener(e->System.exit(3));
add(canselButton, constrains);

        JButton regisButton=new JButton("Регистрация");
        constrains.gridx=0;
        constrains.gridy=3;
        constrains.gridwidth=2;
        regisButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RegistrationWindow();
               //this.dispose
            }
        });

add(regisButton, constrains);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try{
                    UserDaoFactory.getVariable().closeConnection();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    //logger.error("Error on window close, detail: "+ex.getMessage());

                }
            }
        });
        setVisible(true);
    }


    public void authorization (String login, String password) {
        try{
            Connection conn=UserDaoFactory.getVariable().getConnection();
            this.loginId=SignInOn.getInstance().authorisation(login, password);
            if(this.loginId!=0) {
                new FinManagerWindow(login, loginId);
                this.dispose();
            } else{
                JOptionPane.showMessageDialog(getContentPane(), "Данный логин или пароль не существуют", "Authentication Error", JOptionPane.ERROR_MESSAGE);
            }
            UserDaoFactory.getVariable().closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
            //logger.error("Error on window close, detail: "+e.getMessage());

        }
    }
}