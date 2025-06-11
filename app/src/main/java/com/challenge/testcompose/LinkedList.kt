package com.challenge.testcompose

class Node(val next: Node?, val value: Any)
{



}



//hello somaye




//var headNode: Node?  = null
var cachList : HashSet<Node>   = HashSet()

fun hasCycle(head: Node) : Boolean
{
  /*  if (headNode == null)
        headNode = head
*/

   if ( cachList.contains(head))
       return  true
    else cachList.add(head)


    while (head.next!=null)
    {
       return hasCycle(head.next)
    }


    return  false
}

