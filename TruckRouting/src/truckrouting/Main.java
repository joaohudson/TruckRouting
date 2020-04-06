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
        
        while(g.getInit().naoVisitados().size() > 0)
        {
            route = createRandomRoute(g, g.getTruckCapacity());
            routeList.add(route);
        }
        vnd(routeList);
        duration = (double)(System.nanoTime() - time) / 1000_000_000d;
        
        routeList.forEach((r) ->{
            r.show();
            System.out.println( r.getLength());
        });//print all routes
        
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
    
    private static boolean vnd(ArrayList<Route> routes)
    {
        boolean success = false;
        int moviment = 0;//swap
        
        while(true)
        {
            switch(moviment)
            {
                case 0:
                    success = applySwap(routes);
                    break;
                    
                case 1:
                    success = applyReInsertion(routes);
                    break;
                    
                case 2:
                    success = applyTwoOp(routes);
                    break;
            }
            
            if(success)
                moviment = 0;
            else
            {
                moviment++;
                if(moviment > 2)//foi o último movimento.
                    break;
            }
        }
        
        return success;
    }
    
    private static boolean applyTwoOp(ArrayList<Route> routes)
    {
        boolean success = false;
        int size;
        
        for(Route route : routes)
        {
            size = route.getWay().size();//número de pontos da rota
            
            for(int ax = 0; ax < size - 1; ax++)
            {
                for(int ay = ax + 1; ay < size; ay++)
                {
                    for(int bx = ay; bx < size - 1; bx++)
                    {
                        for(int by = bx + 1; by < size; by++)
                        {
                            success = route.twoOpt(ax, ay, bx, by);
                        }
                    }
                }
            }
        }
        
        return success;
    }
    
    /**
     * Aplica todas as reinserções possíveis.
     * @param routes As rotas.
     * @return Se foi possível aplicar alguma reinserção.
     */
    private static boolean applyReInsertion(ArrayList<Route> routes)
    {
        boolean success = false;
        
        for(Route r0 : routes)
        {
            for(Route r1 : routes)
            {
                for(int i = r0.getWay().size() - 2; i >= 1; i--)
                {
                    for(int j = 1; j < r1.getWay().size() - 1; j++)
                    {
                        success = r0.reInsertion(i, j, r1);
                        
                        if(success)
                        {
                            break;
                        }
                    }
                }
            }
        }
        
        routes.removeIf((r) -> r.getWay().size() == 2);//remove todas as rotas vazias.
        
        return success;
    }
    
    /**
     * Aplica todos os swaps possíveis na solução atual.
     * @param routes As rotas.
     * @return Se foi possível aplicar algum swap.
     */
    private static boolean applySwap(ArrayList<Route> routes)
    {
        boolean success = false;
        
        for(Route r0 : routes)
        {
            for(Route r1 : routes)
            {
                for(int i = 1; i < r0.getWay().size() - 1; i++)
                {
                    for(int j = i; j < r1.getWay().size() - 1; j++)
                    {
                        success = r0.swap(i, j, r1);
                    }
                }
            }
        }
        
        return success;
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
    private static Route createRandomRoute(Graph graph, int truckCapacity)
    {
        Point current = graph.getInit();
        Point neighbourhood;
        Truck truck = new Truck(0, truckCapacity);
        Route route = new Route(truck, graph);
        final int size = graph.vertexNumber();
        
        route.addPointIntoWay(current);
        
        for(int i = 0; i < size - 1; i++)
        {
            neighbourhood = current.vizinhoAleatorio(graph);

            
            if(neighbourhood != null && truck.getLoad() >= neighbourhood.getDemanda())
                truck.sub(neighbourhood.getDemanda());
            else
            {
                route.addPointIntoWay(graph.getInit());
                break;
            }
            
            route.addPointIntoWay(neighbourhood);
            neighbourhood.visit();
            
            current = neighbourhood;

        }

        return route;
    }

}
