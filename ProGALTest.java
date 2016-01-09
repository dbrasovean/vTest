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
	
	public static ArrayList<Edge> findNewPoints(ArrayList<Edge> list1 ,ArrayList<Edge> list2)
	{
		ArrayList<Edge> rez = new ArrayList<Edge>();
		for(int i=0;i<list1.size();i++)
		{
			boolean ok = true;
			for(int j=0;j<list2.size();j++)
			{
				if(list1.get(i).getX() == list2.get(j).getX() &&
				   list1.get(i).getY() == list2.get(j).getY())
				{
					ok = false;
				}
			}
			if(ok)
			{
				rez.add(list1.get(i));
			}
		}
		
		return rez;
	}
	
	public static ArrayList<Facet> findNewFacets(ArrayList<Facet> list1 ,ArrayList<Facet> list2)
	{
		ArrayList<Facet> rez = new ArrayList<Facet>();
		for(int i=0;i<list1.size();i++)
		{
			boolean ok = true;
			for(int j=0;j<list2.size();j++)
			{
				if(list1.get(i).getX() == list2.get(j).getX() &&
				   list1.get(i).getY() == list2.get(j).getY() &&
				   list1.get(i).getZ() == list2.get(j).getZ())
				{
					ok = false;
				}
			}
			if(ok)
			{
				rez.add(list1.get(i));
			}
		}
		
		return rez;
	}
	
   public static void main(String[] args){
	   
	   //----------------reading-------------
	   ArrayList<ArrayList<Point>> pointList = new ArrayList<ArrayList<Point>>();
	   ArrayList<ArrayList<Edge>> edgeHiddenList = new ArrayList<ArrayList<Edge>>();
	   ArrayList<ArrayList<Facet>> facetHiddenList = new ArrayList<ArrayList<Facet>>();
	   ArrayList<ArrayList<Tetrahedron>> cellHiddenList = new ArrayList<ArrayList<Tetrahedron>>();
	   ArrayList<Edge> edgeList = new ArrayList<Edge>();	   
	   ArrayList<Facet> facetList = new ArrayList<Facet>();
	   ArrayList<Tetrahedron> cellList = new ArrayList<Tetrahedron>();
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
        	   	case"HiddenEdges"	  : readLineType = "HiddenEdges";
								lineIdf = true;
								break;
        	   	case"HiddenFacet"   : readLineType = "HiddenFacet";
								lineIdf = true;
								break;
        	   	case"HiddenCell"    : readLineType = "HiddenCell";
								lineIdf = true;
								break;
        	   	case"Frame"   : 
        		   pointList.add(new ArrayList<Point>());
        		   edgeHiddenList.add(new ArrayList<Edge>());
        		   facetHiddenList.add(new ArrayList<Facet>());
        		   cellHiddenList.add(new ArrayList<Tetrahedron>());
        		   
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
    					int x = Integer.parseInt(strEdgesArray[0]);
    					int y = Integer.parseInt(strEdgesArray[1]);
    					LineSegment newLineSegment = new LineSegment(pointList.get(0).get(x),
    							pointList.get(0).get(y));
    					Edge newEdge = new Edge(x,y,new LSS(newLineSegment,0.05));
    					edgeList.add(newEdge);
    					
    					//System.out.println(strEdgesArray[0]+" "+strEdgesArray[1]);
    					//System.out.println(edge.get(0)+" "+edge.get(1));
    					break;
    				case"Facet"   : 
    					String strFacetArray[] = new String[3];
    					strFacetArray = line.substring(1, line.length()-1).split("\\)\\(");
    					
    					 x = Integer.parseInt(strFacetArray[0]);
    					 y = Integer.parseInt(strFacetArray[1]);
    					 int z = Integer.parseInt(strFacetArray[2]);
    					Facet newFacet = new Facet(x,y,z,new Triangle(
    							pointList.get(frame).get(x),
    							pointList.get(frame).get(y),
    							pointList.get(frame).get(z)));
    					facetList.add(newFacet);
    					//System.out.println(strFacetArray[0]+" "+strFacetArray[1]+" "+strFacetArray[2]);
    					//System.out.println(newTriangle);
    					break;
    				case"Cell"    : 
    					String strCellArray[] = new String[3];
    					strCellArray = line.substring(1, line.length()-1).split("\\)\\(");
    					
    					Tetrahedron newTetrahedron = new Tetrahedron(pointList.get(frame).get(Integer.parseInt(strCellArray[0])),
    							pointList.get(frame).get(Integer.parseInt(strCellArray[1])),
    							pointList.get(frame).get(Integer.parseInt(strCellArray[2])),
    							pointList.get(frame).get(Integer.parseInt(strCellArray[3])));
    					cellList.add(newTetrahedron);
    					//System.out.println(strCellArray[0]+" "+strCellArray[1]+" "+strCellArray[2]+" "+strCellArray[3]);
    					//System.out.println(newTetrahedron);
    								break;				
    				case"HiddenEdges"	  : 
    					String strHiddenEdgesArray[] = new String[2];
    					strHiddenEdgesArray = line.substring(1, line.length()-1).split("\\)\\(");
    					
    					x = Integer.parseInt(strHiddenEdgesArray[0]);
    					y = Integer.parseInt(strHiddenEdgesArray[1]);
    					
    					Edge newHiddenedge = new Edge(x,y,null); 
    					edgeHiddenList.get(frame).add(newHiddenedge);
    					

    					break;
    				case"HiddenFacet"   : 
    					String strHiddenFacetArray[] = new String[3];
    					strHiddenFacetArray = line.substring(1, line.length()-1).split("\\)\\(");

    					x = Integer.parseInt(strHiddenFacetArray[0]);
    					y = Integer.parseInt(strHiddenFacetArray[1]);
   					    z = Integer.parseInt(strHiddenFacetArray[2]);
   					    
   					    Facet newHiddenFacet = new Facet(x,y,z,null);
    					facetHiddenList.get(frame).add(newHiddenFacet);
    					
    								break;
    				case"HiddenCell"    : 
    					String strHiddenCellArray[] = new String[3];
    					strCellArray = line.substring(1, line.length()-1).split("\\)\\(");
    					Tetrahedron newHiddenTetrahedron = new Tetrahedron(
    							pointList.get(frame).get(Integer.parseInt(strCellArray[0])),
    							pointList.get(frame).get(Integer.parseInt(strCellArray[1])),
    							pointList.get(frame).get(Integer.parseInt(strCellArray[2])),
    							pointList.get(frame).get(Integer.parseInt(strCellArray[3])));
    					cellHiddenList.get(frame).add(newHiddenTetrahedron);
    					//System.out.println(strCellArray[0]+" "+strCellArray[1]+" "+strCellArray[2]+" "+strCellArray[3]);
    					//System.out.println(newTetrahedron);
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
	   
       //System.out.println("LD Library Path:" + System.getProperty("java.library.path"));
	   J3DScene scene = J3DScene.createJ3DSceneInFrame();
	   //Display 
	   
	   ArrayList<Sphere> sphereList = new ArrayList<Sphere>();
	   for(int i=0;i< pointList.get(0).size();i++)
	   {
		   sphereList.add(new Sphere(pointList.get(0).get(i),0.3));
		   scene.addShape(sphereList.get(i), java.awt.Color.RED);
	   }
	   
	 //----------------------edge---------------------------------------------------------------
	   
	   ArrayList<Edge> lssList = new ArrayList<Edge>();
	   int ok=0;
	   for(int i=0;i< edgeList.size();i++)
	   {
		   boolean hidden = false;
		   for(int j=0;j<edgeHiddenList.get(1).size();j++)
		   {
			   if(edgeList.get(i).getX() == edgeHiddenList.get(1).get(j).getX() &&
				  edgeList.get(i).getY() == edgeHiddenList.get(1).get(j).getY() ||
				  edgeList.get(i).getY() == edgeHiddenList.get(1).get(j).getX() &&
				  edgeList.get(i).getX() == edgeHiddenList.get(1).get(j).getY())
			   {
				   hidden=true;
			   }
		   }
		   
		   if(!hidden)
		   {	
			   
			   lssList.add(edgeList.get(i));
			   scene.addShape(lssList.get(ok).getLSS(), java.awt.Color.BLACK);
			   ok++;
		   }
		   
	   }
	 //----------------------edge---------------------------------------------------------------
	 //----------------------facet---------------------------------------------------------------
	   ArrayList<Facet> dislayFacetList = new ArrayList<Facet>();
	   int n=0;
	   for(int i=0;i<facetList.size();i++)
	   {
		   boolean hidden = false;
		   for(int j=0;j<facetHiddenList.get(1).size();j++)
		   {
			   if(facetList.get(i).getX() == facetHiddenList.get(1).get(j).getX() &&
				  facetList.get(i).getY() == facetHiddenList.get(1).get(j).getY() &&
				  facetList.get(i).getZ() == facetHiddenList.get(1).get(j).getZ())
			   {
				   hidden=true;
			   }
		   }
		   if(!hidden)
		   {				   
			 dislayFacetList.add(facetList.get(i));
			 Color c = new Color(0,200,0,100);
   		     scene.addShape(dislayFacetList.get(n).getTriangle(), c);
   		     n++;
		   }
	   }
	   
	 //----------------------facet---------------------------------------------------------------

       for(int i=1;i<frame;i++)
       {
    	   //scene.removeAllShapes();
    	   for(int j=0;j< pointList.get(i).size();j++)
    	   {
    		   sphereList.get(j).setCenter(pointList.get(i).get(j));	   
    	   }
    	   
    	   //----------------------edge---------------------------------------------------------------
    	   
    	   ArrayList<Edge> newHiddenEdges = findNewPoints(edgeHiddenList.get(i),edgeHiddenList.get(i-1));
    	   ArrayList<Edge> newEdges = findNewPoints(edgeHiddenList.get(i-1),edgeHiddenList.get(i));

    	   
    	   for(int j=0;j<edgeList.size();j++)
    	   {
    		   for(int z=0;z<newEdges.size();z++)
        	   { 
	    		   if(edgeList.get(j).getX() == newEdges.get(z).getX() &&
	    			  edgeList.get(j).getY() == newEdges.get(z).getY()||
	    			  edgeList.get(j).getY() == newEdges.get(z).getX() &&
	    	    	  edgeList.get(j).getX() == newEdges.get(z).getY())
	    			{ 
	    			   edgeList.get(j).getLSS().segment.setA(pointList.get(i).get(edgeList.get(j).getX()));
	    			   edgeList.get(j).getLSS().segment.setB(pointList.get(i).get(edgeList.get(j).getY()));
	    			   lssList.add(edgeList.get(j));
	    			   scene.addShape(lssList.get(lssList.size()-1).getLSS(), java.awt.Color.BLACK);
	    			}
        	   }
    	   }
    	   
    	   for(int j=0;j<newEdges.size();j++)
    	   {
    		   boolean exist=false;
    		   for(int z=0;z<edgeList.size();z++)
        	   { 
    			   if(edgeList.get(z).getX() == newEdges.get(j).getX() &&
    		    	  edgeList.get(z).getY() == newEdges.get(j).getY()||
    		    	  edgeList.get(z).getY() == newEdges.get(j).getX() &&
    		    	  edgeList.get(z).getX() == newEdges.get(j).getY())
    			   {
    				   exist = true;
    			   }
        	   }
    		   
    		   if(!exist)
    		   {
    			   LineSegment newLineSegment = new LineSegment(pointList.get(i).get(newEdges.get(j).getX()),
							pointList.get(i).get(newEdges.get(j).getY()));
					Edge newEdge = new Edge(newEdges.get(j).getX(),newEdges.get(j).getY(),new LSS(newLineSegment,0.05));
					edgeList.add(newEdge);
					lssList.add(newEdge);
	    			scene.addShape(lssList.get(lssList.size()-1).getLSS(), java.awt.Color.BLACK);
    		   }
    	   }
    	   
    	   for(int j=0;j<lssList.size();j++)
    	   {
    		   for(int z=0;z<newHiddenEdges.size();z++)
        	   {
    			   if(lssList.get(j).getX() == newHiddenEdges.get(z).getX() &&
    				  lssList.get(j).getY() == newHiddenEdges.get(z).getY()||
    			   	  lssList.get(j).getY() == newHiddenEdges.get(z).getX() &&
    			   	  lssList.get(j).getX() == newHiddenEdges.get(z).getY())
    		    	{
    				   scene.removeShape(lssList.get(j).getLSS());
    				   lssList.remove(j);    				   
    				   j--;
    				   break;
    		    	}
        	   }
    	   }
    	   
    	   
    	   for(int j=0;j<lssList.size();j++)
    	   {
    		   lssList.get(j).getLSS().segment.setA(pointList.get(i).get(lssList.get(j).getX()));
    		   lssList.get(j).getLSS().segment.setB(pointList.get(i).get(lssList.get(j).getY()));
    		   	
    	   }
    	   
    	   //----------------------edge--------------------------------------------------------------
    	  //----------------------facet---------------------------------------------------------------
    	   ArrayList<Facet> newHiddenFacets = findNewFacets(facetHiddenList.get(i),facetHiddenList.get(i-1));
    	   ArrayList<Facet> newFacets = findNewFacets(facetHiddenList.get(i-1),facetHiddenList.get(i));

    	   for(int j=0;j<facetList.size();j++)
    	   {
    		   for(int z=0;z<newFacets.size();z++)
        	   { 
    			   if(facetList.get(j).getX() == newFacets.get(z).getX() &&
    				  facetList.get(j).getY() == newFacets.get(z).getY() &&
    				  facetList.get(j).getZ() == newFacets.get(z).getZ() )
    			   {
    				   dislayFacetList.add(facetList.get(j));
    			   }
        	   }
    	   }
    	   
    	   for(int j=0;j<newFacets.size();j++)
    	   {
    		   boolean exist=false;
    		   for(int z=0;z<facetList.size();z++)
        	   { 
    			   if(facetList.get(z).getX() == newFacets.get(j).getX() &&
    	    		  facetList.get(z).getY() == newFacets.get(j).getY() &&
    	    		  facetList.get(z).getZ() == newFacets.get(j).getZ() )
    	    	   {
    				   exist = true;	   
    	    	   }
        	   }
    		   
    		   if(!exist)
    		   {
    			   Facet newFacet = new Facet(
    					   newFacets.get(j).getX(),
    					   newFacets.get(j).getY(),
    					   newFacets.get(j).getZ(),
    					   new Triangle(
      						pointList.get(i).get(newFacets.get(j).getX()),
       						pointList.get(i).get(newFacets.get(j).getY()),
       						pointList.get(i).get(newFacets.get(j).getZ())));
    			   
    			   facetList.add(newFacet);
    			   dislayFacetList.add(newFacet);
    		   }
    	   }
    	   
    	   for(int j=0;j<dislayFacetList.size();j++)
    	   {
    		   for(int z=0;z<newHiddenFacets.size();z++)
        	   {
    			   if(dislayFacetList.get(j).getX() == newHiddenFacets.get(z).getX() &&
    				  dislayFacetList.get(j).getY() == newHiddenFacets.get(z).getY() &&
    				  dislayFacetList.get(j).getZ() == newHiddenFacets.get(z).getZ() )
    			   {
    				   scene.removeShape(dislayFacetList.get(j).getTriangle());
    				   dislayFacetList.remove(j);
    				   j--;
    				   break;
    			   }
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
    	   
    	  //----------------------facet---------------------------------------------------------------
    	   
    	   for(Tetrahedron cell: cellList){
    		    //scene.addShape(cell, java.awt.Color.YELLOW);
    	   }
    	   scene.repaint();
    	   System.out.println(i);
    	   try { Thread.sleep(1000); } catch (InterruptedException e) { break; }
       }
	   
   }
}