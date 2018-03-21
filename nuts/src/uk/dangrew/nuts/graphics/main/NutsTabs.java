package uk.dangrew.nuts.graphics.main;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import uk.dangrew.nuts.graphics.day.UiCalendarPane;
import uk.dangrew.nuts.graphics.day.balance.UiBalanceSummary;
import uk.dangrew.nuts.graphics.goal.GoalManagerPane;
import uk.dangrew.nuts.graphics.graph.WeightRecordingsPane;
import uk.dangrew.nuts.graphics.information.InformationPane;
import uk.dangrew.nuts.graphics.label.UiLabelConfigurationView;
import uk.dangrew.nuts.graphics.manager.FoodManagerPane;
import uk.dangrew.nuts.graphics.meal.TemplateManagerPane;
import uk.dangrew.nuts.graphics.shopping.ShoppingPane;
import uk.dangrew.nuts.graphics.stock.StockTable;
import uk.dangrew.nuts.graphics.tools.dryweight.DryWeightToolPane;
import uk.dangrew.nuts.store.Database;

public class NutsTabs extends TabPane {

   public NutsTabs( Database database ) {
      createConcreteTab( "Nutrition", new InformationPane() );
      createConcreteTab( "Goals", new GoalManagerPane( database ) );
      createConcreteTab( "Database", new FoodManagerPane( database ) );
      createConcreteTab( "Stock", new StockTable( database ) );
      createConcreteTab( "Templates", new TemplateManagerPane( database ) );
      createConcreteTab( "Day Plans", new UiCalendarPane( database ) );
      createConcreteTab( "Labels", new UiLabelConfigurationView( database ) );
      createConcreteTab( "Balance", new UiBalanceSummary( database.dayPlans() ) );
      createConcreteTab( "Shopping", new ShoppingPane( database, database.shoppingLists().objectList().get( 0 ) ) );
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
      getTabs().add( tab );
   }//End Method
}//End Class
