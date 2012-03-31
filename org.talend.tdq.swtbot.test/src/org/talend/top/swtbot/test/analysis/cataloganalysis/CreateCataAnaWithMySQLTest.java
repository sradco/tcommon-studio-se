package org.talend.top.swtbot.test.analysis.cataloganalysis;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.matchers.WidgetOfType;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.talend.swtbot.test.commons.TalendSwtbotForTdq;
import org.talend.swtbot.test.commons.TalendSwtbotTdqCommon;
import org.talend.swtbot.test.commons.TalendSwtbotTdqCommon.TalendAnalysisTypeEnum;
import org.talend.swtbot.test.commons.TalendSwtbotTdqCommon.TalendItemTypeEnum;
import org.talend.swtbot.test.commons.TalendSwtbotTdqCommon.TalendMetadataTypeEnum;

@RunWith(SWTBotJunit4ClassRunner.class)
public class CreateCataAnaWithMySQLTest extends TalendSwtbotForTdq {

	@Before
	public void beforeRunning() {
		TalendSwtbotTdqCommon.createConnection(bot,
				TalendMetadataTypeEnum.MYSQL);
	}

	@Test
	public void createCataAnaWithMySQL() {
		TalendSwtbotTdqCommon.createAnalysis(bot,
				TalendAnalysisTypeEnum.CATALOG, TalendMetadataTypeEnum.MYSQL);
		bot.editorByTitle(TalendMetadataTypeEnum.MYSQL.toString()+" 0.1").close();

		bot.viewByTitle("DQ Repository").setFocus();
		bot.toolbarButtonWithTooltip("Refresh").click();
		SWTBotTree tree = new SWTBotTree((Tree) bot.widget(WidgetOfType
				.widgetOfType(Tree.class)));
		SWTBotTreeItem analysisItem = tree.expandNode("Data Profiling")
				.getNode(0).expand()
				.select(TalendAnalysisTypeEnum.CATALOG.toString() + " 0.1");
		Assert.assertNotNull(analysisItem);
		bot.editorByTitle(TalendAnalysisTypeEnum.CATALOG.toString()+" 0.1").close();
	}

//	@After
//	public void cleanSource() {
//		TalendSwtbotTdqCommon.deleteSource(bot, TalendItemTypeEnum.ANALYSIS,
//				TalendAnalysisTypeEnum.CATALOG.toString());
//		TalendSwtbotTdqCommon.deleteSource(bot, TalendItemTypeEnum.METADATA,
//				TalendMetadataTypeEnum.MYSQL.toString());
//	}

}
