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
     * @param x primeiro vértice(pertence à esta rota)
     * @param y segundo vértice(pertence a outra rota especificada)
     * @param other A outra rota com quem será feita à troca.
     * @return se o movimento foi efetuado
     */
    public boolean swap(int x, int y, Route other)
    {
        Point a, ar, al;
        Point b, br, bl;
        int d0, d1;
        
        a = way.get(x);
        //se o ponto for o último, ponto da direita ignorado
        ar = x < way.size() - 1 ? way.get(x + 1) : way.get(x);
        //se o ponto for o primeiro, ponto da esquerda ignorado
        al = x > 0 ? way.get(x - 1) : way.get(x);
        
        b = other.way.get(y);
        //se o ponto for o último, ponto da direita ignorado
        br = y < other.way.size() - 1 ? other.way.get(y + 1) : other.way.get(y);
        //se o ponto for o primeiro, ponto da esquerda ignorado
        bl = y > 0 ? other.way.get(y - 1) : other.way.get(y);
        
        d0 = graph.distance(a, ar) + graph.distance(a, al) +
             graph.distance(b, br) + graph.distance(b, bl);
        
        //carga insuficiente
        if(other.truck.getLoad() < a.getDemanda() - b.getDemanda() || truck.getLoad() < b.getDemanda() - a.getDemanda())
            return false;
        
        swapArrayList(x, y, other.way);
        
        a = way.get(x);
        //se o ponto for o último, ponto da direita ignorado
        ar = x < way.size() - 1 ? way.get(x + 1) : way.get(x);
        //se o ponto for o primeiro, ponto da esquerda ignorado
        al = x > 0 ? way.get(x - 1) : way.get(x);
        
        b = other.way.get(y);
        //se o ponto for o último, ponto da direita ignorado
        br = y < other.way.size() - 1 ? other.way.get(y + 1) : other.way.get(y);
        //se o ponto for o primeiro, ponto da esquerda ignorado
        bl = y > 0 ? other.way.get(y - 1) : other.way.get(y);
        
        d1 = graph.distance(a, ar) + graph.distance(a, al) +
             graph.distance(b, br) + graph.distance(b, bl);
        
        if(d1 < d0)
        {
            length += d1 - d0;
            return true;
        }
        
        swapArrayList(x, y, other.way);
        
        return false;
    }
    
    /**
     * Troca os vértices de 2 arestas.
     * @param ax primeiro vértice da primeira aresta.
     * @param ay segundo vértice da primeira aresta.
     * @param bx primeiro vértice da segunda aresta.
     * @param by segundo vértice da segunda aresta.
     * @return se o movimento foi efetuado
     */
    public boolean twoOpt(int ax, int ay, int bx, int by)
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
        
        swapArrayList(ay, by, way);
        
        a = way.get(ax);
        b = way.get(ay);
        c = way.get(bx);
        d = way.get(by);
        
        d1 = graph.distance(a, b) + graph.distance(c, d);
        
        if(d1 < d0){
            return true;
        }
        
        swapArrayList(ay, by, way);
        
        return false;
        
    }
    
    /**
     * Remove e insere novamente o ponto em outra rota.
     * @param point O índice do ponto na rota
     * @param endPosition O novo índice do ponto
     * @param other A rota que receberá(ou  não) o ponto
     * 
     * @return Se o movimento foi efetuado
     */
    public boolean reInsertion(int point, int endPosition, Route other){
        
        int d0, d1;
        
        Point a = way.get(point);
        Point al = point > 0 ? way.get(point - 1) : way.get(point);
        Point ar = point < way.size() - 1 ? way.get(point + 1) : way.get(point);
        d0 = graph.distance(al, a) + graph.distance(a, ar);
        
        //carga insuficiente
        if(other.truck.getLoad() < a.getDemanda())
            return false;
        
        way.remove(point);
        other.way.add(endPosition, a);
        
        al = endPosition > 0 ? other.way.get(endPosition - 1) : other.way.get(endPosition);
        ar = endPosition < other.way.size() - 1 ? other.way.get(endPosition + 1) : other.way.get(endPosition);
        d1 = graph.distance(al, a) + graph.distance(a, ar);
        
        if(d1 < d0){
            length += d1 - d0;
            return true;
        }
        
        other.way.remove(endPosition);
        way.add(point, a);        
        return false;
    }
    
    /**
     * Troca 2 elementos entre os array lists
     * @param a O índice do primeiro elemento(este array list)
     * @param b o índice do segundo elemento(outro array list)
     * @param other O array list
     */
    private void swapArrayList(int a, int b, ArrayList<Point> other){
        Point aux = way.get(a);
        way.set(a, other.get(b));
        other.set(b, aux);
    }     
}
