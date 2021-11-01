package jw.gui.examples.chestgui.selectionList.examples;

import jw.gui.examples.chestgui.selectionList.SelectGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockSelectGUI extends SelectGUI<Material>
{
    private static List<Material> blocks = new ArrayList<>();
    static
    {
        blocks = Arrays.stream(Material.values()).toList().stream().filter(Material::isBlock).toList();
    }
    public BlockSelectGUI(String name, int size)
    {
        super(name, size);
        this.addButtons(blocks,(material, button) ->
        {
            button.setMaterial(material);
            button.setName(material.name());
        });
    }
}
