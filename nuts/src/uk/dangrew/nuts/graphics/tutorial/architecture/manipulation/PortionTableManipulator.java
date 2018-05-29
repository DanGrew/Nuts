package uk.dangrew.nuts.graphics.tutorial.architecture.manipulation;

import javafx.scene.control.TableView;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;

public class PortionTableManipulator extends FoodTableManipulator< FoodPortion > {

   public PortionTableManipulator( TableView< ConceptTableRow< FoodPortion > > table ) {
      super( table, f -> f.food().get() );
   }//End Constructor

}//End Class
