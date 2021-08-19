package jw.data.repositories;

import jw.data.models.Entity;
import jw.gui.button.ButtonActionsEnum;
import jw.gui.examples.chestgui.ListGUI;
import jw.gui.button.Button;
import jw.gui.core.InventoryGUI;
import jw.gui.examples.chestgui.utilites.ButtonMapper;
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
      this.addButtons(repository.getMany(),this::mapDataToButton);
    }
    public Repository<T> getRepository()
    {
        return  repository;
    }
    public void mapDataToButton(T data,Button button)
    {
        button.setMaterial(data.icon);
        button.setName(data.name);
        button.setObjectHolder(data);
        button.setAction(ButtonActionsEnum.CLICK);
        button.setDescription(ChatColor.GREEN+" "+ChatColor.BOLD+"[ Click to show details ]");
    }
}
