import scala.util.control.Breaks._
import scala.collection.mutable.ListBuffer
import scala.io.StdIn._

object Player extends App {

  //-----------------------------------FUNCTIONS-----------------------------------
  //GOING TO DIAGNOSIS
  def goDiagnosis(module :String,best_sample : Sample, position :String): Unit ={
    //if position or target is same as module, connect to sample
    if (position.equals(module)){
      println("CONNECT " + best_sample.toString)
    }else{
      //otherwise go to this module
      println("GOTO "+ module)
    }
  }

  //GOING TO MOLECULES
  def goMolecules(module: String, needed_molecule:String, position: String){
    //if position or target is same as module, connect to sample
    if (position.equals(module)){
      println("CONNECT " + needed_molecule)
    }else{
      //otherwise go to this module
      println("GOTO " + module)
    }
  }

  //GOING TO LABORATORY
  def goLaboratory(module: String, bestSampleID : Int, position: String): Unit ={
    //if position or target is same as module, connect to sample
    if (position.equals(module)){
      println("CONNECT " + bestSampleID)
    }else{
      //otherwise go to this module
      println("GOTO " + module)
    }
  }


  val projectCount = readLine.toInt
  for(i <- 0 until projectCount) {
    val Array(a, b, c, d, e) = (readLine split " ").map (_.toInt)
  }

  // game loop
  while(true) {

    //List for storing samples
    var allsamples = ListBuffer[Sample]()

    //List for storing robots
    var allrobots = ListBuffer[Robot]()


    for(i <- 0 until 2) {
      val Array(target, _eta, _score, _storageA, _storageB, _storageC, _storageD, _storageE, _expertiseA, _expertiseB, _expertiseC, _expertiseD, _expertiseE) = readLine split " "
      val eta = _eta.toInt
      val score = _score.toInt
      val storageA = _storageA.toInt
      val storageB = _storageB.toInt
      val storageC = _storageC.toInt
      val storageD = _storageD.toInt
      val storageE = _storageE.toInt
      val expertiseA = _expertiseA.toInt
      val expertiseB = _expertiseB.toInt
      val expertiseC = _expertiseC.toInt
      val expertiseD = _expertiseD.toInt
      val expertiseE = _expertiseE.toInt


      //create Robot object
      var robot1 = new Robot(List(storageA,storageB,storageC,storageD,storageE),target)

      //add this robot to robot list
      allrobots += robot1

    }
    val Array(availableA, availableB, availableC, availableD, availableE) = (readLine split " ").map (_.toInt)
    val sampleCount = readLine.toInt
    for(i <- 0 until sampleCount) {
      val Array(_sampleId, _carriedBy, _rank, expertiseGain, _health, _costA, _costB, _costC, _costD, _costE) = readLine split " "
      val sampleId = _sampleId.toInt
      val carriedBy = _carriedBy.toInt
      val rank = _rank.toInt
      val health = _health.toInt
      val costA = _costA.toInt
      val costB = _costB.toInt
      val costC = _costC.toInt
      val costD = _costD.toInt
      val costE = _costE.toInt

      //Create Sample object
      var sample1 = new Sample(sampleId,carriedBy,rank,expertiseGain,health,List(costA,costB,costC,costD,costE))

      //add this object to samples list
      allsamples += sample1
    }

    // Take the current robot (Also "var" could be a "val")
    var currentRobot = allrobots(0)

    // Creating object that can contain best Sample that can robot take
    var bestSample : Sample = null


    var maxHealth : Int = 0

    // Looking for best sample in Samples LIST
    for (sample <- allsamples){

      //max healthy sample is best to take if it is not in other robot
      if ((sample.health > maxHealth) & sample.carriedBy != 1 ){
        //Set this sample as BEST SAMPLE
        bestSample = sample
        maxHealth = sample.health
      }
    }


    //If bets sample is not carried by u
    if (bestSample.carriedBy != 0){
      //go to Diagnosis to connect this samples id or goto target
      goDiagnosis("DIAGNOSIS", bestSample, currentRobot.target)
    }else{
      var neededMolecule :String = null

      //take the molecule that needed
      breakable {
        for (i <- 0 to 4){
          if (currentRobot.storages(i) < bestSample.cost(i)) {
            neededMolecule = String.valueOf("ABCDE"(i))
            break
          }
        }
      }

      //if still need molecules
      if (neededMolecule != null){
        goMolecules("MOLECULES", neededMolecule, currentRobot.target)
      }
      //if every needed molecule has taken , then go to Laboratory to produce the medicines
      else{
        goLaboratory("LABORATORY", bestSample.id, currentRobot.target)
      }
    }


  }
}

//------------------------------------CLASSES------------------------------------
//ROBOT CLASS
class Robot(var storages:List[Int] ,var target: String){

  //writing manual constructor of List of storages and Robot`s Target
  setList(storages)
  setTarget(target)

  //GETTERS - STORAGE LIST, TARGET
  def getStorage() : List[Int] = storages
  def getTarget() : String = target

  //SETTER - STORAGE LIST, TARGET
  def setList(storages :List[Int]){
    this.storages = storages
  }
  def setTarget(target: String){
    this.target = target
  }


}

//SAMPLE CLASS
class Sample(var id : Int , var carriedBy : Int, var rank  :Int, var gain : String, var health: Int,var  cost: List[Int]){
  setId(id)
  setCarried_by(carriedBy)
  setRank(rank)
  setGain(gain)
  setHealth(health)
  setCost(cost)

  //GETTERS - ID, CARRIED BY INFORMATION, RANK, GAIN, HEALTH, COST
  def getId() : Int = id
  def getCarried_by() : Int = carriedBy
  def getRank() : Int = rank
  def getGain() : String = gain
  def getHealth() : Int = health
  def getCost() : List[Int] = cost

  //SETTERS - ID, CARRIED BY INFORMATION, RANK, GAIN, HEALTH, COST
  def setId(id :Int){
    this.id = id
  }
  def setCarried_by(carriedBy :Int){
    this.carriedBy = carriedBy
  }
  def setRank(rank :Int){
    this.rank = rank
  }
  def setGain(gain : String){
    this.gain = gain
  }
  def setHealth(health : Int): Unit ={
    this.health = health
  }
  def setCost(cost : List[Int]): Unit ={
    this.cost = cost
  }

  //override to "toString" function
  override def toString: String = {
    String.valueOf(this.id)
  }
}

