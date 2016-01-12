package vTest;

import ProGAL.geom3d.volumes.Tetrahedron;

public class Cell {
	private int x;
	private int y;
	private int z;
	private int v;
	private Tetrahedron tet;
	
	public Cell(int x, int y, int z,int v,Tetrahedron tet) {
	    this.x = x;
	    this.y = y;
	    this.z = z;
	    this.z = v;
	    this.tet = tet;
	}
	
	public int getX() {
	    return this.x;
	}
	
	public int getY() {
	    return this.y;
	}
	
	public int getZ() {
	    return this.z;
	}
	
	public int getV() {
	    return this.v;
	}
	
	public Tetrahedron getTetrahedron() {
	    return this.tet;
	}
	
	public void setTetrahedron(Tetrahedron tet) {
	    this.tet = tet;
	}
	public void setX(int x) {
	    this.x = x;
	}
	
	public void setY(int y) {
	    this.y = y;
	}
	
	public void setZ(int z) {
	    this.z = z;
	}
	public void setV(int v) {
	    this.v = v;
	}
}
