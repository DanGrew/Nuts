package uk.dangrew.nuts.graphics.tutorial.database;

import javafx.scene.control.TableView;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;

public class FoodTableManipulator extends TableManipulator< ConceptTableRow< Food >, FoodRowManipulator >{

   public FoodTableManipulator( 
            TableView< ConceptTableRow< Food > > table
   ) {
      super( table, FoodRowManipulator::new );
   }//End Constructor
   
}//End Class
