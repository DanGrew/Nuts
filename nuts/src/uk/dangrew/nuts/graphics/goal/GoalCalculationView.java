/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.goal;

import java.text.DecimalFormat;
import java.util.function.Function;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import uk.dangrew.kode.javafx.registrations.ChangeListenerMismatchBindingImpl;
import uk.dangrew.kode.javafx.registrations.RegistrationManager;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.goal.Goal;

/**
 * {@link GoalCalculationView} provides a pane of settable, and autocalculating, properties to configure
 * for setting the user's {@link Goal}.
 */
public class GoalCalculationView extends VBox {
   
   private static final DecimalFormat FORMAT = new DecimalFormat( "###.##" );
   private static final Function< Double, String > DOUBLE_TO_STRING = FORMAT::format;
   
   private static final Function< String, Double > STRING_TO_DOUBLE = s -> {
      try {
         return Double.valueOf( s );
      } catch ( NumberFormatException e ) {
         return null;
      }
   };
   
   private final JavaFxStyle styling;
   private final RegistrationManager registrations;
   
   /**
    * Constructs a new {@link GoalCalculationView}.
    * @param goal the {@link Goal}.
    */
   public GoalCalculationView( Goal goal ) {
      this.styling = new JavaFxStyle();
      this.registrations = new RegistrationManager();
      
      createPropertiesPane( 
               "Personal Details", 
               new Pair<>( "Age (years)", goal.age() ),
               new Pair<>( "Weight (lb)", goal.weight() ),
               new Pair<>( "Height (m)", goal.height() )
      );

      createPropertiesPane( 
               "Predictive Equations", 
               new Pair<>( "Basal Metabolic Rate (kcal)", goal.bmr() ),
               new Pair<>( "Physical Activity Level (%)", goal.pal() ),
               new Pair<>( "Total Energy Expenditure (kcal)", goal.tee() )
      );

      
      createPropertiesPane( 
               "Calories", 
               new Pair<>( "Exercise (kcal)", goal.exerciseCalories() ),
               new Pair<>( "Deficit (kcal)", goal.calorieDeficit() ),
               new Pair<>( "Calorie Goal (kcal)", goal.properties().calories() )
      );
      
      createPropertiesPane( 
               "Personal Details", 
               new Pair<>( "Protein per Pound", goal.proteinPerPound() ),
               new Pair<>( "Fat per Pound", goal.fatPerPound() ),
               new Pair<>( "Carbohydrates Goal (g)", goal.properties().carbohydrates() ),
               new Pair<>( "Fats Goal (g)", goal.properties().fats() ),
               new Pair<>( "Protein Goal (g)", goal.properties().protein() )
      );
   }//End Constructor
   
   /**
    * Method to create a {@link GridPane} of properties in a consistent way.
    * @param paneName the name of the {@link TitledPane}.
    * @param properties the {@link ObjectProperty}s to display.
    */
   @SafeVarargs 
   private final void createPropertiesPane( String paneName, Pair< String, ObjectProperty< Double > >... properties ){
      GridPane pane = new GridPane();
      styling.configureConstraintsForEvenRows( pane, properties.length );
      styling.configureConstraintsForEvenColumns( pane, 2 );
      
      for ( int i = 0; i < properties.length; i++ ) {
         pane.add( new Label( properties[ i ].getKey() ), 0, i );
         
         TextField field = new TextField();
         pane.add( field, 1, i );
         
         registrations.apply( new ChangeListenerMismatchBindingImpl<>( 
                  properties[ i ].getValue(), field.textProperty(), 
                  STRING_TO_DOUBLE, DOUBLE_TO_STRING
         ) );
      }
      
      TitledPane title = new TitledPane( paneName, pane );
      title.setCollapsible( false );
      getChildren().add( title );
   }//End Method
   
}//End Class
