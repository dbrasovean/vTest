package vTest;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import ProGAL.geom3d.LineSegment;
import ProGAL.geom3d.Point;
import ProGAL.geom3d.Triangle;
import ProGAL.geom3d.viewer.J3DScene;
import ProGAL.geom3d.volumes.LSS;
import ProGAL.geom3d.volumes.Sphere;
import ProGAL.geom3d.volumes.Tetrahedron;

class ProGALTest{
	

   public static void main(String[] args){
	   
	   int timeBetweenFrames=50;
	   boolean edgeOn = true;
	   boolean facetOn = true;
	   boolean cellOn = false;
	   
	   JFileChooser fileChooser = new JFileChooser();
	   fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
	   int result = fileChooser.showOpenDialog(null);
	   if (result == JFileChooser.APPROVE_OPTION) {
	       File selectedFile = fileChooser.getSelectedFile();
	       System.out.println("Selected file: " + selectedFile.getAbsolutePath());
	   }
	   
	   //----------------reading-------------
	   ArrayList<ArrayList<Point>> pointList = new ArrayList<ArrayList<Point>>();
	   ArrayList<ArrayList<Edge>> edgeList = new ArrayList<ArrayList<Edge>>();
	   ArrayList<ArrayList<Facet>> facetList = new ArrayList<ArrayList<Facet>>();
	   ArrayList<ArrayList<Cell>> cellList = new ArrayList<ArrayList<Cell>>();
	   
	   ArrayList<Sphere> sphereList = new ArrayList<Sphere>();
	   ArrayList<Edge> lssList = new ArrayList<Edge>();
	   ArrayList<Facet> dislayFacetList = new ArrayList<Facet>();
	   ArrayList<Cell> dislayCellList = new ArrayList<Cell>();
	   
       int frame = -1;
       int x, y, z, v;
       
	   J3DScene scene = J3DScene.createJ3DSceneInFrame();
	   
	   // The name of the file to open.
       String fileName = fileChooser.getSelectedFile().getPath();

       // This will reference one line at a time
       String line = null;

       try {
           // FileReader reads text files in the default encoding.
           FileReader fileReader = 
               new FileReader(fileName);

           // Always wrap FileReader in BufferedReader.
           BufferedReader bufferedReader = 
               new BufferedReader(fileReader);
           
           
           String readLineType = "Vertices";
           Boolean lineIdf = false;
           
           
           while((line = bufferedReader.readLine()) != null) {
               
        	   switch (line) 
        	   {
        	   	case"Vertices": readLineType = "Vertices";
        	   					lineIdf = true;
        	   					break;
        	   	case"Edges"	  : readLineType = "Edges";
								lineIdf = true;
								break;
        	   	case"Facet"   : readLineType = "Facet";
								lineIdf = true;
								break;
        	   	case"Cell"    : readLineType = "Cell";
								lineIdf = true;
								break;
        	   	case"Frame"   : 
        		   pointList.add(new ArrayList<Point>());
        		   edgeList.add(new ArrayList<Edge>());
        		   facetList.add(new ArrayList<Facet>());
        		   cellList.add(new ArrayList<Cell>());
        		   
        		   frame++;
        		   lineIdf = true;
        		   break;
				default		  : lineIdf = false;
								break;
        	   };
        	   
        	   
        	   if(!lineIdf)
        	   {        		   
        		   switch (readLineType) 
            	   {
            	    case"Vertices": 
            	    ////read points 
                 	    String strVerticesArray[] = new String[3];
                 	    strVerticesArray = line.split(" ");
                        
                        Point newPoint = new Point(Double.parseDouble(strVerticesArray[0]),
                     		   Double.parseDouble(strVerticesArray[1]),Double.parseDouble(strVerticesArray[2]));
                        pointList.get(frame).add(newPoint);
                        //System.out.println(newPoint);
                        break;
                    ////end read points       						
    				case"Edges"	  : 
    					String strEdgesArray[] = new String[2];
    					strEdgesArray = line.substring(1, line.length()-1).split("\\)\\(");
    					
    					x = Integer.parseInt(strEdgesArray[0]);
    					y = Integer.parseInt(strEdgesArray[1]);
    					
    					LineSegment newLineSegment = new LineSegment(pointList.get(0).get(x),
    							pointList.get(0).get(y));
    					Edge newEdge = new Edge(x,y,new LSS(newLineSegment,0.01));
    					edgeList.get(frame).add(newEdge);
    					

    					break;
    				case"Facet"   : 
    					String strFacetArray[] = new String[3];
    					strFacetArray = line.substring(1, line.length()-1).split("\\)\\(");

    					x = Integer.parseInt(strFacetArray[0]);
    					y = Integer.parseInt(strFacetArray[1]);
   					    z = Integer.parseInt(strFacetArray[2]);
   					    
   					 Facet newFacet = new Facet(x,y,z,new Triangle(
 							pointList.get(frame).get(x),
 							pointList.get(frame).get(y),
 							pointList.get(frame).get(z)));
    					facetList.get(frame).add(newFacet);
    					
    								break;
    				case"Cell"    : 
    					String strCellArray[] = new String[3];
    					strCellArray = line.substring(1, line.length()-1).split("\\)\\(");
    					
    					x = Integer.parseInt(strCellArray[0]);
    					y = Integer.parseInt(strCellArray[1]);
   					    z = Integer.parseInt(strCellArray[2]);
   					    v = Integer.parseInt(strCellArray[3]);
   					    
    					Cell newCell = new Cell(x,y,z,v,	
    							new Tetrahedron(
    							pointList.get(frame).get(x),
    							pointList.get(frame).get(y),
    							pointList.get(frame).get(z),
    							pointList.get(frame).get(v)));
    					
    					cellList.get(frame).add(newCell);
    								break;
    				default		  : break;
            	   	}
        	   }
        		   
        	   
        	   //System.out.println();
           }   

           // Always close files.
           bufferedReader.close();         
       }
       catch(FileNotFoundException ex) {
           System.out.println(
               "Unable to open file '" + 
               fileName + "'");                
       }
       catch(IOException ex) {
           System.out.println(
               "Error reading file '" 
               + fileName + "'");                  
           // Or we could just do this: 
           // ex.printStackTrace();
       }
       
       //----------------end reading--------------

	   for(int i=0;i< pointList.get(0).size();i++)
	   {
		   sphereList.add(new Sphere(pointList.get(0).get(i),0.1));
		   scene.addShape(sphereList.get(i), java.awt.Color.RED);
	   }
	   
	 //----------------------edge---------------------------------------------------------------
	  if(edgeOn) 
	  {	
		   for(int i=0;i<edgeList.get(0).size();i++) 
		   {
			   lssList.add(edgeList.get(0).get(i));
			   scene.addShape(lssList.get(lssList.size()-1).getLSS(), java.awt.Color.BLACK);			   
			      
		   }
	  }
	 //----------------------facet---------------------------------------------------------------
	  if(facetOn) 
	  {   
	  	   
		   for(int j=0;j<cellList.get(0).size();j++) 
		   {
			   dislayCellList.add(cellList.get(0).get(j));
			   scene.addShape(dislayCellList.get(dislayCellList.size()-1).getTetrahedron(), java.awt.Color.BLACK);			   
			      
		   }	   
	  }
	  
	  //----------------------cell---------------------------------------------------------------
	  if(cellOn) 
	  {   
	  	   
		   for(int j=0;j<cellList.get(0).size();j++) 
		   {
			   dislayFacetList.add(facetList.get(0).get(j));
			   scene.addShape(dislayFacetList.get(dislayFacetList.size()-1).getTriangle(), java.awt.Color.BLACK);			   
			      
		   }	   
	  }
	   
       for(int i=1;i<frame;i++)
       {
    	   System.out.println();
    	   System.out.println("Frame: "+i);
    	   System.out.println();

    	   for(int j=0;j<pointList.get(i).size();j++)
    	   {
    		   sphereList.get(j).setCenter(pointList.get(i).get(j));	   
    	   }
    	   
    	   //----------------------edge---------------------------------------------------------------
    	   if(edgeOn) 
    	   {
	    	   for(int j=0;j<edgeList.get(i).size();j++) 
	    	   {
	    		   boolean exist=false;
	    		   for(int k=0;k<lssList.size();k++) 
	        	   {
	    			   
	    			   if(edgeList.get(i).get(j).getX()==lssList.get(k).getX() &&
	    				  edgeList.get(i).get(j).getY()==lssList.get(k).getY()   )
	    			   {
	    				   exist=true;
	    			   }
	    			   
	        	   }
	    		   
	    		   if(!exist)
	    		   {
	    			   lssList.add(edgeList.get(i).get(j));
					   lssList.get(lssList.size()-1).getLSS().segment.setA(pointList.get(i).get(lssList.get(lssList.size()-1).getX()));
		    		   lssList.get(lssList.size()-1).getLSS().segment.setB(pointList.get(i).get(lssList.get(lssList.size()-1).getY()));
	    			   scene.addShape(lssList.get(lssList.size()-1).getLSS(), java.awt.Color.BLACK);
	    		   }
	    		      
	    	   }
	    	   
	    	   for(int j=0;j<lssList.size();j++)
	    	   {
	    		   boolean hidden=true;
	    		   for(int k=0;k<edgeList.get(i).size();k++)
	        	   {
	    			   if(edgeList.get(i).get(k).getX()==lssList.get(j).getX() &&
	    	    		  edgeList.get(i).get(k).getY()==lssList.get(j).getY()   )
	    	    			   
	    			   {
	    				   hidden=false;
	    		       }
	        	   }
	    		   
	    		   if(hidden)
	    		   {
	    			   scene.removeShape(lssList.get(j).getLSS());
					   lssList.remove(j);    				   
					   j--;
	    		   }
	    	   }
	    	   
	    	   for(int j=0;j<lssList.size();j++)
	    	   {
	    		   lssList.get(j).getLSS().segment.setA(pointList.get(i).get(lssList.get(j).getX()));
	    		   lssList.get(j).getLSS().segment.setB(pointList.get(i).get(lssList.get(j).getY()));
	    		   	
	    	   }
    	   }
    	   //----------------------edge--------------------------------------------------------------
    	  //----------------------facet---------------------------------------------------------------
    	   if(facetOn) 
    	   { 
	    	   for(int j=0;j<facetList.get(i).size();j++) 
	    	   {
	    		   boolean exist=false;
	    		   for(int k=0;k<dislayFacetList.size();k++) 
	        	   {
	    			   
	    			   if(facetList.get(i).get(j).getX()==dislayFacetList.get(k).getX() &&
	    				  facetList.get(i).get(j).getY()==dislayFacetList.get(k).getY() &&
	    				  facetList.get(i).get(j).getZ()==dislayFacetList.get(k).getZ()   )
	    			   {
	    				   exist=true;
	    			   }
	    			   
	        	   }
	    		   
	    		   if(!exist)
	    		   {
	    			     
	    			   Facet newFacet = new Facet(
	    					   facetList.get(i).get(j).getX(),
	    					   facetList.get(i).get(j).getY(),
	    					   facetList.get(i).get(j).getZ(), 
	    					    new Triangle(
	      						pointList.get(i).get(facetList.get(i).get(j).getX()),
	       						pointList.get(i).get(facetList.get(i).get(j).getY()),
	       						pointList.get(i).get(facetList.get(i).get(j).getZ())));   
	    			   
	   				   dislayFacetList.add(newFacet);
	    			   Color c = new Color(0,200,0,100);
	    			   scene.addShape(dislayFacetList.get(dislayFacetList.size()-1).getTriangle() ,c);
	    		   }
	    		      
	    	   }
	    	   
	    	   for(int j=0;j<dislayFacetList.size();j++)
	    	   {
	    		   boolean hidden=true;
	    		   for(int k=0;k<facetList.get(i).size();k++)
	        	   {
	    			   if(facetList.get(i).get(k).getX()==dislayFacetList.get(j).getX() &&
	    				  facetList.get(i).get(k).getY()==dislayFacetList.get(j).getY() &&
	    				  facetList.get(i).get(k).getZ()==dislayFacetList.get(j).getZ()   )
	    	    			   
	    			   {
	    				   hidden=false;
	    		       }
	        	   }
	    		   
	    		   if(hidden)
	    		   {
	    			   scene.removeShape(dislayFacetList.get(j).getTriangle());
	    			   dislayFacetList.remove(j);    				   
					   j--;
	    		   }
	    	   }
	    	   
	    	   for(int j=0;j<dislayFacetList.size();j++)
	    	   {
	    		   dislayFacetList.get(j).getTriangle();
	    		   scene.removeShape(dislayFacetList.get(j).getTriangle());
	    		   Triangle tri = new Triangle(
							pointList.get(i).get(dislayFacetList.get(j).getX()),
							pointList.get(i).get(dislayFacetList.get(j).getY()),
							pointList.get(i).get(dislayFacetList.get(j).getZ()));
	    		   dislayFacetList.get(j).setTriangle(tri);
	    		   Color c = new Color(0,200,0,100);
	    		   scene.addShape(dislayFacetList.get(j).getTriangle() ,c);
	    	   }
	       }
    	  //----------------------facet---------------------------------------------------------------
    	 //----------------------Cell---------------------------------------------------------------
    	   if(cellOn) 
    	   { 
    		   for(int j=0;j<cellList.get(i).size();j++) 
	    	   {
	    		   boolean exist=false;
	    		   for(int k=0;k<dislayCellList.size();k++) 
	        	   {
	    			   
	    			   if(cellList.get(i).get(j).getX()==dislayCellList.get(k).getX() &&
	    				  cellList.get(i).get(j).getY()==dislayCellList.get(k).getY() &&
	    				  cellList.get(i).get(j).getZ()==dislayCellList.get(k).getZ() &&
	    	    		  cellList.get(i).get(j).getV()==dislayCellList.get(k).getV()   )
	    			   {
	    				   exist=true;
	    			   }
	    			   
	        	   }
	    		   
	    		   if(!exist)
	    		   {
	    			     
	    			   Cell newCell = new Cell(
	    					   cellList.get(i).get(j).getX(),
	    					   cellList.get(i).get(j).getY(),
	    					   cellList.get(i).get(j).getZ(),
	    					   cellList.get(i).get(j).getZ(), 
	    					    new Tetrahedron(
	      						pointList.get(i).get(cellList.get(i).get(j).getX()),
	       						pointList.get(i).get(cellList.get(i).get(j).getY()),
	       						pointList.get(i).get(cellList.get(i).get(j).getZ()),
	       						pointList.get(i).get(cellList.get(i).get(j).getV())));   
	    			   
	    			   dislayCellList.add(newCell);
	    			   Color c = new Color(0,200,0,100);
	    			   scene.addShape(dislayCellList.get(dislayCellList.size()-1).getTetrahedron() ,c);
	    		   }
	    		      
	    	   }
	    	   
	    	   for(int j=0;j<dislayCellList.size();j++)
	    	   {
	    		   boolean hidden=true;
	    		   for(int k=0;k<cellList.get(i).size();k++)
	        	   {
	    			   if(cellList.get(i).get(k).getX()==dislayCellList.get(j).getX() &&
	    				  cellList.get(i).get(k).getY()==dislayCellList.get(j).getY() &&
	    				  cellList.get(i).get(k).getZ()==dislayCellList.get(j).getZ() &&
	    	    		  cellList.get(i).get(k).getV()==dislayCellList.get(j).getV()  )
	    	    			   
	    			   {
	    				   hidden=false;
	    		       }
	        	   }
	    		   
	    		   if(hidden)
	    		   {
	    			   scene.removeShape(dislayCellList.get(j).getTetrahedron());
	    			   dislayCellList.remove(j);    				   
					   j--;
	    		   }
	    	   }
	    	   
	    	   for(int j=0;j<dislayCellList.size();j++)
	    	   {
	    		   dislayCellList.get(j).getTetrahedron();
	    		   scene.removeShape(dislayCellList.get(j).getTetrahedron());
	    		   Tetrahedron tet = new Tetrahedron(
							pointList.get(i).get(dislayCellList.get(j).getX()),
							pointList.get(i).get(dislayCellList.get(j).getY()),
							pointList.get(i).get(dislayCellList.get(j).getZ()),
							pointList.get(i).get(dislayCellList.get(j).getV()));
	    		   dislayCellList.get(j).setTetrahedron(tet);
	    		   Color c = new Color(0,200,0,100);
	    		   scene.addShape(dislayCellList.get(j).getTetrahedron() ,c);
	    	   }
	       }
    	 //----------------------Cell---------------------------------------------------------------
    	   
    	   scene.repaint();
    	   try { Thread.sleep(timeBetweenFrames); } catch (InterruptedException e) { break; }
       }
	   
   }
}