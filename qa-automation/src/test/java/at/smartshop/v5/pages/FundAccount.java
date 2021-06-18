package at.smartshop.v5.pages;

import org.openqa.selenium.By;

public class FundAccount {
    public By objText(String text) {
		return By.xpath("//*[text()='"+text+"']");
	}
}
