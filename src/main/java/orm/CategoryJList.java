package orm;

import javax.swing.*;

/**
 * Created by Uliana on 15.12.2016.
 */
public class CategoryJList extends JList implements IObserver{

public CategoryJList(){super();}


    @Override
    public void handleEvent() {
        CategoryService.getInstance().updateList(this);
    }

    public void remove(int index) {
        Category category=(Category) getSelectedValue();
        int answer=JOptionPane.showConfirmDialog(this, "Вы уверены, что хотите удалить категорию\""+category.getName() + "\"",
                "Подтвердите удаление элеиента", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE );
    if(answer==0) {
        CategoryService.getInstance().remove( this, category);
    }

    }
}
