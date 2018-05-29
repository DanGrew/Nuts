package uk.dangrew.nuts.graphics.tutorial.database;

import com.sun.javafx.application.PlatformImpl;

import javafx.scene.control.Button;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.common.UiEnumTypeSelectionDialog;
import uk.dangrew.nuts.graphics.database.FoodTypes;
import uk.dangrew.nuts.graphics.database.UiDatabaseManagerPane;
import uk.dangrew.nuts.graphics.meal.MealTable;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.store.Database;

public class DatabaseComponents {

   private Database database;
   private DatabaseManipulator databaseManipulator;
   private UiDatabaseManagerPane parent;
   
   private ConceptTable< Food > mainTable;
   private FoodTableManipulator< Food > mainTableManipulator;
   private Button mainTableAddButton;
   private UiEnumTypeSelectionDialog< FoodTypes > mainTableFoodTypeDialog;
   
   private MealTable mealTable;
   private FoodTableManipulator< FoodPortion > mealTableManipulator;
   private Button mealTableAddButton;
   private Button mealTableCopyButton;
   private Button mealTableRemoveButton;
   private Button mealTableUpButton;
   private Button mealTableDownButton;
   
   public DatabaseComponents() {
      this.generateComponents();
   }//End Method
   
   public void generateComponents(){
      this.database = new Database();
      this.databaseManipulator = new DatabaseManipulator( database );
      this.database.stockLists().createConcept( "" );
      PlatformImpl.runAndWait( () -> this.parent = new UiDatabaseManagerPane( database ) );
      this.parent.populateComponents( this );
   }//End Method
   
   public Database database(){
      return database;
   }//End Method
   
   public DatabaseManipulator databaseManipulator(){
      return databaseManipulator;
   }//End Method
   
   public UiDatabaseManagerPane parent(){
      return parent;
   }//End Method
   
   public DatabaseComponents withMainTable( ConceptTable< Food > mainTable ) {
      this.mainTable = mainTable;
      this.mainTableManipulator = new FoodTableManipulator<>( mainTable );
      return this;
   }//End Method
   
   public ConceptTable< Food > mainTable() {
      return mainTable;
   }//End Method

   public FoodTableManipulator< Food > mainTableComponents() {
      return mainTableManipulator;
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
   
   public DatabaseComponents withMealTable( MealTable mealTable ) {
      this.mealTable = mealTable;
      this.mealTableManipulator = new PortionTableManipulator( mealTable );
      return this;
   }//End Method
   
   public MealTable mealTable() {
      return mealTable;
   }//End Method

   public FoodTableManipulator< FoodPortion > mealTableComponents() {
      return mealTableManipulator;
   }//End Method
   
   public DatabaseComponents withMealTableAddButton( Button mealTableAddButton ) {
      this.mealTableAddButton = mealTableAddButton;
      return this;
   }//End Method
   
   public Button mealTableAddButton() {
      return mealTableAddButton;
   }//End Method
   
   public DatabaseComponents withMealTableRemoveButton( Button mealTableRemoveButton ) {
      this.mealTableRemoveButton = mealTableRemoveButton;
      return this;
   }//End Method
   
   public Button mealTableRemoveButton() {
      return mealTableRemoveButton;
   }//End Method
   
   public DatabaseComponents withMealTableCopyButton( Button mealTableCopyButton ) {
      this.mealTableCopyButton = mealTableCopyButton;
      return this;
   }//End Method
   
   public Button mealTableCopyButton() {
      return mealTableCopyButton;
   }//End Method
   
   public DatabaseComponents withMealTableUpButton( Button mealTableUpButton ) {
      this.mealTableUpButton = mealTableUpButton;
      return this;
   }//End Method
   
   public Button mealTableUpButton() {
      return mealTableUpButton;
   }//End Method
   
   public DatabaseComponents withMealTableDownButton( Button mealTableDownButton ) {
      this.mealTableDownButton = mealTableDownButton;
      return this;
   }//End Method
   
   public Button mealTableDownButton() {
      return mealTableDownButton;
   }//End Method
   
}//End Class
