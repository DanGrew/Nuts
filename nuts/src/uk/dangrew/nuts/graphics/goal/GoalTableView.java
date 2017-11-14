/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.goal;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import uk.dangrew.nuts.graphics.day.UiCalendarPane;
import uk.dangrew.nuts.graphics.graph.WeightRecordingsPane;
import uk.dangrew.nuts.graphics.information.InformationPane;
import uk.dangrew.nuts.graphics.manager.FoodManagerPane;
import uk.dangrew.nuts.graphics.meal.TemplateManagerPane;
import uk.dangrew.nuts.graphics.shopping.ShoppingPane;
import uk.dangrew.nuts.graphics.tools.dryweight.DryWeightToolPane;
import uk.dangrew.nuts.persistence.fooditems.FoodSessions;
import uk.dangrew.nuts.store.Database;

/**
 * The {@link GoalTableView} provides the various elements of the system related to a single {@link uk.dangrew.nuts.goal.Goal}.
 */
public class GoalTableView extends BorderPane {

   private final Database database;
   private final TabPane tabPane;
   
   /**
    * Constructs a new {@link GoalTableView}.
    */
   public GoalTableView() {
      this.database = new Database();
      setPrefSize( 800, 600 );
      
      FoodSessions sessions = new FoodSessions( database );
      sessions.read();
      
      HBox controls = new HBox();
      Button load = new Button( "Load" );
      load.setOnAction( e -> sessions.read() );
      Button save = new Button( "Save" );
      save.setOnAction( e -> sessions.write() );
      controls.getChildren().addAll( load, save );
      
      setTop( controls );
      
      this.tabPane = new TabPane();
      createTab( "Nutrition", new InformationPane() );
      createTab( "Goals", new GoalManagerPane( database ) );
      createTab( "Database", new FoodManagerPane( database ) );
      createTab( "Templates", new TemplateManagerPane( database ) );
      createTab( "Day Plans", new UiCalendarPane( database ) );
      createTab( "Shopping", new ShoppingPane( database, database.shoppingLists().objectList().get( 0 ) ) );
      createTab( "Weigh Ins", new WeightRecordingsPane( database.weightProgress() ) );
      createTab( "Tools", new TitledPane( "Dry Weight Conversion", new DryWeightToolPane() ) );
      
      setCenter( tabPane );
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
      tabPane.getTabs().add( tab );
   }//End Method
   
}//End Class
