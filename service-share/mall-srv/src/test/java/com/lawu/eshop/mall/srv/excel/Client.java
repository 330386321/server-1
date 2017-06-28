package com.lawu.eshop.mall.srv.excel;

import java.io.IOException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;


/**
 * 
 * @author Sunny
 * @date 2017年6月23日
 */
public class Client {
	
	@Ignore
	@Test
	public void readExcelToSql() throws IOException{
		String path = "C:/Users/Administrator/Desktop/ExpressCode.xls";
		List<List<String>> list = new ReadExcel().readExcel(path, 1, 1);
		StringBuilder stringBuilder = new StringBuilder();
		
		String sql = "INSERT INTO `eshop_mall`.`express_company` (`id`, `code`, `name`, `homepage`, `tel`, `ordinal`, `status`, `gmt_modified`, `gmt_create`) VALUES ('%d', '%s', '%s', '', '', '1', '1', NOW(), NOW());%n";
		
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
            	List<String> data = list.get(i);
            	String script = String.format(sql, i+1, data.get(0), data.get(1));
            	stringBuilder.append(script);
            }
            System.out.println(stringBuilder.toString());
        }
	}
}