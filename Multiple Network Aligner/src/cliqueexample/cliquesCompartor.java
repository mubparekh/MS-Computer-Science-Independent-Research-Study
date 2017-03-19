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
public class cliquesCompartor implements Comparator<String>{
    @Override
    public int compare(String f, String s){
        if(f.length() < s.length())
            return -1;
        else
            return 1;
        //return (x.getScore() - y.getScore());
    }
}
