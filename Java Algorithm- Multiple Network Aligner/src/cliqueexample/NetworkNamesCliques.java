/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliqueexample;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author PC
 */
public class NetworkNamesCliques {
    private String [] fileNames;
    private int [] numOfCliques;
    
    public NetworkNamesCliques(String path, int numOfFiles) {
        fileNames = new String[26]; // file names/network name
        numOfCliques = new int[numOfFiles]; // store all clique counts for all networks
        int count = 0;
        int clqCount = 0;
        //int subCount; // total num of subordinate nodes in a file
        int temp; // temporary value
        char temp1;
        
        for(char letter = 'A'; letter <= 'Z'; letter++){
            fileNames[count]= Character.toString(letter);
            if (count != numOfFiles){ count ++;}
            else {break;}
        }
        
        // read .clq files of each network. Find and store total number of cliques for each network
        //System.out.println("Total Cliques in Each Network");
        for(int x=0; x<count; x++){
            //subCount = 0;
            try{
                String line; // This will reference one line at a time
                FileReader fileReader1 = new FileReader(path + fileNames[x] + ".clq");
                BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
                
                while((line = bufferedReader1.readLine()) != null) {
                    clqCount ++;
                }
                numOfCliques[x] = clqCount;
       
               /* while((line = bufferedReader1.readLine()) != null) {
                    subCount = Integer.parseInt(line);
                    break;
                }
                numOfCliques[x] = subCount;*/
                //System.out.println(fileNames[x] + ": " +clqCount);
                
            }
            catch(Exception e){
                e.printStackTrace();
            }//end catch clause
        }
    }

    /**
     * @return the fileNames
     */
    public String[] getFileNames() {
        return fileNames;
    }

    /**
     * @return the numOfClqs
     */
    public int[] getNumOfClqs() {
        return numOfCliques;
    }
}

