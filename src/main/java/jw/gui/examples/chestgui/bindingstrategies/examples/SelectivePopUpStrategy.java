package jw.gui.examples.chestgui.bindingstrategies.examples;

import jw.gui.button.Button;
import jw.gui.examples.ChestGUI;
import jw.gui.examples.chestgui.bindingstrategies.BindingStrategy;
import jw.utilites.Emoticons;
import jw.utilites.binding.BindingField;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SelectivePopUpStrategy  extends BindingStrategy<Integer>
{
    private  String[] options;

    public SelectivePopUpStrategy(BindingField<Integer> bindingField, String ... options)
    {
        super(bindingField);
        this.options = options;
    }
    @Override
    protected void onClick(Player player, Button button, BindingStrategy<Integer> bindingStrategy, Integer currentIndex)
    {
      setValue((currentIndex+1)%options.length);
    }

    @Override
    protected void onValueChanged(ChestGUI inventoryGUI, Button button, Integer newIndex)
    {
       String[] description = new String[options.length];
       for(int i=0;i<options.length;i++)
       {
           if(i ==newIndex)
           {
               description[i] = ChatColor.WHITE+""+ChatColor.BOLD+Emoticons.arrowright +" "+options[i];
           }
           else
           {
              description[i] = ChatColor.GRAY+"- "+options[i];
           }
       }
       button.setDescription(description);
    }
}
