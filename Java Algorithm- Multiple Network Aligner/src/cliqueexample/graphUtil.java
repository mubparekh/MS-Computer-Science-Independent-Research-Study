package cliqueexample;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Collection;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.alg.BronKerboschCliqueFinder;

/**
 *
 * @author ahed
 */
public class graphUtil {
    public UndirectedGraph <String, DefaultEdge> g1;
    public DefaultDirectedWeightedGraph<Vertex, DefaultWeightedEdge> g; // graph
    private static final PrintStream o = System.out;
    public Set<String> allNodesNotIn = new HashSet<String>();
    /*public void setGraph(DefaultDirectedWeightedGraph graph) {
		g = graph;
	}*/
    public void setGraph1(UndirectedGraph graph) {
		g1 = graph;
	}
    public void readGraph( String filename ) {
	try{
            o.println("File name is : "+filename);
            BufferedReader reader = new BufferedReader( new FileReader( filename ));

            String line = reader.readLine();
            int nsize = Integer.parseInt( line );
            int count = 0;
            Vertex[] nodes = new Vertex[nsize];
            Map<Integer, Integer> id2count = new HashMap<Integer, Integer>();	
            o.println("nodes are : "+nsize);
            o.println( "starting nodes");

			// process vertices
            while( count < nsize ) {
				// read a new line
		line = reader.readLine();
				// create a node
		Vertex v = new Vertex();
		String id = line.substring( 0, line.indexOf(" ") );
		v.id = Integer.parseInt( id );
                //o.println(id);
		g.addVertex( v );
                g1.addVertex( id );
		nodes[ count ] = v;
		id2count.put( v.id , count );
				//
		line = line.substring( line.indexOf(" ") + 1);
		String weight = line.substring( 0, line.indexOf(" ") );
		v.weight = Float.parseFloat( weight );

		line = line.substring( line.indexOf(" ") + 1 );
		while( line.indexOf(" ") != -1 ) {
                    String key = line.substring( 0, line.indexOf( " " ) );
                    v.addKey( Integer.parseInt( key ) );
                    line = line.substring( line.indexOf(" ") + 1 );					
		}

		count++;
                o.println(count);
//				if( count % 10000 == 0 ) {
//					o.println( "reading nodes " + count );
//				}
            }
            o.println( "starting edges");
            count = 0;
            // process edges
            while( (line = reader.readLine()) != null ) {
                String srcid = line.substring(0, line.indexOf(" "));
                line = line.substring( line.indexOf(" ") + 1);
                String tgtid = line.substring( 0, line.indexOf(" ") );
                line = line.substring( line.indexOf(" ") + 1);	

                Vertex src = nodes[ id2count.get( Integer.parseInt( srcid ) ) ];
                Vertex tgt = nodes[ id2count.get( Integer.parseInt( tgtid ) ) ];
                DefaultWeightedEdge e = g.addEdge( src , tgt );
                g1.addEdge(srcid, tgtid);
                g.setEdgeWeight( e , Float.parseFloat( line ) );		
                count++;
                if( count % 5 == 0 ) {
                    o.println( "reading edge " + count);
                }
            }
            o.println("Done");
	}catch(Exception e) {
            e.printStackTrace();
            }
    }
    
public void readPPIN( String filename ) {
    try{
            o.println("File name is : "+filename+".net");
            //BufferedReader reader = new BufferedReader( new FileReader( filename+"/"+filename+".net" ));
            
            //For NAPAbench
            BufferedReader reader = new BufferedReader( new FileReader( filename+".net" ));
            String line;// = reader.readLine();
            int nsize = 0;// = Integer.parseInt( line );
            int count = 0;
            //Vertex[] nodes = new Vertex[nsize];
            //Map<Integer, Integer> id2count = new HashMap<Integer, Integer>();	
            o.println("nodes are : "+nsize);
            o.println( "starting nodes");

			// process vertices
            //String id = line.substring( 0, line.indexOf('\t') );
            //g1.addVertex( id );	
            //line = line.substring( line.indexOf(" ") + 1 );
            //o.println("******************"+id);
            o.println( "starting edges");
            count = 0;
            // process edges
            
            while( (line = reader.readLine()) != null ) {
                String srcid = line.substring(0, line.indexOf('\t'));
                line = line.substring( line.indexOf('\t') + 1);
                String tgtid = line;//.substring( 0, line.indexOf('\t') );
                //line = line.substring( line.indexOf('\t') + 1);	
                //o.println("***********");
                //o.println(line);
                //o.println(srcid+"  "+tgtid);
                //Vertex src = nodes[ id2count.get( Integer.parseInt( srcid ) ) ];
                //Vertex tgt = nodes[ id2count.get( Integer.parseInt( tgtid ) ) ];

                //DefaultWeightedEdge e = g.addEdge( src , tgt );
                if(!g1.containsVertex(srcid))g1.addVertex(srcid);
                if(!g1.containsVertex(tgtid))g1.addVertex(tgtid);
                if(!srcid.equals(tgtid))
                g1.addEdge(srcid, tgtid);
                allNodesNotIn.add(tgtid);
                allNodesNotIn.add(srcid);
                //g.setEdgeWeight( e , Float.parseFloat( line ) );		
                count++;
                if( count % 1000 == 0 ) {
                    o.println( "reading edge " + count);
                }
            }
            o.println("Done");
            o.println("Total # of nodes is : "+g1.vertexSet().size());
            o.println("Total # of edges is : "+count);
	}catch(Exception e) {
            e.printStackTrace();
            }
}    
	// store graph
public void saveGraph( String filename ) {
    try {
            PrintStream output = new PrintStream( filename );

            // save nodes
            output.println( g.vertexSet().size() );
            Iterator it = g.vertexSet().iterator();
            int count = 0;
            while( it.hasNext() ) {
		Vertex v = (Vertex)it.next();
		//v.id = ++count;
		output.print( "" + v.id + " " + v.weight + " " );
		if( v.keys != null ) {
                    Iterator it2 = v.keys.iterator();
                    while( it2.hasNext() ) {
			output.print( (Integer)it2.next() + " " );
                    }
		}
		output.println();
            }
            //assert( count == g.vertexSet().size());
            // save edges
            Iterator ite = g.edgeSet().iterator();
		while( ite.hasNext() ) {
                    DefaultWeightedEdge e = (DefaultWeightedEdge)ite.next();
                    Vertex src = g.getEdgeSource( e );
                    Vertex tgt = g.getEdgeTarget( e );
                    float weight = (float)g.getEdgeWeight( e );
                    output.println( src.id + " " + tgt.id + " " + weight );
		}

            output.close();			
        }catch( Exception e ) {
            e.printStackTrace();
            }
    }
//..............................................................................
public void saveCliques( String filename, Collection<Set<String>> allCliques) {
    try {
            PrintStream output = new PrintStream( filename );

            // save cliques
            //output.println(allCliques.size());
            
            int count = 0;
            for (Set<String> r : allCliques){
            //    if(r.size()>2) o.println(r);
            //    if(r.size()>maximum) maximum = r.size();
                
                if(r.size()>=3 && r.size() <= 11){
                    count++;
                    output.print(count+"\t"+r.size());
                    Iterator it = r.iterator();
                            while(it.hasNext()){
                                output.print("\t"+it.next().toString());
                                //o.print(it.next().toString()+"\t");
                                }
                            //o.print(it.next()+"\t");
                            
                    //output.print(count+"\t"+r.toString());
                    //o.print(r.toString());
                    output.println();
                    //o.println();
                }
            }
            
            output.close();			
        }catch( Exception e ) {
            e.printStackTrace();
            }
    }
//..............................................................................
public void cliqueFinder(String filename){
    try {
            //This module finds all possible maximal clique for the given network
            o.println("caculate maximal cliques...");
            BronKerboschCliqueFinder<String, DefaultEdge> cliqueFinder=new BronKerboschCliqueFinder<String, DefaultEdge>(g1);
            Collection<Set<String>> rawCliques=cliqueFinder.getAllMaximalCliques();
            //rawCliques = cliqueFinder.getBiggestMaximalCliques();
            o.println(rawCliques.size());
            //for(Object obj)
            //Iterator it = rawCliques.iterator();
            
            Set<String> allNodes = new HashSet<String>();
            int[] tot = new int[25];
            int maximum = 0;
            for (Set<String> r : rawCliques){
            //    if(r.size()>2) o.println(r);
            //    if(r.size()>maximum) maximum = r.size();
                
                if(r.size()>=3 && r.size() <= 11){
                    allNodes.addAll(r);
                    tot[r.size()]++;
                }
            }
            o.println(maximum);
            for(int i=0; i<25; i++){
                o.println(i +"   "+ tot[i]);
            }
            o.println("The number of nodes in all cliques is : "+allNodes.size());
            allNodesNotIn.removeAll(allNodes);
            o.println("The total number of nodes not in cliques is : "+allNodesNotIn.size());
            
            nodeVectorCalculator(rawCliques, filename);
            
            //For NAPAbench
            saveCliques(filename+".clq", rawCliques);
            
            //saveCliques("sample.clq", rawCliques);
            
            //saveCliques("dmela.clq", rawCliques);
            
            //saveCliques("hsapi.clq", rawCliques);
            //saveCliques("scere.clq", rawCliques);
            //saveCliques("celeg.clq", rawCliques);
            //o.println(rawCliques.max());
            //while(it.hasNext()){
            //    Set<String> clique = it.next();
            //    if(clique.equals(clique))o.println(it);
                //it.next();
            //}
            ////////o.println(rawCliques);
            //List<Set<Vertex>> sortedCliques;
            //=sort(rawCliques);
        }catch( Exception e ) {
            e.printStackTrace();
            }
}//cliqueFinder

public void graphLetFinder(){
    try {
            o.println("Hellow World");
    }catch( Exception e ) {
            e.printStackTrace();
            }
}//graphLetFinder

public void nodesNotInCliques(){
    try{
        Set<String> s = new HashSet<String>();
        s.add("a");
        s.add("b");
        s.add("c");
        s.add("d");
        s.add("a");
        s.add("b");
        o.println(s.size());

    }catch(Exception e){
            e.printStackTrace();
        }
}//nodesNotInCliques

public void nodeVectorCalculator(Collection<Set<String>> rawCliques, String filename)
{
    try{
    //This module calculates the clique-signature vector for all subordinate nodes 
            
            //PrintStream output = new PrintStream( "myindex.idx" );
            //PrintStream output1 = new PrintStream("sosample.idx");
            
            //For NABAbench
            PrintStream output = new PrintStream( filename+".idx" );
            
            PrintStream output1 = new PrintStream(filename+"so.idx");
        
            //for hsapi specie
            //PrintStream output = new PrintStream( filename+".idx" );
            //PrintStream output1 = new PrintStream("so"+filename+".idx");
            
            //for dmela specie
            //PrintStream output = new PrintStream( "dmela.idx" );
            
            //for scere specie
            //PrintStream output = new PrintStream( "scere.idx" );
            
            //for celeg specie
            //PrintStream output = new PrintStream( "celeg.idx" );
            
            Map<String, Set<Integer>> nodeClq = new HashMap<String, Set<Integer>>();
            output.println(allNodesNotIn.size());
            Set<String> allNodes = new HashSet<String>();
            int clqNum = 0;
            int[] tot = new int[12];
            int maximum = 0;
            int total = 0;
            //for (String n1: allNodesNotIn){
            //    o.print(n1+"   ");
            //}
            for (String n: allNodesNotIn){
                Set<Integer> clqs = new HashSet<Integer>();
                clqNum = 0;
                for (Set<String> r : rawCliques){
                //    if(r.size()>2) o.println(r);
                //    if(r.size()>maximum) maximum = r.size();
                    if(r.size() >= 3 && r.size() <= 11){
                        //tot[r.size()]++;
                        //o.println(r.toString()+"....");
                        clqNum +=1;
                        int found = 0;
                        Iterator it = r.iterator();
                            while(it.hasNext()){
                                if(g1.containsEdge(n, it.next().toString())){
                                    found = 1;//o.print("Link found..\n");
                                    break;
                                }
                            //o.print(it.next()+"\t");
                            }
                            if(found==1){
                                tot[r.size()]++;
                                clqs.add(clqNum);
                            }
                        //o.println();    
                    }
                    //if(r.size()>= 3 && r.size() <= 11)allNodes.addAll(r);
                }
                //o.println(n);
                output.print( n );
                int check = 0;
                    for(int i=0; i<12; i++){
                        //o.print(tot[i]+"\t");
                        output.print( "\t"+tot[i] );
                        if(tot[i] != 0) check=1;
                    }
                if(check == 0) total++;
                //o.println(maximum);
                //for(int i=0; i<12; i++){
                //    o.print(tot[i]+"\t");
                //}
                //o.println();
                output.println( );
                for(int i=0; i<12; i++){
                    tot[i]=0;
                }
                nodeClq.put(n, clqs);
                output1.print(n);
                Iterator it = clqs.iterator();
                while(it.hasNext()){
                    output1.print("\t"+it.next());
                    //if(g1.containsEdge(n, it.next().toString())){
                    //    break;
                    //}
                            //o.print(it.next()+"\t");
                }
                //o.println(n+"\t"+clqs.toString());
                output1.println();
                //o.println(clqNum);
                clqs.clear();
                //o.println("Total number of cliques extracted is : "+rawCliques.size());
            }
            o.println("The total number of nodes not touching any clique is: "+ total);
            output.close();//Closing file stream
            output1.close();
    }catch( Exception e ) {
        e.printStackTrace();
        }
}//nodeVectorCalculator

}//end of class