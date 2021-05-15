package at.smartshop.sos.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import at.framework.database.mssql.Queries;
import at.framework.database.mssql.ResultSets;
import at.framework.files.Excel;
import at.framework.generic.Numbers;
import at.framework.generic.Strings;
import at.framework.ui.Foundation;
import at.smartshop.database.columns.CNGmaUser;
import at.smartshop.database.columns.CNLoadProduct;
import at.smartshop.database.columns.CNLocationList;
import at.smartshop.database.columns.CNNavigationMenu;
import at.smartshop.database.columns.CNProductSummary;
import at.smartshop.keys.Configuration;
import at.smartshop.keys.Constants;
import at.smartshop.keys.FilePath;
import at.smartshop.pages.NavigationBar;
import at.smartshop.sos.pages.LoadGMA;
import at.smartshop.sos.pages.SOSHome;
import at.smartshop.tests.TestInfra;

@Listeners(at.framework.reportsetup.Listeners.class)
public class Sos extends TestInfra {
	private ResultSets dataBase = new ResultSets();
    private SOSHome sosHome = new SOSHome();
    private Foundation foundation = new Foundation();
    private Strings strings = new Strings();
    private Numbers numbers = new Numbers();
    private Excel excel = new Excel();
    private LoadGMA loadGma = new LoadGMA();
    private NavigationBar navigationBar = new NavigationBar();
   
    private Map<String, String> rstProductSummaryData;
    private Map<String, String> rstLocationListData;
    private Map<String, String> rstLoadProduct;
    private Map<String, String> rstGmaUser;
    private Map<String, String> rstNavigationMenuData;
    
	@Test(description= "This test validate the Success message after updaloding the GMA Template")
    public void verifySuccessMessage() {
        try {           
            final String CASE_NUM ="117462";
            //Reading the Database
            rstLocationListData = dataBase.getLocationListData(Queries.LOCATION_LIST, CASE_NUM);
            rstLoadProduct = dataBase.getLoadProductData(Queries.LOAD_PRODUCT, CASE_NUM);
            rstGmaUser = dataBase.getGmaUserData(Queries.GMA_USER, CASE_NUM);
            rstProductSummaryData = dataBase.getProductSummaryData(Queries.PRODUCT_SUMMARY, CASE_NUM);
            rstNavigationMenuData = dataBase.getNavigationMenuData(Queries.NAVIGATION_MENU, CASE_NUM);
           
            //Navigating to the Global Market Account page
            browser.navigateURL(propertyFile.readPropertyFile(Configuration.SOS_CURRENT_URL,FilePath.PROPERTY_CONFIG_FILE));
            login.login(propertyFile.readPropertyFile(Configuration.CURRENT_USER,FilePath.PROPERTY_CONFIG_FILE), propertyFile.readPropertyFile(Configuration.CURRENT_PASSWORD,FilePath.PROPERTY_CONFIG_FILE));
           
            sosHome.selectOrginazation(propertyFile.readPropertyFile(Configuration.CURRENT_ORG,FilePath.PROPERTY_CONFIG_FILE));
            navigationBar.navigateToMenuItem(rstNavigationMenuData.get(CNNavigationMenu.MENU_ITEM));
           
            //Configuring the Fields in GMA page
            int requiredValue = numbers.generateRandomNumber(0, 999999);
            String requiredData = strings.getRandomCharacter();
            String requiredString = (requiredData + Constants.DELIMITER_HASH + requiredData + Constants.DELIMITER_HASH+ requiredData +  Constants.DELIMITER_HASH+ "5" + Constants.DELIMITER_HASH+ requiredData + "@gmail.com" + Constants.DELIMITER_HASH+ String.valueOf(requiredValue) +  Constants.DELIMITER_HASH + requiredData +  Constants.DELIMITER_HASH+ String.valueOf(requiredValue) + Constants.DELIMITER_HASH+ requiredData+"group");
       
            excel.writeToExcel(FilePath.GMA_ACCOUNT_TEMPLATE,loadGma.SHEET,rstProductSummaryData.get(CNProductSummary.ITERATION_COUNT), requiredString);
           
            loadGma.gMAUser(rstLocationListData.get(CNLocationList.LOCATION_NAME),
                                            rstGmaUser.get(CNGmaUser.PIN_VALUE),
                                            rstGmaUser.get(CNGmaUser.START_BALANCE),
                                            FilePath.GMA_ACCOUNT_TEMPLATE,
                                            rstLoadProduct.get(CNLoadProduct.DELETE_EXISTING_PRODUCT));       
            Assert.assertTrue(foundation.isDisplayed(LoadGMA.LBL_SUCCESS));
           
        } catch (Exception exc) {
            Assert.fail(exc.toString());
        }
    }
}
