package calePWA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class CombineFiles3 {

	private static String FILENAME =System.getProperty("user.home") +"\\Desktop\\calecPWA\\nodes1.txt";
	private static String FILENAME2 =System.getProperty("user.home") +"\\Desktop\\calecPWA\\zdisp.txt";
	private static String home = System.getProperty("user.home")+"\\Desktop\\calecPWA\\Result2.xlsx";
	
	public static void main(String[] args) throws IOException {
		
		String sCurrentLine = null;
		String sCurrentLine2 = null;
		
		PrintWriter writer = new PrintWriter(System.getProperty("user.home")+"\\Desktop\\calecPWA\\Result2.txt");
		
		
		BufferedReader br = new BufferedReader(new FileReader(FILENAME));
		BufferedReader br2 = new BufferedReader(new FileReader(FILENAME2));
		ArrayList <NodeCord> NodeCr = new ArrayList<NodeCord>();
		ArrayList <NodeVal> NodeVal= new ArrayList<NodeVal>();
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
	    int count1=0;
		int count2=0;

		
		while(true){
		
			while(count1!=1000 && (sCurrentLine = br.readLine()) != null){
				
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
						NodeCr.add(node);
						count1++;
						if(count1==1000)break;
						
					}while((sCurrentLine = br.readLine())!=null);
					
				}
				
			}
			count1=0;			
			
			while(count2!=1000 && (sCurrentLine2 = br2.readLine()) != null){
				
				if(sCurrentLine2.replaceAll("\\s+","").equals("NODEUZ"))
				{
					sCurrentLine2 = br2.readLine();
					do
					{
						if(sCurrentLine2.replaceAll("\\s+","").equals("NODEUZ"))
							break;
						if(sCurrentLine2.replaceAll("\\s+","").equals(""))
							break;
						String[] splits = sCurrentLine2.split("\\s+");
						NodeVal nodV = new NodeVal(0,0);
						nodV.setNodeNum(Integer.valueOf(splits[1]));
						nodV.setValue(Double.parseDouble(splits[2]));
						NodeVal.add(nodV);
						count2++;
						if(count2==1000)break;
					}while((sCurrentLine2 = br2.readLine())!=null);
					
				}
				
				
			}
			count2=0;
			
			//rowId=writeToExcel(NodeCr,NodeVal,rowId,row,spreadsheet,workbook);
			rowId++;
			for(int k=0;k<NodeCr.size();k++)
				writer.println(NodeCr.get(k).getNodeNum()+"coords:"+NodeCr.get(k).getxCord()+ " "+NodeCr.get(k).getyCord()+ " "+NodeCr.get(k).getzCord());
			writer.println(" ");
			for(int k=0;k<NodeVal.size();k++)
				writer.println(NodeVal.get(k).getNodeNum()+"val:"+ NodeVal.get(k).getValue());
			writer.println(" ");
			
			NodeCr.clear();
			NodeVal.clear();
			
			if(sCurrentLine==null && sCurrentLine2==null) break;
					
		}
		br.close();
		br2.close();
		writer.close();
		
	}
	
	public static int writeToExcel(ArrayList <NodeCord> NC, ArrayList <NodeVal> NV,int rowId, XSSFRow row, XSSFSheet spreadsheet, XSSFWorkbook workbook) throws IOException{
		
		// TODO Auto-generated method stub
  
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
		return rowId;
		
	}
	/*private static ArrayList<NodeVal> addNodeValuesToList(String sCurrentLine, ArrayList<NodeVal> nodeVal, BufferedReader br) throws IOException {
	// TODO Auto-generated method stub
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
	return nodeVal;
}
private static ArrayList<NodeCord> addNodeCordsToList(String sCurrentLine, ArrayList<NodeCord> nodeCr, BufferedReader br) throws IOException {
	// TODO Auto-generated method stub
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

	return nodeCr;
}*/
}
