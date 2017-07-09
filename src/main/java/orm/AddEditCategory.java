package orm;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Uliana on 15.12.2016.
 */
public class AddEditCategory extends JDialog implements ActionListener {

    private JTextArea name;
    private Category category;
    private CategoriesJDialog categoriesJDialog;
    int operID;

    public AddEditCategory(CategoriesJDialog categoriesJDialog, String title, boolean modal){
        super(categoriesJDialog, title, modal);
        this.categoriesJDialog=categoriesJDialog;
        operID=0;
        setMinimumSize(new Dimension(200, 100));
        setResizable(true);
        Box mainBox=Box.createVerticalBox();
        Box firstRox=Box.createHorizontalBox();
        firstRox.add(new JLabel("Описание"));
        firstRox.add(Box.createVerticalStrut(10));
        name=new JTextArea(3, 20);
        name.setLineWrap(true);
        name.setBorder(new LineBorder(Color.GRAY,1));
        name.setWrapStyleWord(true);
        firstRox.add(name);
        mainBox.add(firstRox);
        mainBox.add(Box.createHorizontalStrut(5));
        Box secondRow=Box.createHorizontalBox();
        JButton okButton=new JButton("Сохранить");
        okButton.setActionCommand("save");
        okButton.addActionListener(this);
        secondRow.add(okButton);
        secondRow.add(Box.createVerticalStrut(10));
        JButton cancelButton=new JButton("Отмена");
        cancelButton.setActionCommand("cancel");
        cancelButton.addActionListener(this);
        secondRow.add(cancelButton);
        mainBox.add(secondRow);
        setContentPane(mainBox);

    }
    public AddEditCategory(CategoriesJDialog categoriesJDialog, String s, boolean b, Category category) {
        this(categoriesJDialog, s, b);
        operID=1;
        name.setText(category.getName());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch ((e.getActionCommand())) {
            case "save" : {
                if(category!=null) {
                    category.setName(name.getText());
                } else {
                    category=new Category(name.getText());
                }
                CategoryService.getInstance().addOrUpdateCategory(category, operID);
                CategoryList.getInstance().notifyObservers();
                this.dispose();
                break;
            }
            case "cancel" : {
                this.dispose();
                break;
            }
        }
    }
}
