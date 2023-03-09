package tests

import com.fasterxml.jackson.databind.util.LinkedNode
import game.enemyai.PlayerLocation
import game.lo4_data_structures.linkedlist.LinkedListNode
import org.scalatest._
import game.enemyai.AIPlayer
import game.maps.GridLocation

class Task1 extends FunSuite {


  test("test PlayerLocation") {
    val location1 = new PlayerLocation(1, 2, "abc")
    val location2 = new PlayerLocation(3, 4, "xyz")
    val location3 = new PlayerLocation(5, 6, "cmh")
    val location4 = new PlayerLocation(7, 8, "123")

    var myList: LinkedListNode[PlayerLocation] = new LinkedListNode[PlayerLocation](location1, null)
    myList = new LinkedListNode[PlayerLocation](location2, myList)
    myList = new LinkedListNode[PlayerLocation](location3, myList)
    myList = new LinkedListNode[PlayerLocation](location4, myList)

    for (x <- myList) {
      println(x.playerId)
    }

    val x = new AIPlayer("abc")
    val computed = x.locatePlayer("xyz", myList)
    val expected = new PlayerLocation(3, 4, "xyz")

    val computed2 = x.locatePlayer("abc", myList)
    val expected2 = new PlayerLocation(1, 2, "abc")

    assert(computed.x == expected.x)
    assert(computed.y == expected.y)
    assert(computed.playerId == expected.playerId)

    assert(computed2.x == expected2.x)
    assert(computed2.y == expected2.y)
    assert(computed2.playerId == expected2.playerId)


  }

  test("test closestDistance") {
    val location1 = new PlayerLocation(1, 2, "abc")
    val location2 = new PlayerLocation(3, 4, "xyz")
    val location3 = new PlayerLocation(1.5, 2.2, "cmh")

    var myList: LinkedListNode[PlayerLocation] = new LinkedListNode[PlayerLocation](location1, null)
    myList = new LinkedListNode[PlayerLocation](location2, myList)
    myList = new LinkedListNode[PlayerLocation](location3, myList)

    val x = new AIPlayer("xyz")
    val computed = x.closestPlayer(myList)
    val expected = new PlayerLocation(1.5, 2.2, "cmh")

    val y = new AIPlayer("cmh")
    val computed2 = y.closestPlayer(myList)
    val expected2 = new PlayerLocation(1, 2, "abc")


    assert(computed.x == expected.x)
    assert(computed.y == expected.y)
    assert(computed.playerId == expected.playerId)

    assert(computed2.x == expected2.x)
    assert(computed2.y == expected2.y)
    assert(computed2.playerId == expected2.playerId)


  }
  test("test computePath") {
    val start = new GridLocation(1, 3)
    val end = new GridLocation(2, 2)

    val startpoint = new GridLocation(5, 6)
    val endpoint = new GridLocation(1, 1)

    val start1 = new GridLocation(1, 1)
    val end1 = new GridLocation(1, 2)


    val x = new AIPlayer("xyz")

    val newList = x.computePath(start1, end1)
    assert(newList.value == start1)
    assert(newList.next.value == end1)
    assert(newList.size() == 2)

    val myList1 = x.computePath(start, end)
    assert(myList1.value == start)
    assert(myList1.size() == 3)

    val myList2 = x.computePath(end, start)
    assert(myList2.value == end)
    assert(myList2.size() == 3)

    val aList1 = x.computePath(startpoint, endpoint)
    assert(aList1.value == startpoint)
    assert(aList1.size() == 10)

    val aList2 = x.computePath(endpoint, startpoint)
    assert(aList2.value == endpoint)
    assert(aList2.size() == 10)

    val aList3 = x.computePath(start1, end1)
    assert(aList3.value == start1)
    assert(aList3.size() == 2)

    val aList4 = x.computePath(end1, start1)
    assert(aList4.value == end1)
    assert(aList4.size() == 2)

  }


}


