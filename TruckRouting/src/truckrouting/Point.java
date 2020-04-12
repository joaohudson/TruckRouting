/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truckrouting;
import java.util.Collections;	
import java.util.LinkedList;
import java.util.Stack;
/**
 *
 * @author João Hudson
 */
public class Point {
    
    private final int id;
    private final int demanda;
    private LinkedList<Point> adjs;
    private Stack<Point> naoVisitados;	

    /**
     * Cria um ponto no grafo.
     * @param id O identificador único do ponto.
     */
    public Point(int id, int demanda)
    {
        this.id = id;
        this.demanda = demanda;
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
    
    public void cloneAdjs()
    {
        naoVisitados = new Stack<>();
        naoVisitados.addAll(adjs);//adiciona os clientes na pilha
    }
    
    /**
     * Obtém os pontos adjacentes a este.
     * @return Os pontos adjacentes.
     */
    public LinkedList<Point> getAdjs()
    {
        return adjs;
    }
    
    public Stack<Point> naoVisitados()
    {
        return naoVisitados;
    }
    
    public void aleatorizarPontos(){
        Collections.shuffle(this.naoVisitados);
    }
    
    public void removeUltimo(){
        this.naoVisitados.pop();
    }
    
    public Point vizinhoAleatorio(Graph graph)
    {        
        if(graph.getInit().naoVisitados().size() > 0){
            Point vizinho = graph.getInit().naoVisitados().peek();
            return vizinho;
        } else  return null;
    }
        
    public int getDemanda()
    {
        return demanda;
    }
    
    /**
     * Obtém o identificador único deste Point
     * @return O identificador único deste Point
     */
    public int getId()
    {
        return id;
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
