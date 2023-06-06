resolvers ++= Resolver.sonatypeOssRepos("snapshots")
resolvers ++= Seq(
  Classpaths.typesafeReleases,
  Classpaths.sbtPluginReleases,
  "jgit-repo" at "https://download.eclipse.org/jgit/maven"
)

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "2.0.8")
addSbtPlugin("com.github.sbt" % "sbt-unidoc" % "0.5.0")
addSbtPlugin("com.github.sbt" % "sbt-pgp" % "2.2.1")
addSbtPlugin("com.github.sbt" % "sbt-release" % "1.1.0")
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "3.9.21")
addSbtPlugin("pl.project13.scala" % "sbt-jmh" % "0.4.5")
addSbtPlugin("com.47deg" % "sbt-microsites" % "1.4.3")
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.5.0")
addSbtPlugin("org.wartremover" % "sbt-wartremover" % "3.1.3")
addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.11.0")

ThisBuild / libraryDependencySchemes +=
  "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
