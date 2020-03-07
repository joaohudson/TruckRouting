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
        if(!way.isEmpty())
            length += graph.distance(way.get(way.size() - 1), point);
        way.add(point);
    }
    
    public boolean contains(Point point)
    {
        return way.contains(point);
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
    
    public void calculateLenght(){
        this.length = 0;
        for(int i = 1; i < way.size(); i++){
            this.length += graph.distance(way.get(i), way.get(i-1));
        }
    }
    
    public void show()
    {
        String str = "";
        
        for(Point p : way)
        {
            str += p.getId() + "--";
        }
        
        System.out.println(str);
    }
    
    /**
     * Troca 2 vértices de lugar.
     * @param x primeiro vértice
     * @param y segundo vértice
     * @return 
     */
    public boolean swap(int x, int y)
    {
        Point a, ar, al;
        Point b, br, bl;
        int d0, d1;
        
        a = way.get(x);
        ar = way.get(x + 1);
        al = way.get(x - 1);
        b = way.get(y);
        br = way.get(y + 1);
        bl = way.get(y - 1);
        
        d0 = graph.distance(a, ar) + graph.distance(a, al) +
             graph.distance(b, br) + graph.distance(b, bl);
        
        swapArrayList(x, y, way);
        
        a = way.get(x);
        ar = way.get(x + 1);
        al = way.get(x - 1);
        b = way.get(y);
        br = way.get(y + 1);
        bl = way.get(y - 1);
        
        d1 = graph.distance(a, ar) + graph.distance(a, al) +
             graph.distance(b, br) + graph.distance(b, bl);
        
        System.out.println("d0: " + d0);
        System.out.println("d1: " + d1);
        
        if(d1 < d0)
        {
            return true;
        }
        
        swapArrayList(x, y, way);
        
        return false;
    }
    
    /**
     * 
     */
    public void reinsertion()
    {
        
    }
    
    /**
     * Troca os vértices de 2 arestas.
     * @param ax primeiro vértice da primeira aresta.
     * @param ay segundo vértice da primeira aresta.
     * @param bx primeiro vértice da segunda aresta.
     * @param by segundo vértice da segunda aresta.
     * @return 
     */
    public boolean towOpt(int ax, int ay, int bx, int by)
    {
        Point a;
        Point b;
        Point c;
        Point d;
        
        int d0, d1;
        
        a = way.get(ax);
        b = way.get(ay);
        c = way.get(bx);
        d = way.get(by);
        
        d0 = graph.distance(a, b) + graph.distance(c, d);
        
        System.out.println("D0: "+d0);
        
        swapArrayList(ay, by, way);
        
        a = way.get(ax);
        b = way.get(ay);
        c = way.get(bx);
        d = way.get(by);
        
        d1 = graph.distance(a, b) + graph.distance(c, d);
        System.out.println("D1: "+d1);
        
        if(d1 < d0){
            return true;
        }
        
        swapArrayList(ay, by, way);
        
        return false;
        
    }
    
    public boolean reInsertion(int point, int endPosition){
        
        calculateLenght();
        int custoAntes = this.length;
        
        Point a = way.get(point);
        
        way.remove(point);
        way.add(endPosition, a);
        calculateLenght();

        if(custoAntes > this.length){
            return true;
        }
        
        way.remove(endPosition);
        way.add(point, a);        
        return false;
    }
    
    
    private void swapArrayList(int a, int b, ArrayList<Point> list){
        Point aux = list.get(a);
        list.set(a, list.get(b));
        list.set(b, aux);
    }     
}
