package pe.com.nextel.provisioning.model.bo;
import java.io.File;
import java.util.Date;
import jxl.*;

public class Excel {

	/**
	 * @param args
	 */
	public static void main(String[] args) {


		//Declaro un workbook con el archivo
		Workbook workbook;
		try {
			workbook = Workbook.getWorkbook(new File("C:/store.xls"));
		

		//Tomo la primera hoja
		Sheet sheet = workbook.getSheet(0);

		// Luego de tener seleccionada la hoja, tomo los datos de las celdas. En el ejemplo que sigue B2 es un valor numerico y C2 es una fecha

		Cell a1 = sheet.getCell(0,0);
		Cell b2 = sheet.getCell(1,1);
		Cell c2 = sheet.getCell(2,1);
		
		
		String stringa1 = a1.getContents();
		String stringb2 = b2.getContents();
		String stringc2 = c2.getContents();
		
		System.out.println("==>(1):"+stringa1+"==>(2):"+stringb2+"==>(2):"+stringc2);	
		
		//En el caso que quiera obtener el tipo de dato que contiene la celda lo debo hacer de la siguiente forma

		//String stringa1 = null;
		double numberb2 = 0;
		Date datec2 = null;

		
		if (a1.getType() == CellType.LABEL)
		{
		LabelCell lc = (LabelCell) a1;
		stringa1 = lc.getString();
		}

		if (b2.getType() == CellType.NUMBER)
		{
		NumberCell nc = (NumberCell) b2;
		numberb2 = nc.getValue();
		}

		if (c2.getType() == CellType.DATE)
		{
		DateCell dc = (DateCell) c2;
		datec2 = dc.getDate();
		}

		// Finalmente cerramos el workbook y liberamos la memoria
		workbook.close();	
		} catch (Exception e) {
			System.out.println("==>"+e.getMessage());	
		}
		
	}

}
