package vTest;

import ProGAL.geom3d.volumes.LSS;

public class Edge {
	private int x;
    private int y;
    private LSS lss;

    public Edge(int x, int y,LSS lss) {
        this.x = x;
        this.y = y;
        this.lss = lss;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
    
    public LSS getLSS() {
        return this.lss;
    }

    public void setLSS(LSS lss) {
        this.lss = lss;
    }
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
