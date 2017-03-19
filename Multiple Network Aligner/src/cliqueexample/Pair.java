/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cliqueexample;

/**
 *
 * @author ahed
 */

public class Pair<A, B> {
    private A First;
    private B Second;
    private double Score;

    public Pair(A first, B second, double score) {
    	super();
    	this.First = first;
    	this.Second = second;
        this.Score = score;
    }
 
    public String toString()
    { 
           return "(" + First + ", " + Second + ", " + Score + ")"; 
    }

    public A getFirst() {
    	return First;
    }

    public void setFirst(A first) {
    	this.First = first;
    }

    public B getSecond() {
    	return Second;
    }

    public void setSecond(B second) {
    	this.Second = second;
    }
    public double getScore() {
    	return Score;
    }

    public void setScore(double score) {
    	this.Score = score;
    }
}
