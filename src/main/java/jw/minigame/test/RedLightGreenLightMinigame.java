package jw.minigame.test;

import jw.colistions.HitBox;
import jw.events.FluentEvent;
import jw.events.FluentEvents;
import jw.minigame.MinigameBase;
import jw.minigame.MinigameConfig;
import jw.task.TaskTimer;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Random;

public class RedLightGreenLightMinigame extends MinigameBase {
    private final boolean isGreenLight = false;
    private FluentEvent<PlayerMoveEvent> playerMoveEvent;
    private HitBox gameArea;
    private TaskTimer gameLoop;

    @Override
    public void onInit(MinigameConfig config) {
        var p1 = config.gameSpawnPoint.clone().add(-50, 0, -50);
        var p2 = config.gameSpawnPoint.clone().add(50, 0, 50);

        gameArea = new HitBox(p1, p2);
        playerMoveEvent = FluentEvents.onEvent(PlayerMoveEvent.class, event ->
        {
            if (gameArea.isCollider(event.getTo(), 5))
            {
                setGameMode(event.getPlayer(), GameMode.SPECTATOR);
            }
        });
        gameLoop = new TaskTimer(20,(time, taskTimer) ->
        {
             if(time > new Random().nextInt(10))
             {
                 playerMoveEvent.setActive(true);
             }
             else
             {
                 playerMoveEvent.setActive(false);
             }
        });

        this.registerEvent(playerMoveEvent);
    }

    @Override
    public void onStart()
    {
        gameLoop.run();
    }
}
