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
import uk.dangrew.nuts.graphics.manager.FoodManagerPane;
import uk.dangrew.nuts.graphics.meal.TemplateManagerPane;
import uk.dangrew.nuts.graphics.shopping.ShoppingPane;
import uk.dangrew.nuts.graphics.tools.dryweight.DryWeightToolPane;
import uk.dangrew.nuts.store.Database;

public class NutsTabs extends TabPane {

   public NutsTabs( Database database ) {
      createTab( "Nutrition", new InformationPane() );
      createTab( "Goals", new GoalManagerPane( database ) );
      createTab( "Database", new FoodManagerPane( database ) );
      createTab( "Templates", new TemplateManagerPane( database ) );
      createTab( "Day Plans", new UiCalendarPane( database ) );
      createTab( "Balance", new UiBalanceSummary( database.dayPlans() ) );
      createTab( "Shopping", new ShoppingPane( database, database.shoppingLists().objectList().get( 0 ) ) );
      createTab( "Weigh Ins", new WeightRecordingsPane( database.weightProgress() ) );
      createTab( "Tools", new TitledPane( "Dry Weight Conversion", new DryWeightToolPane() ) );
   }//End Constructor
   
   /**
    * Creates a {@link Tab} and adds it to the {@link TabPane}.
    * @param title the title of the {@link Tab}.
    * @param content the {@link Node} content.
    */
   private void createTab( String title, Node content ) {
      Tab tab = new Tab( title );
      tab.setClosable( false );
      tab.setContent( content );
      getTabs().add( tab );
   }//End Method
   
}//End Class
