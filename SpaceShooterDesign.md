# Space Shooter Design Documentation

## Class Diagram

```mermaid
classDiagram
    class GameEntity {
        -position: Object
        +dimensions: Object
        +isActive: boolean
        +x: number
        +y: number
        +render(context)*
        +update(deltaTime)
    }
    class Player {
        -projectiles: Projectile[]
        -weapon: WeaponStrategy
        +speed: number
        +fire(timestamp)
        +setWeapon(weapon)
    }
    class Projectile {
        +velocity: Object
    }
    class Enemy {
        +speed: number
    }
    class WeaponStrategy {
        -cooldown: number
        -lastFired: number
        +canFire(timestamp): boolean
        +fire(x, y, projectiles, timestamp)*
    }
    class SingleShotWeapon
    class SpreadShotWeapon
    class RapidFireWeapon
    class GameStateSubject {
        -observers: Observer[]
        -state: Object
        +attach(observer)
        +updateScore(points)
        +notify()
    }
    class HUDObserver {
        -element: HTMLElement
        +update(state)
    }
    class GameEngine {
        -canvas: HTMLCanvasElement
        -context: CanvasRenderingContext2D
        +player: Player
        +enemies: Enemy[]
        +stateSubject: GameStateSubject
        +inputHandler: InputHandler
        +start()
        +stop()
    }
    class InputHandler {
        -keys: Map
        +getInput(): Object
    }
    GameEntity <|-- Player
    GameEntity <|-- Projectile
    GameEntity <|-- Enemy
    WeaponStrategy <|-- SingleShotWeapon
    WeaponStrategy <|-- SpreadShotWeapon
    WeaponStrategy <|-- RapidFireWeapon
    Player o--> WeaponStrategy
    Player o--> Projectile
    GameEngine o--> Player
    GameEngine o--> Enemy
    GameEngine o--> GameStateSubject
    GameEngine o--> InputHandler
    GameStateSubject o--> HUDObserver