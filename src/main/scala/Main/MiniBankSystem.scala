package Main

import akka.actor.{Actor, Props}

import scala.collection.mutable.ListBuffer
object MiniBankSystem{
  def props:Props = Props(new MiniBankSystem)
  case class NewAccount(name:String,gender:String,dob:String,city:String,deposit:Int)
  case class GetDetails(accountNumber:Int)
  case class MiniStatement(accountNumber:Int)
  case class Credit(accountNumber:Int,newAmount:Int)
  case class Debit(accountNumber:Int,amount:Int)
  case class AccountCreated(accountNumber:Int)
  case class GotDetails(accountNumber:Int)
  case class GotMiniStatement(accountNumber:Int)
  case class Credited(AccountNumber:Int,newAmount:Int)
  case class Debited(AccountNumber:Int,amount:Int)
}

class MiniBankSystem extends Actor{
  import MiniBankSystem._

  var AccountNumber:Int = 0
  var AccountDetails = scala.collection.mutable.Map[Int,ListBuffer[String]]()
  var miniStatementDetails = scala.collection.mutable.Map[Int,ListBuffer[String]]()

  override def receive: Receive = {
    case NewAccount(name,gender,dob,city,deposit) =>
      AccountNumber += 1
      AccountDetails(AccountNumber) = ListBuffer(AccountNumber.toString,name,gender,dob,city,deposit.toString)
      miniStatementDetails(AccountNumber) = ListBuffer(deposit.toString+" credited")
      sender() ! AccountCreated(AccountNumber)
    case GetDetails(accountNumber) =>
      if(!AccountDetails.contains(accountNumber)) println("Invalid account number")
      else println("Account number = "+AccountDetails(accountNumber)(0)+" name = "+AccountDetails(accountNumber)(1)+
        " gender = "+AccountDetails(accountNumber)(2)+" dob = "+AccountDetails(accountNumber)(3)+
        " city = "+AccountDetails(accountNumber)(4)+" balance = "+AccountDetails(accountNumber)(5))
      sender() ! GotDetails(AccountNumber)
    case MiniStatement(accountNumber) =>
      if(!AccountDetails.contains(accountNumber)) println("Invalid account number")
      else for(i <- miniStatementDetails(AccountNumber)) println(i)
      sender() ! GotMiniStatement(AccountNumber)
    case Credit(accountNumber,newAmount) =>
      if(!AccountDetails.contains(accountNumber)) println("Invalid account number")
      else {
        AccountDetails(AccountNumber)(5)=(AccountDetails(AccountNumber)(5).toInt+newAmount).toString
        miniStatementDetails(AccountNumber).addOne(newAmount.toString+" credited")
      }
      sender() ! Credited(accountNumber,newAmount)
    case Debit(accountNumber,amount) =>
      if(!AccountDetails.contains(accountNumber)) println("Invalid account number")
      else {
        var num = AccountDetails(AccountNumber)(5).toInt - amount
        if (num > 0) {
          miniStatementDetails(AccountNumber).addOne(amount + " debited")
          AccountDetails(AccountNumber)(5) = (AccountDetails(AccountNumber)(5).toInt-amount).toString
        }
      }
      sender() ! Debited(accountNumber,amount)
  }
}