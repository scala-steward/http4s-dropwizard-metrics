ThisBuild / tlBaseVersion := "0.23"
ThisBuild / tlMimaPreviousVersions ++= (0 to 11).map(y => s"0.23.$y").toSet
ThisBuild / developers := List(
  tlGitHubDev("rossabaker", "Ross A. Baker")
)

val Scala213 = "2.13.8"
ThisBuild / crossScalaVersions := Seq("2.12.15", Scala213, "3.1.2")
ThisBuild / scalaVersion := Scala213

lazy val root =
  project.in(file(".")).aggregate(dropwizardMetrics, example).enablePlugins(NoPublishPlugin)

val http4sVersion = "0.23.11"
val dropwizardMetricsVersion = "4.2.9"
val munitVersion = "0.7.29"
val munitCatsEffectVersion = "1.0.7"

lazy val dropwizardMetrics = project
  .in(file("dropwizard-metrics"))
  .settings(
    name := "http4s-dropwizard-metrics",
    description := "Support for Dropwizard Metrics",
    startYear := Some(2018),
    libraryDependencies ++= Seq(
      "org.http4s" %%% "http4s-core" % http4sVersion,
      "io.dropwizard.metrics" % "metrics-core" % dropwizardMetricsVersion,
      "io.dropwizard.metrics" % "metrics-json" % dropwizardMetricsVersion,
      "org.scalameta" %%% "munit-scalacheck" % munitVersion % Test,
      "org.typelevel" %%% "munit-cats-effect-3" % munitCatsEffectVersion % Test,
      "org.http4s" %%% "http4s-laws" % http4sVersion % Test,
      "org.http4s" %%% "http4s-server" % http4sVersion % Test,
      "org.http4s" %%% "http4s-client" % http4sVersion % Test,
      "org.http4s" %%% "http4s-dsl" % http4sVersion % Test,
    ),
  )

lazy val example = project
  .in(file("example"))
  .dependsOn(dropwizardMetrics)
  .settings(
    startYear := Some(2013),
    libraryDependencies ++= Seq(
      "org.http4s" %%% "http4s-ember-server" % http4sVersion,
      "org.http4s" %%% "http4s-dsl" % http4sVersion,
    ),
  )
  .enablePlugins(NoPublishPlugin)

lazy val docs = project
  .in(file("site"))
  .dependsOn(dropwizardMetrics)
  .settings(
    libraryDependencies ++= Seq(
      "org.http4s" %%% "http4s-server" % http4sVersion,
      "org.http4s" %%% "http4s-client" % http4sVersion,
      "org.http4s" %%% "http4s-dsl" % http4sVersion,
    )
  )
  .enablePlugins(Http4sOrgSitePlugin)
