package jw.data.repositories;

import jw.data.models.Entity;
import jw.gui.button.ButtonActionsEnum;
import jw.gui.examples.chestgui.ListGUI;
import jw.gui.button.Button;
import jw.gui.core.InventoryGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class RepositoryGUI<T extends Entity> extends ListGUI<T>
{
    private final Repository<T> repository;

    public RepositoryGUI(InventoryGUI parent,String name, Repository<T> repository)
    {
        super(parent, name, 6);
        this.repository = repository;
    }

    @Override
    public void onInitialize()
    {
        this.onDelete = (player,button) ->
        {
           T dataModel = button.getHoldingObject();
            repository.deleteOne(dataModel);
           this.open(player);
        };
        this.onInsert = (value) ->
        {
            try
            {
                T dataModel = repository.getEntityClass().newInstance();
                dataModel.name = value;
                repository.insertOne(dataModel);
                this.open(player);
            }
            catch (Exception e)
            {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED+this.toString()+" OnInsert ERROR "+e.getMessage());
            }
        };

    }



    @Override
    public void onOpen(Player player)
    {
        this.addButtons(mapEntityToButtons(repository.getMany(null)));
    }

    public List<Button> mapEntityToButtons(List<T> entityList)
    {
        return entityList.stream().map(this::mapDataToButton).collect(Collectors.toList());
    }
    public Repository<T> getRepository()
    {
        return  repository;
    }
    public Button mapDataToButton(T data)
    {
        Button button = new Button(data.icon,data.name,data.description);
        button.setObjectHolder(data);
        button.setAction(ButtonActionsEnum.CLICK);
        button.setDescription(ChatColor.GREEN+" "+ChatColor.BOLD+"[ Click to show details ]");
        return button;
    }
}
