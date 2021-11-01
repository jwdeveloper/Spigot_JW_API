package jw.minigame;

import jw.commands.FluentCommand;
import jw.commands.FluentCommands;
import jw.events.FluentEvent;
import jw.events.FluentEvents;
import jw.task.TaskTimer;
import jw.utilites.player.PermissionUtility;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class MinigameBase implements Minigame {
    private final HashSet<FluentEvent<?>> events = new HashSet<>();
    private final HashSet<FluentCommand> commands = new HashSet<>();
    private final HashSet<Player> players = new HashSet<>();
    private final HashSet<TaskTimer> tasks = new HashSet<>();
    private final MinigameState state = MinigameState.InActive;
    private MinigameConfig config;
    private TaskTimer gameLoopTask;

    public MinigameBase() {
        init();
    }

    public abstract void onInit(MinigameConfig config);

    public  void onPlayerJoin(Player player) {};

    public  void onPlayerLeave(Player player) {};

    public  void onStart(){};

    public  void onException(Exception e){};

    public  void onStop(){};

    public HashSet<Player> getPlayers()
    {
        return players;
    }

    private void init()
    {
        config.load();
        onInit(config);

        var onPlayerLeaveServer = FluentEvents.onEvent(PlayerQuitEvent.class, event ->
        {
            if (!config.allowLogOut) {
                onPlayerLeave(event.getPlayer());
            }
        });

        this.registerEvent(onPlayerLeaveServer);

        var command = FluentCommands.onPlayerCommand(this.config.name, (player, args) ->
        {
               player.sendMessage(getGameInfo().toArray(new String[3]));
        }).addSubCommand(FluentCommands.onPlayerCommand("leave", (player, args) ->
        {
            playerLeave(player);
        })).addSubCommand(FluentCommands.onPlayerCommand("join", (player, args) ->
        {
            playerJoin(player);
        }));
        this.registerCommand(command);
    }

    public void registerTask(TaskTimer taskTimer)
    {
        tasks.add(taskTimer);
        taskTimer.onStop(this::unregisterTask);
    }
    public void unregisterTask(TaskTimer taskTimer)
    {
        tasks.remove(taskTimer);
    }
    public void registerEvent(FluentEvent<?> fluentEvent) {
        fluentEvent.setPermission(this.getPermission());
        this.events.add(fluentEvent);
    }

    public void registerCommand(FluentCommand fluentCommand) {
        fluentCommand.addPermission(this.getPermission() + fluentCommand);
        this.commands.add(fluentCommand);
    }

    @Override
    public String getPermission() {
        return "game." + config.name;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public World getWorld() {
        return null;
    }

    public List<String> getGameInfo() { return new ArrayList<>();};

    @Override
    public void start() {

    }

    @Override
    public void end() {

    }

    public void kickPlayer(Player player)
    {
        PermissionUtility.removePermission(player,getPermission());
        players.remove(player);
        onPlayerLeave(player);
    }
    public void setGameMode(Player player, GameMode gameMode)
    {
        player.setGameMode(gameMode);
    }


    @Override
    public void playerJoin(Player player) {

    }

    @Override
    public void playerLeave(Player player) {

    }
}
