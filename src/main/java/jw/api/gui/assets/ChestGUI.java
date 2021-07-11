package jw.api.gui.assets;

import jw.api.gui.assets.chestgui.ButtonBuilderChestGUI;
import jw.api.utilites.ObjectHelper;
import jw.api.gui.assets.chestgui.SelectListGUI;
import jw.api.gui.button.Button;
import jw.api.gui.button.ButtonActionsEnum;
import jw.api.gui.button.ButtonFactory;
import jw.api.gui.core.InventoryGUI;
import jw.api.gui.events.InputEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
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

    private Class<T> detailClass;
    public T detail;
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
        for (Button button : this.buttons) {
            if (button != null && button.isBinded()) {
                button.DisplayBindedValue();
            }
        }

        super.Open(player);
    }

    @Override
    public ButtonBuilderChestGUI BuildButton() {
        return new ButtonBuilderChestGUI(this);
    }

    public void Open(Player player, T detail) {
        this.detail = detail;
        this.Initialize();
        for (Button bindedButton : bindedButtons)
        {
            bindedButton.getObjectBinder().setObject(this.detail);
        }
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

    public void AddButtonMaterial(Button gui_button) {
        gui_button.setOnClick((a,b) ->
        {
            SelectListGUI.Get(a, SelectListGUI.SearchType.Materials, (c, d) ->
            {
                try {
                    gui_button.getObjectBinder().setFieldValue(d.GetHoldingObject());
                    gui_button.DisplayBindedValue();
                } catch (Exception e) {
                    this.DisplayLog(e.getMessage(), ChatColor.RED);
                }
                this.Open(player);
            }).SetParent(this).Open(a);

        });
        this.AddButton(gui_button);
    }

    public void AddButtonBool(Button gui_button) {
        gui_button.setOnClick((a,b) ->
        {
            try {
                Object value = gui_button.getObjectBinder().getFieldValue();
                if (value != null) {
                    boolean btn_value = !(boolean) value;
                    gui_button.getObjectBinder().setFieldValue(btn_value);
                    gui_button.DisplayBindedValue();
                    this.Refresh();
                }
            } catch (Exception e) {
                this.DisplayLog(e.getMessage(), ChatColor.RED);
            }
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




    public void AddBindedButton(Button button)
    {
        this.bindedButtons.add(button);
    }

    //autoAcction bind to prepared behaviore of field Class for example string open InputBox
    public void AddButtonBind(String fieldName, Material icon, String name, int height, int width, boolean autoAction) {
        Field field = ObjectHelper.FindProperty(fieldName, detailClass);

        if (field == null)
        {
            Button button = new Button(Material.BARRIER, name);
            button.SetPosition(height, width);
            button.SetDescription(ChatColor.DARK_RED+"Field "+ChatColor.WHITE+fieldName+ChatColor.DARK_RED+" not found");
            this.AddButton(button,height,width);
        }

        Button button = new Button(icon, name);
        button.SetPosition(height, width);
        button.getObjectBinder().setField(field);
        //  Bukkit.getServer().getConsoleSender().sendMessage(field.getType().getTypeName());
        if (autoAction) {
            switch (field.getType().getTypeName()) {

                case "org.bukkit.Material":
                    this.AddButtonMaterial(button);
                    break;
                case "java.lang.String":
                    this.AddButtonTextInput(button);
                    break;
                case "int":
                case "float":
                case "double":
                    this.AddButtonNumberInput(button);
                    break;
                case "boolean":
                    this.AddButtonBool(button);
                    break;

                default:
                    this.AddButton(button);
                    break;
            }
        } else {
            this.AddButton(button);
        }

        bindedButtons.add(button);
    }
}