package game.lo4_data_structures.graphs

import game.lo4_data_structures.linkedlist.LinkedListNode

class Queue[A] {

  var front: LinkedListNode[A] = null
  var back: LinkedListNode[A] = null

  def empty(): Boolean = {
    front == null
  }

  def enqueue(a: A): Unit = {
    if (back == null) {
      this.back = new LinkedListNode[A](a, null)
      this.front = this.back
    } else {
      this.back.next = new LinkedListNode[A](a, null)
      this.back = this.back.next
    }
  }

  def dequeue(): A = {
    val toReturn = this.front.value
    this.front = this.front.next
    if (this.front == null) {
      this.back = null
    }
    toReturn
  }

  def contains(node: A): Boolean = {
    var current = this.front
    while (current != null) {
      if (current.value == node) {
        return true
      }
      current = current.next
    }
    false
  }


}
