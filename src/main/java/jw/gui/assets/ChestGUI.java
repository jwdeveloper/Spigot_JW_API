package jw.gui.assets;

import jw.gui.assets.chestgui.ButtonBuilderChestGUI;
import jw.gui.assets.chestgui.bindingstrategies.BindingStrategy;
import jw.gui.button.Button;
import jw.gui.button.ButtonActionsEnum;
import jw.gui.button.ButtonFactory;
import jw.gui.core.InventoryGUI;
import jw.gui.events.InputEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ChestGUI<T> extends InventoryGUI {
    public ChestGUI(String name, int size) {
        super(name, size, InventoryType.CHEST);
    }

    public ChestGUI(InventoryGUI parent, String name, int size) {
        super(parent, name, size, InventoryType.CHEST);
    }

    public ChestGUI(InventoryGUI parent, String name, int size, Class<T> detailClass) {
        super(parent, name, size, InventoryType.CHEST);
        this.detailClass = detailClass;
    }
    public T detail;


    private Class<T> detailClass;
    private ArrayList<Button> bindedButtons = new ArrayList<>();
    private InputGUI guiInput;
    private boolean isInitialized = false;

    @Override
    public void OnClick(Player player, Button itemStack) {

    }

    @Override
    public void OnRefresh(Player player) {

    }

    @Override
    public void OnOpen(Player player) {

    }

    @Override
    public void OnClose(Player player) {

    }

    public void OnClickAtPlayerItem(Player player, ItemStack itemStack) {
    }

    public void OnInitialize() {
    }

    @Override
    protected void DoClick(Player player, int index, ItemStack itemStack) {
        if (index < this.size) {
            Button button = this.GetButton(index);
            if (button != null && button.IsActive()) {

                if(button.hasSound())
                    player.playSound(player.getLocation(),button.getSound(),1,1);
                if(!button.checkPermission(player))
                  return;


                button.getOnClick().Execute(player,button);
                OnClick(player, button);
            }
        } else {
            OnClickAtPlayerItem(player, itemStack);
        }
    }

    protected InputGUI CreateInputGUI() {
        if (guiInput == null)
            guiInput = new InputGUI("Input", this);

        return guiInput;
    }

    protected void Initialize() {
        if (!isInitialized) {
            isInitialized = true;
            this.OnInitialize();
        }

    }

    public void OpenParent() {
        if (this.GetParent() != null) {
            this.Close();
            this.GetParent().Open(player);
        } else {
            this.Close();
        }
    }

    @Override
    public void Open(Player player) {
        Initialize();
        if(detail != null)
        {
         RefreshBindedButtons();
        }
        super.Open(player);
    }

    @Override
    public ButtonBuilderChestGUI BuildButton() {
        return new ButtonBuilderChestGUI(this);
    }

    public void Open(Player player, T detail) {
        this.detail = detail;
        this.Open(player);
    }

    public void OpenTextInput(String title, String value, InputEvent textEvent) {
        CreateInputGUI();
        guiInput.onText = textEvent;
        guiInput.SetValidation(false);
        guiInput.SetName(title);
        guiInput.SetValue(value);
        guiInput.Open(player);
    }

    public void OpenTextInput(String title, String value, InputEvent textEvent, InputEvent closeEvent) {
        CreateInputGUI();
        guiInput.onText = textEvent;
        guiInput.onExit = closeEvent;
        guiInput.SetValidation(false);
        guiInput.SetName(title);
        guiInput.SetValue(value);
        guiInput.Open(player);
    }

    public void OpenNumberInput(String title, String value, InputEvent textEvent) {
        CreateInputGUI();
        guiInput.onText = textEvent;
        guiInput.SetName(title);
        guiInput.SetValue(value);
        guiInput.Open(player);
    }


    public void AddButtonTextInput(Button gui_button) {
        gui_button.setOnClick((a,b) ->
        {
            this.OpenTextInput("Set text", gui_button.getObjectBinder().getFieldValue().toString(), (p, m) ->
            {
                Bukkit.getConsoleSender().sendMessage(m);
                gui_button.getObjectBinder().setFieldValue(m);
                gui_button.DisplayBindedValue();
            });
        });
        this.AddButton(gui_button);
    }

    public void AddButtonNumberInput(Button gui_button) {
        gui_button.setOnClick((a,b)  ->
        {
            this.OpenNumberInput("Set number", gui_button.getObjectBinder().getFieldValue().toString(), (c, d) ->
            {
                try {
                    gui_button.getObjectBinder().setFieldValue(Integer.parseInt(d));
                    gui_button.DisplayBindedValue();
                } catch (Exception ignored) {

                }

            });
        });
        this.AddButton(gui_button);
    }
    public void DrawBorder(Material material) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < this.height; j++) {
                if (i == 0 || j == 0 || j == this.height - 1 || i == 8) {
                    Button button = GetButton(j, i);

                    if (button == null)
                    {
                        this.AddButton(ButtonFactory.GetBackground(material), j, i);
                    } else if (button.GetAction() == ButtonActionsEnum.BACKGROUND || button.GetAction() == ButtonActionsEnum.EMPTY)
                    {
                        button.setType(material);
                    }
                }
            }
        }
    }
    public void AddBackArrow() {
        Button button = ButtonFactory.ExitButton();
        button.SetPosition(this.height - 1, 8);
        button.setOnClick((a,b) ->
        {
            this.OpenParent();
        });
        this.AddButton(button);
    }
    public void RefreshBindedButtons()
    {
        BindingStrategy bindingStrategy;
        for (Button bindedButton : bindedButtons)
        {
            bindedButton.getObjectBinder().setObject(this.detail);
            bindingStrategy = (BindingStrategy)bindedButton.getOnClick();
            bindingStrategy.OnValueChanged(this,bindedButton,bindedButton.getObjectBinder().getFieldValue());
        }
    }

    public void AddBindedButton(Button button)
    {
        this.bindedButtons.add(button);
    }

}