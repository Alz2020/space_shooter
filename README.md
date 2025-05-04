Space Shooter Game

Overview
Space Shooter is a dual-implementation game developed in both JavaScript (web-based) and Java (console-based) to demonstrate proficiency in object-oriented programming, design patterns, modular architecture, and UML documentation. The game challenges players to control a spaceship, shoot enemies, and earn points using different weapon strategies, with a focus on scalable design and robust error handling.
The project showcases:

Object-Oriented Principles: Encapsulation, inheritance, and polymorphism through classes like GameObject, Player, and Enemy.
Design Patterns: Strategy pattern for weapon switching (WeaponStrategy, SingleShot, SpreadShot, RapidFire) and Observer pattern for score updates (ScoreSubject, ScoreDisplay).
System Architecture: Modular design with separated concerns (game logic, rendering, input handling) and UML diagrams for clarity.
Code Quality: Readable code, comprehensive error handling (e.g., Mermaid.js fallbacks, try-catch blocks), and detailed documentation.
UML Diagrams: Exemplary class diagrams in space_shooter.html (via Mermaid.js) and SpaceShooterDesign.md, illustrating the system architecture.

Project Structure
The project consists of the following files:

space_shooter.html: The web-based game implementation using HTML5 Canvas and JavaScript. It includes the game logic, a rendered UML class diagram (via Mermaid.js), and links to styles.css for styling.
styles.css: CSS styles for the web game, covering the canvas, game container, score display, controls, and UML diagram.
SpaceShooter.java: The console-based game implementation in Java, using a text-based interface for gameplay.
SpaceShooterDesign.md: Markdown documentation containing a UML class diagram that outlines the system architecture and design rationale.
README.md: This file, providing an overview, setup instructions, and usage details.

Features

Gameplay:
Control a player spaceship to shoot enemies and earn points.
Switch between three weapon types: Single Shot, Spread Shot, and Rapid Fire.
Uses Arrow Keys to move, Space to shoot, 1-3 to switch weapons.
Console version: Uses a/d to move, Space to shoot, 1/2 to switch weapons, q to quit.

Running the Web 

Open a browser and visit http://127.0.0.1:5500/space_shooter.html.

UML Diagrams:
space_shooter.html renders the UML diagram dynamically using Mermaid.js.
SpaceShooterDesign.md provides a static UML diagram for design reference.
Both diagrams detail class relationships, attributes, and methods, aligning with the code.

Design Patterns:
Strategy Pattern: WeaponStrategy interface/class with SingleShot, SpreadShot, and RapidFire implementations, enabling dynamic weapon switching.
Observer Pattern: ScoreSubject notifies ScoreDisplay of score changes, ensuring decoupled UI updates.

Modular Design:
Separated concerns: Game logic (SpaceShooter), rendering (draw/render), input handling (initControls/Scanner).
CSS in styles.css for maintainable styling.

Developed by Ali Alzabidi