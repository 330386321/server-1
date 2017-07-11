package com.lawu.eshop.mall.srv.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class RegionDO implements Serializable {
    /**
     *
     * 区域ID
     * region.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * 父级区域
     * region.parent_id
     *
     * @mbg.generated
     */
    private Integer parentId;

    /**
     *
     * 路径
     * region.path
     *
     * @mbg.generated
     */
    private String path;

    /**
     *
     * 层级
     * region.level
     *
     * @mbg.generated
     */
    private Byte level;

    /**
     *
     * 区域名称
     * region.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * 经度
     * region.longitude
     *
     * @mbg.generated
     */
    private BigDecimal longitude;

    /**
     *
     * 纬度
     * region.latitude
     *
     * @mbg.generated
     */
    private BigDecimal latitude;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table region
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column region.id
     *
     * @return the value of region.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column region.id
     *
     * @param id the value for region.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column region.parent_id
     *
     * @return the value of region.parent_id
     *
     * @mbg.generated
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column region.parent_id
     *
     * @param parentId the value for region.parent_id
     *
     * @mbg.generated
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column region.path
     *
     * @return the value of region.path
     *
     * @mbg.generated
     */
    public String getPath() {
        return path;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column region.path
     *
     * @param path the value for region.path
     *
     * @mbg.generated
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column region.level
     *
     * @return the value of region.level
     *
     * @mbg.generated
     */
    public Byte getLevel() {
        return level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column region.level
     *
     * @param level the value for region.level
     *
     * @mbg.generated
     */
    public void setLevel(Byte level) {
        this.level = level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column region.name
     *
     * @return the value of region.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column region.name
     *
     * @param name the value for region.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column region.longitude
     *
     * @return the value of region.longitude
     *
     * @mbg.generated
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column region.longitude
     *
     * @param longitude the value for region.longitude
     *
     * @mbg.generated
     */
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column region.latitude
     *
     * @return the value of region.latitude
     *
     * @mbg.generated
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column region.latitude
     *
     * @param latitude the value for region.latitude
     *
     * @mbg.generated
     */
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
}