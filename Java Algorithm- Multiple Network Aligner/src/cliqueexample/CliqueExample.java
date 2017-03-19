package cliqueexample;



import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
//import org.jgrapht.graph.DefaultDirectedWeightedGraph;
//import org.jgrapht.graph.DefaultWeightedEdge;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ahed Elmsallati
 */
public class CliqueExample {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
 
        UndirectedGraph<String, DefaultEdge> graph1; // graph
        graph1 = new SimpleGraph<String, DefaultEdge>( DefaultEdge.class );
        
        
	graphUtil gu = new graphUtil();
	//gu.setGraph( graph );
        gu.setGraph1( graph1 );
        
        //to set the network of specie to be read
        //String networkNmae = new String("hsapi");
        //String networkNmae = new String("dmela");
	//String networkNmae = new String("celeg");
        //String networkNmae = new String("scere");
        
        //String networkNmae = new String("sample");
        //gu.readGraph( graphfile );
        //graph G1;
        //G1 = new graphUtil();
        
        //For NAPAbench
        char [] Alphabet = new char[26];
        int count = 0;
        for (char letter = 'A'; letter <= 'Z'; letter++)
            {
                Alphabet[count]= letter;
                count++;
            }
        int setSize = 5;
        int family = 10;
        String setName = "CG_set";
        for(int x = 0; x<setSize; x++){
            String networkNmae = new String("C:\\Users\\PC\\workspace\\NAPAbench\\5-way\\DMR_set\\Family_10\\"+Alphabet[x]);
        
        
            //..........................................
            //Reading network
        
            gu.readPPIN(networkNmae);
            //gu.readGraph("mygraph1.txt");
        
            System.out.println("Hello World");
        
            //...........................................
            //Extracting all maximal cliques
            gu.cliqueFinder(networkNmae);
        }
        
        //...........................................
        //Extracting all subordinate nodes
        //gu.nodesNotInCliques();
        
        //...........................................
        //Loading annotations of a specie
        /*
        
        GOCrelated GOC = new GOCrelated("sample","sample");
        GOC.readingAnnotations("sample");
        //GOC.printAnnotations("ZFP330");
        double goc;// = GOC.getGOC("NOA36", "ZFP330");//"a6", "a7");
        //goc = GOC.getGOC("NOA36", "NFP330");
        goc = GOC.getGOCcliques(1, 4);
        if(goc== -1.0) return;
        
        System.out.println(goc);*/
    }
    
}
