package calePWA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class CombineFiles2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String FILENAME = "C:\\Users\\Toshiba\\Desktop\\calecPWA\\nodes.txt";
		String FILENAME2= "C:\\Users\\Toshiba\\Desktop\\calecPWA\\zdisp.txt";
		String home = System.getProperty("user.home")+"\\Downloads\\Result.xlsx";
		
		
		XSSFWorkbook workbook = new XSSFWorkbook(); 
	    //Create a blank sheet
	    XSSFSheet spreadsheet = workbook.createSheet( 
	      "Node details");
	    //Create row object
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
	    int j=0;
	    
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(FILENAME));
			BufferedReader br2 = new BufferedReader(new FileReader(FILENAME2));
			String sCurrentLine = br.readLine();
			String sCurrentLine2 = br2.readLine();

				
			while (sCurrentLine !=null && sCurrentLine2 !=null) {
					//System.out.println(sCurrentLine);
					//System.out.println(sCurrentLine2);
				
					String[] splits = sCurrentLine.split("\\s+");
					String[] splits2 = sCurrentLine2.split("\\s+");
					//System.out.println("Split length"+splits.length);
					//System.out.println("Split2 length"+splits.length);
					if(splits.length!=7){
						sCurrentLine = br.readLine();
					}
					if(splits.length!=2){
						sCurrentLine2 = br2.readLine();
					}

					if(splits.length==7 && splits2.length==2){
						System.out.println(sCurrentLine);
						System.out.println(sCurrentLine2);
						int i=0;
						j=0;
						row=spreadsheet.createRow(rowId++);
						for(i=0;i<splits.length;i++){
							if(splits[i].equals("") || splits[i].equals("0.00")) continue;
 				    	    Cell cellx=row.createCell(j);
 				    	    j++;
 				    	    cellx.setCellValue(splits[i]);
						}
						Cell cellx=row.createCell(j);
				    	cellx.setCellValue(splits2[1]);
				    	sCurrentLine = br.readLine();
				    	sCurrentLine2 = br2.readLine();
					}
				
			}
			
			FileOutputStream out = new FileOutputStream(new File(home));
			workbook.write(out);
			out.close();
			br.close();
			br2.close();
			

		} 
		catch (IOException e) {
				e.printStackTrace();
		}
	
		/*rowId=1;
		int n=j;
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME2))) {

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
						//System.out.println(sCurrentLine);
						String[] splited = sCurrentLine.split("\\s+");
						int i=0;
						j=n;
						row=spreadsheet.createRow(rowId++);
						for(i=0;i<splited.length;i++){
							if(splited[i].equals("")) continue;
 				    	    Cell cellx=row.createCell(j);
 				    	    j++;
 				    	    cellx.setCellValue(splited[i]);
						}
						
					}while((sCurrentLine = br.readLine())!=null);
					
				}
			}
			//String home = System.getProperty("user.home")+"\\Downloads\\Result.xlsx";
			FileOutputStream out = new FileOutputStream(new File(home));
			workbook.write(out);
			out.close();

		} 
		catch (IOException e) {
				e.printStackTrace();
		}*/
	
	}
}
