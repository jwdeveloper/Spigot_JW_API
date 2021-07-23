package jw.gui.examples.chestgui;

import jw.gui.examples.ChestGUI;
import jw.gui.examples.SingleInstanceGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class QuestionFormGUI extends SingleInstanceGUI<ChestGUI> {

    public static QuestionFormGUI Instance() {
        if (Instance == null) {
            Instance = new QuestionFormGUI();
        }
        return Instance;
    }

    private static QuestionFormGUI Instance;


    @Override
    public ChestGUI setGUI() {
        ChestGUI chestGUI = new ChestGUI(null, "Select", 4);
        chestGUI.FillWithMaterial(Material.GRAY_STAINED_GLASS_PANE);

        return chestGUI;
    }

    public static ChestGUI Open(Player player,String title, Consumer<Boolean> result)
    {
        ChestGUI gui = Instance().getGUI(player);
        gui.SetTitle(title);
        return gui;
    }


}
