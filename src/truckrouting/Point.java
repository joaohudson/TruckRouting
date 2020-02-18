/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truckrouting;

import java.util.LinkedList;

/**
 *
 * @author João Hudson
 */
public class Point {
    
    private final boolean isClient;
    private final int id;
    private LinkedList<Point> adjs;
    
    /**
     * Cria um ponto no grafo.
     * @param id O identificador único do ponto.
     * @param isClient Se o ponto é um client.
     */
    public Point(int id, boolean isClient)
    {
        this.id = id;
        this.isClient = isClient;
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
}
