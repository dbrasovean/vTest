package vTest;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import ProGAL.geom3d.LineSegment;
import ProGAL.geom3d.Point;
import ProGAL.geom3d.Triangle;
import ProGAL.geom3d.viewer.J3DScene;
import ProGAL.geom3d.volumes.LSS;
import ProGAL.geom3d.volumes.Sphere;
import ProGAL.geom3d.volumes.Tetrahedron;

class ProGALTest{
   public static void main(String[] args){
	   
	   //----------------reading-------------
	   ArrayList<ArrayList<Point>> pointList = new ArrayList<ArrayList<Point>>();
	   ArrayList<ArrayList<LineSegment>> edgeList = new ArrayList<ArrayList<LineSegment>>();
	   ArrayList<ArrayList<Triangle>> facetList = new ArrayList<ArrayList<Triangle>>();
	   ArrayList<ArrayList<Tetrahedron>> cellList = new ArrayList<ArrayList<Tetrahedron>>();
       int frame = -1;
	   // The name of the file to open.
       String fileName = "temp.txt";

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
        		   edgeList.add(new ArrayList<LineSegment>());
        		   facetList.add(new ArrayList<Triangle>());
        		   cellList.add(new ArrayList<Tetrahedron>());
        		   
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
                        System.out.println(newPoint);
                        break;
                    ////end read points
    								
    				case"Edges"	  : 
    					String strEdgesArray[] = new String[2];
    					strEdgesArray = line.substring(1, line.length()-1).split("\\)\\(");
    					
    					LineSegment newLineSegment = new LineSegment(pointList.get(frame).get(Integer.parseInt(strEdgesArray[0])),
    							pointList.get(frame).get(Integer.parseInt(strEdgesArray[1])));
    					edgeList.get(frame).add(newLineSegment);
    					System.out.println(strEdgesArray[0]+" "+strEdgesArray[1]);
    					System.out.println(newLineSegment);
    					break;
    				case"Facet"   : 
    					String strFacetArray[] = new String[3];
    					strFacetArray = line.substring(1, line.length()-1).split("\\)\\(");
    					
    					Triangle newTriangle = new Triangle(pointList.get(frame).get(Integer.parseInt(strFacetArray[0])),
    							pointList.get(frame).get(Integer.parseInt(strFacetArray[1])),
    							pointList.get(frame).get(Integer.parseInt(strFacetArray[2])));
    					facetList.get(frame).add(newTriangle);
    					System.out.println(strFacetArray[0]+" "+strFacetArray[1]+" "+strFacetArray[2]);
    					System.out.println(newTriangle);
    								break;
    				case"Cell"    : 
    					String strCellArray[] = new String[3];
    					strCellArray = line.substring(1, line.length()-1).split("\\)\\(");
    					
    					Tetrahedron newTetrahedron = new Tetrahedron(pointList.get(frame).get(Integer.parseInt(strCellArray[0])),
    							pointList.get(frame).get(Integer.parseInt(strCellArray[1])),
    							pointList.get(frame).get(Integer.parseInt(strCellArray[2])),
    							pointList.get(frame).get(Integer.parseInt(strCellArray[3])));
    					cellList.get(frame).add(newTetrahedron);
    					System.out.println(strCellArray[0]+" "+strCellArray[1]+" "+strCellArray[2]+" "+strCellArray[3]);
    					System.out.println(newTetrahedron);
    								break;
    				default		  : break;
            	   	}
        	   }
        		   
        	   
        	   System.out.println();
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
	   
       //System.out.println("LD Library Path:" + System.getProperty("java.library.path"));
	   J3DScene scene = J3DScene.createJ3DSceneInFrame();
	   //Display 
	   
       for(int i=0;i<=frame;i++)
       {
    	   scene.removeAllShapes();
    	   for(Point point: pointList.get(i)){
    		   scene.addShape(new Sphere(point,0.3), java.awt.Color.RED);
    	   }
    	   for(LineSegment edge: edgeList.get(i)){
    		   scene.addShape(new LSS(edge,0.05), java.awt.Color.BLACK);
    	   }
    	   for(Triangle facet: facetList.get(i)){
    		   	Color c = new Color(0,200,0,100);
    		    scene.addShape(facet, c);
    	   }
    	   for(Tetrahedron cell: cellList.get(i)){
    		    //scene.addShape(cell, java.awt.Color.YELLOW);
    	   }
    	   System.out.println(i);
    	   try { Thread.sleep(500); } catch (InterruptedException e) { break; }
       }
	   
   }
}