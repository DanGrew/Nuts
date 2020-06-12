package uk.dangrew.nuts.graphics.tutorial.architecture.manipulation;

import javafx.scene.control.TableView;
import uk.dangrew.kode.javafx.table.base.ConceptTableRow;
import uk.dangrew.nuts.food.FoodPortion;

public class PortionTableManipulator extends FoodTableManipulator< FoodPortion > {

   public PortionTableManipulator( TableView< ConceptTableRow< FoodPortion > > table ) {
      super( table, f -> f.food().get() );
   }//End Constructor

}//End Class
