package jw.gui.examples.chestgui;


import jw.gui.examples.ChestGUI;
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
    protected List<Button> filteredContent = new ArrayList<>();
    protected List<Button> actionButtons = new ArrayList<>();

    protected Material backgroundMaterial = Material.BLACK_STAINED_GLASS_PANE;
    protected ButtonActionsEnum currentAction = ButtonActionsEnum.EMPTY;

    protected int maxPages = 1;
    protected int page = 1;
    private int maxItemsOnPage = 7;

    public ListGUI(InventoryGUI parent, String name, int size) {
        super(parent, name, size);
        maxItemsOnPage = 7 * (size - 2);
        setActionButtons();
    }

    public ListGUI(InventoryGUI parent, String name, int size, Class<T> tClass) {
        super(parent, name, size, tClass);
        maxItemsOnPage = 7 * (size - 2);
        setActionButtons();
    }
    @Override
    public void onInitialize() {

    }

    @Override
    public void onClick(Player player, Button button) {
        if (button.getAction() == ButtonActionsEnum.CLICK) {
            switch (this.currentAction)
            {
                case EDIT:
                    player.playSound(player.getLocation(), Sound.UI_TOAST_IN, 1, 1);
                    this.onEdit.Execute(player, button);
                    this.addButtons(this.content);
                    this.cancelAction();
                    this.refresh();
                    break;
                case DELETE:
                    player.playSound(player.getLocation(), Sound.UI_TOAST_IN, 1, 1);
                    this.onDelete.Execute(player, button);
                    this.addButtons(this.content);
                    this.cancelAction();
                    this.refresh();
                    break;
                case COPY:
                    player.playSound(player.getLocation(), Sound.BLOCK_SLIME_BLOCK_PLACE, 1, 1);
                    this.onCopy.Execute(player, button);
                    this.addButtons(this.content);
                    this.cancelAction();
                    this.refresh();
                    break;
                case GET:
                    this.onSelect.Execute(player, button);
                    this.cancelAction();
                    break;

                default:
                    if (this.content.contains(button)) {
                        this.onClickContent.Execute(player, button);
                    }
                    break;
            }
        }
    }

    private void setActionButtons() {

        Button exitButton = ButtonFactory.exitButton();
        exitButton.setPosition(this.height - 1, 8);
        exitButton.setOnClick((a,b) ->
        {
            cancelAction();
            this.openParent();
        });
        Button search_box = ButtonFactory.searchButton();
        search_box.setPosition(0, 0);
        search_box.setOnClick((a,b) ->
        {
            a.sendMessage("Search not implemented yet");
          /* this.OpenTextInput("Search", "", (x, y) ->
            {
                this.Filter_Content(y);
                SetActionButtonVisibility(ButtonActionsEnum.CANCEL, true);
                this.Refresh();
            });*/
        });

        Button removeButton = ButtonFactory.deleteButton();
        removeButton.setPosition(0, 7);
        removeButton.setOnClick((a,b) ->
        {
            currentAction = ButtonActionsEnum.DELETE;
            setActionButtonVisibility(ButtonActionsEnum.CANCEL, true);
            this.drawBorder(Material.RED_STAINED_GLASS_PANE);

            if (!isTitleSet)
                this.setName(this.name + ChatColor.DARK_RED + " [Delete]");
            else
                this.refresh();
        });
        Button insertbutton = ButtonFactory.insertButton();
        insertbutton.setPosition(0, 6);
        insertbutton.setOnClick((a,b) ->
        {
            cancelAction();
            currentAction = ButtonActionsEnum.INSERT;
            /*this.openTextInput("Insert", "", (x, y) ->
            {
                player.playSound(player.getLocation(), Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, 1, 1);
                onInsert.accept(y);
            }, (c, d) ->
            {
                currentAction = ButtonActionsEnum.EMPTY;
            });*/
        });
        Button editButton = ButtonFactory.editButton();
        editButton.setPosition(0, 7);
        editButton.setOnClick((a,b) ->
        {
            currentAction = ButtonActionsEnum.EDIT;
            setActionButtonVisibility(ButtonActionsEnum.CANCEL, true);
            this.drawBorder(Material.YELLOW_STAINED_GLASS_PANE);

            if (!isTitleSet)
                this.setName(this.name + ChatColor.YELLOW + " [Edit]");
            else
                this.refresh();
        });

        Button copyButton = ButtonFactory.copyButton();
        copyButton.setPosition(0, 5);
        copyButton.setOnClick((a,b) ->
        {
            currentAction = ButtonActionsEnum.COPY;
            setActionButtonVisibility(ButtonActionsEnum.CANCEL, true);
            this.drawBorder(Material.LIME_STAINED_GLASS_PANE);

            if (!isTitleSet)
                this.setName(this.name + ChatColor.DARK_GREEN + " [Copy]");
            else
                this.refresh();

        });

        Button cancelButton = ButtonFactory.cancelButton();
        cancelButton.setPosition(this.height - 1, 4);
        cancelButton.setOnClick((a,b) ->
        {
            a.playSound(a.getLocation(), Sound.UI_TOAST_OUT, 1, 1);
            cancelAction();
            this.addButtons(this.content);
            this.refresh();
        });

        Button left_arrow = ButtonFactory.BackButton(PotionType.SLOWNESS);
        left_arrow.setPosition(this.height - 1, 3);
        left_arrow.setOnClick((a,b) ->
        {
            a.playSound(a.getLocation(), Sound.BLOCK_SNOW_BREAK, 1, 1);
            this.backPage();
            this.refresh();
        });

        Button right_arrow = ButtonFactory.nextButton(PotionType.SLOWNESS);
        right_arrow.setPosition(this.height - 1, 5);
        right_arrow.setOnClick((a,b) ->
        {
            a.playSound(a.getLocation(), Sound.BLOCK_SNOW_BREAK, 1, 1);
            this.nextPage();
            this.refresh();
        });

        this.actionButtons.add(exitButton);
        this.actionButtons.add(search_box);
        this.actionButtons.add(removeButton);
        this.actionButtons.add(insertbutton);
        this.actionButtons.add(copyButton);
        this.actionButtons.add(cancelButton);
        this.actionButtons.add(left_arrow);
        this.actionButtons.add(right_arrow);
        this.actionButtons.add(editButton);


        for (Button button:actionButtons)
        {
            this.addButton(button);
        }
        setActionButtonVisibility(ButtonActionsEnum.CANCEL, false);
        this.drawBorder(backgroundMaterial);
    }

    public void selectItem(InventoryEvent onSelect) {
        this.currentAction = ButtonActionsEnum.GET;
        this.drawBorder(Material.BLUE_STAINED_GLASS_PANE);
        this.refresh();
        this.onSelect = onSelect;
    }

    private void cancelAction() {
        if (this.currentAction == ButtonActionsEnum.DELETE ||
            this.currentAction == ButtonActionsEnum.COPY   ||
            this.currentAction == ButtonActionsEnum.EDIT)
        {
            currentAction = ButtonActionsEnum.EMPTY;
            setActionButtonVisibility(ButtonActionsEnum.CANCEL, false);
            this.drawBorder(this.backgroundMaterial);
            setPageName();
        }
    }

    private void setPageName() {
        if (isTitleSet)
            return;
        if (maxPages > 1)
            this.setName(this.name + " " + ChatColor.DARK_GRAY + "[" + String.valueOf(page) + "/" + String.valueOf(maxPages) + "]");
        else
            this.setName(this.name + " " + ChatColor.DARK_GRAY);
    }

    public void setActionButtonVisibility(ButtonActionsEnum buttonActionsEnum, boolean value) {
        for (Button actionButton : actionButtons) {
            if (actionButton.getAction().equals(buttonActionsEnum)) {
                if (value) {
                    this.addButton(actionButton);
                } else {
                    this.addButton(ButtonFactory.getBackground(
                            this.backgroundMaterial),
                            actionButton.getHeight(),
                            actionButton.getWidth());
                }
                break;
            }
        }
    }

    public Button getActionButton(ButtonActionsEnum buttonActionsEnum) {
        for (Button actionButton : actionButtons) {
            if (actionButton.getAction().equals(buttonActionsEnum)) {
                return actionButton;
            }
        }
        return ButtonFactory.emptyButton();
    }

    public void selectiveMode() {
        setActionButtonVisibility(ButtonActionsEnum.COPY, false);
        setActionButtonVisibility(ButtonActionsEnum.INSERT, false);
        setActionButtonVisibility(ButtonActionsEnum.DELETE, false);
    }

    private void fileterContent(String value) {

        if (value == null) {

            filteredContent = content;
            this.openPage(1);
            return;
        }
        value = value.toLowerCase();
        filteredContent = new ArrayList<>();

        for (Button button : content) {
            if (button.getItemMeta().getDisplayName().toLowerCase().startsWith(value))
                filteredContent.add(button);
        }

        this.openPage(1);
    }

    public void clearItems() {
        this.content = new ArrayList<>();
        this.filteredContent = new ArrayList<>();

        for (int j = 1; j < this.height - 1; j++) {
            for (int i = 1; i <= 7; i++) {
                this.addButton(null, j, i);
            }
        }
    }

    public void addButtons(List<Button> items) {
        int new_max_size = 7 * (this.height - 2);
        this.maxItemsOnPage = new_max_size == 0 ? 1 : new_max_size;
        this.maxPages = (int) Math.ceil(items.size() / (double) maxItemsOnPage);
        this.content = items;
        this.filteredContent = new ArrayList<>();
        openPage(page);
    }

    public void nextPage() {
        if (this.page + 1 <= this.maxPages) {
            this.page += 1;
            openPage(this.page);
        }
    }

    public void backPage() {
        if (this.page - 1 >= 1) {
            this.page -= 1;
            openPage(this.page);
        }
    }

    public void openPage(int page) {
        List<Button> content_to_show = this.filteredContent.size() == 0 ? this.content : this.filteredContent;

        int size = content_to_show.size() - 1;
        int start_index = maxItemsOnPage * (page - 1);

        int end_index = Math.min(maxItemsOnPage * page, size);
        Button itemStack;
        for (int j = 1; j < this.height - 1; j++) {
            for (int i = 1; i <= 7; i++) {
                if (start_index <= end_index) {
                    itemStack = content_to_show.get(start_index);
                    this.addButton(itemStack, j, i);
                    start_index += 1;
                } else {
                    this.addButton(null, j, i);
                }
            }
        }
        setPageName();
    }

    public int getPage() {
        return this.page;
    }

    public int getMaxItemsOnPage() {
        return this.maxItemsOnPage;
    }
}