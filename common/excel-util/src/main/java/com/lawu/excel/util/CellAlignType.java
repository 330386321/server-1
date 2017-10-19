package com.lawu.excel.util;


import org.apache.poi.ss.usermodel.CellStyle;

/**
 * Excel单元格内容对齐类型导出对齐类型
 *
 * @author Leach
 * @since 2016/6/13
 */
public enum CellAlignType {

    LEFT(CellStyle.ALIGN_LEFT),
    CENTER(CellStyle.ALIGN_CENTER),
    RIGHT(CellStyle.ALIGN_RIGHT);

    private short alignment;

    CellAlignType(short alignment) {
        this.alignment = alignment;
    }

    public short getAlignment() {
        return alignment;
    }
}
