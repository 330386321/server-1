package com.lawu.eshop.product.srv.service.impl;

import com.lawu.eshop.product.srv.bo.ProductCategoryBO;
import com.lawu.eshop.product.srv.converter.ProductCategoryConverter;
import com.lawu.eshop.product.srv.domain.ProductCategoryeDO;
import com.lawu.eshop.product.srv.domain.ProductCategoryeDOExample;
import com.lawu.eshop.product.srv.mapper.ProductCategoryeDOMapper;
import com.lawu.eshop.product.srv.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shoubao-016 on 2017/3/22.
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryeDOMapper productCategoryeDOMapper;

    @Override
    public List<ProductCategoryBO> findAll() {
        ProductCategoryeDOExample example = new ProductCategoryeDOExample();
        List<ProductCategoryeDO>  productCategoryeDOS = productCategoryeDOMapper.selectByExample(example);

        return ProductCategoryConverter.convertBOS(productCategoryeDOS);
    }

	@Override
	public ProductCategoryBO getById(Integer id) {
		ProductCategoryeDOExample example = new ProductCategoryeDOExample();
        example.createCriteria().andIdEqualTo(id);
        List<ProductCategoryeDO>  productCategoryeDOS = productCategoryeDOMapper.selectByExample(example);
        return productCategoryeDOS.isEmpty() ? null :  ProductCategoryConverter.convertBO(productCategoryeDOS.get(0));
	}

	@Override
	public String getFullName(Integer id) {
		ProductCategoryeDOExample example = new ProductCategoryeDOExample();
        example.createCriteria().andIdEqualTo(id);
        List<ProductCategoryeDO>  productCategoryeDOS = productCategoryeDOMapper.selectByExample(example);
        if(productCategoryeDOS == null || productCategoryeDOS.isEmpty()){
        	return "";
        }
        
        String path = productCategoryeDOS.get(0).getPath();
        String []paths = path.split("/");
        StringBuffer sb = new StringBuffer();
        if(paths.length==1){
        	return productCategoryeDOS.get(0).getName();
        }else if(paths.length==2){
        	example.clear();
        	example.createCriteria().andIdEqualTo(Integer.valueOf(paths[0]));
        	List<ProductCategoryeDO>  productCategoryeDOS1 = productCategoryeDOMapper.selectByExample(example);
        	if(productCategoryeDOS1 != null && !productCategoryeDOS1.isEmpty()){
        		sb.append(productCategoryeDOS1.get(0).getName());
            }
        	
        	example.clear();
        	example.createCriteria().andIdEqualTo(Integer.valueOf(paths[1]));
        	List<ProductCategoryeDO>  productCategoryeDOS2 = productCategoryeDOMapper.selectByExample(example);
        	if(productCategoryeDOS2 != null && !productCategoryeDOS2.isEmpty()){
        		sb.append("-").append(productCategoryeDOS2.get(0).getName());
            }
        	return sb.toString();
        }else{
        	example.clear();
        	example.createCriteria().andIdEqualTo(Integer.valueOf(paths[0]));
        	List<ProductCategoryeDO>  productCategoryeDOS1 = productCategoryeDOMapper.selectByExample(example);
        	if(productCategoryeDOS1 != null && !productCategoryeDOS1.isEmpty()){
        		sb.append(productCategoryeDOS1.get(0).getName());
            }
        	
        	example.clear();
        	example.createCriteria().andIdEqualTo(Integer.valueOf(paths[1]));
        	List<ProductCategoryeDO>  productCategoryeDOS2 = productCategoryeDOMapper.selectByExample(example);
        	if(productCategoryeDOS2 != null && !productCategoryeDOS2.isEmpty()){
        		sb.append("-").append(productCategoryeDOS2.get(0).getName());
            }
        	
        	example.clear();
        	example.createCriteria().andIdEqualTo(Integer.valueOf(paths[2]));
        	List<ProductCategoryeDO>  productCategoryeDOS3 = productCategoryeDOMapper.selectByExample(example);
        	if(productCategoryeDOS3 != null && !productCategoryeDOS3.isEmpty()){
        		sb.append("-").append(productCategoryeDOS3.get(0).getName());
            }
        	return sb.toString();
        }
	}

    

}
