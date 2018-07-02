# Elite Dangerous Commander Companion #

### edcc-server ###

* core functions: journal log file watchdog, events parser
* ver. 1.0.0-pre-alpha

### edcc-gui ###
* JavaFx GUI for edcc-server
* ver. 1.0.0-pre-alpha

### Set up ###

This project is written in Java 8 and uses [gradle](http://gradle.org) as a build tool. To build it you will need:

* [JRE](http://www.java.com/download/) 8+

### Running project ###

```
./gradlew jfxRun
```

Test journal location: `./edcc-server/src/test/resources/Journal.Voyak.log`
