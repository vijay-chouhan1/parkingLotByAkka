package Main

import Main.MiniBankSystem._
import akka.actor.ActorSystem

object Operator {
  def main(args:Array[String]): Unit ={
    val actorSystem = ActorSystem("new")
    val bank = actorSystem.actorOf(MiniBankSystem.props,"bank")
    val customer1 = actorSystem.actorOf(Customer.props(bank))
    customer1 ! NewAccount("vijay","M","24/12/2000","Jaipur",9000)
    customer1 ! GetDetails(1)
    customer1 ! Credit(1,1000)
    customer1 ! Debit(1,2500)
    customer1 ! MiniStatement(1)
    val customer2 = actorSystem.actorOf(Customer.props(bank))
    customer2 ! NewAccount("Shubham","M","08/01/2000","Bhilwara",12000)
    customer2 ! GetDetails(2)
    customer2 ! Credit(1,1500)
    customer2 ! Debit(1,3500)
    customer2 ! MiniStatement(2)


    customer1 ! GetDetails(1)
    customer2 ! GetDetails(2)
    actorSystem.terminate()
  }
}
