/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cliqueexample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author ahed
 */
public class GOCrelated {
    
    private static final PrintStream o = System.out;
    private static Map<String, Set<Integer>> annotations = new HashMap<String, Set<Integer>>();
    //private static Map<String, Set<Integer>> annotations= new HashMap<String, Set<Integer>>();
    private static Map<Integer, Set<String>> cNet01 = new HashMap<Integer, Set<String>>();
    private static Map<Integer, Set<String>> cNet02 = new HashMap<Integer, Set<String>>();
    private static Map<String, Set<Integer>> soCliques01 = new HashMap();
    private static Map<String, Set<Integer>> soCliques02 = new HashMap();
    
    
    public GOCrelated(){
        o.println("Start reading annotations");
    }
    
    public GOCrelated(String network1, String network2, String path){
    try{
            soCliques01 = readSOIdx(network1, path);
            soCliques02 = readSOIdx(network2, path);
            o.println("Specie names are : "+network1+".clq"+" and "+network2+".clq");
            
            //For NAPAbench
            //String path="pairwise/CG_set/Family_2/";
            BufferedReader reader = new BufferedReader( new FileReader(path+network1+".clq" ));
            //Reading first network's cliques
            //BufferedReader reader = new BufferedReader( new FileReader(network1+"/"+network1+".clq" ));
            
            String line;// = reader.readLine();
            int count = 0;
            while( (line = reader.readLine()) != null ) {
                Integer clqNum = Integer.parseInt(line.substring(0, line.indexOf("\t")));
                Set<String> nodes = new HashSet<String>();
                line = line.substring( line.indexOf("\t") + 1 );
                line = line.substring( line.indexOf("\t") + 1 );
		while( line.indexOf("\t") != -1 ) {
                    String key = line.substring(0, line.indexOf("\t"));
                    nodes.add( key );
                    line = line.substring( line.indexOf("\t") + 1 );					
		}
                nodes.add(line);
                count++;
                cNet01.put(clqNum, nodes);
            }
            o.println("Done........ Cliques of first network");
            
            //For NAPAbench
            reader = new BufferedReader( new FileReader( path+network2+".clq" ));
            
            //Reading second network's cliques
            //reader = new BufferedReader( new FileReader( network2+"/"+network2+".clq" ));
            
            count = 0;
            while( (line = reader.readLine()) != null ) {
                Integer clqNum = Integer.parseInt(line.substring(0, line.indexOf("\t")));
                Set<String> nodes = new HashSet<String>();
                line = line.substring( line.indexOf("\t") + 1 );
                line = line.substring( line.indexOf("\t") + 1 );
		while( line.indexOf("\t") != -1 ) {
                    String key = line.substring(0, line.indexOf("\t"));
                    nodes.add( key );
                    line = line.substring( line.indexOf("\t") + 1 );					
		}
                nodes.add(line);
                count++;
                cNet02.put(clqNum, nodes);
            }
            o.println("Done........ Cliques of second network");
            
            //The next line is to print all cliques of one specie
            //viewCliques(cNet01);
    }catch(Exception e){
            e.printStackTrace();
        }
        
        
    }//End of COG constructor
    
    public Map<String, Set<Integer>> readSOIdx(String filename, String path){
        try{
            //For NAPAbench
            //String path="pairwise/CG_set/Family_2/";
            o.println("index file of subordinate is : "+"so"+filename+".idx");
            //Reading first network's cliques
            BufferedReader reader = new BufferedReader( new FileReader(path+filename+"so.idx" ));
            
            //o.println("index file of subordinate is : "+"so"+filename+".idx");
            //Reading first network's cliques
            //BufferedReader reader = new BufferedReader( new FileReader(filename+"/"+"so"+filename+".idx" ));
            Map<String, Set<Integer>> temp = new HashMap();
            String line;// = reader.readLine();
            int count = 0;
            boolean noCliques;
            while( (line = reader.readLine()) != null ) {
                String soName;
                noCliques = false;
                if(line.indexOf("\t")==-1){
                    soName = line;
                    noCliques = true;
                }
                else
                    soName = line.substring(0, line.indexOf("\t"));
                Set<Integer> cliques = new HashSet<>();
                line = line.substring( line.indexOf("\t") + 1 );
		while( line.indexOf("\t") != -1 ) {
                    int key = Integer.parseInt(line.substring(0, line.indexOf("\t")));
                    cliques.add( key );
                    line = line.substring( line.indexOf("\t") + 1 );					
		}
                if(!noCliques)cliques.add(Integer.parseInt(line));
                count++;
                temp.put(soName, cliques);
                    //o.println(soName+"\t"+cliques.toString());
            }
            //o.println("Done........ Cliques of first network");
            return temp;
            
        }catch(Exception e){
            e.printStackTrace();
        }
        //return temp;
        
        return null;
    }
    
    public void viewCliques(Map<Integer, Set<String>> network){
        try{
            
            for(int i = 1; i<= network.size(); i++ ){
            //for (Set<String> r : rawCliques){
            //    if(r.size()>2) o.println(r);
            //    if(r.size()>maximum) maximum = r.size();
                //Vertex src = nodes[ id2count.get( Integer.parseInt( srcid ) ) ];
                Set<String> r = (network.get(i));
                //r.add("Ahed");
                o.println(i+"\t"+r.size()+"\t"+r.toString());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }// End of viewCliques
    
    public void readingAnnotations(String filename){
    try{
            //o.println("File name is : "+filename);
            BufferedReader reader = new BufferedReader( new FileReader( filename+"/"+filename+".annos" ));
            
            String line;// = reader.readLine();
            int count;
            //o.println( "starting annotations ...");

            count = 0;
            while( (line = reader.readLine()) != null ) {
                String node = line.substring(0, line.indexOf(" "));
                //o.println(node);
                Set<Integer> anns = new HashSet<Integer>();
                line = line.substring( line.indexOf(" ") + 1 );
		while( line.indexOf(" ") != -1 ) {
                    String key = line.substring(0, line.indexOf(" "));
                    anns.add( Integer.parseInt(key) );
                    line = line.substring( line.indexOf(" ") + 1 );					
		}
                //o.println(line);
                anns.add(Integer.parseInt(line));
                //o.println(anns.toString());
                count++;
                annotations.put(node, anns);
            }
            o.println("Done");
            //o.println("Total # of nodes is : "+count);
    }catch(Exception e){
            e.printStackTrace();
        }
}//readingAnnotations
    
    public void printAnnotations(String anns){
        try{
            Set<Integer> node;
            o.println("node name is : "+anns);
            if(annotations.get(anns) != null)
                node = annotations.get( anns  );
            else{
                    o.print("Can't find annotations for node "+anns);
                    return;
            }    
            o.println(anns+"  "+node.toString());
        }catch(Exception e){
            e.printStackTrace();
            }
    }//printAnnotations

    public Set<Integer> getAnnotations(String anns){
        if(annotations.get(anns) != null)
                return annotations.get( anns  );
        else{
                //o.print("Can't find annotations for node "+anns);
                //return null;
                return new HashSet<Integer>();
        }
    }//printAnnotations

    public double getGOC(String anns1, String anns2){
        
        //double GOC;
        //o.println(anns1+" - "+anns2);
        //o.println(annotations.get(anns1).toString());
        //o.println(annotations.get(anns2).toString());
        if((annotations.get(anns1) != null && annotations.get(anns2)!= null)){
            Set<Integer> intersect = new HashSet<Integer>(annotations.get(anns1));
            intersect.retainAll(annotations.get(anns2));
            //o.println(intersect.toString());
            Set<Integer> union = new HashSet<Integer>(annotations.get(anns1));
            union.addAll(annotations.get(anns2));
            //o.println(union.toString());
            //o.println("union size is "+union.size());
            //o.println("intersection size is "+intersect.size());
            return (double)intersect.size() / (double)union.size();
        }
        else{
                //o.println("Can't find GOC for node "+anns1+", "+anns2);
                return 0.0;
        }
    }//getGOC

    public double getGOCcliques (int clqNum1, int clqNum2){
        //get nodes of each clique
        if(clqNum1 > cNet01.size() || clqNum2 > cNet02.size()){
            o.println("Error in the number of cliques a network has. \nPlease review your input networks.");
            System.exit(0);
        }
        Set<String> set1 = new HashSet<String>(cNet01.get(clqNum1));
        Set<String> set2 = new HashSet<String>(cNet02.get(clqNum2));
        
        //o.println(set1.toString()+" - "+set2.toString());
        //get annotations of all nodes in a clique for both cliques
        Set<Integer> clqAnns01 = new HashSet<Integer>();
        Iterator it = set1.iterator();
        while(it.hasNext()){
            clqAnns01.addAll(getAnnotations(it.next().toString()));
        }
        Set<Integer> clqAnns02 = new HashSet<Integer>();
        it = set2.iterator();
        while(it.hasNext()){
            clqAnns02.addAll(getAnnotations(it.next().toString()));
        }
        
        //o.println(clqAnns01.toString()+" - "+clqAnns02.toString());

        if((!set1.isEmpty() && !set2.isEmpty())){
            Set<Integer> intersect = new HashSet<Integer>(clqAnns01);
            intersect.retainAll(clqAnns02);
            Set<Integer> union = new HashSet<Integer>(clqAnns01);
            union.addAll(clqAnns02);
            //o.println("union size is "+union.size());
            //o.println("intersection size is "+intersect.size());
            return (double)intersect.size() / (double)union.size();
        }
        else{
                o.print("The score is ZERO as the size of two sets are zero");
                return 0.0;
        }
    }//getGOCcliques

    public int getCliqueSize(int network, int idx){
        if(network == 1)
            return cNet01.get(idx).size();
        else
            return cNet02.get(idx).size();
    }
    
    public Set<String> getCliqueNodes(int network, int idx){
        if(network == 1)
            return new HashSet<String>(cNet01.get(idx));
        else{
            //o.println("problem "+idx);
            return new HashSet<String>(cNet02.get(idx));
        }
    }
    
    public Set<Integer> getSOCliques(String subordinate, int Idx){
        if(Idx == 1)return soCliques01.get(subordinate);
        return soCliques02.get(subordinate);
    }
    
    
    public void viewSOidx(){
        int size = soCliques01.size();
        o.println(getSOCliques("a5",1).toString());
        o.println(getSOCliques("a6",1).toString());
        o.println(getSOCliques("a11",1).toString());
    }
    
    public double getGOCalignment(String PPI1, String PPI2, String alignment){
        double goc = 0.0;
        try{
                BufferedReader reader = new BufferedReader( new FileReader( alignment+".aln" ));
            
                String line;// = reader.readLine();
                int count;
                
                o.println( "reading the alignment file ...");

                count = 0;
                while( (line = reader.readLine()) != null ) {
                    //String node1 = line.substring(0, line.indexOf(" "));
                    //line = line.substring( line.indexOf(" ") + 1);
                    String node1 = line.substring(0, line.indexOf("\t"));
                    line = line.substring( line.indexOf("\t") + 1);
                    String node2 = line;
                    //o.println(node);
                    //Set<Integer> anns = new HashSet<Integer>();
                    //o.println(node1+"\t"+node2);
                    goc += getGOC(node1, node2);
                    //o.println(goc);
                    count++;
                }
                o.println("Total of "+count+" entries" );
                return goc;
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return goc;
    }

public void readingSAnnotations(String path, String filename){
    try{
            o.println("Reading FO file : "+filename);
            //o.println("File name is : "+filename);
            BufferedReader reader = new BufferedReader( new FileReader( path+filename+".fo" ));
            
            String line;// = reader.readLine();
            int count;
            //o.println( "starting annotations ...");

            count = 0;
            while( (line = reader.readLine()) != null ) {
                String node = line.substring(0, line.indexOf('\t'));
                
                //o.println(node);
                Set<Integer> anns = new HashSet<Integer>();
                line = line.substring( line.indexOf('\t') + 1 );
                //o.println(line);
		//while( line.indexOf(" ") != -1 ) {
                    //String key = line.substring(0, line.indexOf('F'));
                    String key=line.substring(1);
                    //o.println("The string is "+key+" -- "+key.substring(0,1));
                    key = key.substring(1);
                    //while( key.substring(0,1).equals("0") ){
                    //    key = key.substring(1);
                    //    o.println("In while");
                    //}
                    //key = key+' ';
                    //o.println(key+" --- "+key.length());
                    //Integer myint = ;
                    //o.println("myint = "+Integer.parseInt(key));
                    anns.add( Integer.parseInt(key) );
                //    line = line.substring( line.indexOf(" ") + 1 );					
		//}
                //o.println(line);
                //anns.add(Integer.parseInt(line));
                //o.println(anns.toString());
                count++;
                annotations.put(node, anns);
            }
            o.println("Done");
            //o.println("Total # of nodes is : "+count);
    }catch(Exception e){
            e.printStackTrace();
        }
}//readingAnnotations
}//GOCrelated
