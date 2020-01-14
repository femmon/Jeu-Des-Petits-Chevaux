#OOP final assignment - CoCaNgua - Group 8
###Introduction
The software is to do the coding section and design a game which is named Horse Race. The purpose of the game is that players will roll the dices to help the horses move to the path node as fast as they can. The first player to get all their horses go to the path node will win the Horse Race game.
###Feature
* The software can display game and its GUI components.
* Users are able to setup players.
* Users can set up the order of play.
* Game can help user roll dices.
* Basing on the value of dices, players can move the horses.
* Players can stop the game.
* The game also have a machine mode to play the game
* This game displays score and plays sound
* Users are able to play again if they want or even quit the game.
* The software can check game status.
* User can choose game language.
###Design
Moving algorithm for machine:
>find all horse available for machine in the board  
>if can summon  
>precedencePoint += 5  
>if can move  
>calculate each move for each dice  
>find move that have highest precedencePoint  
>Return dice status and picked horse
###Instalation
Go the main.java in the source file and run the program. Note that the program use javaFX and javafx.media
###Known bug
