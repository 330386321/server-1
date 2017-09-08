package com.lawu.eshop.order.srv.json;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.DateDeserializer;

public class JCDateDeserializer extends DateDeserializer {
	
    public static final JCDateDeserializer instance = new JCDateDeserializer();

    public JCDateDeserializer() {}

    protected <T> T cast(DefaultJSONParser parser, Type clazz, Object fieldName, Object val){
        if (val == null) {
            return null;
        } else if (val instanceof String) {
            String strVal = (String) val;
            if (strVal.length() == 0) {
                return null;
            } else {
            	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            	dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            	parser.setDateFomrat(dateFormat);
            }
        }
        return super.cast(parser, clazz, fieldName, val);
    }
}