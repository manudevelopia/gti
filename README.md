# GTI
A Dependency Injection experimental library made with love to learn.

# Why?
Dependency injection has been always to me a magical feature. As far as I get more used with we application and apis, 
this feature has become a must. 

The more I use it the more I like until I decided to drop application frameworks (like Spring, Micronaut, etc) and 
I started using minimal frameworks or plain java, then I became aware that this feature was bounded to them with no 
easy alternative, so I decided to create a one with the minimal features that I need.

# How to Use it
Add dependency to your Gradle build.gradle or Maven pom.xml
```
plugins {
    id 'info.developia.gradle.docker.slimjar' version '1.0.1'
}
```

Add `Gti.startOn()` with the main application class. This will return the instance of the class.
```java
var application = Gti.startOn(Application.class);
application.run();
```

You can find an example of application that has a [Launcher](src/test/java/fixture/Launcher.java) ] that orchestrates application execution.
