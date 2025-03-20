# Shortest_PathGraph
This system calculates and displays the shortest path between two points on a map using different methods.

Components: 

-User Interface (UI): Allows the user to specify the starting and ending points for the path calculation. Displays the calculated route.

-TCP Server: Listens for multiple requests from the UI. Communicates with the chosen pathfinding service or algorithm. Sends the calculated path back to the UI.

-Pathfinding Module: Can either call an external API or use an internal algorithm to calculate the shortest path.

Pathfinding Methods.

The system offers three options to find the shortest path:

-Via a API, which receives the information about both points on the map, for which the shortest path is to be known, and which returns the path.

-Via either the Dijkstra or the A Star algorithms: They work in the following way: From the point of start, it is looked into the nodes to which one could move from that point, and obtains the cost from the origin to move to them, which are added and ordered in a priority queue according to cost, it is decided to move to the one which cost is lowest, in subsequent steps, similarly, it is looked into which nodes could be reached, nodes not previously on the priority queue are added to it, and nodes already on the priority queue, for which new forms of reaching them which are cheaper in cost as the previously considered paths are found, are updated in cost in the priority queue. The process is continued until either: the end node is found, or, there are no longer additional nodes in the priority queue. The A Star algorithm works in a similar manner, just with the addition that distances are calculated as the sum of the cost from origin to the currently considered point, plus a way of approximation of the cost of getting from the reaching node to the final target one, to guide the algorithm to favour decisions which get you closer to the objective. 

## Results

![Shortest Paths](./Shortest_Path.jpg)
