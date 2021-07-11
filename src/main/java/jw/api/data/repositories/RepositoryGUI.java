package jw.api.data.repositories;

import jw.api.data.models.Entity;
import jw.api.gui.assets.chestgui.ListGUI;
import jw.api.gui.button.Button;
import jw.api.gui.core.InventoryGUI;
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
    public void OnInitialize()
    {
        this.onDelete = (player,button) ->
        {
           T dataModel = button.GetHoldingObject();
           repositoryGUI.deleteOne(dataModel.id,dataModel);
           this.Open(player);
        };


    }

    @Override
    public void OnOpen(Player player)
    {
        this.AddButtons(MapEntityToButtons(repositoryGUI.getMany(null)));
    }

    private List<Button> MapEntityToButtons(List<T> entityList)
    {
        return entityList.stream().map(this::MapDataToButton).collect(Collectors.toList());
    }

    public Button MapDataToButton(T data)
    {
        Button button = new Button(data.icon,data.name,data.description);
        button.setTag(data.id);
        button.setObjectHolder(data);
        button.SetDescription(ChatColor.GREEN+" "+ChatColor.BOLD+"[ Click to show details ]");
        return button;
    }
   /* public List<String> DisplayValues(Player player,T model)
    {
        List<String> result = new ArrayList<>();

        for(AttributeManager.MethodAttribute method :attributeManager.getMethodAttribute())
        {
            if(!method.hasCustomName())
                continue;

            if(!method.isPlayerMeetPermisssions(player, ButtonActionsEnum.DISPLAY))
                continue;

            result.add(ChatColor.WHITE+" "+ChatColor.BOLD+method.getCustomName()+" "+ChatColor.RESET+method.getValue());
        }

      return result;
    }*/
}
