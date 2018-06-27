package uk.dangrew.nuts.graphics.goal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import uk.dangrew.kode.javafx.custom.BoundTextProperty;
import uk.dangrew.kode.javafx.custom.PropertiesPane;
import uk.dangrew.kode.javafx.registrations.ChangeListenerBindingImpl;
import uk.dangrew.kode.javafx.registrations.ChangeListenerMismatchBindingImpl;
import uk.dangrew.kode.javafx.registrations.ChangeListenerRegistrationImpl;
import uk.dangrew.kode.javafx.registrations.ReadOnlyChangeListenerRegistrationImpl;
import uk.dangrew.kode.javafx.registrations.RegistrationManager;
import uk.dangrew.kode.javafx.style.Conversions;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.kode.javafx.style.PropertyRowBuilder;
import uk.dangrew.nuts.goal.calorie.CalorieGoal;
import uk.dangrew.nuts.goal.calorie.CalorieGoalImpl;
import uk.dangrew.nuts.goal.calorie.Gender;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class CalorieGoalView extends VBox {

   private final Conversions conversions;
   private final RegistrationManager uiRegistrations;
   private final RegistrationManager modelRegistrations;
   private final JavaFxStyle styling;
   private final CalorieGoal viewModel; 
   
   public CalorieGoalView() {
      this.viewModel = new CalorieGoalImpl( "Selected" );
      this.styling = new JavaFxStyle();
      this.conversions = new Conversions();
      this.uiRegistrations = new RegistrationManager();
      this.modelRegistrations = new RegistrationManager();
      
      createGoalDetails();
      
      createPersonalDetailsPane( 
               "Personal Details",
               viewModel.gender(),
               new Pair<>( "Age (years)", viewModel.age() ),
               new Pair<>( "Weight (lb)", viewModel.weight() ),
               new Pair<>( "Height (m)", viewModel.height() )
      );
      
      getChildren().add( new PropertiesPane( 
               "Predictive Equations",
               new PropertyRowBuilder()
                  .withLabelName( "Basal Metabolic Rate (kcal)" )
                  .withBinding( new BoundTextProperty( viewModel.bmr(), true ) ),
               new PropertyRowBuilder()
                  .withLabelName( "Physical Activity Level (%)" )
                  .withBinding( new BoundTextProperty( viewModel.pal(), true ) ),
               new PropertyRowBuilder()
                  .withLabelName( "Total Energy Expenditure (kcal)" )
                  .withBinding( new BoundTextProperty( viewModel.tee(), true ) )
      ) );
      
      getChildren().add( new PropertiesPane( 
               "Calories", 
               new PropertyRowBuilder()
                  .withLabelName( "Exercise (kcal)" )
                  .withBinding( new BoundTextProperty( viewModel.exerciseCalories(), true ) ),
               new PropertyRowBuilder()
                  .withLabelName( "Deficit (kcal)" )
                  .withBinding( new BoundTextProperty( viewModel.calorieDeficit(), true ) ),
               new PropertyRowBuilder()
                  .withLabelName( "Calorie Goal (kcal)" )
                  .withBinding( new BoundTextProperty( NutritionalUnit.Calories.of( viewModel ).property(), true ) )
      ) );
      
      getChildren().add( new PropertiesPane( 
               "Goals", 
               new PropertyRowBuilder()
                  .withLabelName( "Protein per Pound" )
                  .withBinding( new BoundTextProperty( viewModel.proteinPerPound(), true ) ),
               new PropertyRowBuilder()
                  .withLabelName( "Fat per Pound" )
                  .withBinding( new BoundTextProperty( viewModel.fatPerPound(), true ) ),
               new PropertyRowBuilder()
                  .withLabelName( "Carbohydrates Goal (g)" )
                  .withBinding( new BoundTextProperty( NutritionalUnit.Carbohydrate.of( viewModel ).property(), true ) ),
               new PropertyRowBuilder()
                  .withLabelName( "Fats Goal (g)" )
                  .withBinding( new BoundTextProperty( NutritionalUnit.Fat.of( viewModel ).property(), true ) ),
               new PropertyRowBuilder()
                  .withLabelName( "Protein Goal (g)" )
                  .withBinding( new BoundTextProperty( NutritionalUnit.Protein.of( viewModel ).property(), true ) )
      ) );
      
      List< PropertyRowBuilder > builders = new ArrayList<>();
      TreeSet< NutritionalUnit > orderedUnits = new TreeSet<>( ( a, b ) -> a.name().compareToIgnoreCase( b.name() ) );
      orderedUnits.addAll( Arrays.asList( NutritionalUnit.values() ) );
      
      //these are covered by the calorie calculations
      orderedUnits.remove( NutritionalUnit.Calories );
      orderedUnits.remove( NutritionalUnit.Carbohydrate );
      orderedUnits.remove( NutritionalUnit.Fat );
      orderedUnits.remove( NutritionalUnit.Protein );
      
      for ( NutritionalUnit unit : orderedUnits ) {
         builders.add( new PropertyRowBuilder()
            .withLabelName( unit.displayName() )
            .withBinding( new BoundTextProperty( unit.of( viewModel ).property(), true ) )
         );
      }
      getChildren().add( new PropertiesPane( "Nutritional Units", builders ) );
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
      
      uiRegistrations.apply( new ChangeListenerRegistrationImpl<>( genderProperty, ( s, o, n ) -> gender.getSelectionModel().select( n ) ) );
      uiRegistrations.apply( new ReadOnlyChangeListenerRegistrationImpl<>( gender.getSelectionModel().selectedItemProperty(), ( s, o, n ) -> genderProperty.set( n ) ) );
      
      for ( int i = 1; i < properties.length + 1; i++ ) {
         pane.add( new Label( properties[ i - 1 ].getKey() ), 0, i );
         
         TextField field = new TextField();
         pane.add( field, 1, i );
         
         uiRegistrations.apply( new ChangeListenerMismatchBindingImpl<>( 
                  properties[ i - 1 ].getValue(), field.textProperty(), 
                  conversions.stringToDoubleFunction(), conversions.doubleToStringFunction()
         ) );
      }
      
      TitledPane title = new TitledPane( paneName, pane );
      title.setCollapsible( false );
      getChildren().add( title );
      
      return pane;
   }//End Method
   
   /**
    * Method to create the details of the {@link Goal}.
    */
   private void createGoalDetails(){
      GridPane wrapper = new GridPane();
      styling.configureConstraintsForEvenColumns( wrapper, 2 );
      styling.configureConstraintsForEvenRows( wrapper, 1 );
      Label nameLabel = new Label( "Goal Name" );
      wrapper.add( nameLabel, 0, 0 );
      TextField name = new TextField();
      wrapper.add( name, 1, 0 );
      viewModel.properties().nameProperty().addListener( ( s, o, n ) -> name.setText( n ) );
      name.textProperty().addListener( ( s, o, n ) -> viewModel.properties().nameProperty().set( n ) );
      
      TitledPane goalDetails = new TitledPane( "Goal Details", wrapper );
      goalDetails.setCollapsible( false );
      getChildren().add( goalDetails );
   }//End Method
   
   void detach(){
      modelRegistrations.shutdown();
   }//End Method
   
   void show( CalorieGoal calorieGoal ) {
      modelRegistrations.apply( new ChangeListenerBindingImpl<>( calorieGoal.properties().nameProperty(), viewModel.properties().nameProperty() ) );
      modelRegistrations.apply( new ChangeListenerBindingImpl<>( calorieGoal.gender(), viewModel.gender() ) );
      modelRegistrations.apply( new ChangeListenerBindingImpl<>( calorieGoal.age(), viewModel.age() ) );
      modelRegistrations.apply( new ChangeListenerBindingImpl<>( calorieGoal.height(), viewModel.height() ) );
      modelRegistrations.apply( new ChangeListenerBindingImpl<>( calorieGoal.weight(), viewModel.weight() ) );
      
      modelRegistrations.apply( new ChangeListenerBindingImpl<>( calorieGoal.bmr(), viewModel.bmr() ) );
      modelRegistrations.apply( new ChangeListenerBindingImpl<>( calorieGoal.pal(), viewModel.pal() ) );
      modelRegistrations.apply( new ChangeListenerBindingImpl<>( calorieGoal.tee(), viewModel.tee() ) );
      
      modelRegistrations.apply( new ChangeListenerBindingImpl<>( calorieGoal.exerciseCalories(), viewModel.exerciseCalories() ) );
      modelRegistrations.apply( new ChangeListenerBindingImpl<>( calorieGoal.calorieDeficit(), viewModel.calorieDeficit() ) );
      
      modelRegistrations.apply( new ChangeListenerBindingImpl<>( calorieGoal.proteinPerPound(), viewModel.proteinPerPound() ) );
      modelRegistrations.apply( new ChangeListenerBindingImpl<>( calorieGoal.fatPerPound(), viewModel.fatPerPound() ) );
      
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         modelRegistrations.apply( new ChangeListenerBindingImpl<>( 
                  unit.of( calorieGoal ).property(),
                  unit.of( viewModel ).property()
         ) );
      }
   }//End Method
   
}//End Class
