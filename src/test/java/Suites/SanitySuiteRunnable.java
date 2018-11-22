package Suites;

import categories.SanityTest;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Categories.class)
@Categories.IncludeCategory(SanityTest.class)
@Suite.SuiteClasses(SanitySuiteRunner.class)
public interface SanitySuiteRunnable {
}
