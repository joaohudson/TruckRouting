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
public class Route {
    
    private final Truck truck;
    private final ArrayList<Point> way;
    private final Graph graph;
    private int length;
    
    /**
     * Contrói uma rota.
     * @param truck O caminhão a ser usado na rota.
     * @param graph O grafo que contém os pontos.
     */
    public Route(Truck truck, Graph graph)
    {
        this.truck = truck;
        this.graph = graph;
        way = new ArrayList<>();
        length = 0;
    }
    
    /**
     * Obtém o caminhão da rota.
     * @return O caminhão da rota.
     */
    public Truck getTruck()
    {
        return truck;
    }
    
    /**
     * Adiciona um ponto ao caminho da rota.
     * @param point O ponto a ser adicionado.
     */
    public void addPointIntoWay(Point point)
    {
        way.add(point);
        
        if(way.size() == 1)
            return;
        
        way.add(point);
        length += graph.distance(way.get(way.size() - 2), point);
    }
    
    /**
     * Obtém o caminho da rota em forma
     * de lista de Points.
     * @return O caminho da rota.
     */
    public ArrayList<Point> getWay()
    {
        return way;
    }
    
    /**
     * Obtém o comprimento da rota.
     * @return O comprimento da rota.
     */
    public int getLength()
    {
        return length;
    }
}
