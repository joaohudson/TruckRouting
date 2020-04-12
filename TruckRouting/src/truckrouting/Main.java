/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truckrouting;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author João Hudson
 */
public class Main {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{ 
        Graph g = Graph.loadFromFile("P-n50-k10.txt");
        Route route;
        ArrayList<Route> routeList;
        long time;
        int quantIterations = 10;
        List<Integer> solucoesConstrucao = new ArrayList<Integer>();
        List<Integer> solucoesVND = new ArrayList<Integer>();
        List<Double> temposConstrucao = new ArrayList<Double>();
        List<Double> temposVND = new ArrayList<Double>();

        for(int i = 0; i < quantIterations; i++){
            time = System.nanoTime();
            routeList = new ArrayList<>();
            routeList.ensureCapacity(g.vertexNumber());
            
            g.getInit().cloneAdjs();
            g.getInit().aleatorizarPontos();
            
            while(g.getInit().naoVisitados().size() > 0)
            {   
                route = createRandomRoute(g, g.getTruckCapacity());
                routeList.add(route);
            }
            solucoesConstrucao.add(totalLength(routeList));
            temposConstrucao.add((double)(System.nanoTime() - time) / 1000_000_000d);
            
            vnd(routeList);
            
            solucoesVND.add(totalLength(routeList));
            temposVND.add((double)(System.nanoTime() - time) / 1000_000_000d);
        }
       
        double valorOtimo = g.getValorOtimo();
        double gapConstrucao = (melhorSolucao(solucoesConstrucao)-valorOtimo)/valorOtimo*100;
        double gapVND = (melhorSolucao(solucoesVND)-valorOtimo)/valorOtimo*100;
        
        System.out.println("Valor ótimo: " + g.getValorOtimo());
        System.out.println("Solução média construção: " + mediaSol(solucoesConstrucao));
        System.out.println("Melhor solução construção: " + melhorSolucao(solucoesConstrucao));
        System.out.println("Tempo médio construção: " + media(temposConstrucao));        
        System.out.println("GAP construção: " + gapConstrucao);
        System.out.println("Solução média VND: " + mediaSol(solucoesVND));
        System.out.println("Melhor solução VND: " + melhorSolucao(solucoesVND));
        System.out.println("Tempo médio VND: " + media(temposVND));       
        System.out.println("GAP VND: " + gapVND);
        
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
                for(int bx = ax + 2; bx < size - 1; bx++)
                {
                    if(route.twoOpt(ax, bx))
                        success = true;
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
                        if(r0.reInsertion(i, j, r1))
                        {
                            success = true;
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
                        if(r0.swap(i, j, r1))
                            success = true;
                    }
                }
            }
        }
        
        return success;
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

            if(neighbourhood != null && truck.getLoad() >= neighbourhood.getDemanda()){
                truck.sub(neighbourhood.getDemanda());

                graph.getInit().removeUltimo();
            }
            else
            {
                route.addPointIntoWay(graph.getInit());
                break;
            }
            
            route.addPointIntoWay(neighbourhood);
            
            current = neighbourhood;

        }

        return route;
    }
    
    public static void showAllRoutes(ArrayList<Route> routeList){
        routeList.forEach((r) ->{
            r.show();
            System.out.println(r.getLength());
        });//print all routes
        
        //System.out.println("Total Lenght: " + totalLength(routeList));
    }
    
    public static double media(List<Double> lista){
        double soma = 0;
        for(Double d: lista){
            soma+=d;
        }
        return soma/lista.size();
    }
    public static double mediaSol(List<Integer> lista){
        double soma = 0;
        for(Integer d: lista){
            soma+=d;
        }
        return soma/lista.size();
    }
    
    public static int melhorSolucao(List<Integer> lista){
        int melhor = lista.get(0);
        for(Integer i: lista){
            if(i < melhor){
                melhor = i;
            }
        }
        return melhor;
    }

}
