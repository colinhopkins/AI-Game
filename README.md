# AI Game

In this project, I programmed the AI of the enemy characters in a 2D top-down game to compete with the player and other AI players. The game involves moving your character with "WASD" or arrow keys and firing a projectile with the space bar. The enemies wander aimlessly until I programmed their AI.

<img width="307" alt="Screenshot 2023-03-09 at 4 55 50 PM" src="https://user-images.githubusercontent.com/111472051/224169108-ced02127-7b0d-4657-8673-41448f8296ce.png">

## Linked-Lists

### Functionality

A method named "locatePlayer" with:
Parameters of type String and LinkedListNode of PlayerLocation
The String represents the player id of the player to locate
The LinkedListNode is the head of a list containing references to PlayerLocation objects
Returns the PlayerLocation of the player with the input id
A method named "closestPlayer" with:
One parameter of type LinkedListNode of PlayerLocation
Returns the PlayerLocation of the closest player to this AI player
Do not return this player itself (eg. the distance would be 0 and it would always be returned)
You have access to the id of the this player (The constructor parameter) which can be used to find the location of this player
Distance should be calculated as Euclidean distance aka L2 (Square root of the sum of the squares of the differences in x and y)
You can assume that the list contains this player and has length at least 2 (There is at least one other player in the list) so there will always be a valid answer to return
A method named "computePath" with:
Two parameters of type GridLocation representing the start then end of the path to compute
Returns a path from the start to end locations as a LinkedListNode of GridLocations
Each GridLocation on the path must be connected to each adjacent GridLocation
Valid path connections only travel up, down, left, and right. Diagonal moves are not allowed
The returned path must contain the minimal number of GridLocations possible
For testing, keep in mind that there are many valid paths that can be returned. As long as the path is valid and has the minimal possible length, it should pass your tests


## Trees

### Functionality

A method named "makeDecision" with:
Parameters of type AIGameState and BinaryTreeNode of DecisionTreeValue
The BinaryTreeNode is the root of a decision tree that will be used to determine which action the AI will take for this game state
Returns the action determined by the decision tree
To determine the action, first call the check method on the DecisionTreeValue
If a negative number is returned, navigate to the left child of the node
If a positive number is returned, navigate to the right child of the node
If 0 is returned, call the action method to determine which action to take and return this action
Once I completed this task, I could run my server again and see that the AI improved behavior. They moved towards the closest player and fired a projectile when they were close to another player. The next goal was to program the AI to avoid walls.

## Graphs and BFS

This program includes a method named "distanceAvoidWalls" with three parameters: an instance of the AIGameState class, and two GridLocation objects. The purpose of this method is to calculate the distance between the two input GridLocations while avoiding all walls in the game state. The method returns an integer value that represents the length of the shortest path between the two locations without any wall tiles. Diagonal movements are not allowed.

The function operates by using the game state parameter to find the locations of all the walls that need to be avoided. The AIGameState class includes a method named "levelAsGraph," which returns a graph of GridLocations representing the level. Each node in this graph is a location in the level that does not contain a wall. Each node has an edge connecting it to each adjacent grid location (up/down/left/right) that is part of the level and is not a wall. In other words, each edge is a valid movement in the level. The id of each node in this graph is equal to its y location times the width of the level plus its x location.

To find the distance between the two GridLocations while avoiding all walls, the method utilizes the breadth-first search (BFS) algorithm. BFS starts at the starting GridLocation, and it visits all neighboring GridLocations to calculate the shortest distance between them. During this process, BFS checks if any neighboring GridLocations contain walls, and it avoids them. Finally, it returns the shortest path as the distance between the two GridLocations.

#### Examples:

If the inputs are (2,3) and (5,5) with no walls in the way, a shortest path would be (2, 3) -> (3, 3) -> (4, 3) -> (5, 3) -> (5, 4) -> (5, 5), which has length 5. Therefore, the method should return 5 for the distance.
If the inputs (2, 9) and (4, 9) with walls at (3, 10), (3, 9), (3, 8), and (3, 7), then the shortest path is (2, 9) -> (2, 10) -> (2, 11) -> (3, 11) -> (4, 11) -> (4, 10) -> (4, 9) for a distance of 6.
