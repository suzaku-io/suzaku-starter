# Suzaku starter
A starter project for using the Suzaku framework.

> **Note** Suzaku is still in experimental phase and this starter project is also very much Work In Progress!

# Getting started

First of all make sure you have the necessary tools installed. You'll need the following:

-   Git ([downloads](https://git-scm.com/downloads) for all platforms)
-   Java 8 JDK ([downloads](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) for all
    platforms)
-   SBT (setup for [Linux](http://www.scala-sbt.org/0.13/docs/Installing-sbt-on-Linux.html) \|
    [Windows](http://www.scala-sbt.org/0.13/docs/Installing-sbt-on-Windows.html) \|
    [Mac](http://www.scala-sbt.org/0.13/docs/Installing-sbt-on-Mac.html))

Next it's time to publish a local version of the [Suzaku](https://github.com/suzaku-io/suzaku) framework since it's
currently not available in public repositories.

1. `git clone https://github.com/suzaku-io/suzaku.git`
1. `cd suzaku`
1. `sbt publishLocal`
1. `cd ..`

Now you're ready to download and start using the starter project:

1. `git clone https://github.com/suzaku-io/suzaku-starter.git`
1. `cd suzaku-starter`
1. `sbt`
1. \> `~reStart`
1. Open browser at [`http://localhost:12121`](http://localhost:12121).

By using the `~reStart` command, sbt will automatically recompile your code and restart the server when you make changes.

# What next?

You can start experimenting with your Suzaku app by making changes to the `StarterApp.scala` file in the `client` project.
All the other files are mainly just necessary boilerplate to get the application up and running.

