package org.chiknrice.concordion.spec;

import org.chiknrice.concordion.SetVarCommandExtension;
import org.concordion.api.FullOGNL;
import org.concordion.api.extension.Extensions;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:chiknrice@gmail.com">Ian Bondoc</a>
 */
@RunWith(ConcordionRunner.class)
@FullOGNL
@Extensions({SetVarCommandExtension.class, ResourceExtension.class})
public abstract class SetVarBaseFixture {
}
