name := "gdbscan-spray"

version := "1.0"

scalaVersion := "2.11.8"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV = "2.3.9"
  val sprayV = "1.3.3"
  Seq(
    "io.spray"            %%  "spray-can"     % sprayV,
    "io.spray"            %%  "spray-routing" % sprayV,
    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
    "io.spray" 			  %%  "spray-json"    % "1.3.2",
    
    "org.apache.spark"    %% "spark-core"     % "1.6.1",
    "org.scalanlp"        %% "nak"            % "1.3",
    "org.scalanlp" 		  %% "breeze-natives" % "0.8" % "test, runtime",
    
    "io.spray"            %%  "spray-testkit" % sprayV  % "test",
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV   % "test",
    "org.specs2"          %%  "specs2-core"   % "3.7.2" % "test",
    "org.scalacheck" 	  %% "scalacheck"     % "1.13.0" % "test"
  )
}
// for spec2
scalacOptions in Test ++= Seq("-Yrangepos")

mainClass in (Compile, run) := Some("com.example.Boot")

EclipseKeys.withSource := false

// for "re-start re-stop"
Revolver.settings
