package uk.dangrew.nuts.graphics.main;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import uk.dangrew.nuts.graphics.database.UiDatabaseManagerPane;
import uk.dangrew.nuts.graphics.day.UiCalendarPane;
import uk.dangrew.nuts.graphics.day.UiCalendarPaneWithTree;
import uk.dangrew.nuts.graphics.day.balance.UiBalanceSummary;
import uk.dangrew.nuts.graphics.goal.GoalManagerPane;
import uk.dangrew.nuts.graphics.graph.weight.WeightRecordingsPane;
import uk.dangrew.nuts.graphics.information.IntroductionPane;
import uk.dangrew.nuts.graphics.label.UiLabelConfigurationView;
import uk.dangrew.nuts.graphics.meal.TemplateManagerPane;
import uk.dangrew.nuts.graphics.progress.custom.ProgressSeriesPane;
import uk.dangrew.nuts.graphics.research.UiResearchPane;
import uk.dangrew.nuts.graphics.stock.StockPane;
import uk.dangrew.nuts.graphics.tools.dryweight.DryWeightToolPane;
import uk.dangrew.nuts.store.Database;

public class NutsTabs extends AnchorPane {

   private final TabPane tabPane;
   private final CoreInterfaceOperationControls controls;
   
   public NutsTabs( Database database, CoreInterfaceOperations operations ) {
      this.tabPane = new TabPane();
      this.controls = new CoreInterfaceOperationControls( operations );
      
      this.getChildren().addAll( tabPane, controls );
      
      AnchorPane.setTopAnchor( controls, 3.0 );
      AnchorPane.setRightAnchor( controls, 5.0 );
      
      AnchorPane.setTopAnchor( tabPane, 1.0 );
      AnchorPane.setRightAnchor( tabPane, 1.0 );
      AnchorPane.setLeftAnchor( tabPane, 1.0 );
      AnchorPane.setBottomAnchor( tabPane, 1.0 );
      
      createConcreteTab( "Nutrition", new IntroductionPane() );
      createConcreteTab( "Research Blog", new UiResearchPane( database.researchArticles() ) );
      createConcreteTab( "Goals", new GoalManagerPane( database ) );
      createConcreteTab( "Database", new UiDatabaseManagerPane( database ) );
      createConcreteTab( "Stock", new StockPane( database ) );
      createConcreteTab( "Templates", new TemplateManagerPane( database ) );
      createConcreteTab( "Day Plans", new UiCalendarPane( database ) );
      createConcreteTab( "Day Plans", new UiCalendarPaneWithTree( database ) );
      createConcreteTab( "Graph", new ProgressSeriesPane( database ) );
      createConcreteTab( "Labels", new UiLabelConfigurationView( database ) );
      createConcreteTab( "Balance", new UiBalanceSummary( database.dayPlans() ) );
      createConcreteTab( "Weigh Ins", new WeightRecordingsPane( database.weightProgress() ) );
      createConcreteTab( "Tools", new TitledPane( "Dry Weight Conversion", new DryWeightToolPane() ) );
   }//End Constructor
   
   private void createConcreteTab( String title, Node content ) {
      createTab( title, content, false );
   }//End Method
   
   private void createTab( String title, Node content, boolean closeable ) {
      Tab tab = new Tab( title );
      tab.setClosable( closeable );
      tab.setContent( content );
      tabPane.getTabs().add( tab );
   }//End Method
   
   TabPane tabPane(){
      return tabPane;
   }//End Method
   
   CoreInterfaceOperationControls controls(){
      return controls;
   }//End Method
   
}//End Class
