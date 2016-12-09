package calePWA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class CombineFiles {

	private static String FILENAME =System.getProperty("user.home") +"\\Desktop\\calecPWA\\nodes (1).txt";
	private static String FILENAME2 =System.getProperty("user.home") +"\\Desktop\\calecPWA\\displacement.txt";
	private static String home = System.getProperty("user.home")+"\\Desktop\\calecPWA\\Result2.xlsx";
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
	
		ArrayList <NodeCord> NodeCr = new ArrayList<NodeCord>();
		ArrayList <NodeVal> NodeVal= new ArrayList<NodeVal>();
		
		//Read from files and add to arraylists
		NodeCr = readNodeCordFile(NodeCr,FILENAME);
		NodeVal = readNodeValFile(NodeVal,FILENAME2);	
		
		//Write arraylists to excel file.
		writeToExcel(NodeCr,NodeVal);
		
		NodeCord nc = new NodeCord(0, 0, 0, 0);
		//Point to be found
		double x=0.0045,y=0.0189,z = 0.0056;
		//Find nearest point in a 3d plane to a given point.
		nc=findNearestNode(nc,x,y,z,NodeCr);
		System.out.println("Nearest point:\n"
				+"Node:"+nc.getNodeNum()
				+" x:"+nc.getxCord()
				+" y:"+nc.getyCord()
				+" z:"+nc.getzCord());
		
	}
	

	
	private static NodeCord findNearestNode(NodeCord nc, double x, double y, double z, ArrayList<NodeCord> nodeCr) {
		// TODO Auto-generated method stub
		
		double min= Math.sqrt(Math.pow(x - nodeCr.get(0).getxCord(),2)+
							Math.pow(y-nodeCr.get(0).getyCord(),2)+
							Math.pow(z-nodeCr.get(0).getzCord(),2));
		
		for(int i=1;i<nodeCr.size();i++){
			
			double dist=Math.sqrt(Math.pow(x - nodeCr.get(i).getxCord(),2)+
					Math.pow(y-nodeCr.get(i).getyCord(),2)+
					Math.pow(z-nodeCr.get(i).getzCord(),2));
			 
			if((dist)<min)
			{
				min=dist;
				nc=nodeCr.get(i);
			}
			
			
		}
		
		return nc;
	}



	private static ArrayList<calePWA.NodeVal> readNodeValFile(ArrayList<NodeVal> nodeVal, String fILENAME2) {
		// TODO Auto-generated method stub
		
		try (BufferedReader br = new BufferedReader(new FileReader(fILENAME2))) {

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				
				if(sCurrentLine.replaceAll("\\s+","").equals("NODEUZ"))
				{
					sCurrentLine = br.readLine();
					do
					{
						if(sCurrentLine.replaceAll("\\s+","").equals("NODEUZ"))
							break;
						if(sCurrentLine.replaceAll("\\s+","").equals(""))
							break;
						String[] splits = sCurrentLine.split("\\s+");
						NodeVal nodV = new NodeVal(0,0);
						nodV.setNodeNum(Integer.valueOf(splits[1]));
						nodV.setValue(Double.parseDouble(splits[2]));
						nodeVal.add(nodV);
					}while((sCurrentLine = br.readLine())!=null);
					
				}
			}

		} 
		catch (IOException e) {
				e.printStackTrace();
		}
		
		return nodeVal;
	}
	private static ArrayList<NodeCord> readNodeCordFile(ArrayList<NodeCord> nodeCr, String fILENAME) {
		// TODO Auto-generated method stub
		 try (BufferedReader br = new BufferedReader(new FileReader(fILENAME))) {

				String sCurrentLine;

				while ((sCurrentLine = br.readLine()) != null) {
					
					if(sCurrentLine.replaceAll("\\s+","").equals("NODEXYZTHXYTHYZTHZX"))
					{
						sCurrentLine = br.readLine();
						do
						{
							if(sCurrentLine.replaceAll("\\s+","").equals("NODEXYZTHXYTHYZTHZX"))
								break;
							if(sCurrentLine.replaceAll("\\s+","").equals(""))
								break;
							String[] splits = sCurrentLine.split("\\s+");
							NodeCord node = new NodeCord(0,0,0,0);
							node.setNodeNum(Integer.valueOf(splits[1]));
							node.setxCord(Double.parseDouble(splits[2]));
							node.setyCord(Double.parseDouble(splits[3]));
							node.setzCord(Double.parseDouble(splits[4]));
							nodeCr.add(node);
							
						}while((sCurrentLine = br.readLine())!=null);
						
					}
				}
			} 
			catch (IOException e) {
					e.printStackTrace();
			}
		
		
		return nodeCr;
	}
	public static void writeToExcel(ArrayList <NodeCord> NC, ArrayList <NodeVal> NV) throws IOException{
		
		// TODO Auto-generated method stub
		XSSFWorkbook workbook = new XSSFWorkbook(); 
	    XSSFSheet spreadsheet = workbook.createSheet("Node details");
	    XSSFRow row=spreadsheet.createRow(0);
	    Cell cell=row.createCell(0);
	    cell.setCellValue("Node");
	    Cell cell1=row.createCell(1);
	    cell1.setCellValue("X");
	    Cell cell2=row.createCell(2);
	    cell2.setCellValue("Y");
	    Cell cell3=row.createCell(3);
	    cell3.setCellValue("Z");
	    Cell cell4=row.createCell(4);
	    cell4.setCellValue("Value");
	    int rowId=1;
	    
	    for(int i=0;i<NC.size();i++){
	    	int j=0;
	    	row=spreadsheet.createRow(rowId++);
	    	Cell cellx=row.createCell(j++);
	    	cellx.setCellValue(NC.get(i).getNodeNum());
	    	Cell celly=row.createCell(j++);
	    	celly.setCellValue(NC.get(i).getxCord());
	    	Cell cellz=row.createCell(j++);
	    	cellz.setCellValue(NC.get(i).getyCord());
	    	Cell cellw=row.createCell(j++);
	    	cellw.setCellValue(NC.get(i).getzCord());
	    	Cell cellv=row.createCell(j);
	    	cellv.setCellValue(NV.get(i).getValue());
	    }
	    FileOutputStream out = new FileOutputStream(new File(home));
		workbook.write(out);
		out.close();
		
	}
}
