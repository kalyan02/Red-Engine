# Red-Engine

Android game engine for building tile based games

The basic engine is complete and the code demonstrates the usage of all the aspects of the framework.

Actual implementation of the game is incomplete.

## Package Info

 * red.game -> base implementation of the game engine
 * red.game.core -> core engine which handles the game loop, game stage, init actions and the game state.
 * red.game.tween -> tweening libraries for animations, etc
 * red.game.screens -> base classes for various game screens. Each scene/screen of the game would extend GameView
 * red.game.display -> A game can be composed of layers. EachLayer extends DisplayContainer
 * red.wordblocks -> code demonstrating the aspects of the game engine
 * red.wordblocks.screens -> classes that handle individual screens
 * red.wordblocks.game.core -> Game implementation core with layer definitions
 * red.wordblocks.game.mode -> Various actions that user can perform or the states in which user action can exist
 * red.wordblocks.game.tween -> custom animations helpers
 * red.wordblocks.game.config -> run time config specific to the game
 
## Note

 * The classes in red.game.* package can be simply dropped into your own project
 * To run the code first import the project sources into an eclipse-android project setup your project with the above package structure
