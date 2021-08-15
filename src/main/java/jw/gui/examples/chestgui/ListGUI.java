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

    private Button insertButton;
    private Button searchButton;
    private Button deleteButton;
    private Button copyButton;
    private Button cancelButton;

    protected List<Button> content = new ArrayList<>();
    protected List<Button> filteredContent = new ArrayList<>();
    protected Material backgroundMaterial = Material.BLACK_STAINED_GLASS_PANE;
    protected ButtonActionsEnum currentAction = ButtonActionsEnum.EMPTY;

    protected int maxPages = 1;
    protected int page = 1;
    private int maxItemsOnPage = 7;

    public ListGUI(InventoryGUI parent, String name, int size) {
        super(parent, name, size);
        maxItemsOnPage = 7 * (size - 2);
        setActionButtons();
        this.drawBorder(backgroundMaterial);
    }

    public ListGUI(InventoryGUI parent, String name, int size, Class<T> tClass) {
        super(parent, name, size, tClass);
        maxItemsOnPage = 7 * (size - 2);
        setActionButtons();
        this.drawBorder(backgroundMaterial);
    }
    @Override
    public void onInitialize() {

    }
    @Override
    public void onClick(Player player, Button button) {
        if (button.getAction() == ButtonActionsEnum.CLICK)
        {
            this.displayLog("Click, current action "+this.currentAction.name(),ChatColor.GREEN);
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
    public Button getInsertButton()
    {
        if(this.insertButton!=null)
            return this.insertButton;

        insertButton = ButtonFactory.insertButton();
        insertButton.setSound(Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT);
        insertButton.setPosition(0, 6);
        insertButton.setOnClick((a,b) ->
        {
            cancelAction();
            this.openTextInput(ChatColor.BOLD+"Enter name",input ->
            {
                onInsert.accept(input);
            });
        });
        return insertButton;
    }
    public Button getSearchButton()
    {
        if(this.searchButton!=null)
            return this.searchButton;
        searchButton = ButtonFactory.searchButton();
        searchButton.setPosition(0, 0);
        searchButton.setOnClick((a,b) ->
        {
            cancelAction();
            this.openTextInput("Enter value on chat",input ->
            {
                this.filterContent(input);
                this.getCancelButton().setActive(true);
            });
        });
        return searchButton;
    }
    public Button getDeleteButton()
    {
        if(this.deleteButton!=null)
            return this.deleteButton;

        deleteButton = ButtonFactory.deleteButton();
        deleteButton.setPosition(0, 8);
        deleteButton.setOnClick((a,b) ->
        {
            this.setCurrentAction(ButtonActionsEnum.DELETE);
            this.getCancelButton().setActive(false);
            this.drawBorder(Material.RED_STAINED_GLASS_PANE);

            if (!isTitleSet)
                this.setName(this.name + ChatColor.DARK_RED + " [Delete]");
            else
                this.refresh();
        });
        return deleteButton;
    }
    public Button getCopyButton()
    {
        if(this.copyButton!=null)
            return this.copyButton;

        copyButton = ButtonFactory.copyButton();
        copyButton.setPosition(0, 5);
        copyButton.setOnClick((a,b) ->
        {
            this.setCurrentAction(ButtonActionsEnum.COPY);
            this.drawBorder(Material.LIME_STAINED_GLASS_PANE);
            if (!isTitleSet)
                this.setName(this.name + ChatColor.DARK_GREEN + " [Copy]");
            else
                this.refresh();
        });
        return copyButton;
    }

    public Button getCancelButton()
    {
        if(this.cancelButton!=null)
            return this.cancelButton;

        cancelButton = ButtonFactory.cancelButton();
        cancelButton.setPosition(this.height - 1, 4);
        cancelButton.setOnClick((a,b) ->
        {
            a.playSound(a.getLocation(), Sound.UI_TOAST_OUT, 1, 1);
            cancelAction();
            this.addButtons(this.content);
            this.refresh();
        });
        return cancelButton;
    }
    private void setActionButtons() {


        Button exitButton = ButtonFactory.exitButton();
        exitButton.setPosition(this.height - 1, 8);
        exitButton.setOnClick((a,b) ->
        {
            cancelAction();
            this.openParent();
        });

        Button leftArrow = ButtonFactory.BackButton(PotionType.SLOWNESS);
        leftArrow.setPosition(this.height - 1, 3);
        leftArrow.setOnClick((a,b) ->
        {
            a.playSound(a.getLocation(), Sound.BLOCK_SNOW_BREAK, 1, 1);
            this.backPage();
            this.refresh();
        });
        Button rightArrow = ButtonFactory.nextButton(PotionType.SLOWNESS);
        rightArrow.setPosition(this.height - 1, 5);
        rightArrow.setOnClick((a,b) ->
        {
            a.playSound(a.getLocation(), Sound.BLOCK_SNOW_BREAK, 1, 1);
            this.nextPage();
            this.refresh();
        });


        this.addButton(exitButton);
        this.addButton(leftArrow);
        this.addButton(rightArrow);
        this.addButton(getCancelButton());
        this.addButton(getCopyButton());
        this.addButton(getDeleteButton());
        this.addButton(getInsertButton());
        this.addButton(getSearchButton());
    }
    private void cancelAction() {
        if (this.currentAction == ButtonActionsEnum.DELETE ||
            this.currentAction == ButtonActionsEnum.COPY   ||
            this.currentAction == ButtonActionsEnum.EDIT)
        {
            this.displayLog("Cancel action ",ChatColor.RED);
            this.setCurrentAction(ButtonActionsEnum.EMPTY);
            this.getCancelButton().setActive(false);
            this.drawBorder(this.backgroundMaterial);
            setPageName();
        }
    }

    private void setCurrentAction(ButtonActionsEnum action)
    {
        this.displayLog("Set current action: "+action.name(),ChatColor.GREEN);
        this.currentAction = action;
    }

    private void setPageName() {
        if (isTitleSet)
            return;
        if (maxPages > 1)
            this.setName(this.name + " " + ChatColor.DARK_GRAY + "[" + String.valueOf(page) + "/" + String.valueOf(maxPages) + "]");
        else
            this.setName(this.name + " " + ChatColor.DARK_GRAY);
    }

    private void filterContent(String value) {

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

            this.displayLog("Next page "+page,ChatColor.GREEN);
        }
    }

    public void backPage() {
        if (this.page - 1 >= 1) {
            this.page -= 1;
            openPage(this.page);

            this.displayLog("Back page "+page,ChatColor.GREEN);
        }
    }

    public void openPage(int page) {
        List<Button> contentToDisplay = this.filteredContent.size() == 0 ? this.content : this.filteredContent;

        int size = contentToDisplay.size() - 1;
        int startIndex = maxItemsOnPage * (page - 1);

        int end_index = Math.min(maxItemsOnPage * page, size);
        Button itemStack;
        for (int j = 1; j < this.height - 1; j++) {
            for (int i = 1; i <= 7; i++) {
                if (startIndex <= end_index) {
                    itemStack = contentToDisplay.get(startIndex);
                    this.addButton(itemStack, j, i);
                    startIndex += 1;
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