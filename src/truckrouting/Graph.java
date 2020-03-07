/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truckrouting;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author João Hudson
 */
public class Graph {
    
    private final int[][] points;
    private final Point init;
    private int truckCapacity;
    
    /**
     * Carrega um grafo de um arquivo.
     * @param dir O nome do arquivo.
     * @return O grafo carregado.
     */
    public static Graph loadFromFile(String dir) throws IOException
    {
        File file = new File(dir);
        Scanner scan = new Scanner(file);
        int size = 0;
        int[] demand = null;
        int[][] mat = null;
        Graph g = null;
        
        while(scan.hasNext())
        {
            String str = scan.nextLine();
            
            if(str.contains("DIMENSION"))
            {
                size = Integer.parseInt(str.substring(11));
                mat = new int[size][size];
            }
            else if(str.contains("CAPACITY"))
            {
                 g = new Graph(size);
                 g.truckCapacity = Integer.parseInt(str.substring(10));
            }
            else if(str.contains("DEMAND_SECTION"))
            {
                demand = new int[size];
                
                for(int i = 0; i < size; i++)
                {
                    scan.nextInt();
                    demand[i] = scan.nextInt();
                }
                
            }
            else if(str.contains("EDGE_WEIGHT_SECTION"))
            {
                for(int i = 0; i < size; i++)
                {
                    for(int j = 0; j < size; j++)
                    {
                        mat[i][j] = scan.nextInt();
                    }
                }
            }
        }
        
        Point[] points = new Point[size];
        Point p0, p1;
        
        points[0] = g.init;
        
        for(int i = 1; i < size; i++)
        {
            points[i] = new Point(i, demand[i], true);
        }
        
        for(int i = 0; i < size; i++)
        {
            p0 = points[i];
                
            for(int j = i; j < size; j++)
            {
                if(mat[i][j] == 0)
                    continue;
                
                
                p1 = points[j];
                g.addArest(p0, p1, mat[i][j]);
            }
        }
        
        return g;
    }
    
    /**
     * Cria um grafo.
     * @param size O tamanho do grafo.
     */
    public Graph(int size)
    {
        init = new Point(0,0, false);
        points = new int[size][size];
    }
    
    /**
     * Obtém a distância entre dois pontos do grafo.
     * @param p0 O primeiro ponto.
     * @param p1 O segundo ponto.
     * @return A distância entre os pontos.
     */
    public int distance(Point p0, Point p1)
    {
        return points[p0.getId()][p1.getId()];
    }
    
    public int vertexNumber()
    {
        return points.length;
    }
    
    /**
     * Adiciona uma aresta ao grafo.
     * @param p0 O primeiro ponto da aresta.
     * @param p1 O segundo ponto da aresta.
     * @param distance O comprimento da aresta.
     */
    public void addArest(Point p0, Point p1, int distance)
    {
        points[p0.getId()][p1.getId()] = distance;
        points[p1.getId()][p0.getId()] = distance;
        p0.addAdj(p1);
        p1.addAdj(p0);
    }
    
    /**
     * Obtém o ponto inicial do grafo.
     * @return O ponto inicial do grafo.
     */
    public Point getInit()
    {
        return init;
    }
    
    /**
     * Exibe o gráfo no console.
     */
    public void show()
    {
        Point point = init;
        LinkedList<Point> adjs = init.getAdjs();
        
        printPoint(init);
        printAdjs(adjs, init);
        
        for(Point p : adjs)
        {
            adjs = p.getAdjs();
            printPoint(p);
            printAdjs(adjs, p);
        }
    }
    
    public int getTruckCapacity()
    {
        return truckCapacity;
    }
    
    public void showMatrix()
    {
        for(int i = 0; i < points.length; i++)
        {
            for(int j = 0; j < points.length; j++)
            {
                System.out.print(points[i][j] + " ");
            }
            System.out.println("");
        }
    }
    
    private void printPoint(Point point)
    {
        System.out.println("[" + point.getId() + "]:");
    }
    
    private void printAdjs(LinkedList<Point> adjs, Point point)
    {
        for(Point p : adjs)
        {
            System.out.println("\t" + "dist(" + p.getId() + ") = " + distance(p, point));
        }
    }
}
