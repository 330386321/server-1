package utils.com.lawu.eshop.utils;

import org.junit.Test;

import com.lawu.eshop.utils.PinyinUtil;

public class PinYinUtilTest {
	
	@Test
	public void getPingYin() {
		System.out.println(PinyinUtil.getPingYin("顺丰"));
	}
}
