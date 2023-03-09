# AI Game

In this project, I programmed the AI of the enemy characters in a 2D top-down game to compete with the player and other AI players. The game involves moving your character with "WASD" or arrow keys and firing a projectile with the space bar. The enemies wander aimlessly until I programmed their AI.

<img width="307" alt="Screenshot 2023-03-09 at 4 55 50 PM" src="https://user-images.githubusercontent.com/111472051/224169108-ced02127-7b0d-4657-8673-41448f8296ce.png">

## Linked-Lists

### Functionality

The "locatePlayer" method takes in a String representing the player ID and a LinkedListNode of PlayerLocation objects, and returns the PlayerLocation object for the player with the given ID. The "closestPlayer" method takes in a LinkedListNode of PlayerLocation objects and returns the PlayerLocation object for the player closest to the current AI player. The distance between players is calculated using Euclidean distance, and the current AI player is not returned. The LinkedListNode is used to store references to PlayerLocation objects in a linked list. Finally, the "computePath" method takes in two GridLocation objects representing the start and end points of a path, and returns the minimal path between the two points as a LinkedListNode of GridLocations. The path is computed by connecting adjacent GridLocations in valid directions (up, down, left, and right), and diagonal moves are not allowed. The LinkedListNode is again used to store references to GridLocation objects in a linked list.

## Trees

### Functionality

The "makeDecision" method takes in an AIGameState and a BinaryTreeNode of DecisionTreeValue as parameters. It returns the action determined by the decision tree rooted at the given BinaryTreeNode. The decision tree is used to determine which action the AI will take for the given game state. The check method is called on the DecisionTreeValue to determine which direction to navigate the tree. If a negative number is returned, the left child of the node is navigated to. If a positive number is returned, the right child of the node is navigated to. If 0 is returned, the action method is called to determine which action to take and return this action. This function improves the behavior of the AI, making it move towards the closest player and fire a projectile when close to another player. The next task is to program the AI to avoid walls.

## Graphs and BFS

This program includes a method named "distanceAvoidWalls" with three parameters: an instance of the AIGameState class, and two GridLocation objects. The purpose of this method is to calculate the distance between the two input GridLocations while avoiding all walls in the game state. The method returns an integer value that represents the length of the shortest path between the two locations without any wall tiles. Diagonal movements are not allowed.

The function operates by using the game state parameter to find the locations of all the walls that need to be avoided. The AIGameState class includes a method named "levelAsGraph," which returns a graph of GridLocations representing the level. Each node in this graph is a location in the level that does not contain a wall. Each node has an edge connecting it to each adjacent grid location (up/down/left/right) that is part of the level and is not a wall. In other words, each edge is a valid movement in the level. The id of each node in this graph is equal to its y location times the width of the level plus its x location.

To find the distance between the two GridLocations while avoiding all walls, the method utilizes the breadth-first search (BFS) algorithm. BFS starts at the starting GridLocation, and it visits all neighboring GridLocations to calculate the shortest distance between them. During this process, BFS checks if any neighboring GridLocations contain walls, and it avoids them. Finally, it returns the shortest path as the distance between the two GridLocations.

#### Examples:

If the inputs are (2,3) and (5,5) with no walls in the way, a shortest path would be (2, 3) -> (3, 3) -> (4, 3) -> (5, 3) -> (5, 4) -> (5, 5), which has length 5. Therefore, the method should return 5 for the distance.
If the inputs (2, 9) and (4, 9) with walls at (3, 10), (3, 9), (3, 8), and (3, 7), then the shortest path is (2, 9) -> (2, 10) -> (2, 11) -> (3, 11) -> (4, 11) -> (4, 10) -> (4, 9) for a distance of 6.
