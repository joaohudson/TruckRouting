/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truckrouting;
import java.util.Collections;	
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;	
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
    private ArrayList<Point> naoVisitados;	

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
        //naoVisitados = new ArrayList<>();
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
    
    public void cloneAdjs()
    {
        naoVisitados = new ArrayList<Point>(this.adjs);

    }
    
    /**
     * Obtém os pontos adjacentes a este.
     * @return Os pontos adjacentes.
     */
    public LinkedList<Point> getAdjs()
    {
        return adjs;
    }
    
    public ArrayList<Point> naoVisitados()
    {
        return naoVisitados;
    }
    
    public void aleatorizarPontos(){
        Collections.shuffle(this.naoVisitados);
    }
    
    public void removeUltimo(){
        this.naoVisitados.remove(this.naoVisitados.size()-1);
    }
    
    /**
     * Obtém o ponto mais próximo ignorando os
     * pontos visitados.
     * @param graph O grafo que contém os pontos.
     * @return O ponto mais próximo ou null, caso
     * todos os pontos já tenha sido visitados.
     */
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
        
        if(maisProxim.isVisited())//todos os pontos já foram visitados
            maisProxim = null;
        
        return maisProxim;
    }
    
    public Point vizinhoAleatorio(Graph graph)
    {        
        if(graph.getInit().naoVisitados().size() > 0){
            Point vizinho = graph.getInit().naoVisitados().get(graph.getInit().naoVisitados().size()-1);
            return vizinho;
        } else  return null;
    }
    
    public void addListaAleatoria(Graph graph){
        graph.getInit().removeUltimo();
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
