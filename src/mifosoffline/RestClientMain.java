package mifosoffline;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.joda.time.LocalDate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RestClientMain {

    //load the applicationContext file
    static ApplicationContext ctx = new ClassPathXmlApplicationContext("mifosoffline/applicationContext.xml");
    @SuppressWarnings("StaticNonFinalUsedInInitialization")
    static RestController restControllerBean = (RestController) ctx.getBean("restController");
    static File file = null;
    static Logger logger = Logger.getLogger(RestClientMain.class);

    public static void main(String[] args) {

        RestClientMain rcm = new RestClientMain();

        Chooser chooser = rcm.new Chooser();
        rcm.paymentsForCenter(file);

        //logger.info("Hi!!! How are you?");
    }

    public String paymentsForLoanSavings(OfflineCollectionsDto dto, String statusInExcel) {
        String status = "fail";
        String loanStatus = "error";
        String savingStatus = "error";
        LocalDate date=new LocalDate();
        String todayDate=date.getDayOfMonth()+"-"+date.getMonthOfYear()+"-"+date.getYear();
        //amount should be greater than zero
        Double loanAmount = new Double(dto.getLoanAmount());
        Double savingAmount = new Double(dto.getSavingAmount());
        String loanglobalnum = dto.getLoanGlobalNum();

        if (statusInExcel.equals("need to update")) {
            String lglobalnum=dto.getLoanGlobalNum();
               if(lglobalnum.trim().length()==15){

                if (loanAmount > 0.0) {
                    //mifos loan payment

                    Map loanOutput = restControllerBean.postLoanRepayByGlobalNum(dto.getLoanGlobalNum(), dto.getLoanAmount(), dto.getSavingTrxnDate(), dto.getReceiptId());
                    
                    if(dto.getSavingTrxnDate().equalsIgnoreCase(todayDate)==false){
                    System.out.println("Date comparison failed in loan Account");
                    logger.info("Date comparison failed in loan Account");
                        
                    }
                    if (loanOutput != null) {
                        loanStatus = (String) loanOutput.get("status");
                    }
                } else {
                    System.out.println("-------------");
                    System.out.println("loan amount should be greater than zero");
                    System.out.println("--------------");
                }
                }
                else{
                    System.out.println("invalid loan account number found");
                    logger.info("invalid loan account number for the below row");
                }
                
                
                String savingaccnum=dto.getSavingGlobalNum();
                if(savingaccnum.trim().length()==15){
                if (savingAmount > 0.0) {
                    //mifos savings deposit
                    Map savingOutput = restControllerBean.postSavingDepositByGlobalAccountNum(dto.getSavingGlobalNum(), dto.getSavingAmount(), dto.getSavingTrxnDate(), "1", dto.getReceiptId());
                    if(dto.getSavingTrxnDate().equalsIgnoreCase(todayDate)==false){
                    System.out.println("Date comparison failed in loan Account");
                    logger.info("Date comparison failed in saving Account");
                        
                    }
                    if (savingOutput != null) {
                        savingStatus = (String) savingOutput.get("status");
                    }
                } else {
                    System.out.println("-------------");
                    System.out.println("saving amount should be greater than zero");
                    System.out.println("--------------");
                }
                }
                else{
                    System.out.println("invalid saving account number found");
                    logger.info("invalid Saving account number found for the below row");
                }

            //status string
            if ("success".equals(loanStatus) && "success".equals(savingStatus)) {
                status = "success";
            } else if ("error".equals(loanStatus) && "success".equals(savingStatus)) {
                status = "loan NA and savings success";
            } else if ("success".equals(loanStatus) && "error".equals(savingStatus)) {
                status = "loan success and savings NA";
            } else {
                status = "fail";
            }
        } else {
            System.out.println("--------");
            logger.info("the below row is already updated once");
            System.out.println("the below row is already updated once");
            System.out.println("--------");
        }


        return status;
    }

    public void paymentsForCenter(File file) {
        File f = file;
        FileInputStream myInput;
        PropertyConfigurator.configure("Authentication.properties");
        try {
            myInput = new FileInputStream(f.toString());
            /** Create a POIFSFileSystem object**/
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

            /** Create a workbook using the File System**/
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);


            /** Get the first sheet from workbook**/
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);

            /** We now need something to iterate through the cells.**/
            Iterator rowIter = mySheet.rowIterator();
            rowIter.next();
            int count = 2;
            int temp = 0;
            while (rowIter.hasNext()) {
                HSSFRow myRow = (HSSFRow) rowIter.next();
                Iterator cellIter = myRow.cellIterator();
                @SuppressWarnings("UseOfObsoleteCollectionType")
                Vector cellStoreVector = new Vector();
                HSSFCell statusCell = null;
                temp = 0;
                while (cellIter.hasNext()) {
                    temp++;
                    HSSFCell myCell = (HSSFCell) cellIter.next();
                    cellStoreVector.addElement(myCell.toString());
                    if (temp == 14) {
                        statusCell = myCell;
                    }
                }

                String status = "fail";
                if (cellStoreVector.size() >= 13) {
                    OfflineCollectionsDto dto = new OfflineCollectionsDto();
                    dto.setLoanGlobalNum((String) cellStoreVector.get(1));
                    dto.setLoanAmount((String) cellStoreVector.get(6));
                    dto.setSavingAmount((String) cellStoreVector.get(7));
                    dto.setSavingTrxnDate((String) cellStoreVector.get(9));
                    double d = Double.valueOf((String) cellStoreVector.get(10));
                    int i = (int) d;
                    dto.setReceiptId(i);
                    dto.setSavingGlobalNum((String) cellStoreVector.get(12));
                    System.out.println("***********ROW " + count + "*************");
                    status = paymentsForLoanSavings(dto, statusCell.toString());
                    System.out.println("row  " + count + " status:" + status);
                    logger.info("row  " + count + " status:" + status);
                    System.out.println("**************************************");
                    System.out.println();
                    System.out.println();
                    statusCell.setCellValue(status);
                    FileOutputStream fileOut = new FileOutputStream(f.toString());
                    myWorkBook.write(fileOut);
                    fileOut.close();
                    ++count;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e + " : Excel file is not Found: check once if excel file is opened");
        } catch (Exception e) {
            //e.printStackTrace();
            System.err.println(e);
            logger.info(e);

        }

    }

    public class Chooser extends JFrame {

        @SuppressWarnings("LeakingThisInConstructor")
        public Chooser() {

            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Select a Excel File *.xls");
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            chooser.setAcceptAllFileFilterUsed(true);
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                file = chooser.getSelectedFile();
            } else {
                JOptionPane.showMessageDialog(null,
                        "Please close and select a file");
                System.exit(0);
            }

        }
    }
}