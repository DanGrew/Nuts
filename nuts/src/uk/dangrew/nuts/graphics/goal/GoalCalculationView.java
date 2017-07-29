/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.goal;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import uk.dangrew.kode.javafx.custom.PropertiesPane;
import uk.dangrew.kode.javafx.registrations.ChangeListenerMismatchBindingImpl;
import uk.dangrew.kode.javafx.registrations.ChangeListenerRegistrationImpl;
import uk.dangrew.kode.javafx.registrations.ReadOnlyChangeListenerRegistrationImpl;
import uk.dangrew.kode.javafx.registrations.RegistrationManager;
import uk.dangrew.kode.javafx.style.Conversions;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.kode.javafx.style.PropertyRowBuilder;
import uk.dangrew.nuts.goal.Gender;
import uk.dangrew.nuts.goal.Goal;

/**
 * {@link GoalCalculationView} provides a pane of settable, and autocalculating, properties to configure
 * for setting the user's {@link Goal}.
 */
public class GoalCalculationView extends VBox {
   
   private final JavaFxStyle styling;
   private final Conversions conversions;
   private final RegistrationManager registrations;
   
   /**
    * Constructs a new {@link GoalCalculationView}.
    * @param goal the {@link Goal}.
    */
   public GoalCalculationView( Goal goal ) {
      this.styling = new JavaFxStyle();
      this.conversions = new Conversions();
      this.registrations = new RegistrationManager();
      
      createPersonalDetailsPane( 
               "Personal Details", 
               goal.gender(),
               new Pair<>( "Age (years)", goal.age() ),
               new Pair<>( "Weight (lb)", goal.weight() ),
               new Pair<>( "Height (m)", goal.height() )
      );

      getChildren().add( new PropertiesPane( 
               "Predictive Equations",
               new PropertyRowBuilder()
                  .withLabelName( "Basal Metabolic Rate (kcal)" )
                  .withProperty( goal.bmr() ),
               new PropertyRowBuilder()
                  .withLabelName( "Physical Activity Level (%)" )
                  .withProperty( goal.pal() ),
               new PropertyRowBuilder()
                  .withLabelName( "Total Energy Expenditure (kcal)" )
                  .withProperty( goal.tee() )
      ) );
      
      getChildren().add( new PropertiesPane( 
               "Calories", 
               new PropertyRowBuilder()
                  .withLabelName( "Exercise (kcal)" )
                  .withProperty( goal.exerciseCalories() ),
               new PropertyRowBuilder()
                  .withLabelName( "Deficit (kcal)" )
                  .withProperty( goal.calorieDeficit() ),
               new PropertyRowBuilder()
                  .withLabelName( "Calorie Goal (kcal)" )
                  .withProperty( goal.properties().calories() )
      ) );
      
      getChildren().add( new PropertiesPane( 
               "Goals", 
               new PropertyRowBuilder()
                  .withLabelName( "Protein per Pound" )
                  .withProperty( goal.proteinPerPound() ),
               new PropertyRowBuilder()
                  .withLabelName( "Fat per Pound" )
                  .withProperty( goal.fatPerPound() ),
               new PropertyRowBuilder()
                  .withLabelName( "Carbohydrates Goal (g)" )
                  .withProperty( goal.properties().carbohydrates() ),
               new PropertyRowBuilder()
                  .withLabelName( "Fats Goal (g)" )
                  .withProperty( goal.properties().fats() ),
               new PropertyRowBuilder()
                  .withLabelName( "Protein Goal (g)" )
                  .withProperty( goal.properties().protein() )
      ) );
   }//End Constructor
   
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
                  conversions.stringToDoubleFunction(), conversions.doubleToStringFunction()
         ) );
      }
      
      TitledPane title = new TitledPane( paneName, pane );
      title.setCollapsible( false );
      getChildren().add( title );
      
      return pane;
   }//End Method
   
}//End Class
