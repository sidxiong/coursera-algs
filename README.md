# coursera-algs

"Algorithms, Part I & Part II" by Princeton University on Coursera (basically the COS226 at Princeton University).

I use this terrific online course and its well designed programming assignments to recap basics in algorithms.

#### update 01/20/2017
Finished programming assignments week1 of Part II.

* Word-Net: Find shortest path common ancestor in Word Net of nouns.

#### update 11/04/2016
Finished programming assignments week5 of Part I.

* Kd-Tree: Implemented 2D-Tree datastructure that support efficient range search and nearest neighbor operations.

#### update 11/03/2016
Notes for geo-application of BST (week5 of Part I).

![pic](https://s3-us-west-2.amazonaws.com/sid-static/bst-application.png)

* Using BST search 1D range
* Converting Line Intersection problem into 1D range search by sweeping line algorithm
    * Keep in BST y-coordinates of left point of lines that haven't end yet
    * When encountering a vertical line, do search in BST. Points found indicate intersections
* Kd-Tree
    * Using 2d-Tree to find nearest neighbor: pruning the other subtree when updating current nearest in current subtree, or will search in both subtrees
    * kd-Tree: recursively do 2-partition on one dimension at a time. Use $level = i~mod~k$.
    * k nearest neighbor: use a fixed size priority queue to keep candidates.
   

#### update 11/02/2016
Finished programming assignments week4 of Part I.

* 8-Puzzle: Solve 8-puzzle problem using the A\* search algorithm. (Heap/PriorityQueue)

#### update 10/31/2016
Finished programming assignments week1 to week3 of Part I.

* Percolation - Estimate percolation threshold via Monte Carlo simulation using union-find algorithm; (Union-Find)
* Deque, RandomizedQueue - Implement these two data structures; (Linear datastructure)
* Collinear - Using sort of slope to find lines on 2D plane. (Sort)
