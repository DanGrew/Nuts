/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.table.meal;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.meal.MealChangeListener;
import uk.dangrew.nuts.meal.Meal;

/**
 * {@link MealSummary} provides a summary of properties of a {@link Meal}.
 * This should be updated to a {@link uk.dangrew.nuts.food.Food}, not just a {@link Meal}.
 */
public class MealSummary extends GridPane {
   
   private Meal meal;
   
   private final MealChangeListener updater;
   private final Label totalWeight;
   
   private final Label carbsTotal;
   private final Label fatsTotal;
   private final Label proteinTotal;
   
   private final Label carbsRatio;
   private final Label fatsRatio;
   private final Label proteinRatio;
   
   /**
    * Constructs a new {@link MealSummary}.
    */
   public MealSummary() {
      this.updater = this::updateProperties;
      new JavaFxStyle().configureConstraintsForEvenColumns( this, 7 );
      new JavaFxStyle().configureConstraintsForEvenRows( this, 2 );
      
      add( createCentredLabel( "Total Weight" ), 0, 0 );
      
      add( createCentredLabel( "Total Carbs" ), 1, 0 );
      add( createCentredLabel( "Total Fats" ), 2, 0 );
      add( createCentredLabel( "Total Protein" ), 3, 0 );
      
      add( createCentredLabel( "% Carbs" ), 4, 0 );
      add( createCentredLabel( "% Fats" ), 5, 0 );
      add( createCentredLabel( "% Protein" ), 6, 0 );
      
      add( totalWeight = createCentredLabel( "0g" ), 0, 1 );
      
      add( carbsTotal = createCentredLabel( "0g" ), 1, 1 );
      add( fatsTotal = createCentredLabel( "0g" ), 2, 1 );
      add( proteinTotal = createCentredLabel( "0g" ), 3, 1 );
      
      add( carbsRatio = createCentredLabel( "0%" ), 4, 1 );
      add( fatsRatio = createCentredLabel( "0%" ), 5, 1 );
      add( proteinRatio = createCentredLabel( "0%" ), 6, 1 );
   }//End Constructor
   
   /**
    * Method to update the properties displayed.
    */
   private void updateProperties(){
      double totalCarbs = meal.properties().carbohydrates().get();
      double totalFats = meal.properties().fats().get();
      double totalProtein = meal.properties().protein().get();
      
      double totalCarbsRatio = meal.goalAnalytics().carbohydratesRatio();
      double totalFatsRatio = meal.goalAnalytics().fatsRatio();
      double totalProteinRatio = meal.goalAnalytics().proteinRatio();
      
      double total = totalCarbs + totalFats + totalProtein;
      
      totalWeight.setText( total + "g" );
      
      carbsTotal.setText( totalCarbs + "g" );
      fatsTotal.setText( totalFats + "g" );
      proteinTotal.setText( totalProtein + "g" );
      
      carbsRatio.setText( totalCarbsRatio + "%" );
      fatsRatio.setText( totalFatsRatio + "%" );
      proteinRatio.setText( totalProteinRatio + "%" );
   }//End Method
   
   /**
    * Method to create a {@link Pos#CENTER}ed {@link Label}.
    * @param text the text for the {@link Label}.
    * @return the {@link Label}.
    */
   private Label createCentredLabel( String text ) {
      Label label = new Label( text );
      label.setAlignment( Pos.CENTER );
      return label;
   }//End Method
   
   /**
    * Method to show a particular {@link Meal} in the summary.
    * @param MealImpl the {@link Meal} to show.
    */
   public void showMeal( Meal meal ) {
      if ( this.meal != null ) {
         this.meal.registrations().stopListening( updater );
      }
      this.meal = meal;
      if ( this.meal != null ) {
         this.meal.registrations().listen( updater );
      }
      updateProperties();
   }//End Method
   
   /**
    * Getter for the {@link Meal} being shown.
    * @return the {@link Meal}.
    */
   public Meal getShowingMeal(){
      return meal;
   }//End Method

}//End Class
