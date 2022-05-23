package TestCases
import Main.{Customer, MiniBankSystem}
import Main.MiniBankSystem
import Main.MiniBankSystem.{AccountCreated, Credit, Credited, GetDetails, GotDetails, NewAccount}
import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpecLike


class Test1()
  extends TestKit(ActorSystem("TestCases"))
    with ImplicitSender
    with AnyWordSpecLike
    with Matchers
    with BeforeAndAfterAll {
      override def afterAll(): Unit = {
        TestKit.shutdownActorSystem(system)
      }
  "MiniBankSystem" must{

    "After creating new account" in{
      val sender = TestProbe()
      val bank = system.actorOf(Props[MiniBankSystem])
      sender.send(bank,NewAccount("vijay","M","24/12/2000","Jaipur",9000))
      sender.expectMsg(AccountCreated(1))
    }

    "show details" in{
      val sender = TestProbe()
      val bank = system.actorOf(Props[MiniBankSystem])
      sender.send(bank,GetDetails(1))
      sender.expectMsg(GotDetails(1))
    }

    "show credited" in{
      val sender = TestProbe()
      val bank = system.actorOf(Props[MiniBankSystem])
      sender.send(bank,Credit(1,1000))
      sender.expectMsg(Credited(1,1000))
    }

  }

}
