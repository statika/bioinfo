name := "statika-distributions"

description := "statika distributions"

homepage := Some(url("https://github.com/ohnosequences/statika-distributions"))

organization := "ohnosequences"

organizationHomepage := Some(url("http://ohnosequences.com"))

licenses := Seq("AGPLv3" -> url("http://www.gnu.org/licenses/agpl-3.0.txt"))


publishMavenStyle := true

publishBucketSuffix := "era7.com"

bucketSuffix := "statika.ohnosequences.com"


bundleObjects := Seq("ohnosequences.statika.distributions.AmazonLinux")


libraryDependencies ++= Seq(
    "ohnosequences" % "statika-cli_2.10.2" % "0.15.2" % "test"
  , "ohnosequences" %% "ami-44939930" % "0.9.0-SNAPSHOT"
  // members: //
  , "ohnosequences" %% "git" % "0.7.0-SNAPSHOT"
  , "ohnosequences" %% "gcc" % "0.2.0-SNAPSHOT"
  , "ohnosequences" %% "zlib-devel" % "0.2.0-SNAPSHOT"
  , "ohnosequences" %% "velvet" % "0.2.0-SNAPSHOT"
  // , "ohnosequences" %% "boost" % "0.1.0"
  // , "ohnosequences" %% "bowtie" % "0.1.0"
  // , "ohnosequences" %% "tophat" % "0.1.0"
  // , "ohnosequences" %% "cufflinks" % "0.1.0"
  // , "ohnosequences" %% "python" % "0.1.1"
  // , "ohnosequences" %% "s3cmd" % "0.1.0"
  )


// Running test in parallel
testOptions in Test += Tests.Argument("-P12")

// Showing time spent on each test
testOptions in Test += Tests.Argument("-oD")
