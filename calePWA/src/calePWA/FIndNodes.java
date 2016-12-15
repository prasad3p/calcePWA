package calePWA;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import dataStructures.Component;
import dataStructures.Node;
import dataStructures.Part;

public class FIndNodes {

	private static String COMPFILEPATH=System.getProperty("user.home") +"\\Desktop\\calecPWA\\comp1.xlsx";
	private static String PARTSFILEPATH=System.getProperty("user.home") +"\\Desktop\\calecPWA\\parts1.xlsx";
	private static Component comp = new Component("", null, 0, 0, 0, "");
	private static ArrayList<Node> nodeListMain = new ArrayList<Node>();
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String myID="FD8";
		
		Thread t1= new Thread(new Runnable() {
			
			@Override
			public void run() {
				//Searches component in the excel file.
				//Initialize component data structure.
				try {
					comp=getComponentDetails(comp,myID,COMPFILEPATH);
					comp=getPartDetails(comp,PARTSFILEPATH);
					//Printing part details
					System.out.println("Comp ID:"+comp.getId());
					System.out.println("Side:"+comp.getSide());
					System.out.println("Center x-cord:"+comp.getxC());
					System.out.println("Center y-cord:"+comp.getyC());
					System.out.println("Center z-cord:"+comp.getzC());
					System.out.println("Part id:"+comp.getP().getId());
					System.out.println("Part length:"+comp.getP().getLen());
					System.out.println("Part width:"+comp.getP().getWid());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Initialize part data structure.
			}
		});
		
		
		Thread t2= new Thread(new Runnable() {
			public void run() {
			//Create list of all the Nodes on the grid.
				CreateList createList = new CreateList();
				try {
					nodeListMain=createList.createListStarter(nodeListMain);
					System.out.println("Main nodelist size:"+nodeListMain.size());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		//Find list of Nodes below the given component/part.
		ArrayList<Node> nodesUnderThecomp = new ArrayList<Node>();
		nodesUnderThecomp = findNodesUnderThePart(nodesUnderThecomp,comp,nodeListMain);
		System.out.println("Number of Nodes under given part:"+nodesUnderThecomp.size());
		
		double ySum=0;
		for(int i=0;i<nodesUnderThecomp.size();i++){
			ySum+=nodesUnderThecomp.get(i).getzCord();
		}
				
		if(nodesUnderThecomp.size()!=0)
			comp.setzC(ySum/nodesUnderThecomp.size());
	
		System.out.println("Center z-cord:"+comp.getzC());
		
		
	}

	private static ArrayList<Node> findNodesUnderThePart(ArrayList<Node> nodeList, Component comp,ArrayList<Node> nodeListMain) {
		// TODO Auto-generated method stub
		double xMax = comp.getxC()+(comp.getP().getLen()/2);
		double xMin = comp.getxC()-(comp.getP().getLen()/2);
		double yMax = comp.getyC()+(comp.getP().getWid()/2);
		double yMin = comp.getyC()-(comp.getP().getWid()/2);
		
		/*xMax=xMax*Math.pow(10, -3);
		xMin=xMin*Math.pow(10, -3);
		yMax=yMax*Math.pow(10, -3);
		yMin=yMin*Math.pow(10, -3);*/
		
		System.out.println("xMax:"+xMax);
		System.out.println("xMin:"+xMin);
		System.out.println("yMax:"+yMax);
		System.out.println("yMin:"+yMin);
		
		for(int i=0;i<nodeListMain.size();i++){
			
			double xCord=nodeListMain.get(i).getxCord();
			double yCord=nodeListMain.get(i).getyCord();
			
			if(xCord <= xMax && xCord >=xMin && yCord <=yMax && yCord >= yMin){
				nodeList.add(nodeListMain.get(i));
			}
		}
		return nodeList;
	}

	private static Component getPartDetails(Component comp, String fileName) throws IOException {
		// TODO Auto-generated method stub
		InputStream input = new FileInputStream(fileName);
		XSSFWorkbook workbook = new XSSFWorkbook(input); 
	    XSSFSheet sheet = workbook.getSheetAt(0);
	    int j=-1;
	    boolean flag=false;
		for(Row row : sheet) {
            for(Cell cell : row) {
                j++;    
            	if(cell.getRichStringCellValue().getString().replaceAll("\\s","").equals(comp.getP().getId())){
            		
            		flag=true;
                   	j+=5;
                   	cell=row.getCell(++j);
                   	comp.getP().setLen((double)(cell.getNumericCellValue()));
                   	cell=row.getCell(++j);
                   	comp.getP().setWid((double)(cell.getNumericCellValue()));

            	}
               	break;
            }
            if(flag) break;
            j=-1;
		}
		workbook.close();
		comp.getP().setLen(comp.getP().getLen()*Math.pow(10, -3));
		comp.getP().setWid(comp.getP().getWid()*Math.pow(10, -3));
		return comp;	
	}

	private static Component getComponentDetails(Component comp, String myID, String fileName) throws IOException {
		// TODO Auto-generated method stub

		InputStream input = new FileInputStream(fileName);
		XSSFWorkbook workbook = new XSSFWorkbook(input); 
	    XSSFSheet sheet = workbook.getSheetAt(0);
	    int j=-1;
	    boolean flag=false;
		for(Row row : sheet) {
            for(Cell cell : row) {
                j++;    
            	if(cell.getRichStringCellValue().getString().replaceAll("\\s","").equals(myID)){
            		
            		flag=true;
                   	comp.setId(myID);
                   	Part p1=new Part("", 0, 0);
                   	cell=row.getCell(++j);
                   	p1.setId(cell.getRichStringCellValue().getString().replaceAll("\\s",""));
                   	comp.setP(p1);
                   	cell=row.getCell(++j);
                   	comp.setSide(cell.getRichStringCellValue().getString().replaceAll("\\s",""));
                   	cell=row.getCell(++j);
                   	comp.setxC((double)(cell.getNumericCellValue()));
                   	cell=row.getCell(++j);
                   	comp.setyC((double)(cell.getNumericCellValue()));
            	}
               	break;
            }
            if(flag) break;
            j=-1;
		}
		workbook.close();
		//Scale to mm.
		comp.setxC(comp.getxC()*Math.pow(10,-3));
		comp.setyC(comp.getyC()*Math.pow(10,-3));
		comp.setzC(comp.getzC()*Math.pow(10,-3));
		return comp;
	}

}
