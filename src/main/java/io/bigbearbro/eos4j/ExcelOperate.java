package io.bigbearbro.eos4j;

import io.bigbearbro.eos4j.entity.EosAccount;
import org.apache.poi.hssf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lsh
 * @date 2018/12/8.
 */
public class ExcelOperate {

    public static void main(String[] args) {
        ExcelOperate excelOperate = new ExcelOperate();
        List<EosAccount> eosAccounts = excelOperate.getStudent();
        // 创建Excel表格
        excelOperate.createExcel(eosAccounts, "F:/eosAccount.xls");

        // 读取Excel表格
        List<EosAccount> list = excelOperate.readExcel("F:/eosAccount.xls");
        System.out.println(list.toString());
    }

    /**
     * 初始化数据
     *
     * @return 数据
     */
    public List<EosAccount> getStudent() {
        List<EosAccount> list = new ArrayList<EosAccount>();
        EosAccount student1 = new EosAccount("test",
                "test", "xxxxx");
        list.add(student1);
        return list;
    }

    /**
     * 创建Excel
     *
     * @param list 数据
     * @param path 路径
     */
    public void createExcel(List<EosAccount> list, String path) {
        // 创建一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建一个工作表
        HSSFSheet sheet = workbook.createSheet("eos账号模板");
        // 添加表头行
        HSSFRow hssfRow = sheet.createRow(0);
        // 设置单元格格式居中
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        // 添加表头内容
        HSSFCell headCell = hssfRow.createCell(0);
        headCell.setCellValue("私钥");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(1);
        headCell.setCellValue("自己的账户");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(2);
        headCell.setCellValue("目标账户");
        headCell.setCellStyle(cellStyle);

        // 添加数据内容
        for (int i = 0; i < list.size(); i++) {
            hssfRow = sheet.createRow((int) i + 1);
            EosAccount eosAccount = list.get(i);

            // 创建单元格，并设置值
            HSSFCell cell = hssfRow.createCell(0);
            cell.setCellValue(eosAccount.getPk());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(1);
            cell.setCellValue(eosAccount.getFromAccount());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(2);
            cell.setCellValue(eosAccount.getToAccount());
            cell.setCellStyle(cellStyle);
        }

        // 保存Excel文件
        try {
            OutputStream outputStream = new FileOutputStream(path);
            workbook.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取Excel
     *
     * @param path 路径
     * @return 数据集合
     */
    public List<EosAccount> readExcel(String path) {
        List<EosAccount> list = new ArrayList<EosAccount>();
        HSSFWorkbook workbook = null;

        try {
            // 读取Excel文件
            InputStream inputStream = new FileInputStream(path);
            workbook = new HSSFWorkbook(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 循环工作表
        for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = workbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // 循环行
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow == null) {
                    continue;
                }

                // 将单元格中的内容存入集合
                EosAccount eosAccount = new EosAccount();

                HSSFCell cell = hssfRow.getCell(0);
                if (cell == null) {
                    continue;
                }
                eosAccount.setPk(cell.getStringCellValue());

                cell = hssfRow.getCell(1);
                if (cell == null) {
                    continue;
                }
                eosAccount.setFromAccount(cell.getStringCellValue());

                cell = hssfRow.getCell(2);
                if (cell == null) {
                    continue;
                }
                eosAccount.setToAccount(cell.getStringCellValue());

                list.add(eosAccount);
            }
        }
        return list;
    }
}
