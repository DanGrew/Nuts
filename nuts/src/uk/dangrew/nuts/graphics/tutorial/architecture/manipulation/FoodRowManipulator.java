package uk.dangrew.nuts.graphics.tutorial.architecture.manipulation;

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
   
   public FoodRowManipulator< FoodT > changeName( String name ) {
      node().getItem().concept().properties().nameProperty().set( name );
      return this;
   }//End Method
   
   public FoodRowManipulator< FoodT > changeCalories( double calories ) {
      node().getItem().concept().properties().calories().set( calories );
      return this;
   }//End Method
   
   public FoodRowManipulator< FoodT > changeMacro( MacroNutrient macro, double value ) {
      node().getItem().concept().properties().nutritionFor( macro ).set( value );
      return this;
   }//End Method
   
   public FoodRowManipulator< FoodT > changeFibre( double fibre ) {
      node().getItem().concept().properties().fiber().set( fibre );
      return this;
   }//End Method
   
   public FoodRowManipulator< FoodT > triggerCellEdit( int column ) {
      table.edit( rowIndex, table.getColumns().get( column ) );
      return this;
   }//End Method
   
   public void finishCellEdit() {
      table.edit( -1, null );
   }//End Method
   
   public FoodRowManipulator< FoodT > selectInTable(){
      table.getSelectionModel().select( node().getItem() );
      return this;
   }//End Method

}//End Class
