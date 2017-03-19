/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliqueexample;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Mubaraka Parekh
 */
public class MultipleAlignment {

    /**
     * @param args the command line arguments
     */
    
    public static void CreateNewFiles(String path, String PPI1, String PPI2){
        try{
        //String path = "C:\\Users\\PC\\workspace\\IBNAL\\pairwise\\CG_set\\Family_1\\";
        
        String line; // This will reference one line at a time
        String[] alnNodes; // stores all nodes from output alignment (.aln) file 
        String[] var_1 = new String [100]; // stores all the first nodes form the .aln file
        String var_2; // stores first node form a line, file .net
        String var_3; // stores second node form a line, file .net
        String foElement; // to store functional orthologs for an aligned node
        int exist1; // flag
        int exist2; // flag
        int numberOfAlgNodes = 0;
        
        //For NAPAbench
        PrintStream output = new PrintStream( path +"A-B.net" );
        
        // read file A-B.aln to read all nodes that are in alignment and 
        // store in an array
        FileReader fileReader1 = new FileReader(path +"A-B.aln");
        BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
       
        while((line = bufferedReader1.readLine()) != null) {
            alnNodes = line.split("\\s+");
            var_1[numberOfAlgNodes] = alnNodes[0];
            System.out.println(var_1[numberOfAlgNodes]+"******");
            numberOfAlgNodes++;
        }
        
        // Build a new.net by using the nodes stored in alnNodes[] and searching 
        // the edges between these node from file B.net.
        FileReader fileReader2 = new FileReader(path + PPI1+ ".net");
        BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
        //System.out.println("h1");
                
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
                        System.out.println(line);
                        exist2=1;
                        break;
                    }
                }
            }
        }
        
        // Read .fo file to build a new file-new.fo for aligned nodes
        FileReader fileReader3 = new FileReader(path + PPI1 +".fo");
        BufferedReader bufferedReader3 = new BufferedReader(fileReader3);
        PrintStream output1 = new PrintStream( path + "A-B.fo" );
        
        while((line = bufferedReader3.readLine()) != null) {
            alnNodes = line.split("\\s+");
            foElement = alnNodes[0];
            for(int x=0; x<numberOfAlgNodes; x++){
                if(var_1[x].equals(foElement)){
                    output1.println(line);
                    System.out.println(line);
                }
            }
        }
        
        output.close();
        output1.close();
        
        }catch(Exception e){
            e.printStackTrace();
        }//end catch clause
    }
    
    public static String[] ArrangeNetworksAscending(String path, int numOfFiles, String[] fileNames, int[] numOfClqs){
        int temp; // temporary value
        String temp1;
        
        // arrange files in ascending order
        for (int i = 0; i < numOfFiles; i++){
            for (int j = i + 1; j < numOfFiles; j++){
                if (numOfClqs[i] > numOfClqs[j]){
                    temp = numOfClqs[i];
                    temp1 = fileNames[i];
                    
                    numOfClqs[i] = numOfClqs[j];
                    fileNames[i] = fileNames[j];
                    
                    numOfClqs[j] = temp;
                    fileNames[j] = temp1;
                }
            }
        }
        System.out.println("Ascending Order of Networks:");
        PrintNetworksNames(numOfFiles, fileNames, numOfClqs);
        return fileNames;
    }
    
    public static String[] ArrangeNetworksDescending(String path, int numOfFiles, String[] fileNames, int[] numOfClqs){
        
        String[] fileNamesDescending = new String[numOfFiles];
        int[] numOfCliqsDescending = new int[numOfFiles];
        int count = numOfFiles-1;
        int temp; // temporary value
        String temp1;
        
        // First order networks in ascending 
        for (int i = 0; i < numOfFiles; i++){
            for (int j = i + 1; j < numOfFiles; j++){
                if (numOfClqs[i] > numOfClqs[j]){
                    temp = numOfClqs[i];
                    temp1 = fileNames[i];
                    
                    numOfClqs[i] = numOfClqs[j];
                    fileNames[i] = fileNames[j];
                    
                    numOfClqs[j] = temp;
                    fileNames[j] = temp1;
                }
            }
        }
        
        // Second, reverse the order to get descending order of network names
        for(int x = 0; x < numOfFiles; x++){
            fileNamesDescending[count] = fileNames[x];
            numOfCliqsDescending[count] = numOfClqs[x];
            count --;
        }
        
        // print files in descending order with their clique 
        System.out.println("Decscending Order of Networks:");
        PrintNetworksNames(numOfFiles, fileNamesDescending, numOfCliqsDescending);
        
        return fileNamesDescending;
    }
    
        
    public static void PrintNetworksNames(int numOfFiles, String[] fileNames, int[] numOfClqs){
        System.out.println("File Name Cliques");
        String format = "%-10s%s%n";
        for (int i = 0; i < numOfFiles; i++){
            System.out.format(format, fileNames[i], numOfClqs[i]);
        }
    }
      
    
    public static NetworkNamesCliques getNamesAndCliques(String path, int numOfFiles){
        return new NetworkNamesCliques(path, numOfFiles);
    }
    
    
    public static void SequenceAlignment(String path, int numOfFiles, String[] fileNames){
        String [] newFileName = new String[10];
        int x = 0;
        int flag1 = 0;
        int flag2 = 0;
        int alignment = 0; //number of alignment done
        String PPI1; // aligning protein network1 name
        String PPI2; // aligning protein network2 name
        int threshold = 10000;
        // "alnFileName" used in createFile function nodes to be copied from file: alnFileName after the alignment
        String alnFileName = null; 
        
        while(x<numOfFiles){
            if(flag1==1){
                PPI1 = newFileName[x-2];
                PPI2 = fileNames[x];
                System.out.println("\n\n\nSTARTING NEW ALIGNMENT...");
                System.out.println("PPI1: "+ PPI1 +" PPI2: "+ PPI2);
                x = x+1;
            } else{
                alnFileName = fileNames[x];
                PPI1 = fileNames[x];
                PPI2 = fileNames[x+1];
                flag1 = 1;
                x = x+2;
                System.out.println("\n\n\nSTARTING NEW ALIGNMENT...");
                System.out.println("PPI1: "+ PPI1 +" PPI2: "+ PPI2);
            }
            System.out.println(alnFileName);
            alignmentStep as01 = new alignmentStep();
            as01.readCliqueSignatures(PPI1, PPI2, threshold, path);
            as01.extractAlignment(threshold, PPI1, PPI2, path);
            flag2 = as01.CreateNewFiles(path, PPI1, PPI2, alnFileName);
            newFileName[alignment] = PPI1+ "-" +PPI2;
            alignment++;
        }
    }
    
    public static void ConsecutivePairAlignment(String path, int numOfFiles, String[] fileNames){
        String [] newFileNames = new String[10];
        //int[] trueOrthologs = new int[10];
        int[] trueOrthologs = {4,2,6,1};
        int x = 0;
        int flag = 0;
        int alignment = 0; //number of alignment done
        String PPI1; // aligning protein network1 name
        String PPI2; // aligning protein network2 name
        int threshold = 10000;
        String oddFile = null;
        while(x<numOfFiles){
            if((x == numOfFiles-1) && (numOfFiles % 2 == 1)){
                oddFile = fileNames[x];
                System.out.println(oddFile);
                x++;
            }else{
                PPI1 = fileNames[x];
                PPI2 = fileNames[x+1];
                System.out.println("PPI1: "+ PPI1 +" PPI2: "+ PPI2);
            
                //alignmentStep as01 = new alignmentStep();
                //as01.readCliqueSignatures(PPI1, PPI2, threshold, path);
                //as01.extractAlignment(threshold, PPI1, PPI2, path); 
                //trueOrthologs[alignment] = as01.getTrueOrthologs(PPI1, PPI2, path);
                //as01.CreateNewFiles(path, PPI1, PPI2);
                newFileNames[alignment] = PPI1+ "-" +PPI2;
                System.out.println("New file created: " + newFileNames[alignment]);
                alignment++;
                System.out.println("alignment: "+ alignment);
                x = x+2;
            }
        }
        
        // arrange files in ascending order with respect to true orthologs found for each resulting alignment
        newFileNames = ArrangeNetworksAscending(path, alignment, newFileNames, trueOrthologs);
        
        // If the num of files in initial input are odd, then add the oddFile
        // which is yet not aligned in the begining with the first resulting alignment 
        // files (newFileNames[]) and perform second alignment: sequence alignment 
        // of newFileNames[] files.
        if(numOfFiles % 2 == 1){
            for(int n = alignment; n>=0; n--){
                if(n==0){newFileNames[n]=oddFile;}
                else{newFileNames[n]= newFileNames[n-1];}
            }
            //re-assign num of files as total of the new result file generated for each alignment
            numOfFiles = alignment+1;
            SequenceAlignment(path, numOfFiles, newFileNames);
        }
        // else if the initial input files/networks are even, then directly proceed 
        // with sequence alignment of resulting alignment files (newFileNames[]).
        else{
            //reasign num of files as total of the new result file generated for each alignment
            numOfFiles = alignment;
            SequenceAlignment(path, numOfFiles, newFileNames);
        }
    }
    
    public static void HighLowPairAlignment(String path, int numOfFiles, String[] fileNames){
        String [] newFileNames = new String[10];
        //int[] trueOrthologs = new int[10];
        int[] trueOrthologs = {4,2,6,1};
        int low = 0;
        int high = numOfFiles-1;
        int flag = 0;
        int alignment = 0; //number of alignment done
        String PPI1; // aligning protein network1 name
        String PPI2; // aligning protein network2 name
        int threshold = 10000;
        String oddFile = null;
        // "alnFileName" used in createNewFiles() function nodes to be copied from file: alnFileName after the alignment
        String alnFileName = null; 
        
        while(low<=high){
            if((low==high) && (numOfFiles % 2 == 1)){
                oddFile = fileNames[low];
                System.out.println("low: "+ low + "high: "+ high);
                System.out.println(oddFile);
                low++;
            }else{
                alnFileName = fileNames[low];
                PPI1 = fileNames[low];
                PPI2 = fileNames[high];
                System.out.println("\n\n\nSTARTING NEW ALIGNMENT...");
                System.out.println("PPI1: "+ PPI1 +" PPI2: "+ PPI2);
                
                // alignment steps...
                alignmentStep as01 = new alignmentStep();
                as01.readCliqueSignatures(PPI1, PPI2, threshold, path);
                as01.extractAlignment(threshold, PPI1, PPI2, path); 
                trueOrthologs[alignment] = as01.getTrueOrthologs(PPI1, PPI2, path);
                as01.CreateNewFiles(path, PPI1, PPI2, alnFileName);
                
                newFileNames[alignment] = PPI1+ "-" +PPI2;
                System.out.println("New file created: " + newFileNames[alignment]);
                alignment++;
                System.out.println("alignment: "+ alignment);
                low++;
                high--;
            }
        }
        // arrange files in ascending order with respect to true orthologs found for each resulting alignment
        newFileNames = ArrangeNetworksAscending(path, alignment, newFileNames, trueOrthologs);
        
        // If the num of files in initial input are odd, then add the oddFile
        // which is yet not aligned in the begining with the first resulting alignment 
        // files (newFileNames[]) and perform second alignment: sequence alignment 
        // of newFileNames[] files.
        if(numOfFiles % 2 == 1){
            for(int n = alignment; n>=0; n--){
                if(n==0){newFileNames[n]=oddFile;}
                else{newFileNames[n]= newFileNames[n-1];}
            }
            //re-assign num of files as total of the new result file generated for each alignment
            numOfFiles = alignment+1;
            SequenceAlignment(path, numOfFiles, newFileNames);
        }
        // else if the initial input files/networks are even, then directly proceed 
        // with sequence alignment of resulting alignment files (newFileNames[]).
        else{
            //reasign num of files as total of the new result file generated for each alignment
            numOfFiles = alignment;
            SequenceAlignment(path, numOfFiles, newFileNames);
        }
    }
    
    public static void ProcessDataForAnnosFile() throws FileNotFoundException{
        String path = "C:\\Users\\PC\\workspace\\IBNAL\\IsoBase\\";
        PrintStream output1 = new PrintStream( path + "ce" + ".annos" );
        PrintStream output2 = new PrintStream( path + "dm" + ".annos" );
        PrintStream output3 = new PrintStream( path + "hs" + ".annos" );
        PrintStream output4 = new PrintStream( path + "mm" + ".annos" );
        PrintStream output5 = new PrintStream( path + "sc" + ".annos" );
        String[] word;
        char [] Alphabet = new char[26];
        int count = 0;
        int i = 0;
        for (char letter = 'A'; letter <= 'Z'; letter++)
            {
                Alphabet[count]= letter;
                count++;
            }
        try{
                String line; // This will reference one line at a time
                FileReader fileReader1 = new FileReader(path+"__pid.go.goevid.goaspect.txt");
                BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
                System.out.println("hi");
                while((line = bufferedReader1.readLine()) != null) {
                    /*for (int i = 0; i < 26; i++){
                        line = line.replace(Character.toString(Alphabet[i]),"");
                    }*/
                    if ((line.charAt(i) == 'd') && (line.charAt(i+1) == 'm')) {  
                    }
                    else if ((line.charAt(i) == 'c') && (line.charAt(i+1) == 'e')) {
                        output1.println(line);
                    }
                    else if ((line.charAt(i) == 'm') && (line.charAt(i+1) == 'm')) {
                        output4.println(line);
                    }
                    else if ((line.charAt(i) == 'h') && (line.charAt(i+1) == 's')) {
                        output3.println(line);
                    }
                    else if ((line.charAt(i) == 's') && (line.charAt(i+1) == 'c')) { 
                        output5.println(line);
                    }
                    System.out.println("hi");
                     // print to another file.
                }
                System.out.println("end of file reading");
            }
            catch(Exception e){
                e.printStackTrace();
            }//end catch clause
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        String path = "C:\\Users\\PC\\workspace\\IBNAL\\3-way\\Family_3\\";
        int numOfFiles = 5;
        int threshold = 10000;
        NetworkNamesCliques nc = getNamesAndCliques(path, numOfFiles);
        String [] fileNames = nc.getFileNames(); // file names/network name
        int [] numOfClqs =  nc.getNumOfClqs(); // store clique count for all networks
        
        
        /* After one allignment is performed, the result is in the form of nodes that are 
         * alingned in two networks. Hence, to perform next alignment using this result 
         * needs the result (.aln file) to be converted in to a network. Also a .fo file 
         * (functional orthologs) is needed to be created beore next alignment begins. Below 
         * function creates new files from .aln file using .net and .fo file 
         * from previous alignment either of two parent networks.
         */
        
        //CreateNewFiles(path, "A", "B");
        
        /* Ascending Sequence Alignment-1: Arrange all the networks in a Ascending order 
         * and then perform multiple alignment by pair alignment in ascending
         * (number of cliques) sequence of networks.
         */
        fileNames = ArrangeNetworksAscending(path, numOfFiles, fileNames, numOfClqs);
        SequenceAlignment(path, numOfFiles, fileNames);
        
        /* Descending Sequence Alignment-2: Arrange all the networks in a descending order 
         * and then perform multiple alignment by pair alignment in descending
         * (number of cliques) sequence of networks.
         */
        //fileNames = ArrangeNetworksDescending(path, numOfFiles, fileNames, numOfClqs);
        //SequenceAlignment(path, numOfFiles, fileNames);
        
        /* High-low Pair Alignment: Arrange all the networks in an ascending/descending order 
         * and then perform multiple alignment by combining results from pair alignment of 
         * one hightest number of cilques network to lowest cliques network.
         * The results from pair alignment are agian arranged in ascending order
         * based on true orthogols found in those results. This is then followed 
         * by sequence alignment.
         */
        //fileNames = ArrangeNetworksAscending(path, numOfFiles, fileNames, numOfClqs);
        //HighLowPairAlignment(path, numOfFiles, fileNames);
        
        /* Consecutive Pair Alignment: Arrange all the networks in an ascending/descending order 
         * and then perform multiple alignment by pair alignment of 
         * one hightest number of cilques network to lowest cliques network.
         * The results form pair alignment are agian arranged in ascending order
         * based on true orthogols found in those results. This is then followed 
         * by sequence alignment.
         */ 
        //fileNames = ArrangeNetworksAscending(path, numOfFiles, fileNames, numOfClqs);
        //ConsecutivePairAlignment(path, numOfFiles, fileNames);
        
        // ProcessDataForAnnosFile();
    }    

}