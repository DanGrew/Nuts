/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.table.food;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.food.FoodStore;

/**
 * {@link FoodTable} provides a {@link TableView} for {@link Food}s.
 */
public class FoodTable< FoodTypeT extends Food > extends TableView< FoodTableRow< FoodTypeT > > {

   static final String COLUMN_TITLE_FOOD = "Food";
   static final String COLUMN_TITLE_CALORIES = "Calories";
   static final String COLUMN_TITLE_CARBS = "Carbohydrates";
   static final String COLUMN_TITLE_FATS = "Fats";
   static final String COLUMN_TITLE_PROTEINS = "Protein";
   static final String COLUMN_TITLE_CALORIES_PROPORTION = "Calories %";
   static final String COLUMN_TITLE_CARBS_PROPORTION = "Carbohydrates %";
   static final String COLUMN_TITLE_FATS_PROPORTION = "Fats %";
   static final String COLUMN_TITLE_PROTEINS_PROPORTION = "Protein %";
   
   private final FoodTableConfiguration configuration;
   private final FoodTableController< FoodTypeT > controller;

   /**
    * Constructs a new {@link FoodTable}.
    * @param foods the {@link FoodStore} providing the {@link Food}s.
    */
   public FoodTable( FoodStore< FoodTypeT > foods ) {
      this( new FoodTableController< FoodTypeT >( foods ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link FoodTable}.
    * @param controller the {@link FoodTableController}.
    */
   FoodTable( FoodTableController< FoodTypeT > controller ) {
      this.controller = controller;
      this.controller.associate( this );
      this.configuration = new FoodTableConfiguration();
      initialiseColumns();
   }//End Constructor
   
   /**
    * Friendly access to the {@link #getItems()}.
    * @return the {@link ObservableList}.
    */
   public ObservableList< FoodTableRow< FoodTypeT > > getRows(){
      return getItems();
   }//End Method
   
   /**
    * Method to initialise the {@link javafx.scene.control.TableColumn}s in the {@link TableView}. 
    */
   private void initialiseColumns(){
      setEditable( true );
      
      configuration.initialStringColumn( this, COLUMN_TITLE_FOOD, 0.2, FoodProperties::nameProperty );
      
      configuration.initialNutrientColumn( this, COLUMN_TITLE_CALORIES, 0.1, FoodProperties::calories, true );
      configuration.initialNutrientColumn( this, COLUMN_TITLE_CARBS, 0.08, FoodProperties::carbohydrates, true );
      configuration.initialNutrientColumn( this, COLUMN_TITLE_FATS, 0.08, FoodProperties::fats, true );
      configuration.initialNutrientColumn( this, COLUMN_TITLE_PROTEINS, 0.08, FoodProperties::protein, true );
      
      configuration.initialRatioColumn( this, COLUMN_TITLE_CALORIES_PROPORTION, 0.1, f -> f.goalAnalytics().caloriesRatioProperty() );
      configuration.initialRatioColumn( this, COLUMN_TITLE_CARBS_PROPORTION, 0.08, f -> f.goalAnalytics().carbohydratesRatioProperty() );
      configuration.initialRatioColumn( this, COLUMN_TITLE_FATS_PROPORTION, 0.08, f -> f.goalAnalytics().fatsRatioProperty() );
      configuration.initialRatioColumn( this, COLUMN_TITLE_PROTEINS_PROPORTION, 0.08, f -> f.goalAnalytics().proteinRatioProperty() );
   }//End Method
   
   /**
    * Access to the {@link FoodTableController}.
    * @return the {@link FoodTableController}.
    */
   FoodTableController< FoodTypeT > controller() {
      return controller;
   }//End Method

}//End Class
