sra-deploy-plugin
=================

This plugin integrates Jenkins functionality with Serena Deployment Automation.

For more information on the capabilities of this plugin, please see the plugin wiki located at:
https://wiki.jenkins-ci.org/display/JENKINS/Serena+Deploy+Plugin

Build Instructions
------------------

You will need Apache Maven and a Java JDK 1.6 or later to build the plugin.

To successfully build the plugin using maven you will need to install a number of Serena DA libraries into your local maven (.m2) cache.
To do this navigate to the WEB-INF\lib directory of a Serena Deployment Automation installation and execute the following commands:

```
>mvn install:install-file -Dfile=.\serenara-client-CURRENT.jar -DgroupId=com.urbancode.vfs -DartifactId=serenara-client -Dversion=CURRENT -Dpackaging=jar
>mvn install:install-file -Dfile=.\serenara-vfs-CURRENT.jar -DgroupId=com.urbancode.vfs -DartifactId=serenara-vfs -Dversion=CURRENT -Dpackaging=jar
>mvn install:install-file -Dfile=.\commons-fileutils-CURRENT.jar  -DgroupId=com.urbancode.vfs  -DartifactId=commons-fileutils -Dversion=CURRENT -Dpackaging=jar
>mvn install:install-file -Dfile=.\commons-util-CURRENT.jar -DgroupId=com.urbancode.vfs -DartifactId=commons-util -Dversion=CURRENT -Dpackaging=jar
```

Thank you!
