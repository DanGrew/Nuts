package uk.dangrew.nuts.graphics.tutorial.database;

import javafx.scene.control.TableRow;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;
import uk.dangrew.nuts.nutrients.MacroNutrient;

public class FoodRowManipulator extends TableRowManipulator< ConceptTableRow< Food > >{
   
   public FoodRowManipulator( TableRow< ConceptTableRow< Food > > row ) {
      super( row );
   }//End Method
   
   public void changeName( String name ) {
      node().getItem().concept().properties().nameProperty().set( name );
   }//End Method
   
   public void changeCalories( double calories ) {
      node().getItem().concept().properties().calories().set( calories );
   }//End Method
   
   public void changeMacro( MacroNutrient macro, double value ) {
      node().getItem().concept().properties().nutritionFor( macro ).set( value );
   }//End Method
   
   public void changeFibre( double fibre ) {
      node().getItem().concept().properties().fiber().set( fibre );
   }//End Method

}//End Class
