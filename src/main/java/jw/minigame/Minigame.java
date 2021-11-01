package jw.minigame;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

//To do
//Player need to join
//After join player should be spawn in the lobby
//If Player amount is correct or waiting time is over game should start
//Minigame events should only be invoked for attentands
//after game close all minigame items rangs permissions should be unregisterd
//Minigame should has teams
//Player should see stats
//There should be custom nicknames
//There should be log system
//Player progress should be saved to database

//there should be enums in case of player leave
// 1. keep player in game
// 2. kick player from game

//Minigame modul should be based on Chain of responsibility and modules

//  resert
//   generate new minigameID
//   log everything
//   open Lobby
//   handle Lobby events
//   if lobby is full start game
//   register all events to participants
//     -permission for command events = game."gameName".uniqueGameID
//   save progess to database
//   reDoProcess


// IsGameAlreadyStarted
// Game state Lobby, Start, End

public interface Minigame
{
     String getName();

     World getWorld();

     void start();

     void end();

     void playerJoin(Player player);

     void playerLeave(Player player);

     String getPermission();

     List<String> getGameInfo();
}
