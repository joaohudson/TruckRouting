/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truckrouting;

import java.util.ArrayList;

/**
 *
 * @author João Hudson
 */
public class Main {
    
    private static int visitedPoints = 1;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{ 
        Graph g = Graph.loadFromFile("P-n16-k8.txt");
        Route route;
        ArrayList<Route> routeList = new ArrayList<>();
        g.getInit().visit();
        
        while(visitedPoints < g.vertexNumber())
        {
            route = createRoute(g, g.getTruckCapacity());
            routeList.add(route);
            route.show();
        }
        
        routeList.get(5).show();
        routeList.get(5).reInsertion(1, 3);
        routeList.get(5).show();
        /*boolean certo = routeList.get(5).towOpt(1, 2, 0, 3);
        System.out.println(certo ? "trocou" : "não trocou");
        routeList.get(5).show();*/
        /*System.out.println(routeList.get(5).getLength());
        boolean certo = routeList.get(5).swap(1, 2);
        routeList.get(5).show();
        System.out.println(routeList.get(5).getLength());
        
        System.out.println(certo ? "trocou" : "não trocou");*/
        
    }
    
    private static Route createRoute(Graph graph, int truckCapacity)
    {
        Point current = graph.getInit();
        Point next;
        Truck truck = new Truck(0, truckCapacity);
        Route route = new Route(truck, graph);
        final int size = graph.vertexNumber();
        
        route.addPointIntoWay(current);
        
        for(int i = 0; i < size - 1; i++)
        {
            next = current.maisProximo(graph);
            truck.sub(next.getDemanda());
            
            if(truck.getLoad() <= 0)
            {
                route.addPointIntoWay(graph.getInit());
                break;
            }
            route.addPointIntoWay(next);
            next.visit();
            visitedPoints++;
            
            current = next;
        }
        
        return route;
    }
}
