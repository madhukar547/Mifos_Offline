/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mifosoffline;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.LocalDate;

public class DataRetrieve_Remote {
    static Connection conn;
    private static String dbName;
    private static String driver;
    private static String url;
    private static String userName;
    private static String password;

    public static final Connection getDBConnection() {
        FileInputStream fis = null;
        
        try {
            File file=new File("./Authentication.properties");
            if(file.exists()){
//                System.out.println(".Properties File found");
            Properties prop=new Properties();
            fis = new FileInputStream(file);
            prop.load(fis);
            
            url=prop.getProperty("Export.Url");
            dbName=prop.getProperty("Export.DBName");
            driver=prop.getProperty("Export.Driver");
            userName=prop.getProperty("Export.Username");
            password=prop.getProperty("Export.Password");
            
            System.out.println("From properties file.."+url+"\t"+dbName+"\t"+driver+"\t"+userName+"\t"+password);
            Class.forName(driver);
            conn = DriverManager.getConnection(url + dbName, userName, password);
            }
            else{
                System.out.println(".Properties File not found");
            }
        } catch (Exception ex) {
            Logger.getLogger(DataRetrieve_Remote.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(DataRetrieve_Remote.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return conn;
    }

    public DataRetrieve_Remote(int globalOfficeNum, String branchName) {
        String cluster = "";
        String offname = "";
        Set s = new HashSet();
        int number = globalOfficeNum;
        System.out.println(number);
        String loanOfficerName = "";
        String id = "";
        
        Connection conn = getDBConnection();

        if (conn != null) {

            try {
                


            //System.out.println("Connected to the database");
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("Select office_id,display_name from office where parent_office_id=" + globalOfficeNum);
            //System.out.println("Global office Number:"+globalOfficeNum);
            File f = new File(branchName);
            f.mkdir();
            FileWriter writer = new FileWriter(f.getCanonicalPath() + "/Details.csv");
            List<OfficeDto> officeDetails = new ArrayList<OfficeDto>();
            while (rs.next()) {
                //System.out.println("Office-ids are:"+rs.getInt(1));
                OfficeDto dto = new OfficeDto();
                dto.setGlobalNo(rs.getInt(1));
                dto.setDisplayName(rs.getString(2));
                officeDetails.add(dto);

            }
            rs.close();
            Iterator<OfficeDto> listiterator = officeDetails.iterator();
            while (listiterator.hasNext()) {
                OfficeDto dto = listiterator.next();
                int num = dto.getGlobalNo();
                System.out.println("Global number:" + num);
                String name = dto.getDisplayName();
                ResultSet resultSet = statement.executeQuery("Select * from Monthly_Demand where Office_id =" + num + " order by external_id");
                if (resultSet.next()) {
                    resultSet.previous();
                    int index = 1;
                    //while (resultSet.next()) {
                    ResultSetMetaData rsmd = resultSet.getMetaData();
                    int numberOfColumns = rsmd.getColumnCount();
                    HSSFWorkbook wb = new HSSFWorkbook();
                    wb.writeProtectWorkbook("reddy", "Madhukar");
                    HSSFSheet sheet = wb.createSheet("Excel Sheet");
                    HSSFRow rowhead = sheet.createRow((short) 0);
                    for (int i = 1; i <= numberOfColumns; i++) {
                        rowhead.createCell((short) i - 1).setCellValue(rsmd.getColumnName(i));
                        sheet.autoSizeColumn(i - 1);

                    }
                    
                    while (resultSet.next()) {
                        //System.out.println(resultSet.getInt(1));
                        HSSFRow row = sheet.createRow((short) index);
                        sheet.autoSizeColumn(index);
                        int external_id = resultSet.getInt(1);
                        id = new Integer(external_id).toString().trim();
                        String account_no = resultSet.getString(2);
                        String brnch_name = resultSet.getString(3);
                        String membername = resultSet.getString(4);
                        int priciple = resultSet.getInt(5);
                        int interest = resultSet.getInt(6);
                        int loanAmount = resultSet.getInt(7);
                        int savingAmount = resultSet.getInt(8);
                        int fees = resultSet.getInt(9);
                        //int date = resultSet.getInt(10);
                        int receipt_no = resultSet.getInt(11);
                        loanOfficerName = resultSet.getString(12);
                        String globalAccNum = resultSet.getString(13);
                        String status = resultSet.getString(14);
                        String cStatus = resultSet.getString(15);
                        int totPrincipal = resultSet.getInt(16);
                        int totInterest = resultSet.getInt(17);
                        int totSavings = resultSet.getInt(18);
                        int pastDuePrincipal = resultSet.getInt(19);
                        int pastDueInterest = resultSet.getInt(20);
                        int cDemandPrincipal = resultSet.getInt(21);
                        int cDemandInterest = resultSet.getInt(22);
                        String mdate = resultSet.getString(23);
                        int offId = resultSet.getInt(24);
                        String mifosAccNum = resultSet.getString(25);
                        String DisDate = resultSet.getString(26);
                        int NoInst = resultSet.getInt(27);
                        int OsPrincipal = resultSet.getInt(28);
                        int OsInterest = resultSet.getInt(29);
                        SimpleDateFormat sdf = new SimpleDateFormat("MM");
                        String month = sdf.format(new Date());
                        row.createCell((short) 0).setCellValue(String.valueOf(resultSet.getInt(1)));
                        String lname = "";
                        if (resultSet.getString(2) == null) {
                            lname = "0";
                        } else {
                            lname = resultSet.getString(2);
                        }
                        row.createCell((short) 1).setCellValue(lname);
                        row.createCell((short) 2).setCellValue(resultSet.getString(3));
                        row.createCell((short) 3).setCellValue(resultSet.getString(4));
                        row.createCell((short) 4).setCellValue(resultSet.getInt(5));
                        row.createCell((short) 5).setCellValue(resultSet.getInt(6));
                        row.createCell((short) 6).setCellValue(resultSet.getInt(7));
                        row.createCell((short) 7).setCellValue(resultSet.getInt(8));
                        row.createCell((short) 8).setCellValue(resultSet.getInt(9));
                        row.createCell((short) 9).setCellValue("0");
                        row.createCell((short) 10).setCellValue("0");
                        row.createCell((short) 11).setCellValue(resultSet.getString(12));
                        String sname = "";
                        if (resultSet.getString(13) == null) {
                            sname = "0";
                        } else {
                            sname = resultSet.getString(13);
                        }
                        row.createCell((short) 12).setCellValue(sname);
                        row.createCell((short) 13).setCellValue("need to update");
                        row.createCell((short) 14).setCellValue(resultSet.getString(15));
                        row.createCell((short) 15).setCellValue(resultSet.getInt(16));
                        row.createCell((short) 16).setCellValue(resultSet.getInt(17));
                        row.createCell((short) 17).setCellValue(resultSet.getInt(18));
                        row.createCell((short) 18).setCellValue(resultSet.getInt(19));
                        row.createCell((short) 19).setCellValue(resultSet.getInt(20));
                        row.createCell((short) 20).setCellValue(resultSet.getInt(21));
                        row.createCell((short) 21).setCellValue(resultSet.getInt(22));
                        row.createCell((short) 22).setCellValue(resultSet.getString(23));
                        row.createCell((short) 23).setCellValue(resultSet.getInt(24));
                        row.createCell((short) 24).setCellValue(resultSet.getString(25));
                        row.createCell(25).setCellValue(DisDate);
                        row.createCell(26).setCellValue(NoInst);
                        row.createCell(27).setCellValue(OsPrincipal);
                        row.createCell(28).setCellValue(OsInterest);

                        index++;
                        //System.out.println(external_id+"\t"+account_no+"\t"+brnch_name+"\t"+membername+"\t"+priciple+"\t"+interest+"\t"+loanAmount+"\t"+savingAmount+"\t"+fees+"\t"+date+"\t"+receipt_no+"\t"+loanOfficerName+"\t"+"\t"+"status");
                    }

                    LocalDate date = new LocalDate();
                    String path = System.getProperty("user.dir");
                    String excelName = "./" + branchName + "/Demand_0" + num + "_" + date.getMonthOfYear() + date.getYear() + ".xls";

                    FileOutputStream fileOut = new FileOutputStream(excelName);
                    sheet.protectSheet("reddy");
                    wb.write(fileOut);
                    //if(((id.length())!=0)&&(loanOfficerName.length()!=0)){
                    String xlname = "Demand_0" + num + "_" + date.getMonthOfYear() + date.getYear() + ".xls";
                    writer.append((loanOfficerName.substring(0, loanOfficerName.length() - 5)).trim());
                    writer.append(',');
                   
                    writer.append(id.substring(0, 6));
                    writer.append(',');
                    writer.append(xlname);
                    writer.append('\n');
                    writer.flush();
                    }
                    
//                } else {
//                    System.out.println("No data found in.." + globalOfficeNum + " and " + num);
//                }
                resultSet.close();


            }
            writer.close();
                } catch (Exception e) {
                e.printStackTrace();
            } 
            finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(DataRetrieve_Remote.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        } //If close
        else {
            System.out.println("Could not get Connection!");
        }
    }

    public static List<OfficeDto> getOfficeDetails() {
        Connection con = getDBConnection();

        String name = null;
        List<OfficeDto> list = null;
        try {
             Statement statement = con.createStatement();
            String sql = "Select office_id,display_name from office where office_level_id=4";
            ResultSet resultSet = statement.executeQuery(sql);

            list = new ArrayList<OfficeDto>();
            while (resultSet.next()) {
                OfficeDto dto = new OfficeDto();
                dto.setGlobalNo(resultSet.getInt(1));
                dto.setDisplayName(resultSet.getString(2));
                list.add(dto);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return list;
    }
}