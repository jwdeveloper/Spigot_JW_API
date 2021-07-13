package jw.gui.assets.chestgui;


import jw.gui.assets.ChestGUI;
import jw.gui.button.Button;
import jw.gui.button.ButtonActionsEnum;
import jw.gui.button.ButtonFactory;
import jw.gui.core.InventoryGUI;
import jw.gui.events.InventoryEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ListGUI<T> extends ChestGUI<T> {

    public InventoryEvent onClickContent = (a, b) -> {
    };
    public InventoryEvent onSelect = (a, b) -> {
    };
    public Consumer<String> onInsert = (a) -> {
    };
    public InventoryEvent onDelete = (a, b) -> {
    };
    public InventoryEvent onCopy = (a, b) -> {
    };
    public InventoryEvent onEdit = (a, b) -> {
    };

    protected List<Button> content = new ArrayList<>();
    protected List<Button> filtred_content = new ArrayList<>();
    protected List<Button> actionButtons = new ArrayList<>();

    public Material backgroundMaterial = Material.BLACK_STAINED_GLASS_PANE;
    public ButtonActionsEnum current_acction = ButtonActionsEnum.EMPTY;

    protected int maxPages = 1;
    protected int page = 1;
    private int maxItemsOnPage = 7;

    public int GetPage() {
        return this.page;
    }

    public int GetMaxItemsOnPage() {
        return this.maxItemsOnPage;
    }


    public ListGUI(InventoryGUI parent, String name, int size) {
        super(parent, name, size);
        maxItemsOnPage = 7 * (size - 2);
        SetAcctionButtons();
    }

    public ListGUI(InventoryGUI parent, String name, int size, Class<T> tClass) {
        super(parent, name, size, tClass);
        maxItemsOnPage = 7 * (size - 2);
        SetAcctionButtons();
    }

    @Override
    public void Open(Player player) {
        this.Initialize();
        super.Open(player);
    }

    private void SetAcctionButtons() {

        Button exitButton = ButtonFactory.ExitButton();
        exitButton.SetPosition(this.height - 1, 8);
        exitButton.setOnClick((a,b) ->
        {
            CancelAction();
            this.OpenParent();
        });
        Button search_box = ButtonFactory.SearchButton();
        search_box.SetPosition(0, 0);
        search_box.setOnClick((a,b) ->
        {
            this.OpenTextInput("Search", "", (x, y) ->
            {
                this.Filter_Content(y);
                SetActionButtonVisibility(ButtonActionsEnum.CANCEL, true);
                this.Refresh();
            });

        });

        Button removeButton = ButtonFactory.DeleteButton();
        removeButton.SetPosition(0, 7);
        removeButton.setOnClick((a,b) ->
        {
            current_acction = ButtonActionsEnum.DELETE;
            SetActionButtonVisibility(ButtonActionsEnum.CANCEL, true);
            this.DrawBorder(Material.RED_STAINED_GLASS_PANE);

            if (!isTitleSet)
                this.SetName(this.name + ChatColor.DARK_RED + " [Delete]");
            else
                this.Refresh();
        });
        Button insetbutton = ButtonFactory.InsertButton();
        insetbutton.SetPosition(0, 6);
        insetbutton.setOnClick((a,b) ->
        {
            CancelAction();
            current_acction = ButtonActionsEnum.INSERT;
            this.OpenTextInput("Insert", "", (x, y) ->
            {
                player.playSound(player.getLocation(), Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, 1, 1);
                onInsert.accept(y);
            }, (c, d) ->
            {
                current_acction = ButtonActionsEnum.EMPTY;
            });
        });
        Button editButton = ButtonFactory.EditButton();
        editButton.SetPosition(0, 7);
        editButton.setOnClick((a,b) ->
        {
            current_acction = ButtonActionsEnum.EDIT;
            SetActionButtonVisibility(ButtonActionsEnum.CANCEL, true);
            this.DrawBorder(Material.YELLOW_STAINED_GLASS_PANE);

            if (!isTitleSet)
                this.SetName(this.name + ChatColor.YELLOW + " [Edit]");
            else
                this.Refresh();
        });

        Button copyButton = ButtonFactory.CopyButton();
        copyButton.SetPosition(0, 5);
        copyButton.setOnClick((a,b) ->
        {
            current_acction = ButtonActionsEnum.COPY;
            SetActionButtonVisibility(ButtonActionsEnum.CANCEL, true);
            this.DrawBorder(Material.LIME_STAINED_GLASS_PANE);

            if (!isTitleSet)
                this.SetName(this.name + ChatColor.DARK_GREEN + " [Copy]");
            else
                this.Refresh();

        });

        Button cancelButton = ButtonFactory.CancelButton();
        cancelButton.SetPosition(this.height - 1, 4);
        cancelButton.setOnClick((a,b) ->
        {
            a.playSound(a.getLocation(), Sound.UI_TOAST_OUT, 1, 1);
            CancelAction();
            this.AddButtons(this.content);
            this.Refresh();
        });

        Button left_arrow = ButtonFactory.BackButton(PotionType.SLOWNESS);
        left_arrow.SetPosition(this.height - 1, 3);
        left_arrow.setOnClick((a,b) ->
        {
            a.playSound(a.getLocation(), Sound.BLOCK_SNOW_BREAK, 1, 1);
            this.BackPage();
            this.Refresh();
        });

        Button right_arrow = ButtonFactory.NextButton(PotionType.SLOWNESS);
        right_arrow.SetPosition(this.height - 1, 5);
        right_arrow.setOnClick((a,b) ->
        {
            a.playSound(a.getLocation(), Sound.BLOCK_SNOW_BREAK, 1, 1);
            this.NextPage();
            this.Refresh();
        });

        this.actionButtons.add(exitButton);
        this.actionButtons.add(search_box);
        this.actionButtons.add(removeButton);
        this.actionButtons.add(insetbutton);
        this.actionButtons.add(copyButton);
        this.actionButtons.add(cancelButton);
        this.actionButtons.add(left_arrow);
        this.actionButtons.add(right_arrow);
        this.actionButtons.add(editButton);

        this.AddButton(exitButton);
        this.AddButton(search_box);
        this.AddButton(removeButton);
        this.AddButton(insetbutton);
        this.AddButton(copyButton);
        this.AddButton(cancelButton);
        this.AddButton(left_arrow);
        this.AddButton(right_arrow);
        this.AddButton(editButton);

        SetActionButtonVisibility(ButtonActionsEnum.CANCEL, false);
        this.DrawBorder(backgroundMaterial);
    }


    @Override
    public void OnClick(Player player, Button button) {
        if (button.GetAction() == ButtonActionsEnum.CLICK) {
            switch (this.current_acction)
            {

                case EDIT:
                    player.playSound(player.getLocation(), Sound.UI_TOAST_IN, 1, 1);
                    this.onEdit.Execute(player, button);
                    this.AddButtons(this.content);
                    this.CancelAction();
                    this.Refresh();
                    break;
                case DELETE:
                    player.playSound(player.getLocation(), Sound.UI_TOAST_IN, 1, 1);
                    this.onDelete.Execute(player, button);
                    this.AddButtons(this.content);
                    this.CancelAction();
                    this.Refresh();
                    break;
                case COPY:
                    player.playSound(player.getLocation(), Sound.BLOCK_SLIME_BLOCK_PLACE, 1, 1);
                    this.onCopy.Execute(player, button);
                    this.AddButtons(this.content);
                    this.CancelAction();
                    this.Refresh();
                    break;
                case GET:
                    this.onSelect.Execute(player, button);
                    this.CancelAction();
                    break;

                default:
                    if (this.content.contains(button)) {
                        this.onClickContent.Execute(player, button);
                    }
                    break;
            }
        }
    }

    public void SelectItem(InventoryEvent onSelect) {
        this.current_acction = ButtonActionsEnum.GET;
        this.DrawBorder(Material.BLUE_STAINED_GLASS_PANE);
        this.Refresh();
        this.onSelect = onSelect;
    }

    private void CancelAction() {
        if (this.current_acction == ButtonActionsEnum.DELETE ||
            this.current_acction == ButtonActionsEnum.COPY   ||
            this.current_acction == ButtonActionsEnum.EDIT)
        {
            current_acction = ButtonActionsEnum.EMPTY;
            SetActionButtonVisibility(ButtonActionsEnum.CANCEL, false);
            this.DrawBorder(this.backgroundMaterial);

            SetPageName();
        }
    }

    private void SetPageName() {
        if (isTitleSet)
            return;

        if (maxPages > 1)
            this.SetName(this.name + " " + ChatColor.DARK_GRAY + "[" + String.valueOf(page) + "/" + String.valueOf(maxPages) + "]");
        else
            this.SetName(this.name + " " + ChatColor.DARK_GRAY);
    }

    public void SetActionButtonVisibility(ButtonActionsEnum ButtonActionsEnum, boolean value) {
        for (Button acction_button : actionButtons) {
            if (acction_button.GetAction().equals(ButtonActionsEnum)) {
                if (value) {
                    this.AddButton(acction_button);
                } else {
                    this.AddButton(ButtonFactory.GetBackground(this.backgroundMaterial), acction_button.GetHeight(), acction_button.GetWidth());
                }
                break;
            }
        }
    }

    public Button GetActionButton(ButtonActionsEnum ButtonActionsEnum) {
        for (Button acction_button : actionButtons) {
            if (acction_button.GetAction().equals(ButtonActionsEnum)) {
                return acction_button;
            }
        }
        return ButtonFactory.EmptyButton();
    }

    public void SelectiveMode() {
        SetActionButtonVisibility(ButtonActionsEnum.COPY, false);
        SetActionButtonVisibility(ButtonActionsEnum.INSERT, false);
        SetActionButtonVisibility(ButtonActionsEnum.DELETE, false);
    }

    private void Filter_Content(String value) {

        if (value == null) {

            filtred_content = content;
            this.OpenPage(1);
            return;
        }
        value = value.toLowerCase();
        filtred_content = new ArrayList<>();

        for (Button button : content) {
            if (button.getItemMeta().getDisplayName().toLowerCase().startsWith(value))
                filtred_content.add(button);
        }

        this.OpenPage(1);
    }

    public void ClearItems() {
        this.content = new ArrayList<>();
        this.filtred_content = new ArrayList<>();

        for (int j = 1; j < this.height - 1; j++) {
            for (int i = 1; i <= 7; i++) {
                this.AddButton(null, j, i);
            }
        }
    }

    public void AddButtons(List<Button> items) {
        int new_max_size = 7 * (this.height - 2);
        this.maxItemsOnPage = new_max_size == 0 ? 1 : new_max_size;
        this.maxPages = (int) Math.ceil(items.size() / (double) maxItemsOnPage);
        this.content = items;
        this.filtred_content = new ArrayList<>();
        OpenPage(page);

    }
    public void NextPage() {
        if (this.page + 1 <= this.maxPages) {
            this.page += 1;
            OpenPage(this.page);
        }
    }

    public void BackPage() {
        if (this.page - 1 >= 1) {
            this.page -= 1;
            OpenPage(this.page);
        }
    }
    public void OpenPage(int page) {
        List<Button> content_to_show = this.filtred_content.size() == 0 ? this.content : this.filtred_content;

        int size = content_to_show.size() - 1;
        int start_index = maxItemsOnPage * (page - 1);

        int end_index = Math.min(maxItemsOnPage * page, size);
        Button itemStack;
        for (int j = 1; j < this.height - 1; j++) {
            for (int i = 1; i <= 7; i++) {
                if (start_index <= end_index) {
                    itemStack = content_to_show.get(start_index);
                    this.AddButton(itemStack, j, i);
                    start_index += 1;
                } else {
                    this.AddButton(null, j, i);
                }
            }
        }
        SetPageName();
    }

}