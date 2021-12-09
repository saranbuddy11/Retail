package at.smartshop.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import at.framework.browser.Factory;
import at.framework.ui.CheckBox;
import at.framework.ui.TextBox;

public class DataSourceManager extends Factory {
	private TextBox textBox=new TextBox();
	private CheckBox checkBox=new CheckBox();
	public static final By TXT_SEARCH = By.id("search-box");
	
    public void unCheckCheckBox(String reportName) {
        try {
        	textBox.enterText(TXT_SEARCH, reportName);        	
        	By objCheckBox=By.xpath ("//td[text()='"+reportName+"']//..//td[@aria-describedby='datasourcemangergrid_issfenabled']/label/input");
        	checkBox.unCheck(objCheckBox);
                
        }catch(Exception exc) {
            Assert.fail(exc.toString());
        }
    }
}
