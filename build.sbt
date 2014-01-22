name := "our-wedding-site"

version := "0.1"

scalaVersion := "2.10.3"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
 
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.2.1"

libraryDependencies += "org.eclipse.jetty" % "jetty-server" % "9.0.7.v20131107"

libraryDependencies += "org.eclipse.jetty" % "jetty-servlet" % "9.0.7.v20131107"

libraryDependencies += "joda-time" % "joda-time" % "2.3"

libraryDependencies += "com.mchange" % "c3p0" % "0.9.2.1"

libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.1"

libraryDependencies += "postgresql" % "postgresql" % "9.1-901.jdbc4"

