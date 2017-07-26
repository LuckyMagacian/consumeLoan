package com.lanxi.consumeLoan.functions;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import com.lanxi.consumeLoan.basic.AbstractFunction;
import com.lanxi.consumeLoan.basic.RetMessage;
import com.lanxi.consumeLoan.entity.Apply;
import com.lanxi.util.utils.BeanUtil;
import com.lanxi.util.utils.ExcelUtil;
/**
 * 导出excel功能
 * @author yangyuanjian
 *
 */
@Service
public class ExportExcelFunction extends AbstractFunction{

	@Override
	public RetMessage successNotice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RetMessage failNotice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RetMessage showInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RetMessage excuted(Map<String, Object> args) {
		List<Apply> list=(List<Apply>) args.get("list");
	 	List<Field> fileds=BeanUtil.getFieldListNoStatic(Apply.class);
		int rowIndex=0;
	 	for(Field each:fileds){
	 		HSSFCell cell=ExcelUtil.getCell(0, rowIndex++, 0);
		 	cell.setCellValue(each.getName());
		}
	 	
	 	int colIndex=1;
	 	for(Apply each:list){
	 		rowIndex=0;
	 		for(Field one:fileds){
	 			HSSFCell cell=ExcelUtil.getCell(0, rowIndex++, colIndex);
	 			cell.setCellValue(BeanUtil.get(each, one.getName())+"");
	 		}
	 		colIndex++;
		}
	 	ExcelUtil.generatorExcel(System.nanoTime()+".xls");
	 	
		return null;
	}

}
