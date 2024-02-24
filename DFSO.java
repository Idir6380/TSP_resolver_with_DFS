import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class DFSO {
    public static void main(String[] args) throws InterruptedException {
        int size = 5;
        int[][] distances = new int[size][size];
        int[] best_of_best = new int[size];
        int best_cost = Integer.MAX_VALUE;
        remplir_mat(distances, size);
        for (int i = 0; i < distances.length; i++) {
            best_cost=TSP_DFS(distances, 300, i, best_of_best, best_cost);
        }
        System.out.println("\nle chmin le plus optimal est:");
        for(int  i = 0; i < best_of_best.length; i++){
            System.out.print(best_of_best[i]);
            System.out.print("->");
        }
        System.out.print(best_of_best[0]);
        System.out.println("et son cout est de"+best_cost);
    }

    static void remplir_mat(int[][] mat, int size) throws InterruptedException {
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (j == i) {
                    mat[i][j] = 0;
                } else {
                    mat[i][j] = rand.nextInt(100);
                }
                Thread.sleep(2);
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mat[j][i] = mat[i][j];
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(mat[i][j] + "\t");
            }
            System.out.println();
        }
    }
    public static int TSP_DFS(int[][] mat,int dist, int start, int[] best_of_best
    , int best_best_cost) {
         Stack<Node> stack = new Stack<Node>();
         int [] sol= new int[mat.length];
         //initialiser sol 
         for(int i = 0; i<sol.length; i++){
            sol[i] = -1;
         }
         int [] Bsol= new int[mat.length];
         boolean [] visited= new boolean[mat.length];
         Node d= new Node(start, 0);
         int best_cost = Integer.MAX_VALUE;
         int cost = 0;
        stack.push(d);
        visited[d.city]= true;
        while(!stack.isEmpty()){
            d = stack.pop();
            for (int i = d.position; i<mat.length; i++){
                if(sol[i]!=-1)
                    visited[sol[i]] = false;
                sol[i]=-1;    
            }
            visited[d.city]= true;
           // System.out.println("pop:"+d.city+","+d.position);
            
            //System.out.println(visited[d.city]);
            //System.out.println("insertion :"+d.city+" a la position"+d.position);



            sol[d.position] = d.city;



   
            if(allVisited(visited)){
                cost = evaluate(mat, sol);
                System.out.println("chemin envisageable:");
                for(int  i = 0; i < mat.length; i++){
                    System.out.print(sol[i]);
                    System.out.print("->");
                }
                System.out.print(sol[0]);
                System.out.println("cout:"+cost);
                if (cost < best_cost) {
                    for(int  i = 0; i < mat.length; i++){
                        Bsol[i] = sol[i];
                    }
                    best_cost=cost;
                   /*  for(int  i = 0; i < mat.length; i++){
                        System.out.print(Bsol[i]);
                        System.out.print("->");
                    }
                     */
                    System.out.println();
                }

            }
            else{
                ArrayList<Node> scc = succesors(mat, d);
                for(Node s: scc){
                    //System.out.println(visited[s.city]);
                    if (!visited[s.city]) {
                        stack.push(s);
                        //System.out.println("push:"+s.city+","+s.position);
                    /* for (int i = s.position+1; i<mat.length; i++){
                visited[sol[i]] = false;
            }*/
            
                    }
                    else{
                        //System.out.println("deja visité");
                    }
                }
            }
    

        }
        System.out.println("meilleur chemin pour ce point:");
        for(int i = 0; i< Bsol.length; i++){
            System.out.print(Bsol[i]);
            System.out.print("->");
            
        }
        System.out.print(Bsol[0]);
        System.out.println("Le cout est de:"+best_cost);
        if (best_cost<best_best_cost) {
            for (int j = 0; j < best_of_best.length; j++) {
                best_of_best[j] = Bsol[j];
            }
            best_best_cost = best_cost;
        }
        return best_best_cost;
    }
    static boolean allVisited(boolean[] visited) {
        for (int i = 0; i < visited.length; i++) {
            if (visited[i] == false) {
                return false;
            }
        }
        return true;
    }

    public static int evaluate(int[][] mat, int[] CurrentSol) {
        int dist = 0;
        for (int i = 0; i < mat.length - 1; i++) {
            dist = dist + mat[CurrentSol[i]][CurrentSol[i + 1]];
        }
        dist = dist + mat[CurrentSol[CurrentSol.length - 1]][CurrentSol[0]];

        return dist;
    }
    public static void MAJ(int[] sol, int[] solt) {
        for (int i = 0; i < sol.length; i++) {
            sol[i] = solt[i];
        }
    }

    public static void show_path(int[][] mat, int[] sol) {
        System.out.println("Solution envisageable");
        for (int city : sol) {
            System.out.print(city + "->");
        }
        System.out.println(sol[0]);
        System.out.println("Le coût de ce chemin est de: " + evaluate(mat, sol));
    }
    static ArrayList<Node> succesors(int[][] mat, Node d) {
        ArrayList<Node> succ = new ArrayList<Node>();
    
        for(int i = 0; i < mat.length; i++){
            if(mat[d.city][i] > 0 && d.position < mat.length - 1){
                Node node = new Node(i, d.position+1);
                succ.add(node);
            }
        }
        return succ;
    }

}
