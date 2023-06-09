# Sloth Engine
<img src="https://i.imgur.com/aIGEXcp.png" align="right" title="no-idea-engine logo">

Sloth Engine's goal is to be a general-purpose Java game engine which is heavily inspired by the architecture of Game Maker Studio 1.x. The engine is implemented in a way to have a detached backend. Included within this repository is a JavaFX backend, however in the future I intend to port over the OpenGL backend from no-idea-engine.

The primary purpose of the engine is currently 2D. At the moment I don't have any plans to implement 3D as the scope of the engine will balloon exponentially. However once the features are stable enough and the OpenGL backend gets implemented, I may start adding basic 3D functionality.

# Features

## The Game

The JavaFX backend is used to create and run a game:
```java
Game game = new FxGame(gameState);
game.run();
```

## Resource Management

Resources are specified in a game.json file and automatically loaded:
```json
{
  "resources": {
    "images": [
      {
        "filename": "data/sloth.png",
        "key":"sloth"
      }
    ]
  }
}
```

## States and Scenes
Todo

## Images and Sprites
Todo

## Fonts
Todo

## Sounds and Music
Todo

## Entities
Todo