package uk.dangrew.nuts.graphics.tutorial.database;

import javafx.scene.control.Button;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.common.UiEnumTypeSelectionDialog;
import uk.dangrew.nuts.graphics.database.FoodTypes;
import uk.dangrew.nuts.graphics.database.UiDatabaseManagerPane;
import uk.dangrew.nuts.graphics.table.ConceptTable;

public class DatabaseComponents {

   private UiDatabaseManagerPane parent;
   private ConceptTable< Food > mainTable;
   private Button mainTableAddButton;
   private UiEnumTypeSelectionDialog< FoodTypes > mainTableFoodTypeDialog;
   
   public DatabaseComponents withParent( UiDatabaseManagerPane pane ) {
      this.parent = pane;
      return this;
   }//End Method
   
   public UiDatabaseManagerPane parent(){
      return parent;
   }//End Method
   
   public DatabaseComponents withMainTable( ConceptTable< Food > mainTable ) {
      this.mainTable = mainTable;
      return this;
   }//End Method
   
   public ConceptTable< Food > mainTable() {
      return mainTable;
   }//End Method
   
   public DatabaseComponents withMainTableAddButton( Button mainTableAddButton ) {
      this.mainTableAddButton = mainTableAddButton;
      return this;
   }//End Method
   
   public Button mainTableAddButton() {
      return mainTableAddButton;
   }//End Method
   
   public DatabaseComponents withMainTableFoodTypeDialog( UiEnumTypeSelectionDialog< FoodTypes > mainTableFoodTypeDialog ) {
      this.mainTableFoodTypeDialog = mainTableFoodTypeDialog;
      return this;
   }//End Method
   
   public UiEnumTypeSelectionDialog< FoodTypes > mainTableFoodTypeDialog() {
      return mainTableFoodTypeDialog;
   }//End Method
   
}//End Class
