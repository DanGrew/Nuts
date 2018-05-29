package uk.dangrew.nuts.graphics.tutorial.database;

import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;
import uk.dangrew.nuts.nutrients.MacroNutrient;

public class FoodRowManipulator< FoodT extends Food > extends TableRowManipulator< ConceptTableRow< FoodT > >{
   
   private final TableView< ConceptTableRow< FoodT > > table;
   private final int rowIndex;
   
   public FoodRowManipulator( TableRow< ConceptTableRow< FoodT > > row, int rowIndex ) {
      super( row );
      this.table = row.getTableView();
      this.rowIndex = rowIndex;
   }//End Method
   
   public int index(){
      return rowIndex;
   }//End Method
   
   public FoodT concept(){
      return node().getItem().concept();
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
   
   public void triggerCellEdit( int column ) {
      table.edit( rowIndex, table.getColumns().get( column ) );
   }//End Method
   
   public void selectInTable(){
      table.getSelectionModel().select( node().getItem() );
   }//End Method

}//End Class
