package vTest;

import ProGAL.geom3d.Triangle;

public class Facet {
	private int x;
    private int y;
    private int z;
    private Triangle tri;

    public Facet(int x, int y, int z,Triangle tri) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.tri = tri;
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

    public Triangle getTriangle() {
        return this.tri;
    }

    public void setTriangle(Triangle tri) {
        this.tri = tri;
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
}
