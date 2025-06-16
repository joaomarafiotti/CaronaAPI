package br.ifsp.demo.integration;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("br.ifsp.demo.integration.ui.test")
@IncludeTags("UiTest")
public class AllUiTests {
}
