/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truckrouting;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author João Hudson
 */
public class Point {
    
    private final boolean isClient;
    private final int id;
    private final int demanda;
    private LinkedList<Point> adjs;
    private boolean visited;
    
    /**
     * Cria um ponto no grafo.
     * @param id O identificador único do ponto.
     * @param isClient Se o ponto é um client.
     */
    public Point(int id, int demanda, boolean isClient)
    {
        this.id = id;
        this.demanda = demanda;
        this.isClient = isClient;
        visited = false;
        adjs = new LinkedList<>();
    }
    
    /**
     * Adiciona um ponto aos adjacentes
     * deste.
     * @param point O ponto adjacente. 
     */
    public void addAdj(Point point)
    {
        adjs.add(point);
    }
    
    /**
     * Obtém os pontos adjacentes a este.
     * @return Os pontos adjacentes.
     */
    public LinkedList<Point> getAdjs()
    {
        return adjs;
    }
    
    public Point maisProximo(Graph graph)
    {
        Point maisProxim = graph.getInit().getAdjs().getFirst();
        
        for(Point point : adjs)
        {
            if(point.isVisited())
                continue;
            
            if(graph.distance(this, maisProxim) > graph.distance(this, point) || maisProxim.isVisited())
                maisProxim = point;
        }
        
        return maisProxim;
    }
    
    public int getDemanda()
    {
        return demanda;
    }
    
    /**
     * Se este ponto é um client.
     * @return Se este ponto é um client.
     */
    public boolean isClient()
    {
        return isClient;
    }
    
    /**
     * Obtém o identificador único deste Point
     * @return O identificador único deste Point
     */
    public int getId()
    {
        return id;
    }
    
    public void visit()
    {
        visited = true;
    }
    
    public boolean isVisited()
    {
        return visited;
    }
    
    @Override
    public String toString()
    {
        return "ID: " + id;
    }
    
    @Override
    public boolean equals(Object object)
    {
        if(object == null)
            return false;
        
        Point other = (Point)object;
        
        return id == other.id;
    }
}
