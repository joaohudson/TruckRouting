/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truckrouting;

/**
 *
 * @author João Hudson
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{ 
        Graph g = Graph.loadFromFile("P-n16-k8.txt");
        g.show();
        //g.showMatrix();
    }
    
}
