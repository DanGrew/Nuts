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
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import uk.dangrew.kode.javafx.registrations.ChangeListenerMismatchBindingImpl;
import uk.dangrew.kode.javafx.registrations.ChangeListenerRegistrationImpl;
import uk.dangrew.kode.javafx.registrations.ReadOnlyChangeListenerRegistrationImpl;
import uk.dangrew.kode.javafx.registrations.RegistrationManager;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.goal.Gender;
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
         return 0.0;
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
      
      createPersonalDetailsPane( 
               "Personal Details", 
               goal.gender(),
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
               "Goals", 
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
   
   /**
    * Method to create a {@link GridPane} of the personal details.
    * @param paneName the name of the {@link TitledPane}.
    * @param genderProperty the {@link ObjectProperty}.
    * @param properties the {@link ObjectProperty}s to display.
    */
   @SafeVarargs 
   private final GridPane createPersonalDetailsPane( String paneName, ObjectProperty< Gender > genderProperty, Pair< String, ObjectProperty< Double > >... properties ){
      GridPane pane = new GridPane();
      styling.configureConstraintsForEvenRows( pane, properties.length + 1 );
      styling.configureConstraintsForEvenColumns( pane, 2 );
      
      ComboBox< Gender > gender = new ComboBox<>( FXCollections.observableArrayList( Gender.values() ) );
      gender.getSelectionModel().select( genderProperty.get() );
      gender.setMaxWidth( Double.MAX_VALUE );
      
      pane.add( new Label( "Gender" ), 0, 0 );
      pane.add( gender, 1, 0 );
      
      registrations.apply( new ChangeListenerRegistrationImpl<>( genderProperty, ( s, o, n ) -> gender.getSelectionModel().select( n ) ) );
      registrations.apply( new ReadOnlyChangeListenerRegistrationImpl<>( gender.getSelectionModel().selectedItemProperty(), ( s, o, n ) -> genderProperty.set( n ) ) );
      
      for ( int i = 1; i < properties.length + 1; i++ ) {
         pane.add( new Label( properties[ i - 1 ].getKey() ), 0, i );
         
         TextField field = new TextField();
         pane.add( field, 1, i );
         
         registrations.apply( new ChangeListenerMismatchBindingImpl<>( 
                  properties[ i - 1 ].getValue(), field.textProperty(), 
                  STRING_TO_DOUBLE, DOUBLE_TO_STRING
         ) );
      }
      
      TitledPane title = new TitledPane( paneName, pane );
      title.setCollapsible( false );
      getChildren().add( title );
      
      return pane;
   }//End Method
   
}//End Class
