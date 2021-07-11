package jw.api.gui.assets;
import jw.api.InicializerAPI;
import jw.api.gui.button.Button;
import jw.api.gui.core.AnivalGUIOld;
import jw.api.gui.core.InventoryGUI;
import jw.api.gui.events.InputEvent;
import jw.api.gui.validation.Validator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class InputGUI {


    private String name;
    private InventoryGUI parent;
    private String displayed_text = "";
    private ArrayList<Validator> validators = new ArrayList<>();
    private boolean validate = false;

    public InputEvent onText = (a, b) -> {
    };
    public InputEvent onExit = (a, b) -> {
    };

    public InputGUI(String name, InventoryGUI parent) {
        this.name = name;
        this.parent = parent;

    }

    public InputGUI(String name, InventoryGUI parent, String displayed_text) {
        this(name, parent);
        this.displayed_text = displayed_text;

    }

    public void SetValidation(boolean b) {
        validate = b;
    }

    public void add_validation(Validator validator) {
        validators.add(validator);
        validate = true;
    }

    public void SetName(String name) {
        this.name = name;
    }

    public void SetValue(String value) {
        this.displayed_text = value;
    }

    public void Open(Player player) {
        parent.SetActive(false);
        AnivalGUIOld guiTextinput = new AnivalGUIOld(player, new AnivalGUIOld.AnvilClickEventHandler() {

            @Override
            public void onAnvilClick(AnivalGUIOld.AnvilClickEvent e) {
                Bukkit.getScheduler().runTask(InicializerAPI.GetPlugin(), () ->
                {

                    if (e.getSlot() == AnivalGUIOld.AnvilSlot.OUTPUT && e.hasText()) {

                        if (validate) {
                            for (Validator validator : validators) {
                                if (!validator.Validate(e.getText()))
                                    return;
                            }
                        }
                        onText.Execute(player, e.getText());
                        parent.Open(player);

                        e.setWillClose(true);
                        e.setWillDestroy(true);
                    }
                    if (e.getSlot() == AnivalGUIOld.AnvilSlot.INPUT_LEFT) {

                        onExit.Execute(player, " ");
                        parent.Open(player);

                        e.setWillClose(true);
                        e.setWillDestroy(true);
                    }
                });
            }
        });

        guiTextinput.setSlot(AnivalGUIOld.AnvilSlot.INPUT_LEFT, new Button(Material.NAME_TAG, ChatColor.BLACK + ChatColor.stripColor(displayed_text)));
        guiTextinput.setSlot(AnivalGUIOld.AnvilSlot.OUTPUT, new Button(Material.NAME_TAG, displayed_text));
        Bukkit.getScheduler().runTask(InicializerAPI.GetPlugin(), () ->
        {
            guiTextinput.open(name);
        });
    }
}