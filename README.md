# Software Engineering Project - 2023-2024

![alt text](src/main/resources/images/logo.png)

Digitalization of the game [Codex Naturalis](https://www.craniocreations.it/prodotto/codex-naturalis).
The project consists of a single executable .jar file which can be used to deploy either a game server or a client.
A server can manage multiple games simultaneously, each game is made of 2 to 4 players.
Each player can choose to play using either the Command Line Interface or the Graphical User Interface.
The project relies on the Model-View-Controller design pattern and on a Visitor pattern for server request-reply dispatch.
More information about design patterns and project's architecture can be found [here](deliverables/).

## Documentation

### UML

UML diagrams can be found below:

- [Model]()
- [Client]()
- [Network](https://github.com/SigCatta/prog-ingsw-The_Compiler_Coalition/blob/main/deliveries/UML/network.jpg)

### JavaDoc

The following documentation includes information about most relevant classes and methods: [Javadoc]()

### Test Coverage

The JUnit [tests](src/test/java) provide a model line coverage of --% and an overall line coverage of --%.

### Tools

| Lib/Plugin   | Descrizione           |
|--------------|-----------------------|
| __Maven__    | Build automation tool |
| __JavaFx__   | Java GUI Framework    |
| __JUnit__    | Testing Framework     |
| __PlantUML__ | UML design tool       |

## Features

### Developed Features

- Complete Rules
- CLI 
- GUI
- __Advanced Features__:
    - Chat: players can exchange messages on a match-specific chat.
    - Multiple Games: The server must be able to simultaneously manage multiple games.

## Running the application

The [precompiled jar]() can be used to run the application. <br />
To independently create a client application jar, execute the following command in the project root:

```
mvn clean install assembly:single
```

The compiled jar will be found in ```target/``` under the name ```my-shelfie-client-jar-with-dependencies.jar```. <br />
To run the application launcher, open the terminal and run ```java -jar -----.jar``` or ```java -jar -------.jar```.
The user can choose to run a server or a client, either on the CLI or GUI, after the application has started.

## Group members

- [__Abate Kevin Pio__](https://github.com/)
- [__Bottaro Simone Pio__](https://github.com/)
- [__Cestele Guido__](https://github.com/)
- [__Pigato Lorenzo__](https://github.com/lorenzo-pigato)