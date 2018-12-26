package assignment4Graph;

public class Graph {
    
    boolean[][] adjacency;
    int nbNodes;
    
    public Graph(int nb) {
        this.nbNodes = nb;
        this.adjacency = new boolean[nb][nb];
        for (int i = 0; i < nb; i++) {
            for (int j = 0; j < nb; j++) {
                this.adjacency[i][j] = false;
            }
        }
    }
    
    public void addEdge(int i, int j) {
        adjacency[i][j] = true;
        adjacency[j][i] = true;
    }
    
    public void removeEdge(int i, int j) {
        adjacency[i][j] = false;
        adjacency[j][i] = false;
    }
    
    public int nbEdges() {
        int count = 0;
        for (int i = 0; i < nbNodes; i++) {
            for (int j = i; j < nbNodes; j++) {
                if (adjacency[i][j] == true) {
                    count++;
                }
            }
            
        }
        return count; // DON'T FORGET TO CHANGE THE RETURN
    }
    
    public boolean check(int i, int prev, int start, boolean[] visited) {
        if (adjacency[i][start] && prev != start) {
            return true;
        } else {
            for (int j = 0; j < nbNodes; j++) {
                if (adjacency[i][j] && j != i && j != prev && !visited[j]) {
                    visited[j] = true;
                    boolean r = check(j, i, start, visited);
                    if (r) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean cycle(int start) {
        boolean visited[] = new boolean[nbNodes];
        visited[start] = true;
        for (int i = 0; i < nbNodes; i++) {
            //            if (adjacency[start][i] == true && start == i) {
            //                return true;
            //            } else
            if (adjacency[start][i] == true && start != i) {
                visited[i] = true;
                boolean r = check(i, start, start, visited);
                if (r) {
                    return true;
                }
                
            }
        }
        return false; // DON'T FORGET TO CHANGE THE RETURN
    }
    
    public int shortestPath(int start, int end) {
        if(start==end && adjacency[start][end])
            return 1;
        if (start == end && !cycle(start)) {
            return nbNodes + 1;
        }
        for (int i = 0; i < nbNodes; i++) {
            if (adjacency[end][i]) {
                break;
            } else if (i == nbNodes - 1 && !adjacency[end][i]) {
                return nbNodes + 1;
            }
        }
        boolean[] visited = new boolean[nbNodes];
        int[] paths = new int[nbNodes];
        for (int i = 0; i < nbNodes; i++) {
            paths[i] = -1;
        }
        return shortestPath(start, end, start, visited, paths);
    }
    
    public int shortestPath(int start, int end, int prev, boolean[] visited, int[] paths) {
        visited[start] = true;
        int shortest;
        //        if (start == end && !cycle(start)) {
        //            shortest = 0;
        //        } else
        if (adjacency[start][end] && end != prev) {
            shortest = 1;
        } else {
            shortest = nbNodes + 1;
            for (int i = 0; i < nbNodes; i++) {
                int count = 1;
                if (adjacency[start][i] && !visited[i]) {
                    count += shortestPath(i, end, start, visited, paths);
                    if (count < shortest) {
                        shortest = count;
                    }
                } else if (adjacency[start][i] && visited[i] && paths[i] != -1 && start !=end) {
                    count += paths[i];
                    if (count < shortest) {
                        shortest = count;
                    }
                }
             
            }
        }
        paths[start] = shortest;
        return shortest; // DON'T FRGET TO CHANGE THE RETURN
    }
}

