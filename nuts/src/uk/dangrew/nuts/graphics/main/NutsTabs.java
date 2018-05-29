package uk.dangrew.nuts.graphics.main;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import uk.dangrew.nuts.graphics.database.UiDatabaseManagerPane;
import uk.dangrew.nuts.graphics.day.UiCalendarPane;
import uk.dangrew.nuts.graphics.day.balance.UiBalanceSummary;
import uk.dangrew.nuts.graphics.goal.GoalManagerPane;
import uk.dangrew.nuts.graphics.graph.weight.WeightRecordingsPane;
import uk.dangrew.nuts.graphics.information.InformationPane;
import uk.dangrew.nuts.graphics.label.UiLabelConfigurationView;
import uk.dangrew.nuts.graphics.meal.TemplateManagerPane;
import uk.dangrew.nuts.graphics.progress.custom.ProgressSeriesPane;
import uk.dangrew.nuts.graphics.shopping.ShoppingPane;
import uk.dangrew.nuts.graphics.stock.StockTable;
import uk.dangrew.nuts.graphics.tools.dryweight.DryWeightToolPane;
import uk.dangrew.nuts.store.Database;

public class NutsTabs extends AnchorPane {

   private final TabPane tabPane;
   private final Button saveButton;
   
   public NutsTabs( Database database, CoreInterfaceOperations operations ) {
      this.tabPane = new TabPane();
      this.saveButton = new Button( "Save" );
      this.saveButton.setOnAction( e -> operations.save() );
      this.getChildren().addAll( tabPane, saveButton );
      
      AnchorPane.setTopAnchor( saveButton, 3.0 );
      AnchorPane.setRightAnchor( saveButton, 5.0 );
      AnchorPane.setTopAnchor( tabPane, 1.0 );
      AnchorPane.setRightAnchor( tabPane, 1.0 );
      AnchorPane.setLeftAnchor( tabPane, 1.0 );
      AnchorPane.setBottomAnchor( tabPane, 1.0 );
      
      createConcreteTab( "Nutrition", new InformationPane() );
      createConcreteTab( "Goals", new GoalManagerPane( database ) );
      createConcreteTab( "Database", new UiDatabaseManagerPane( database ) );
      createConcreteTab( "Stock", new StockTable( database ) );
      createConcreteTab( "Templates", new TemplateManagerPane( database ) );
      createConcreteTab( "Day Plans", new UiCalendarPane( database ) );
      createConcreteTab( "Graph", new ProgressSeriesPane( database ) );
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
      tabPane.getTabs().add( tab );
   }//End Method
   
   TabPane tabPane(){
      return tabPane;
   }//End Method
   
   Button saveButton(){
      return saveButton;
   }//End Method
   
}//End Class
