package com.thinkitive.eAmata.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "C:/Users/LNV-24/Desktop/eAmataEuApiFramework/src/test/resources/features",
        glue = "com.thinkitive.eAmata.stepDefinitions",
        tags = "@VerifyHCPGroup"
)

public class TestRunner {

}


//plugin = {"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
// "html:target/cucumber-reports.html"
// "junit:target/cucumber-reports/Cucumber.xml"