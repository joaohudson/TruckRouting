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
        routeList.ensureCapacity(g.vertexNumber());
        long time = System.nanoTime();
        g.getInit().visit();
        
        while(visitedPoints < g.vertexNumber())
        {
            route = createRoute(g, g.getTruckCapacity());
            routeList.add(route);
            route.show();
            System.out.println("Carga restante: " + route.getTruck().getLoad());
        }
        
        boolean ok = routeList.get(2).reInsertion(1, 2, routeList.get(1));
        routeList.get(2).show();
        routeList.get(1).show();
        System.out.println(ok ? "Efetuou" : "Não efetuou");
        /*boolean certo = routeList.get(5).towOpt(1, 2, 0, 3);
        System.out.println(certo ? "trocou" : "não trocou");
        routeList.get(5).show();*/
        /*System.out.println(routeList.get(5).getLength());
        boolean certo = routeList.get(5).swap(1, 2);
        routeList.get(5).show();
        System.out.println(routeList.get(5).getLength());
        
        System.out.println(certo ? "trocou" : "não trocou");*/
        System.out.println("Duration: " + (double)(System.nanoTime() - time) / 1000_000_000d + 's');
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
            if(truck.getLoad() >= next.getDemanda())
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
