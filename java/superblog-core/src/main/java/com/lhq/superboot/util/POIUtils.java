package com.lhq.superboot.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: POI工具类
 * @author: lct
 * @date: 2019年4月25日 上午10:09:48
 */
public class POIUtils {
	
	private static Logger logger  = LoggerFactory.getLogger(POIUtils.class);
	private final static String xls = "xls";  
    private final static String xlsx = "xlsx";
    
    public static List<String[]> readExcel(MultipartFile file, int SheetNo, String[] titleName) throws Exception{  
        //检查文件  
        checkFile(file);  
        //获得Workbook工作薄对象  
        Workbook workbook = getWorkBook(file);  
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回  
        List<String[]> list = new ArrayList<>();  
        if(workbook != null){  
        	//获得当前sheet工作表  
        	Sheet sheet = workbook.getSheetAt(SheetNo);  
        	if(sheet != null){  
                //获得当前sheet的开始行  
                int firstRowNum  = sheet.getFirstRowNum();  
                //获得当前sheet的结束行  
                int lastRowNum = sheet.getLastRowNum();  
                //验证titleName 为null时代表不校验null表头列
                Row titleRow = sheet.getRow(firstRowNum);  
                for (int i = 0; i < titleName.length; i++) {
                	if(StringUtils.isEmpty(titleName[i])) {
                		continue;
                	}
					if(!getCellValue(titleRow.getCell(i)).equals(titleName[i])) {
						throw new Exception("标题有误！顺序应为【"+FastJsonUtils.toJSONFeatures(titleName)+"】");
					}
				}
                //循环除了第一行的所有行  
                for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){  
                    //获得当前行  
                    Row row = sheet.getRow(rowNum);  
                    if(row == null){  
                        continue;  
                    }  
                    //获得当前行的开始列  
                    //int firstCellNum = row.getFirstCellNum();
                    //获得当前行的列数  
                    //int lastCellNum = row.getPhysicalNumberOfCells();  
                    //默认当前行的开始列为第一列
                    int firstCellNum = 0;
                    //默认当前行的列数为标题的列数
                    int lastCellNum = titleName.length;
                    String[] cells = new String[lastCellNum];  
                    //循环当前行  
                    for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){  
                        Cell cell = row.getCell(cellNum);  
                        cells[cellNum] = getCellValue(cell);  
                    }  
                    list.add(cells);  
                }  
        	}
            workbook.close();  
        }  
        return list;  
    }
	
	private static void checkFile(MultipartFile file) throws IOException{  
        //判断文件是否存在  
        if(null == file){  
        	logger.error("文件不存在！");  
            throw new FileNotFoundException("文件不存在！");  
        }  
        //获得文件名  
        String fileName = file.getOriginalFilename();  
        //判断文件是否是excel文件  
        if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){  
        	logger.error(fileName + "不是excel文件");  
            throw new IOException(fileName + "不是excel文件");  
        }  
    }
	
	private static Workbook getWorkBook(MultipartFile file) {  
        //获得文件名  
        String fileName = file.getOriginalFilename();  
        //创建Workbook工作薄对象，表示整个excel  
        Workbook workbook = null;  
        try {  
            //获取excel文件的io流  
            InputStream is = file.getInputStream();  
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象  
            if(fileName.endsWith(xls)){  
                //2003  
                workbook = new HSSFWorkbook(is);  
            }else if(fileName.endsWith(xlsx)){  
                //2007  
                workbook = new XSSFWorkbook(is);  
            }  
        } catch (IOException e) {
        	e.printStackTrace();
            logger.info(e.getMessage());  
        }  
        return workbook;  
    }
	
	@SuppressWarnings("deprecation")
	private static String getCellValue(Cell cell){  
        String cellValue = "";
        if(cell == null){  
            return cellValue;  
        }
		if(cell.getCellType() == CellType.NUMERIC) {
			cell.setCellType(CellType.STRING);
        }
        //判断数据的类型  
        switch (cell.getCellType()){  
            case NUMERIC: //数字  
                cellValue = String.valueOf(cell.getNumericCellValue());  
                break;  
            case STRING: //字符串  
                cellValue = String.valueOf(cell.getStringCellValue());  
                break;  
            case BOOLEAN: //Boolean  
                cellValue = String.valueOf(cell.getBooleanCellValue());  
                break;  
            case FORMULA: //公式  
                cellValue = String.valueOf(cell.getCellFormula());  
                break;  
            case BLANK: //空值   
                cellValue = "";  
                break;  
            case ERROR: //故障  
                cellValue = "非法字符";  
                break;  
            default:  
                cellValue = "未知类型";  
                break;  
        }  
        return cellValue;  
    }
	
	public static Workbook toExcel(String fileName, String exportFilePath, String[] titleName, List<Object[]> resultList) {
		//创建excel工作簿
        Workbook workbook = new XSSFWorkbook();
        //创建工作表sheet
        Sheet sheet = workbook.createSheet();
        
        //创建第一行
        Row row = sheet.createRow(0);
        Cell cell = null;
        //插入第一行数据的表头
        for(int i = 0; i < titleName.length; i++){
            cell = row.createCell(i);
            cell.setCellValue(titleName[i]);
        }
        
        //写入数据
        for (int i = 1; i <= resultList.size(); i++){
        	Object[] objArr = resultList.get(i-1);
            Row nrow = sheet.createRow(i);
            for (int j = 0; j < objArr.length; j++) {
            	Cell ncell = nrow.createCell(j);
                ncell.setCellValue(objArr[j]+"");;
			}
        }
        return workbook;
	}

}
