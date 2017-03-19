package cliqueexample;

import java.util.Set;
import java.util.HashSet;

public class Vertex {
	
	static int count = 0;
	
	public int id; // node id
	public int block_id = -1; // block id
	public float weight = 0;
	public Set<Integer> keys = null; // keywords contained in this vertex
	
	public Vertex() {
		id = ++count;
		keys = new HashSet<Integer>();
	}
	
	public Vertex( int nid ) {
		id = nid;
		keys = new HashSet<Integer>();
	}
	
	void addKey( int keyword ) {
		keys.add( keyword );
	}
	
	public Vertex clone() {
		Vertex u = new Vertex();
		u.id = id;
		u.block_id = block_id;
		u.weight = weight;
		u.keys = keys;
		return u;
	}
}