/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliqueexample;


import java.util.Comparator;
import java.util.PriorityQueue;


/**
 *
 * @author ahed
 */
public class Alignment {
    public static void main(String[] args) {
        
        //int threshold = 10000;
        //String PPI1 = new String("scere");
        //String PPI2 = new String("hsapi");
        
        
        //For NAPAbench
        int threshold = 10000;
        String PPI1 = new String("A");
        String PPI2 = new String("B");

        //......................................................................
        //to calculate the GOC of the extracted alignment
        /*GOCrelated GOC;
        GOC = new GOCrelated();
        GOC.readingAnnotations(PPI1);
        GOC.readingAnnotations(PPI2);
        //System.out.println("GOC = "+GOC.getGOCalignment(PPI1, PPI2, "PINALOG/sc-hs"));
        System.out.println("GOC = "+GOC.getGOCalignment(PPI1, PPI2, "sc-hs50"));
        double gocAlignment = 0.0;
        //......................................................................
        */
        // to read the clique subordinate signatures and extract the alignment
        
        //alignmentStep as01 = new alignmentStep();
        //as01.readCliqueSignatures(PPI1, PPI2, threshold);
        //as01.extractAlignment(threshold, PPI1, PPI2);
        
        //For NAPAbench
        String path = "C:\\Users\\PC\\workspace\\IBNAL\\3-way\\Family_2\\";
        alignmentStep as01 = new alignmentStep();
        as01.readCliqueSignatures(PPI1, PPI2, threshold, path);
        as01.extractAlignment(threshold, PPI1, PPI2, path);
        
        //as01.getNumberOfNodesAligned(PPI1, PPI2);
        
        //Test od NAPAbench
        as01.getTrueOrthologs(PPI1, PPI2, path);
    }
    
}