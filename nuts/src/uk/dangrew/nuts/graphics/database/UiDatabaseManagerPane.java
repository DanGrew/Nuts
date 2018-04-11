package uk.dangrew.nuts.graphics.database;

import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.selection.model.FoodFilterModel;
import uk.dangrew.nuts.graphics.selection.view.UiFoodFilter;
import uk.dangrew.nuts.graphics.selection.view.UiFoodFilterImpl;
import uk.dangrew.nuts.graphics.selection.view.UiFoodSelectionControls;
import uk.dangrew.nuts.graphics.table.ConceptTableWithControls;
import uk.dangrew.nuts.store.Database;

public class UiDatabaseManagerPane extends GridPane {

   private final ConceptTableWithControls< Food > table;
   
   public UiDatabaseManagerPane( Database database ) {
      JavaFxStyle styling = new JavaFxStyle();
      styling.configureConstraintsForRowPercentages( this, 5, 95 );
      styling.configureConstraintsForColumnPercentages( this, 60, 40 );
      
      FoodFilterModel model = new FoodFilterModel( database );
      UiFoodFilter filter = new UiFoodFilterImpl( database, model );
      
      add( new UiFoodSelectionControls( database.labels().objectList(), filter ), 0, 0 );
      add( table = new ConceptTableWithControls<>( "Foods", new MixedFoodTable( database, model ) ), 0, 1 );
   }//End Constructor
   
}//End Class
