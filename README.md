# **15-Puzzle-Solver**

Authors:
[Bryce Leung](https://github.com/Bryce-Leung),
[Peiman Zhiani](https://github.com/peyz21)

# Contents:
- [Abstract](#Abstract)
- [What is an NxN Sliding Puzzle](#What-is-an-NxN-Sliding-Puzzle)
- [What is the A Star Algorithm](#What-is-the-A-Star-Algorithm)
  - [The Goal of Our Project](#The-Goal-of-Our-Project)
  - [The Search Algorithm We Chose](#The-Search-Algorithm-We-Chose)
- [What is a Heuristic](#What-is-a-Heuristic)
  - [How is One Heuristic Better than Another](#How-is-One-Heuristic-Better-than-Another)
  - [Examples of Heuristics](#Examples-of-Heuristics)
- [Optimal vs. Non-Optimal Solution](#Optimal-vs.-Non-Optimal-Solution)
  - [Non-Optimal Use Case (Greedy/Pure-Heuristic)](#Non-Optimal-Use-Case-(Greedy/Pure-Heuristic))
  - [Comparing Admissible A Star vs. Non-Admissibe A Star(Greedy) Using Manhattan Heuristic](#Comparing-Admissible-A-Star-vs.-Non-Admissibe-A-Star(Greedy)-Using-Manhattan-Heuristic)
- [Finding the Right Heuristics](#Finding-the-Right-Heuristics)
  - [Weighted/Randomized Heuristics](#Weighted/Randomized-Heuristics)
  - [Mixed Heuristics](#Mixed-Heuristics)
- [Choice of Classes and Data Structures](#Choice-of-Classes-and-Data-Structures)
  - [Data Structures](#Data-Structures)
- [Concluding Report](#Concluding-Report)

# **15 Puzzle Solver Implementation Documentation:**

### **Abstract**
---
In this project, our group developed a solver for NxN sliding puzzles in Java using a specific version of the A* algorithm. The solver was tested on 43 given solvable boards of different sizes from 3x3 to 9x9, although the program should work for any other given size. During the process, we explored various related algorithms, methods, and Heuristics to create this solver. We gathered data along the way to compare and choose the most optimal approach given the project requirement.

### **What is an NxN Sliding Puzzle**
---
An NxN Sliding Puzzle is a combinational puzzle consisting of tiles that are usually numbered with missing tiles. These tiles are scrambled, with the primary goal of the puzzle being to reorganize the tiles in increasing order from left to right, up to down. With movement being limited to the tiles around the empty spot on the board.
#### **The Goal of Our Project**
Our project aimed to create a program that could solve an NxN Sliding Puzzle. Several approaches can be made, from utilizing a search algorithm to solve the entire board, solving the board by rows and columns to bring it down to a manageable size, or iteratively repositioning tiles one by one in the correct position.
#### **The Search Algorithm We Chose**
The approach we chose was to utilize an A* search algorithm to solve the sliding puzzle boards. This was chosen due to its ability to find the shortest path possible to solve the puzzle, with more efficient time and memory usage compared to DFS and BFS. As the DFS explores all child nodes before backtracking, and BFS explores every node to a given depth. Additionally, with a suitable heuristic, it can solve larger puzzles quickly. A*, compared to other methods such as IDA*, is simpler to implement, which allowed us to focus on optimization and heuristic finding.

### **What is the A Star Algorithm**
---
The A* algorithm, an extension of Dijkstra’s algorithm, is a type of graph traversal algorithm that is best first search utilizing weighted graphs. With the weighted graphs, the cost is computed based on the type of heuristic provided. The algorithm bases its movement on selecting the graph that has the lowest cost. During the search process, there is an open set that stores nodes not yet explored and a closed set that stores the nodes that have been explored. Leading to a path with the least cost, which finds the best route for distance and time. However, the trade-off is its large space complexity due to its need to store all possible paths along with the large time complexity spent finding these paths. In the case of the NxN-Board solver, Each puzzle instance with the empty space would be considered a node, and the goal is to find the “solved state node” within this graph by traversing from the given initial board state. There is a total of (N.N)! permutations of such nodes where N is the size of the board’s width/ height. However, not all instances are solvable. About half are unsolvable boards, which makes this (N.N)! /2 nodes or board states. This is why A*, with the help of a very precise heuristic to guide the process, is necessary.

### **What is a Heuristic**
---
A heuristic is a technique designed to solve a problem quickly when other regular methods are too slow for when there is a need to find an approximate solution. A heuristic trading-off accuracy for efficiency and is useful for making quick decisions in a rapidly changing environment. In our case, being a need for the algorithm to decide a path to traverse on the graph of the puzzle when multiple paths are present.
#### **How is One Heuristic Better than Another**
A heuristic is considered better than another when the cost calculated by the heuristic is closer to the actual distance without surpassing the cost to reach the goal, more efficient when calculating cost, and able to allow the algorithm to deeply explore the problem while being easy to understand and use. These characteristics help improve an algorithm’s memory usage and speed.
#### **Examples of Heuristics**
Some common heuristic examples used to solve NxN sliding puzzles are Manhattan Distance, Linear Conflicts, and Pattern Databases.

### **Optimal vs. Non-Optimal Solution**
---
When solving an NxN sliding puzzle using graph traversal, a solution that reaches the goal state (Solved Board) with the minimum number of moves is optimal. In comparison, the non-optimal solution, also known as non-Admissible, despite still reaching the goal state, requires more moves than the optimal method.
#### **Non-Optimal Use Case (Greedy/Pure-Heuristic)**
In the case of making a solver for the NxN sliding puzzle, while the optimal A* approach displayed the shortest path to the answer, due to its high memory usage and high computation time, it was only able to solve the maximum 4x4 board instances since on each iteration the algorithm visits both closed and open sets. Since we were seeking a reasonable solution which computes faster, we decided to use other approaches, such as the non-optimal A* algorithm. Greedy A*, also known as Pure-Heuristic A*, changes the computation factors within the function f(n) = g(n) + h(n) and ignores the close-Set g(n), resulting in f(n) = h(n). This method excluding the path already covered to the current node guides the A* more aggressively towards the goal state and was preferred in our case where computation time and solving a greater number of boards mattered the most.

<p align="center"><img width="700" alt="Table 1-Non-Greedy A Star With Manhattan" src="https://github.com/Bryce-Leung/15-Puzzle-Solver/assets/74439762/b773e4dc-3dec-460b-b536-fb0e06aa678f"></p>

<p align="center"><img width="700" alt="Table 1-Greedy With Manhattan" src="https://github.com/Bryce-Leung/15-Puzzle-Solver/assets/74439762/60857363-56ce-4922-8ab3-97ae6b31fba7"></p>

#### **Comparing Admissible A Star vs. Non-Admissibe A Star(Greedy) Using Manhattan Heuristic**
As it is represented in table1 and table2, using the pure heuristic approach, considering only the Manhattan heuristic, resulted in more boards solved. In the case of all 43 boards, the greedy approach solved 29 board instances, while its non-greedy version solved only 17.

<p align="center"><img width="700" alt="Greedy vs Non-Greedy Manhattan Distance" src="https://github.com/Bryce-Leung/15-Puzzle-Solver/assets/74439762/1ad95056-7ae3-46ae-86f7-feb94764be13"></p>

While our eventual goal is to solve more board instances, it is also worth noting, as the figure above (Total Nodes Visited) shows, despite visiting fewer nodes and achieving the result at a faster pace, as the complexity of the board increases, based on the below figure (Total Number of Moves) the optimality of the pure-heuristic approach decreases significantly. Despite that, we chose to continue with the pure-Heuristic method approach with the aim of solving the larger size of the boards while keeping the computation time low.

<p align="center"><img width="700" alt="Greedy vs Non-Greedy Manhattan Distance Total Number of Moves Solution" src="https://github.com/Bryce-Leung/15-Puzzle-Solver/assets/74439762/d3eec038-f5f3-4e6b-9021-f78f097de103"></p>

### **Finding the Right Heuristics**
---
Throughout the process of creating the solver, we went through numerous different heuristic options. We decided to keep and present some of these in the final code: The Manhattan Distance, Hamming Distance, Euclidean Distance, and Max-coordinate. What we discovered was that each heuristic had its own strength and weakness,and when applied to different board sizes, each one worked differently. The final form of the heuristic we used was a mixture of Manhattan, Euclidean, and Max-coordinate, each for a specific range of boards.
#### **Weighted/Randomized Heuristics**
According to the previous section on the non-optimal solution (greedy A*), we also covered areas such as Weighted and Randomized heuristics, which is essentially in the calculation of the heuristic; we multiplied or added the final value after each iteration with a constant C either to all the values or to each at random for each desired heuristic, in order to influence the search behaviour, often to find a solution more quickly at the expense of optimality. The best value C for each heuristic was found by trial and error. The weighted non-optimal heuristic can be expressed as f(n) = C* f(h). When w < 1, the heuristic value is deflated, which reduces the influence of the heuristic on the search. Since the heuristic can overestimate the cost in non-admissible searches, deflating it might help counteract some of the overestimations, leading to a more narrowed exploration. Shown below is an example result for board25.txt being solved with the Manhattan heuristic multiplied by this value C value of 0.8 and 0.75, which decreased the computation time significantly. Due to the variety of the results, we decided not to include them in our final version of the Solver.

<p align="center"><img width="700" alt="Data" src="https://github.com/Bryce-Leung/15-Puzzle-Solver/assets/74439762/eb98b3f4-5570-4e23-ad1d-b623a47b0aa4"></p>

#### **Mixed Heuristics**
Another method that we increase the performance of the heuristics was by adding various heuristics together and comparing the results. In some instances, the combined heuristics would solve boards in a way with less computation time and even solve boards that were initially impossible to solve. As shown in the table3, the Max Coordinate heuristic, when added to the Manhattan, resulted in mostly faster computation time and a more significant number of solved boards.

<p align="center"><img width="700" alt="Table 2 Computation Time for Max Coordinate" src="https://github.com/Bryce-Leung/15-Puzzle-Solver/assets/74439762/29e6fae6-5b30-4298-9c31-456e9759eae7"></p>


### **Choice of Classes and Data Structures**
---
We broke down the problem into three main classes:
- Tile class: This class represents each board and its instance; it reads the file and initializes a 2d array which stores the boards and holds all the given methods for the boards, including getters and setters, movement and solvability checks, board and Tile comparisons, and the overwritten Equals and HashCode methods.
- Positional class: This is a helper class which holds the position of the x,y value of each piece on the board.
- Solver class: This class holds the main and gets the files. It also does check for null files. Furthermore, it uses the Tile class to create board instances and uses the Greedy A* algorithm and heuristic methods. Evaluates the boards, find the path, and store the solution in the required format inside the solution file.
  - PuzzleNode subclass: This subclass is used in the HashMap (closed set) inside A* to store the score of each board and its predecessor as the value of HashMap.
#### **Data Structures**
The key data structures used were:
- File: used to read the puzzle file and store the solution File. (Used Java.nio file and java.io)
- Tile: a custom data structure which holds each puzzle state.
- LinkedList: a doubly linked list used for storing the solution path.
- ArrayList: used to store adjacent board states and valid movement options.
- HashMap: used as a closed set, this not only holds the nodes already covered, but it also keeps the predecessor of each board and their score value, which then is used to extract the solution.
- Priority Queue: used in the form of a minheap as an open set. It uses a custom comparator for sorting, and each dequeue prioritizes the board/node with the least score.
- Enum: used in organizing and passing the heuristic type based on the 4 types; this makes it easier to adjust and use different heuristics with a few clicks.
- 2D array: used in the Tile class to store each piece of a board, eventually representing each board.

### **Concluding Report**
---
As shown on the chart below, by utilizing the greedy A* algorithm and the right heuristic for each board size inside (combined Greedy), our final solution produced the most optimal solution for each board while solving the highest number of boards between other approaches and keeping the computation time low.

<p align="center"><img width="700" alt="Completed Puzzles Comparison" src="https://github.com/Bryce-Leung/15-Puzzle-Solver/assets/74439762/bdec79de-68d1-4209-8bdd-2e695a22f41f"></p>
