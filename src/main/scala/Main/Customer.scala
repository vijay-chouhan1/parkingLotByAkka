package Main

import Main.MiniBankSystem._
import akka.actor.{Actor, ActorRef, Props}

object Customer{
  def props(miniBank:ActorRef):Props =Props(new Customer(miniBank))
  case class AccountCreated(accountNum:Int)
  case class GotDetails(accountNum:Int)
  case class GotMiniStatement(accountNum:Int)
  case class Credited(AccountNum:Int,amount:Int)
  case class Debited(AccountNum:Int,amount:Int)
}

class Customer(miniBank:ActorRef) extends Actor{
  override def receive: Receive = {
    case NewAccount(name:String,gender:String,dob:String,city:String,deposit:Int) =>
      miniBank ! NewAccount(name:String,gender:String,dob:String,city:String,deposit:Int)
    case GetDetails(accountNumber:Int) => miniBank ! GetDetails(accountNumber:Int)
    case MiniStatement(accountNumber:Int) => miniBank ! MiniStatement(accountNumber:Int)
    case Credit(accountNumber:Int,newAmount:Int) => miniBank ! Credit(accountNumber:Int,newAmount:Int)
    case Debit(accountNumber:Int,amount:Int) => miniBank ! Debit(accountNumber:Int,amount:Int)
    case AccountCreated(accountNum) =>
      context.system.log.info(s"Account Created Successfully with account Num : $accountNum")
      println(s"Account Created Successfully with account Num : $accountNum")
    case GotDetails(accountNum:Int) =>
      println(s"Details for account number $accountNum is printed")
    case GotMiniStatement(accountNum:Int) =>
      println(s"Mini statement for account number $accountNum is printed")
    case Credited(accountNum:Int,amount:Int) =>
      println(s"$amount is debited from account number $accountNum")
    case Debited(accountNum:Int,amount:Int) =>
        println(s"$amount is debited from account number $accountNum")
    case msg:String => println(msg)
  }

}

