package jw.data.repositories;

import jw.data.models.Entity;
import jw.gui.examples.chestgui.ListGUI;
import jw.gui.button.Button;
import jw.gui.core.InventoryGUI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class RepositoryGUI<T extends Entity> extends ListGUI<T>
{
    private Repository<T> repositoryGUI;

    public RepositoryGUI(InventoryGUI parent,String name, Repository<T> repositoryGUI)
    {
        super(parent, name, 6);
        this.repositoryGUI = repositoryGUI;
    }

    @Override
    public void onInitialize()
    {
        this.onDelete = (player,button) ->
        {
           T dataModel = button.getHoldingObject();
           repositoryGUI.deleteOne(dataModel.id,dataModel);
           this.open(player);
        };


    }

    @Override
    public void onOpen(Player player)
    {
        this.addButtons(MapEntityToButtons(repositoryGUI.getMany(null)));
    }

    private List<Button> MapEntityToButtons(List<T> entityList)
    {
        return entityList.stream().map(this::mapDataToButton).collect(Collectors.toList());
    }

    public Button mapDataToButton(T data)
    {
        Button button = new Button(data.icon,data.name,data.description);
        button.setObjectHolder(data);
        button.setDescription(ChatColor.GREEN+" "+ChatColor.BOLD+"[ Click to show details ]");
        return button;
    }
}
