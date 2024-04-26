#!/bin/bash

#javac ./game/src/utils/*.java
javac -classpath "./controle/src/misc/json.jar;." ./game/src/controller/*.java
javac -classpath "./controle/src/misc/json.jar;." ./game/src/App.java
