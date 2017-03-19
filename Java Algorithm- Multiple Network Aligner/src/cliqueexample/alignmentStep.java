/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliqueexample;

/**
 *
 * @author ahed
 * @author mubaraka
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
//import java.util.concurrent.*;

public class alignmentStep {
    
    private static final PrintStream o = System.out;
    private static final int VectorLength = 11;
    
    String[] subordinateNodes1;// = new String[nsize2];
    Map<String, Integer> id2count1;// = new HashMap<String, Integer>();
    Integer [][] CliqueSignature1;// = new Integer [nsize2][VectorLength];
    String[] subordinateNodes2;// = new String[nsize2];
    Map<String, Integer> id2count2;// = new HashMap<String, Integer>();
    Integer [][] CliqueSignature2;// = new Integer [nsize2][VectorLength];
    GOCrelated GOC;// = new GOCrelated(PPI1, PPI2);
    Integer size1;
    Integer size2;
    
    Comparator<Pair<String,String>> comp = new subordinateCompartor();
    PriorityQueue<Pair<String, String>> queue;// = new PriorityQueue<Pair<String, String>>(10, comp);
    
    public void readCliqueSignatures( String filename1, String filename2, int threshold, String path ) {
    try{
            //For NAPAbench
            //String path = "pairwise/CG_set/Family_2/";
            
            //Reading clique signature of first network
            o.println("File name is : "+filename1);
            //BufferedReader reader = new BufferedReader( new FileReader(filename1+"/"+filename1+".idx" ));
            //For NAPAbench
            BufferedReader reader = new BufferedReader( new FileReader(path+filename1+".idx" ));
            
            
            String line = reader.readLine();// = reader.readLine();
            int nsize1 = Integer.parseInt( line );// = Integer.parseInt( line );
            int count1 = 0;
            size1 = nsize1;
            o.println("Total number of subordinate nodes is : "+nsize1);
            /*String[] */subordinateNodes1 = new String[nsize1];
            /*Map<String, Integer> */id2count1 = new HashMap<String, Integer>();
            /*Integer [][] */CliqueSignature1 = new Integer [nsize1][VectorLength];
            o.println( "starting reading subordinate ...");

            count1 = 0;
            // process edges
            
            while( (line = reader.readLine()) != null ) {
                String Snode = line.substring(0, line.indexOf('\t'));
                line = line.substring( line.indexOf('\t') + 1);
                //o.print(Snode+"\n");
                subordinateNodes1[count1] = Snode;
                id2count1.put(Snode, count1);
                int index=0;
                while( -1 != line.indexOf("\t") ) {
                    String key = line.substring( 0, line.indexOf( "\t" ) );
                    //o.print( Integer.parseInt( key ) );
                    CliqueSignature1[count1][index++] = Integer.parseInt( key );
                    line = line.substring( line.indexOf("\t") + 1 );					
		}
                //o.println();
                count1++;
                if( count1 % 100 == 0 ) {
                    o.println("reading subordinate : " + count1);
                }
            }
            o.println("Done");
            o.println("Total # of subordinate is : "+count1);
            
            reader.close();
            
	/*for(int i=0; i<count1; i++){
            o.println(subordinateNodes1[i]);
            for(int j=0; j<VectorLength; j++)
                o.print(CliqueSignature1[i][j]);
            o.println();
        }*/
        //.....................................................................//
        //reading clique's signature of second network
        
            o.println("File name is : "+filename2);
            
            //reader = new BufferedReader( new FileReader( filename2+"/"+filename2+".idx" ));
            
            //For NAPAbench
            reader = new BufferedReader( new FileReader( path+filename2+".idx" ));
            
            
            line = reader.readLine();// = reader.readLine();
            int nsize2 = Integer.parseInt( line );// = Integer.parseInt( line );
            int count2 = 0;
            size2 = nsize2;
            o.println("Total number of subordinate nodes is : "+nsize2);
            /*String[] */subordinateNodes2 = new String[nsize2];
            /*Map<String, Integer> */id2count2 = new HashMap<String, Integer>();
            /*Integer [][] */CliqueSignature2 = new Integer [nsize2][VectorLength];
            
            o.println( "starting reading subordinate of second network...");

            count2 = 0;
            // process edges
            
            while( (line = reader.readLine()) != null ) {
                String Snode = line.substring(0, line.indexOf('\t'));
                line = line.substring( line.indexOf('\t') + 1);
                //o.print(Snode+"\n");
                subordinateNodes2[count2] = Snode;
                id2count2.put(Snode, count2);
                int index=0;
                while( -1 != line.indexOf("\t") ) {
                    String key = line.substring( 0, line.indexOf( "\t" ) );
                    //o.print( Integer.parseInt( key ) );
                    CliqueSignature2[count2][index++] = Integer.parseInt( key );
                    line = line.substring( line.indexOf("\t") + 1 );					
		}
                //o.println();
                count2++;
                if( count2 % 100 == 0 ) {
                    o.println("reading subordinate : " + count2);
                }
            }
            o.println("Done");
            o.println("Total # of subordinate is : "+count2);
        
        /* Printing all subordinate nodes along with their signatures   
	for(int i=0; i<count2; i++){
            o.println(subordinateNodes2[i]);
            for(int j=0; j<VectorLength; j++)
                o.print(CliqueSignature2[i][j]);
            o.println();
        }*/
        
            
        /*    
        //Calculate signature similarity matrix using Cosine Similarity
            
        double [][] signSimilarity = new double[nsize1][nsize2];
        double sum = 0.0, sqSum1 = 0.0, sqSum2 = 0.0;
        for(int i=0; i<nsize1; i++){
            sum = 0.0;
            for(int k=0; k<nsize2; k++){
                sqSum1 = 0.0;
                sqSum2 = 0.0;
                sum = 0.0;
            for(int j=0; j<VectorLength; j++){
                sum = sum + (CliqueSignature1[i][j]*CliqueSignature2[k][j]);
                sqSum1 = sqSum1 + (CliqueSignature1[i][j]*CliqueSignature1[i][j]);
                sqSum2 = sqSum2 + (CliqueSignature2[k][j]*CliqueSignature2[k][j]);
                
                //o.println(sqSum1+"\t"+sqSum2);    
            }
            double temp = Math.sqrt(sqSum1) * Math.sqrt(sqSum2);
            if(temp == 0.0) 
                signSimilarity[i][k] = -1.0; // avoid division by ZERO
            else //o.println("sum = "+sum+" sqSum1 = "+sqSum1+" sqSum2 = "+sqSum2);
                signSimilarity[i][k] = sum / temp;
            }
        }// End of calculation of Cosine Similarity
        */
        
        //PrintStream output = new PrintStream( filename1.substring(0, 2)+"-"+filename2.substring(0, 2)+".sim" );
        
        //For NAPAbench
        PrintStream output = new PrintStream( path+filename1+filename2+"sim"+".sim" );
        
        //PrintStream op = new PrintStream( "temp1.sim" );
        // Using GRAAL's distance with out weigth
        double [][] signSimilarity = new double[nsize1][nsize2];
        //Comparator<Pair<String,String>> comp = new subordinateCompartor();
        
        queue = new PriorityQueue<Pair<String, String>>(nsize1*nsize2, comp);
        
        double distance = 0.0;
        for(int i=0; i<nsize1; i++){
            for(int k=0; k<nsize2; k++){
                distance = 0.0;
                for(int j=0; j<VectorLength;j++)
                        distance = distance + Math.pow((CliqueSignature1[i][j] - CliqueSignature2[k][j]),2);//VectorLength;
                signSimilarity[i][k] = (Math.sqrt(distance));
                //..........................................................................
                Pair<String,String> pair = new Pair<String, String>(subordinateNodes1[i],subordinateNodes2[k],signSimilarity[i][k]);
                if(signSimilarity[i][k]<=threshold)queue.add(pair);
                //..........................................................................
                output.print( signSimilarity[i][k]+"\t" );
                //op.println(signSimilarity[i][k]);
            }
            output.println();
        }
        
        // End of calculation of GRAAL's distance
        output.close();
        //op.close();
        //viewSimilarityMatrix(signSimilarity, nsize1, nsize2);
        
        //o.println("Total number of equals = "+numOfEquals+" out of "+nsize1*nsize2+"\nDigonals are "+digonalElements+" out of "+nsize1);
        
        }catch(Exception e) {
            e.printStackTrace();
            }
}//readCliqueSignature

public void viewSimilarityMatrix(double [][] signSimilarity, int nsize1, int nsize2){
    //printing the matrix
        
        int numOfEquals = 0;
        int digonalElements = 0;
        for(int i=0; i<nsize1; i++){
            for(int j=0; j<nsize2; j++){
                if(signSimilarity[i][j] == 1.0){//signSimilarity[i][j]>0.99999999 && signSimilarity[i][j]<=1.0){
                    numOfEquals++;
                    //o.print(subordinateNodes1[i]);
                    //o.print("\t"+Arrays.toString(CliqueSignature1[i]));
                    o.println();
                    //o.print(subordinateNodes2[j]);
                    //o.print("\t"+Arrays.toString(CliqueSignature2[j]));
                    o.println();
                }
                if(i==j && signSimilarity[i][j]>0.99999999)digonalElements++;
                //o.print(signSimilarity[i][j]+"\n");
            }
            //o.println();
        }
    
}

public void extractAlignment(int threshold, String PPI1, String PPI2, String path){
    try{
        //TimeUnit seconds = new TimeUnit();
        long startTime = System.nanoTime();
        
        //For NAPAbench
        //String path = "pairwise/CG_set/Family_3/";
        GOC = new GOCrelated(PPI1, PPI2, path);
        GOC.readingSAnnotations(path,PPI1);
        GOC.readingSAnnotations(path,PPI2);
        
        /*GOC = new GOCrelated(PPI1, PPI2);
        GOC.readingAnnotations(PPI1);
        GOC.readingAnnotations(PPI2);*/
        
        double gocAlignment = 0.0;
        Set<String> aligned1 = new HashSet<String>();
        Set<String> aligned2 = new HashSet<String>();
        boolean stopAlign = false;
        String first;
        String second;
        double score;
        //PrintStream output = new PrintStream( PPI1.substring(0, 2)+"-"+PPI2.substring(0, 2)+Integer.toString(threshold)+".aln" );
        
        //For NAPAbench
        PrintStream output = new PrintStream( path+PPI1+"-"+PPI2+".aln" );
        
        o.println("Starting the alignment");
        int align = 0;
        int numberOfSubordinateAligned = 0;
        while(queue.size() != 0 && numberOfSubordinateAligned <= size1/*&& queue.peek().getScore()<= threshold*/){
            first = new String(queue.peek().getFirst());
            second = new String(queue.peek().getSecond());
            score = queue.peek().getScore();
            
            //...................
            //Dealin with nodes that have no cliques
            /*if(GOC.getSOCliques(first, 1).isEmpty()){
                queue.remove();
                continue;
            }*/
            //....................
            
            //o.println(queue.remove()+"\t"+getTotalTouches(1, id2count1.get(first))+"\t"+getTotalTouches(2, id2count2.get(second))+"\t"+score);
            //o.println("aligning"+first+" with "+second);
            
            if(!aligned1.contains(first)&& !aligned2.contains(second)){
                numberOfSubordinateAligned+=1;
                output.println(first+"\t"+second);
                gocAlignment += GOC.getGOC(first, second);
                aligned1.add(first);
                aligned2.add(second);
                
                //alignCliques(first, second);
                //..............................................................
                o.println(first+"---"+second);
                Set<Integer> cliques1 = new HashSet<Integer>(getSubordinateCliques(first, 1));
                Set<Integer> cliques2 = new HashSet<Integer>(getSubordinateCliques(second, 2));
                //o.println(cliques1.toString());
                //o.println(cliques2.toString());
                //o.println("Aligning "+first+" with "+second);
                double scre;
                //for(int i=3; i<=VectorLength; i++){
                for(int i : cliques1){
                    int mappedto = 0;
                    int mappedto1 = 0;
                    scre = -1.0;
                    
                    int map = 0;
                    for(int j : cliques2){
                        /*if(map==0){
                            mappedto = j;
                            scre = GOC.getGOCcliques(i, j);
                            map=1;
                        }*/
                        
                        //o.println(GOC.getCliqueSize(1, i)+"\t"+GOC.getCliqueSize(2, j));
                        if(GOC.getCliqueSize(1, i)==GOC.getCliqueSize(2, j)){
                            //GOC.printAnnotations();
                            
                            double temp = GOC.getGOCcliques(i, j);
                            //o.println(temp);
                            if(temp >scre){
                                scre = temp;
                                //o.println("GOC score = "+scre);
                                mappedto = j;
                                mappedto1 = i;
                                map=1;
                                //break;
                            }
                        }
                    }
                    
                    if(map==1){
                        //gocAlignment += scre;
                        //o.println(GOC.getCliqueNodes(1, i).toString()+"\t"+GOC.getCliqueNodes(2, mappedto));
                        //o.println(scre+"  "+numberOfSubordinateAligned);
                        
                        String clq1[] = new String[GOC.getCliqueNodes(1, mappedto1).size()];
                        clq1 = GOC.getCliqueNodes(1, mappedto1).toArray(clq1);
                        String clq2[] = new String[GOC.getCliqueNodes(1, mappedto1).size()];
                        clq2 = GOC.getCliqueNodes(2, mappedto).toArray(clq2);
                        int clqSize = GOC.getCliqueNodes(1, mappedto1).size();
                        for(int indx = 0; indx < clqSize; indx++){
                            //o.println(clq1[indx]+"\t"+clq2[indx]);
                            //..................................................
                            //Aligning nodes within a clique
                            if(aligned1.contains(clq1[indx])) continue;
                            int clqMap = 0;
                            double scr = GOC.getGOC(clq1[indx], clq2[0]);
                            for(int innerIndx = 1; innerIndx < clqSize; innerIndx++){
                                if(aligned2.contains(clq2[innerIndx])) continue;
                                if(GOC.getGOC(clq1[indx], clq2[innerIndx]) > scr){
                                    clqMap = innerIndx;
                                    scr = GOC.getGOC(clq1[indx], clq2[innerIndx]);
                                }
                            }
                            
                            if(!aligned1.contains(clq1[indx])&&!aligned2.contains(clq2[clqMap])){
                                output.println(clq1[indx]+"\t"+clq2[clqMap]);
                                gocAlignment += scr;//GOC.getGOC(clq1[indx], clq2[indx]);
                                //o.println(scr);
                                aligned1.add(clq1[indx]);
                                aligned2.add(clq2[clqMap]);
                                align++;
                            }
                            /*else
                            {
                                //gocAlignment -= scr;
                            }*/
                            //..................................................
                            
                            
                            /*
                            if(!aligned1.contains(clq1[indx])&&!aligned2.contains(clq2[indx])){
                                output.println(clq1[indx]+"\t"+clq2[indx]);
                                gocAlignment += scre;//GOC.getGOC(clq1[indx], clq2[indx]);
                                aligned1.add(clq1[indx]);
                                aligned2.add(clq2[indx]);
                                align++;
                            }
                            */
                        }
                    }
                    
                    //for(String nodes1 : GOC.getCliqueNodes(1, i)){
                    //for(String nodes1 : GOC.getCliqueNodes(1, i)){
                    //o.println("clique "+i+" is mapped to clique "+mappedto+" with a GOC of "+score);
                }
                //..............................................................
                align++;
            }
            queue.remove(); 
            
            //if(align%1000==0)o.println("aligning "+align+" nodes");
            //if(score > threshold) stopAlign = true;
        }
        output.close();
        long estimatedTime = System.nanoTime() - startTime;
        o.println("Total number of nodes aligned is "+align);
        o.println("GOC of this alignment is "+gocAlignment);
        o.println("It tooks IBNAL "+(estimatedTime/1000000000.0)+" to get the alignment");
        //o.println("First value at the queue is : "+getSubordinateCliques().peek());
    }catch(Exception e){
        e.printStackTrace();
    }//end catch clause
    
    //CreateNewFiles(path, PPI1, PPI2);
}

public int getTotalTouches(int signNum, int idx){
    int totalTouches=0;
    if(signNum == 1)
        for(int i=0; i<VectorLength; i++)
            totalTouches += CliqueSignature1[idx][i];
    else
        for(int i=0; i<VectorLength; i++)
            totalTouches += CliqueSignature2[idx][i];
    return totalTouches;
}

public int getCliqueSize(String name, int Idx){
    return 1;
}

public Set<Integer> getSubordinateCliques(String so, int Idx){
    return GOC.getSOCliques(so, Idx);
    //PriorityQueue<Integer> q1 = new PriorityQueue();
    //q1.add(1);
    //q1.add(2);
    //q1.add(5);
    //q1.add(4);
    //return q1;
}

public void alignCliques(String first, String second){
    Set<Integer> cliques1 = new HashSet<Integer>(getSubordinateCliques(first, 1));
    Set<Integer> cliques2 = new HashSet<Integer>(getSubordinateCliques(second, 2));
    //o.println(cliques1.toString());
    //o.println(cliques2.toString());
    o.println("Aligning "+first+" with "+second);
    double score;
    //for(int i=3; i<=VectorLength; i++){
    for(int i : cliques1){
        int mappedto=0;
        score = 0.0;
        for(int j : cliques2){
            //o.println(GOC.getCliqueSize(1, i)+"\t"+GOC.getCliqueSize(2, j));
            if(GOC.getCliqueSize(1, i)==GOC.getCliqueSize(2, j)){
                //GOC.printAnnotations();
                double temp = GOC.getGOCcliques(i, j);
                //o.println(temp);
                if(temp >score){
                    score = temp;
                    mappedto = j;
                }
            }
        }
        o.println(GOC.getCliqueNodes(1, i).toString()+"\t"+GOC.getCliqueNodes(2, mappedto));
        String clq1[] = new String[GOC.getCliqueNodes(1, i).size()];
        clq1 = GOC.getCliqueNodes(1, i).toArray(clq1);
        String clq2[] = new String[GOC.getCliqueNodes(2, mappedto).size()];
        clq2 = GOC.getCliqueNodes(2, mappedto).toArray(clq2);
        for(int indx=0; indx<GOC.getCliqueNodes(1, i).size(); indx++){
              o.println(clq1[indx]+"\t"+clq2[indx]);
        }
        //for(String nodes1 : GOC.getCliqueNodes(1, i)){
        //for(String nodes1 : GOC.getCliqueNodes(1, i)){
        //o.println("clique "+i+" is mapped to clique "+mappedto+" with a GOC of "+score);
    }
}

public int getNumberOfNodesAligned(String PPI1, String PPI2){
    double goc = 0.0;
    int count = 0;
    try{
            //BufferedReader reader = new BufferedReader( new FileReader( "others/PINALOG/"+"ce-dm.aln" ));
            //BufferedReader reader = new BufferedReader( new FileReader( "others/PINALOG/"+"ce-hs.aln" ));
            //BufferedReader reader = new BufferedReader( new FileReader( "others/PINALOG/"+"ce-sc.aln" ));
            //BufferedReader reader = new BufferedReader( new FileReader( "others/PINALOG/"+"dm-hs.aln" ));
            //BufferedReader reader = new BufferedReader( new FileReader( "others/PINALOG/"+"dm-sc.aln" ));
            //BufferedReader reader = new BufferedReader( new FileReader( "others/PINALOG/"+"hs-sc.aln" ));
            
            
            //BufferedReader reader = new BufferedReader( new FileReader( "others/MAGNA/"+"ce-dm_S3_15000_2000_2000.aln" ));
            //BufferedReader reader = new BufferedReader( new FileReader( "others/MAGNA/"+"ce-hs_S3_15000_2000_2000.aln" ));
            //BufferedReader reader = new BufferedReader( new FileReader( "others/MAGNA/"+"ce-sc_S3_15000_2000_2000.aln" ));
            //BufferedReader reader = new BufferedReader( new FileReader( "others/MAGNA/"+"dm-hs_S3_15000_2000_2000.aln" ));
            //BufferedReader reader = new BufferedReader( new FileReader( "others/MAGNA/"+"sc-dm_S3_15000_2000_2000.aln" ));
            //BufferedReader reader = new BufferedReader( new FileReader( "others/MAGNA/"+"sc-hs_S3_15000_2000_2000.aln" ));
            
            //BufferedReader reader = new BufferedReader( new FileReader( "others/C-GRAAL/"+"ce-dm.aln" ));
            //BufferedReader reader = new BufferedReader( new FileReader( "others/C-GRAAL/"+"ce-hs.aln" ));
            //BufferedReader reader = new BufferedReader( new FileReader( "others/C-GRAAL/"+"ce-sc.aln" ));
            //BufferedReader reader = new BufferedReader( new FileReader( "others/C-GRAAL/"+"dm-hs.aln" ));
            //BufferedReader reader = new BufferedReader( new FileReader( "others/C-GRAAL/"+"sc-dm.aln" ));
            //BufferedReader reader = new BufferedReader( new FileReader( "others/C-GRAAL/"+"sc-hs.aln" ));
            
            //BufferedReader reader = new BufferedReader( new FileReader( "others/GHOST/"+"ce-dm.aln" ));
            //BufferedReader reader = new BufferedReader( new FileReader( "others/GHOST/"+"ce-hs.aln" ));
            //BufferedReader reader = new BufferedReader( new FileReader( "others/GHOST/"+"ce-sc.aln" ));
            //BufferedReader reader = new BufferedReader( new FileReader( "others/GHOST/"+"dm-hs.aln" ));

            //BufferedReader reader = new BufferedReader( new FileReader( "others/NETAL/"+"ce-dm.aln" ));
            //BufferedReader reader = new BufferedReader( new FileReader( "others/NETAL/"+"ce-hs.aln" ));
            //BufferedReader reader = new BufferedReader( new FileReader( "others/NETAL/"+"ce-sc.aln" ));
            //BufferedReader reader = new BufferedReader( new FileReader( "others/NETAL/"+"dm-hs.aln" ));
            //BufferedReader reader = new BufferedReader( new FileReader( "others/NETAL/"+"dm-sc.aln" ));
            //BufferedReader reader = new BufferedReader( new FileReader( "others/NETAL/"+"sc-hs.aln" ));
        
        
            //BufferedReader reader = new BufferedReader( new FileReader( "ce-dm10000.aln" ));
            //BufferedReader reader = new BufferedReader( new FileReader( "ce-hs10000.aln" ));
            //BufferedReader reader = new BufferedReader( new FileReader( "ce-sc10000.aln" ));
            //BufferedReader reader = new BufferedReader( new FileReader( "dm-hs10000.aln" ));
            //BufferedReader reader = new BufferedReader( new FileReader( "sc-dm10000.aln" ));
            BufferedReader reader = new BufferedReader( new FileReader( "sc-hs10000.aln" ));
        
            
            String line;// = reader.readLine();
            o.println( "reading the alignment file ...");

            count = 1;
            while( (line = reader.readLine()) != null ) {
                    //String node1 = line.substring(0, line.indexOf(" "));
                    //line = line.substring( line.indexOf(" ") + 1);
                    //String node1 = line.substring(0, line.indexOf("\t"));
                    //line = line.substring( line.indexOf("\t") + 1);
                    //String node2 = line;
                    //o.println(node);
                    //Set<Integer> anns = new HashSet<Integer>();
                    //o.println(node1+"\t"+node2);
                    //goc += getGOC(node1, node2);
                    //o.println(goc);
                count++;
            }
            o.println("Total of "+count+" entries" );
            return count;
            
    }catch(Exception e){
        e.printStackTrace();
    }
    return count;
}

public int getTrueOrthologs(String PPI1, String PPI2, String path){
    //String path = "pairwise/CG_set/Family_1/";
    double goc = 0.0;
    int count = 0;
    int trueOrtholgs = 0;
    try{
            
            //For NAPAbench
            //String path="pairwise/CG_set/Family_2/";
            o.println("reading true alignment "+PPI1+"-"+PPI2+".sim");
            //Reading first network's cliques
            BufferedReader reader = new BufferedReader( new FileReader(path+PPI1+"-"+PPI2+".sim" ));
            
            //o.println("index file of subordinate is : "+"so"+filename+".idx");
            //Reading first network's cliques
            //BufferedReader reader = new BufferedReader( new FileReader(filename+"/"+"so"+filename+".idx" ));
            Map<String, String> trueAln = new HashMap();
            Map<String, Double> trueScr = new HashMap();
            String line;// = reader.readLine();
            //int count = 0;
            //boolean noCliques;
            while( (line = reader.readLine()) != null ) {
                String node1;
                String node2;
                double score;
                
                node1 = line.substring(0, line.indexOf("\t"));
                //Set<Integer> cliques = new HashSet<>();
                line = line.substring( line.indexOf("\t") + 1 );
		node2 = line.substring(0, line.indexOf("\t"));
                line = line.substring( line.indexOf("\t") + 1 );
                //while( line.indexOf("\t") != -1 ) {
                score = Double.parseDouble(line);
                
                if(trueScr.get(node1)!= null){
                    double tempscr = trueScr.get(node1);
                    if(score>tempscr){
                        trueScr.remove(node1);
                        trueScr.put(node1, score);
                        trueAln.remove(node1);
                        trueAln.put(node1,node2);
                    }
                }
                else{
                    trueAln.put(node1,node2);
                    trueScr.put(node1, score);
                }
                //if(node2.equals("b211"))
                //    o.println(node1+"-"+node2+"-"+score);
            }
            
            o.println("SIZE = "+trueAln.size());
            
            
            /*BufferedReader*/ reader = new BufferedReader( new FileReader( path+PPI1+"-"+PPI2+".aln" ));

            //String line;// = reader.readLine();
            o.println( "reading the alignment file ...");

            count = 1;
            
            String tmp;
            while( (line = reader.readLine()) != null ) {
                count++;
                //if(line.substring(0, line.indexOf("\t")).equals("b211"))
                //o.println("TRUE is : "+trueAln.get(line.substring(0, line.indexOf("\t"))));
                //o.println(line);
                //line = line.substring(0, line.indexOf("\t"));
                
                tmp = line.substring(line.indexOf("\t"), line.length()).trim();
                o.println(tmp+"-"+trueAln.get(line.substring(0, line.indexOf("\t"))));
                if(tmp.equals(trueAln.get(line.substring(0, line.indexOf("\t"))))){
                    trueOrtholgs++;
                }
                
                /*tmp = line.substring(0, line.indexOf("\t"));
                o.println(tmp+"-"+trueAln.get(line.substring(line.indexOf("\t"), line.length()).trim()));
                if(tmp.equals(trueAln.get(line.substring(line.indexOf("\t"), line.length()).trim()))){
                    trueOrtholgs++;
                }*/            
            }
            o.println("Total of "+count+" entries" );
            o.println("true Ortholgs = "+trueOrtholgs);
            o.println("The percentage of tru orthologs for this alignment is : "+(double)trueOrtholgs/(double)count);
            return count;
            
    }catch(Exception e){
        e.printStackTrace();
    }
    return trueOrtholgs;
}

public int CreateNewFiles(String path, String PPI1, String PPI2, String alnFileName){
        try{
        //String path = "C:\\Users\\PC\\Desktop\\Mubaraka\\CS9600\\CliqueExample\\"
               // + "pairwise\\CG_set\\Family_1\\";
        
        String line; // This will reference one line at a time
        String[] alnNodes; // stores all nodes from output alignment (.aln) file 
        String[] var_1 = new String [1000]; // stores all the first nodes form the .aln file
        String var_2; // stores first node form a line, file .net
        String var_3; // stores second node form a line, file .net
        String foElement; // to store functional orthologs for an aligned node
        int exist1; // flag
        int exist2; // flag
        int numberOfAlgNodes = 0;
        
        //For NAPAbench
        PrintStream output = new PrintStream( path + PPI1 + "-" + PPI2+ ".net" );
        
        // read file A-B.aln to read all nodes that are in alignment and 
        // store in an array
        FileReader fileReader1 = new FileReader(path + PPI1 + "-" + PPI2 + ".aln");
        BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
       
        while((line = bufferedReader1.readLine()) != null) {
            //System.out.println("reading line");
            alnNodes = line.split("\\s+");
            var_1[numberOfAlgNodes] = alnNodes[0];
            //System.out.println(var_1[numberOfAlgNodes]+"******");
            numberOfAlgNodes++;
        }
        

        // Build a new.net by using the nodes stored in alnNodes[] and searching 
        // the edges between these node from file parent network  example: A.net.
        FileReader fileReader2 = new FileReader(path + alnFileName + ".net");
        BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
                
        while((line = bufferedReader2.readLine()) != null) {
            //System.out.println("h2");
            alnNodes = line.split("\\s+");
            var_2 = alnNodes[0];
            var_3 = alnNodes[1];
            exist1=0;
            exist2=0;
            for(int x=0; x<numberOfAlgNodes; x++){
                if(var_1[x].equals(var_2)){
                    //System.out.println("var 2 exists");
                    exist1=1;
                    break;
                }
            }
            if(exist1 == 1){
                for(int x=0; x<numberOfAlgNodes; x++){
                    if(var_1[x].equals(var_3)){
                        //System.out.println("var 3 exists");
                        output.println(line);
                        //System.out.println(line);
                        exist2=1;
                        break;
                    }
                }
            }
        }
        System.out.println("File Created: " + PPI1 + "-" + PPI2+ ".net");
        // Read .fo file to build a new file-new.fo for aligned nodes
        FileReader fileReader3 = new FileReader(path + alnFileName + ".fo");
        BufferedReader bufferedReader3 = new BufferedReader(fileReader3);
        PrintStream output1 = new PrintStream( path+ PPI1 + "-" + PPI2+ ".fo" );
        
        while((line = bufferedReader3.readLine()) != null) {
            alnNodes = line.split("\\s+");
            foElement = alnNodes[0];
            for(int x=0; x<numberOfAlgNodes; x++){
                if(var_1[x].equals(foElement)){
                    output1.println(line);
                    //System.out.println(line);
                }
            }
        }
        System.out.println("File Created: " + PPI1 + "-" + PPI2+ ".fo");
        output.close();
        output1.close();
        
        }catch(Exception e){
            e.printStackTrace();
        }//end catch clause
        
        UndirectedGraph<String, DefaultEdge> graph1; // graph
        graph1 = new SimpleGraph<String, DefaultEdge>( DefaultEdge.class );
        graphUtil gu = new graphUtil();
	//gu.setGraph( graph );
        gu.setGraph1( graph1 );
        String networkName = new String(path+ PPI1 + "-" + PPI2);
            //..........................................
            //Reading network
        
            gu.readPPIN(networkName);
            //gu.readGraph("mygraph1.txt");
        
            System.out.println("Hello World");
        
            //...........................................
            //Extracting all maximal cliques
            gu.cliqueFinder(networkName);
            
        return 1;
    }

}//Class alignmentStep
