package uk.dangrew.nuts.graphics.selection;

import java.util.function.Function;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.table.ConceptOptions;
import uk.dangrew.nuts.store.Database;

public class UiFoodSelectionTabs extends BorderPane {

   private final TabPane tabPane;
   
   public UiFoodSelectionTabs( Database database, UiFoodSelectionController controller ) {
      this.tabPane = new TabPane();
      this.createTab( "All", new UiFoodSelectionPane( new ConceptOptions< Food >( database.foodItems() ).options(), controller ) );
      this.createTab( "Popular (coming soon)", new BorderPane() );
      
      this.createTab( "Carbs", new UiFoodSelectionPane( 
               ratioComparatorOptions( database, f -> f.foodAnalytics().carbohydratesRatio() ), 
               controller 
      ) );
      this.createTab( "Fats", new UiFoodSelectionPane( 
               ratioComparatorOptions( database, f -> f.foodAnalytics().fatsRatio() ), 
               controller 
      ) );
      this.createTab( "Protein", new UiFoodSelectionPane( 
               ratioComparatorOptions( database, f -> f.foodAnalytics().proteinRatio() ), 
               controller 
      ) );
      
      this.setCenter( tabPane );
   }//End Constructor
   
   private ObservableList< Food > ratioComparatorOptions( Database database, Function< Food, Double > propertyRetriever ) {
      ConceptOptions< Food > carbOptions = new ConceptOptions<>( database.foodItems() );
      carbOptions.customSort( ( a, b ) -> Double.compare( propertyRetriever.apply( b ), propertyRetriever.apply( a ) ) );
      return carbOptions.options();
   }//End Method
   
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
