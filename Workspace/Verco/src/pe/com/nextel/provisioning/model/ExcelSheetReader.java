package pe.com.nextel.provisioning.model;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;



public class ExcelSheetReader
{

/**
* This method is used to read the data's from an excel file.
* @param fileName - Name of the excel file.
*/
private void readExcelFile(String fileName)
{
/**
* Create a new instance for cellDataList
*/
List cellDataList = new ArrayList();
try
{
/**
* Create a new instance for FileInputStream class
*/
FileInputStream fileInputStream = new FileInputStream(fileName);

/**
* Create a new instance for POIFSFileSystem class
*/
POIFSFileSystem fsFileSystem = new POIFSFileSystem(fileInputStream);

/*
* Create a new instance for HSSFWorkBook Class
*/
HSSFWorkbook workBook = new HSSFWorkbook(fsFileSystem);
HSSFSheet hssfSheet = workBook.getSheetAt(0);

/**
* Iterate the rows and cells of the spreadsheet
* to get all the datas.
*/
Iterator rowIterator = hssfSheet.rowIterator();

while (rowIterator.hasNext())
{
HSSFRow hssfRow = (HSSFRow) rowIterator.next();
Iterator iterator = hssfRow.cellIterator();
List cellTempList = new ArrayList();
while (iterator.hasNext())
{
HSSFCell hssfCell = (HSSFCell) iterator.next();
cellTempList.add(hssfCell);
}
cellDataList.add(cellTempList);
}
}
catch (Exception e)
{
e.printStackTrace();
}
/**
* Call the printToConsole method to print the cell data in the
* console.
*/
printToConsole(cellDataList);
}

/**
* This method is used to print the cell data to the console.
* @param cellDataList - List of the data's in the spreadsheet.
*/
private void printToConsole(List cellDataList)
{
for (int i = 0; i < cellDataList.size(); i++)
{
List cellTempList = (List) cellDataList.get(i);
for (int j = 0; j < cellTempList.size(); j++)
{
HSSFCell hssfCell = (HSSFCell) cellTempList.get(j);

String stringCellValue = hssfCell.toString();

//System.out.print(i+" <==> "+stringCellValue + "\t");

System.out.print(stringCellValue + "\t");



}
System.out.println();
}
}

public static void main(String[] args)
{
/*String fileName = "C:" + File.separator + "Users" +
File.separator + "Giftsam" + File.separator + "Desktop" +
File.separator + "sampleexcel.xls";
*/
	String fileName ="C:/store.xls";
new ExcelSheetReader().readExcelFile(fileName);
}
}

