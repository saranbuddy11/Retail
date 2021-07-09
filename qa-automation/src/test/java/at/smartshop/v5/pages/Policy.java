package at.smartshop.v5.pages;

import org.openqa.selenium.By;

public class Policy {
	public static final By LBL_POLICY_TITLE = By.xpath("//*[@id='viewPolicyModal']/div/h2");
	public static final By BTN_OK = By.id("term-condition-btn-go-id");
	public static final By LBL_TERMS_CONDITION_TITLE = By.xpath("//*[@id='viewTCModal']/div/h2");
	public static final By LBL_BIOMETRIC = By.xpath("//*[@id='toggle']/div/label");
	public static final By CHK_BIOMETRIC = By.xpath("//*[@id='bpModal']/div/h2");
	public static final By BTN_CONFIRM = By.id("bpmodal-btn-go-id");
}
