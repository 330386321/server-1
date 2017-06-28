package com.lawu.eshop.order.srv;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Ignore;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lawu.eshop.order.dto.foreign.ExpressInquiriesDetailDTO;

public class SerializableTest {

	@Ignore
	@Test
	public void serTest() {
		
		ExpressInquiriesDetailDTO DTO = new ExpressInquiriesDetailDTO();
		DTO.setReason("hahha");
		
		String json = JSONObject.toJSON(DTO).toString();
		
		json = JSONObject.toJSONString(DTO, SerializerFeature.WriteMapNullValue);
		
		System.out.println(json);
		
		FileOutputStream fs = null;  
        ObjectOutputStream os =  null;  
        try {
        	fs =  new FileOutputStream("C:\\Users\\Administrator\\Desktop\\dto.ser");
        	os =  new ObjectOutputStream(fs);
			os.writeObject(DTO);
		} catch (IOException e) {
			e.printStackTrace();
		}  finally {
			try {
				fs.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
        ObjectInputStream ois = null; 
        ExpressInquiriesDetailDTO DTO1 = null;
		try {
			ois = new ObjectInputStream(new FileInputStream("C:\\Users\\Administrator\\Desktop\\dto.ser"));
			DTO1 = (ExpressInquiriesDetailDTO)ois.readObject();
			json = JSONObject.toJSONString(DTO1, SerializerFeature.WriteMapNullValue);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
        System.out.println(json); 
        
	}

}
