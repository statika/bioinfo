package ohnosequences.statika.tests

import ohnosequences.statika._
import ohnosequences.statika.aws._
import distributions._

import cli.StatikaEC2._
import ohnosequences.awstools.ec2._
import ohnosequences.awstools.ec2.{Tag => Ec2Tag}
import java.io._
import org.scalatest._

class ApplicationTest extends FunSuite with ParallelTestExecution {

  // for running test you need to have this file in your project folder
  val ec2 = EC2.create(new File("AwsCredentials.properties"))

  def applyAndWait(name: String, specs: InstanceSpecs): Boolean =
    ec2.runInstances(1, specs) match {
      case List(inst) => {
        def status: Option[String] = inst.getTagValue("statika-status") 

        val id = inst.getInstanceId()
        var previous: Option[String] = None

        inst.createTag(Ec2Tag("Name", name))
        println(name+" ("+id+"): launched")

        while({val s = status; s != Some("failure") && s != Some("success")}) {
          val s = status
          if (s != previous) println(name+" ("+id+"): "+s.getOrElse("..."))
          previous = s
          Thread sleep 3000
        }
        val result = status == Some("success")
        if(result) inst.terminate()
        result
      }
      case _ => false
    }

  val dist = AmazonLinux

  def testBundle[B <: AnyBundle : dist.isMember : dist.isInstallable](bundle: B) = {
    test("Apply "+bundle.name+" bundle to an instance"){
      val userscript = dist.userScript(bundle, RoleCredentials)
      // println(userscript)

      val specs = InstanceSpecs(
          instanceType = InstanceType.InstanceType("c1.medium")
        , amiId = dist.ami.id
        , keyName = "statika-launcher" 
        , deviceMapping = Map()
        , userData = userscript
        , instanceProfileARN = Some("arn:aws:iam::857948138625:instance-profile/statika-private-resolver")
        )

      assert(applyAndWait(bundle.name, specs))
      // println(userscript)
    }
  }

  testBundle(Git)
  testBundle(S3cmd)
  testBundle(Velvet)
  testBundle(Bowtie)
  testBundle(Bowtie2)
  testBundle(Tophat)
  testBundle(Cufflinks)

}
