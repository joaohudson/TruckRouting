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
        Graph g = Graph.loadFromFile("P-n51-k10.txt");
        Route route;
        ArrayList<Route> routeList = new ArrayList<>();
        routeList.ensureCapacity(g.vertexNumber());
        long time = System.nanoTime();
        double duration;
        g.getInit().visit();
        
        while(visitedPoints < g.vertexNumber())
        {
            route = createRoute(g, g.getTruckCapacity());
            routeList.add(route);
        }
        
        vnd(routeList);
        duration = (double)(System.nanoTime() - time) / 1000_000_000d;
        
        routeList.forEach((r) -> r.show());//print all routes
        System.out.println("Total Lenght: " + totalLength(routeList));
        System.out.println("Duration: " + duration + 's');
    }
    
    /**
     * Calcula o comprimento total das rotas.
     * @param routes Lista de rotas.
     * @return O comprimento total das rotas.
     */
    private static int totalLength(ArrayList<Route> routes)
    {
        int plus = 0;
        for(Route r : routes)
        {
            plus += r.getLength();
        }
        
        return plus;
    }
    
    private static void vnd(ArrayList<Route> routes)
    {
        //...
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
            if(next != null && truck.getLoad() >= next.getDemanda())
                truck.sub(next.getDemanda());
            else
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
