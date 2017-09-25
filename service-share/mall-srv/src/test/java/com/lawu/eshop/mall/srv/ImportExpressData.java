package com.lawu.eshop.mall.srv;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.lawu.eshop.utils.ExcelUtil;

/**
 * 
 * @author jiangxinjun
 * @date 2017年9月22日
 */
public class ImportExpressData {

	@Test
	public void importExpressData() throws IOException {
		String path = "C:/Users/Administrator/Desktop/CODE.xlsx";
		List<List<String>> list = ExcelUtil.readExcel(path, 1, 3);
		StringBuilder stringBuilder = new StringBuilder();

		// String sql = "INSERT INTO `eshop_mall`.`express_company` (`code`,
		// `name`, `homepage`, `tel`, `ordinal`, `status`, `gmt_modified`,
		// `gmt_create`) VALUES ('%s', '%s', '', '', '1', '1', NOW(),
		// NOW());%n";
		String sql = "UPDATE express_company SET kuaidi100_code='%s', gmt_modified=NOW() WHERE name = '%s';%n";

		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				List<String> data = list.get(i);
				String script = String.format(sql, data.get(1), data.get(2));
				stringBuilder.append(script);
			}
			System.out.println(stringBuilder.toString());
		}

	}
}