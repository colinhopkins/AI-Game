package game.enemyai

import game.enemyai.decisiontree.DecisionTreeValue
import game.lo4_data_structures.graphs.Graph
import game.lo4_data_structures.linkedlist.LinkedListNode
import game.lo4_data_structures.trees.BinaryTreeNode
import game.maps.GridLocation
import game.{AIAction, MovePlayer}
import game.enemyai.AIGameState
import game.lo4_data_structures.graphs.Queue


class AIPlayer(val id: String) {

  def locatePlayer(playerId: String, playerLocations: LinkedListNode[PlayerLocation]): PlayerLocation = {
    if (playerId == playerLocations.value.playerId) {
      new PlayerLocation(playerLocations.value.x, playerLocations.value.y, playerLocations.value.playerId)
    }
    else {
      locatePlayer(playerId, playerLocations.next)
    }
  }

  def closestPlayer(playerLocations: LinkedListNode[PlayerLocation]): PlayerLocation = {

    val AIPlayer = locatePlayer(this.id, playerLocations)
    var location = new PlayerLocation(0.0, 0.0, "")
    var num = 100000.0

    val distancesList = for (data <- playerLocations) yield {
      distanceFormula(AIPlayer.x, data.x, AIPlayer.y, data.y)
    }

    for (line <- distancesList) {
      if (line != 0.0 && line < num) {
        num = line
      }
    }
    for (data <- playerLocations) {
      if (distanceFormula(AIPlayer.x, data.x, AIPlayer.y, data.y) == num) {
        location = new PlayerLocation(data.x, data.y, data.playerId)
      }
    }
    location
  }

  def distanceFormula(x1: Double, x2: Double, y1: Double, y2: Double): Double = {
    Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2))
  }

  def computePath(start: GridLocation, end: GridLocation): LinkedListNode[GridLocation] = {
    val gridLocation = new LinkedListNode[GridLocation](start, null)
    gridLocation.insert(new GridLocation(end.x, end.y))

    if (start.x == end.x && start.y < end.y) {
      for (i <- 1 until end.y - start.y) {
        gridLocation.insert(new GridLocation(end.x, end.y - i))
      }
    }
    if (start.x == end.x && start.y > end.y) {
      for (i <- 1 until start.y - end.y) {
        gridLocation.insert(new GridLocation(end.x, end.y + i))
      }
    }
    if (start.y == end.y && start.x < end.x) {
      for (i <- 1 until end.y - start.y) {
        gridLocation.insert(new GridLocation(end.x - i, end.y))
      }
    }
    if (start.y == end.y && start.x > end.x) {
      for (i <- 1 until start.y - end.y) {
        gridLocation.insert(new GridLocation(end.x + i, end.y))
      }
    }
    if (start.x < end.x && start.y < end.y) {
      for (i <- 1 to (end.x - start.x)) {
        gridLocation.insert(new GridLocation(end.x - i, end.y))
      }
      for (e <- 1 until end.y - start.y) {
        gridLocation.insert(new GridLocation(start.x, end.y - e))
      }
    }
    if (start.x > end.x && start.y > end.y) {
      for (i <- 1 to (start.x - end.x)) {
        gridLocation.insert(new GridLocation(end.x + i, end.y))
      }
      for (e <- 1 until start.y - end.y) {
        gridLocation.insert(new GridLocation(start.x, end.y + e))
      }
    }
    if (start.x < end.x && start.y > end.y) {
      for (i <- 1 to (end.x - start.x)) {
        gridLocation.insert(new GridLocation(end.x - i, end.y))
      }
      for (e <- 1 until start.y - end.y) {
        gridLocation.insert(new GridLocation(start.x, end.y + e))
      }
    }
    if (start.x > end.x && start.y < end.y) {
      for (i <- 1 to (start.x - end.x)) {
        gridLocation.insert(new GridLocation(end.x + i, end.y))
      }
      for (e <- 1 until end.y - start.y) {
        gridLocation.insert(new GridLocation(start.x, end.y - e))
      }
    }

    gridLocation

  }

  def makeDecision(gameState: AIGameState, decisionTree: BinaryTreeNode[DecisionTreeValue]): AIAction = {
    val node = makeDecisionHelper(gameState, decisionTree)
    node.value.action(gameState)

  }

  def makeDecisionHelper(gameState: AIGameState, decisionTree: BinaryTreeNode[DecisionTreeValue]): BinaryTreeNode[DecisionTreeValue] = {

    if (decisionTree.value.check(gameState) == 0) {
      decisionTree
    }
    else if (decisionTree.value.check(gameState) > 0) {
      makeDecisionHelper(gameState, decisionTree.right)
    }
    else {
      makeDecisionHelper(gameState, decisionTree.left)

    }
  }

  def distanceAvoidWalls(gameState: AIGameState, location1: GridLocation, location2: GridLocation): Int = {

    val gridID: GridLocation => Int = location => location.x + location.y * gameState.levelWidth
    val graph: Graph[GridLocation] = gameState.levelAsGraph()
    var distance = 0
    println(graph.nodes)
    val y = (bfs(graph, gridID(location1)))

    //println(y)
    for ((a, b) <- y) {
      if (a == gridID(location2)) {
        distance += b
      }
    }
    distance
  }

  def bfs(graph: Graph[GridLocation], startID: Int): Map[Int, Int] = {

    var explored: Set[Int] = Set()
    var levels: Map[Int, Int] = Map()
    levels += (startID -> 0)
    val toExplore: Queue[Int] = new Queue()
    toExplore.enqueue(startID)

    while (!toExplore.empty()) {
      val next = toExplore.dequeue()
      println(next)
      for (node <- graph.adjacencyList(next)) {
        if (!explored.contains(node) && !toExplore.contains(node)) {
          toExplore.enqueue(node)
          levels += (node -> (levels(next) + 1))
        }
      }
      explored = explored + next

      println(levels)
    }
    levels
  }


  def BFS[A](graph: Graph[A], startID: Int): Map[Int, A] = {

    var distance = 0
    var levels: Map[Int, A] = Map()
    val toExplore: Queue[Int] = new Queue()
    val explored: Queue[Int] = new Queue()
    toExplore.enqueue(startID)

    while (!toExplore.empty()) {
      val nodeToExplore = toExplore.dequeue()
      for (node <- graph.adjacencyList(nodeToExplore)) {
        if (!explored.contains(node) && !toExplore.contains(node)) {
          levels += (distance -> graph.nodes(node))
          distance += 1
          println("Exploring: " + graph.nodes(node))
          //println(levels)
          toExplore.enqueue(node)
        }
      }
      explored.enqueue(nodeToExplore)
    }
    levels
  }
  def Bfs(graph: Graph[GridLocation], startID: Int): Map[Int, List[Int]] = {
    var explored: Set[Int] = Set()
    var levels: Map[Int, List[Int]] = Map()
    val toExplore: Queue[Int] = new Queue()
    toExplore.enqueue(startID)
    while (!toExplore.empty()) {
      val next = toExplore.dequeue()
      var retList: List[Int] = List()
      for (node <- graph.adjacencyList(next)) {
        if (!explored.contains(node)) {
          toExplore.enqueue(node)
          explored = explored + node
          retList = retList :+ node
        }
      }
      levels = levels + (next -> retList)
    }
    println(levels)
    levels
  }


  // TODO: Replace this placeholder code with your own
  def closestPlayerAvoidWalls(gameState: AIGameState): PlayerLocation = {
    closestPlayer(gameState.playerLocations)
  }

  // TODO: Replace this placeholder code with your own
  def getPath(gameState: AIGameState): LinkedListNode[GridLocation] = {
    computePath(locatePlayer(this.id, gameState.playerLocations).asGridLocation(), closestPlayerAvoidWalls(gameState).asGridLocation())
  }


}

