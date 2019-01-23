package uk.dangrew.nuts.graphics.goal;

import java.util.ArrayList;
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
import uk.dangrew.kode.javafx.custom.BoundDoubleAsTextProperty;
import uk.dangrew.kode.javafx.custom.PropertiesPane;
import uk.dangrew.kode.javafx.registrations.ChangeListenerBindingImpl;
import uk.dangrew.kode.javafx.registrations.ChangeListenerMismatchBindingImpl;
import uk.dangrew.kode.javafx.registrations.ChangeListenerRegistrationImpl;
import uk.dangrew.kode.javafx.registrations.ReadOnlyChangeListenerRegistrationImpl;
import uk.dangrew.kode.javafx.registrations.RegistrationManager;
import uk.dangrew.kode.javafx.style.Conversions;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.kode.javafx.style.PropertyRowBuilder;
import uk.dangrew.nuts.goal.proportion.ProportionGoal;
import uk.dangrew.nuts.goal.proportion.ProportionType;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class ProportionGoalView extends VBox {

   private final Conversions conversions;
   private final RegistrationManager uiRegistrations;
   private final RegistrationManager modelRegistrations;
   private final JavaFxStyle styling;
   private final ProportionGoal viewModel; 
   
   public ProportionGoalView() {
      this.viewModel = new ProportionGoal( "Selected" );
      this.styling = new JavaFxStyle();
      this.conversions = new Conversions();
      this.uiRegistrations = new RegistrationManager();
      this.modelRegistrations = new RegistrationManager();
      
      createGoalDetails();
      
      createMacroPane( 
               "Carbohydrates",
               viewModel.configuration().carbohydrateProportionType(),
               viewModel.configuration().carbohydrateTargetValue()
      );
      
      createMacroPane( 
               "Fat",
               viewModel.configuration().fatProportionType(),
               viewModel.configuration().fatTargetValue()
      );

      createMacroPane( 
               "Protein",
               viewModel.configuration().proteinProportionType(),
               viewModel.configuration().proteinTargetValue()
      );
      
      createMacroPane( 
               "Fiber",
               viewModel.configuration().fiberProportionType(),
               viewModel.configuration().fiberTargetValue()
      );
      
      createSimpleWeightGoals();
   }//End Constructor
   
   private final GridPane createMacroPane( 
            String paneName, 
            ObjectProperty< ProportionType > proportionTypeProperty, 
            ObjectProperty< Double > targetProperty
   ){
      GridPane pane = new GridPane();
      styling.configureConstraintsForEvenRows( pane, 2 );
      styling.configureConstraintsForEvenColumns( pane, 2 );
      
      ComboBox< ProportionType > proportionType = new ComboBox<>( FXCollections.observableArrayList( ProportionType.values() ) );
      proportionType.getSelectionModel().select( proportionTypeProperty.get() );
      proportionType.setMaxWidth( Double.MAX_VALUE );
      
      pane.add( new Label( "Proportion Type" ), 0, 0 );
      pane.add( proportionType, 1, 0 );
      
      uiRegistrations.apply( new ChangeListenerRegistrationImpl<>( proportionTypeProperty, ( s, o, n ) -> proportionType.getSelectionModel().select( n ) ) );
      uiRegistrations.apply( new ReadOnlyChangeListenerRegistrationImpl<>( proportionType.getSelectionModel().selectedItemProperty(), ( s, o, n ) -> proportionTypeProperty.set( n ) ) );
      
      pane.add( new Label( "Target Value" ), 0, 1 );
         
      TextField field = new TextField();
      pane.add( field, 1, 1 );
         
      uiRegistrations.apply( new ChangeListenerMismatchBindingImpl<>( 
               targetProperty, field.textProperty(), 
               conversions.stringToDoubleFunction(), conversions.doubleToStringFunction()
      ) );
      
      TitledPane title = new TitledPane( paneName, pane );
      title.setCollapsible( false );
      getChildren().add( title );
      
      return pane;
   }//End Method
   
   private void createSimpleWeightGoals(){
      List< PropertyRowBuilder > builders = new ArrayList<>();
      TreeSet< NutritionalUnit > orderedUnits = new TreeSet<>( ( a, b ) -> a.displayName().compareToIgnoreCase( b.displayName() ) );
      orderedUnits.addAll( ProportionGoal.weightedGoalUnits() );
      
      for ( NutritionalUnit unit : orderedUnits ) {
         builders.add( new PropertyRowBuilder()
            .withLabelName( unit.displayName() )
            .withBinding( new BoundDoubleAsTextProperty( unit.of( viewModel ).property(), true ) )
         );
      }
      getChildren().add( new PropertiesPane( "Nutritional Units", builders ) );
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
   
   void show( ProportionGoal proportionGoal ) {
      modelRegistrations.apply( new ChangeListenerBindingImpl<>( proportionGoal.properties().nameProperty(), viewModel.properties().nameProperty() ) );
      modelRegistrations.apply( new ChangeListenerBindingImpl<>( proportionGoal.configuration().carbohydrateProportionType(), viewModel.configuration().carbohydrateProportionType() ) );
      modelRegistrations.apply( new ChangeListenerBindingImpl<>( proportionGoal.configuration().carbohydrateTargetValue(), viewModel.configuration().carbohydrateTargetValue() ) );
      
      modelRegistrations.apply( new ChangeListenerBindingImpl<>( proportionGoal.configuration().fatProportionType(), viewModel.configuration().fatProportionType() ) );
      modelRegistrations.apply( new ChangeListenerBindingImpl<>( proportionGoal.configuration().fatTargetValue(), viewModel.configuration().fatTargetValue() ) );
      
      modelRegistrations.apply( new ChangeListenerBindingImpl<>( proportionGoal.configuration().proteinProportionType(), viewModel.configuration().proteinProportionType() ) );
      modelRegistrations.apply( new ChangeListenerBindingImpl<>( proportionGoal.configuration().proteinTargetValue(), viewModel.configuration().proteinTargetValue() ) );
      
      modelRegistrations.apply( new ChangeListenerBindingImpl<>( proportionGoal.configuration().fiberProportionType(), viewModel.configuration().fiberProportionType() ) );
      modelRegistrations.apply( new ChangeListenerBindingImpl<>( proportionGoal.configuration().fiberTargetValue(), viewModel.configuration().fiberTargetValue() ) );
      
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         modelRegistrations.apply( new ChangeListenerBindingImpl<>( 
                  unit.of( proportionGoal ).property(),
                  unit.of( viewModel ).property()
         ) );
      }
   }//End Method

}//End Class
