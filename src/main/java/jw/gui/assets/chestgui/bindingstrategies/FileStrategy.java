package jw.gui.assets.chestgui.bindingstrategies;

import jw.gui.assets.ChestGUI;
import jw.gui.assets.chestgui.SelectListGUI;
import jw.gui.button.Button;
import jw.utilites.FileHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FileStrategy extends BindingStrategy<String> {


    private final String path;
    private final String[] extentions;
    private  Material material = Material.PAPER;
    public FileStrategy(String path,String... extentions)
    {
        this.path = path;
        this.extentions = extentions;
    }

    public FileStrategy setFileMaterial(Material material)
    {
        this.material = material;
        return this;
    }

    @Override
    public void OnClick(Player player, Button button, String currentValue)
    {
        SelectListGUI.Get(player,"Select file", Files(),(player2, button1) ->
        {
            SetValue(button1.GetHoldingObject());
            chestGUI.Open(player);
        }).SetParent(chestGUI).Open(player);
    }

    @Override
    public void OnValueChanged(ChestGUI inventoryGUI, Button button, String newValue)
    {
        button.SetDescription("File: "+ newValue);
    }
    private ArrayList<Button> Files()
    {
        return (ArrayList<Button>) FileHelper.Get_FileNames(path,extentions).stream().map(name ->
        {
            Button button = new Button(material,name);
            button.setObjectHolder(name);
            return button;
        }).collect(Collectors.toList());
    }
}