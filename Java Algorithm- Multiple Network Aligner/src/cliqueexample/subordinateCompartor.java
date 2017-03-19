/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cliqueexample;

import java.util.Comparator;

/**
 *
 * @author ahed
 */
public class subordinateCompartor implements Comparator<Pair<String, String>>{
    
    @Override
    public int compare(Pair<String, String> x, Pair<String, String> y){
        if(x.getScore() < y.getScore())
            return -1;
        else
            return 1;
        //return (x.getScore() - y.getScore());
    }
}
