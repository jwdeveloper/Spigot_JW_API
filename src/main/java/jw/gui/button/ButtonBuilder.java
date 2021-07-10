package jw.gui.button;

import jw.gui.events.ButtonEvent;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class ButtonBuilder<SELF extends ButtonBuilder<SELF>>
{

    protected Button button;

    public ButtonBuilder()
    {
        button = new Button(Material.DIRT);
    }

    public SELF SetPosition(int height,int width)
    {
        this.button.SetPosition(height,width);
        return Self();
    }
    public SELF SetDescription(String ... description)
    {
        this.button.SetDescription(description);
        return Self();
    }
    public SELF SetName(String name)
    {
        this.button.SetName(name);
        return Self();
    }
    public SELF SetMaterial(Material material)
    {
        this.button.setMaterial(material);
        return Self();
    }
    public SELF SetOnClick(ButtonEvent onClick)
    {
        this.button.setOnClick(onClick);
        return Self();
    }
    public SELF SetAcction(ButtonActionsEnum actionsEnum)
    {
        this.button.setAction(actionsEnum);
        return Self();
    }
    public SELF SetHighlighted(boolean highlighted)
    {
        this.button.SetHighlighted(highlighted);
        return Self();
    }
    public SELF SetClickSound(Sound sound)
    {
         this.button.setSound(sound);
        return Self();
    }
    protected SELF Self()
    {
        return (SELF)this;
    }

    public Button Build()
    {
        return button;
    }
}
