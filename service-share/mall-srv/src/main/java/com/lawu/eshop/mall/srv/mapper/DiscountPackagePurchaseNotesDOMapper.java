package com.lawu.eshop.mall.srv.mapper;

import com.lawu.eshop.mall.srv.domain.DiscountPackagePurchaseNotesDO;
import com.lawu.eshop.mall.srv.domain.DiscountPackagePurchaseNotesDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DiscountPackagePurchaseNotesDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_package_purchase_notes
     *
     * @mbg.generated
     */
    long countByExample(DiscountPackagePurchaseNotesDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_package_purchase_notes
     *
     * @mbg.generated
     */
    int deleteByExample(DiscountPackagePurchaseNotesDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_package_purchase_notes
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_package_purchase_notes
     *
     * @mbg.generated
     */
    int insert(DiscountPackagePurchaseNotesDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_package_purchase_notes
     *
     * @mbg.generated
     */
    int insertSelective(DiscountPackagePurchaseNotesDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_package_purchase_notes
     *
     * @mbg.generated
     */
    List<DiscountPackagePurchaseNotesDO> selectByExampleWithRowbounds(DiscountPackagePurchaseNotesDOExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_package_purchase_notes
     *
     * @mbg.generated
     */
    List<DiscountPackagePurchaseNotesDO> selectByExample(DiscountPackagePurchaseNotesDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_package_purchase_notes
     *
     * @mbg.generated
     */
    DiscountPackagePurchaseNotesDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_package_purchase_notes
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") DiscountPackagePurchaseNotesDO record, @Param("example") DiscountPackagePurchaseNotesDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_package_purchase_notes
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") DiscountPackagePurchaseNotesDO record, @Param("example") DiscountPackagePurchaseNotesDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_package_purchase_notes
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(DiscountPackagePurchaseNotesDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_package_purchase_notes
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DiscountPackagePurchaseNotesDO record);
}