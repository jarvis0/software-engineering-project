# README #

In order to start the server of the game you have to open the server.java located in it/polimi/ingsw/ps23/server directory and the client.java placed in it/polimi/ingsw/ps23/client directory.

When you start the client you must insert your name and after this the game will create a special id to distinguish you from other players with the same name.
You can also select which methods you want to play with: you can choose between RMI or Socket as connection protocol and GUI or CLI as graphical interface.
You can play against other players using different protocols and graphical interfaces.
On the same server you can run more than one game at the same time.

A game starts when in the waiting list there are more than two players.
When a game is starting, you have 20 seconds to join the waiting list and start with all the other waiting players. Otherwise you will add to another waiting list.

In the inizialization of the game, the program randomly chooses from one of the three directories containing configuration files. These files are placed in the following directory: it/polimi/ingsw/ps23/server/model/initialization/configuration.
If you want to create more map configurations you have to add the new directories in the same directory as above and you have to add the new directories in the maps.csv file.

During the game you have 180 seconds to make your choice after this you will be disconnected from the game by the server.
You can reconnect to the game using the same id the game created for you.

If a game remains with less than two players, it finishes and you can't reconnect to it.

To reach the end of the game you have to build ten emporiums in different cities according to the rules of "The Council of Four".