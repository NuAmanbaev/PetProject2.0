package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
            plugin = {"html:target/cucumberReport.html", "json:target/testReport.json"},
            features = "/Users/nurgazyamanbaev/Desktop/PetProject/src/test/resources/features",
            glue = "steps",
            tags = "@regression",
            dryRun = false

    )
public class RunnerClass {
}





