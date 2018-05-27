package uk.dangrew.nuts.graphics.tutorial.database;

import java.util.Arrays;
import java.util.List;

import org.controlsfx.control.PopOver.ArrowLocation;

import javafx.event.Event;
import javafx.scene.control.TableRow;
import uk.dangrew.kode.javafx.style.TextFlowBuilder;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.database.FoodTypes;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorActionBuilder;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorMessageBuilder;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorialGlass;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorialSelector;
import uk.dangrew.nuts.nutrients.MacroNutrient;

public class DttAddingFoodItemToTable extends DatabaseTableTutorial {

   public DttAddingFoodItemToTable( DatabaseComponents components, TutorialGlass glass, TutorialSelector selector ) {
      super( components, glass, selector );
   }//End Constructor
   
   @Override public List< Runnable > defineInstructions() {
      return Arrays.asList(  
               this::introduceDatabase,
               this::introduceTable,
               this::showHowToAddAnItem,
               this::triggerAddFood,
               this::showAddedFoodInTable,
               this::showFoodNameInTable,
               this::triggerEditFoodItemName,
               this::showPropertiesInRow,
               this::triggerPropertyEdit,
               this::completeTutorial
      );
   }//End Constructor
   
   private void introduceDatabase(){
      tutorUser( 
            new TutorMessageBuilder()
               .withMessage( new TextFlowBuilder()
                        .withPadding( 10 )
                        .normal( "Welcome to the Nuts Database Manager!" )
                        .build()
                )
               .withRespectTo( components().parent() )
               .pointing( ArrowLocation.BOTTOM_CENTER )
      );
   }//End Method
   
   private void introduceTable(){
      tutorUser( 
            new TutorMessageBuilder()
               .withMessage(  new TextFlowBuilder()
                        .withPadding( 10 )
                        .normal( "This table shows all foods within the system and their " ).newLine()
                        .normal( "properties, including their calories, macro nutrient breakdown" ).newLine()
                        .normal( "and fibre." )
                        .build()
               )
               .withRespectTo( components().mainTable() )
               .highlighting( components().mainTable() )
      );
   }//End Method
   
   private void showHowToAddAnItem(){
      tutorUser( 
               new TutorMessageBuilder()
               .withMessage(  new TextFlowBuilder()
                        .withPadding( 10 )
                        .normal( "You can add a food item, such as an ingredient, single product " ).newLine()
                        .normal( "or a packaged product, by clicking the plus button next to the table." ).newLine()
                        .normal( "I'm going to click it for you this time! A dialog will popup to select the " ).newLine()
                        .normal( "type of food to add. I'll go ahead and select 'FoodItems' for you and confirm" )
                        .build()
               )
               .withRespectTo( components().mainTableAddButton() )
               .highlighting( components().mainTableAddButton() ) 
      );
   }//End Method
   
   private void triggerAddFood() {
      tutorAction( 
            new TutorActionBuilder()
                  .graphicalBlockingAction( glass()::clearMessageAndHighlight )
                  .graphicalNonBlockingAction( () -> {
                      components().mainTableFoodTypeDialog().getDialogPane().setMouseTransparent( true );
                      components().mainTableFoodTypeDialog().getDialogPane().addEventFilter( Event.ANY, Event::consume );
                      components().mainTableAddButton().fire();
                  } )
                  .pauseFor( 1 )
                  .graphicalNonBlockingAction( () -> {
                     components().mainTableFoodTypeDialog().setSelectedItem( FoodTypes.FoodItems );
                     components().mainTableFoodTypeDialog().setResult( FoodTypes.FoodItems );
                     components().mainTableFoodTypeDialog().close();
                  } )
                  .pauseFor( 2 )
      );
   }
   
   private void showAddedFoodInTable(){
      tutorUser( 
               new TutorMessageBuilder()
                  .withMessage(  new TextFlowBuilder()
                           .withPadding( 10 )
                           .normal( "Different types of foods can be added. Since we've chosen a FoodItem, " ).newLine()
                           .normal( "a new 'Unnamed' food item has been created in the table." )
                           .build()
                  )
                  .focussingOn( components().mainTableComponents().row( 0 ).node() )
      );
   }//End Method
   
   private void showFoodNameInTable(){
      tutorUser( 
               new TutorMessageBuilder()
                  .withMessage( new TextFlowBuilder()
                           .withPadding( 10 )
                           .normal( "Each concept in Nuts has its own unique id making it no problem to rename things!" ).newLine()
                           .normal( "If you double click on the name - currently 'Unnamed' - you can edit it. " ).newLine()
                           .normal( "I'll show you... I'll double click it for you, change the name and then I'll hit the enter " ).newLine()
                           .normal( "key to submit the change." )
                           .build()
                  )
                  .focussingOn( components().mainTableComponents().row( 0 ).cell( 1 ) )
      );
   }//End Method
   
   private void triggerEditFoodItemName(){
      tutorAction( 
               new TutorActionBuilder()
                     .graphicalBlockingAction( glass()::clearMessageAndHighlight )
                     .graphicalNonBlockingAction( () -> components().mainTableComponents().triggerCellEdit( 0, 1 ) )
                     .pauseFor( 1 )
                     .graphicalNonBlockingAction( () -> components().mainTableComponents().row( 0 ).changeName( "Spinach (100g)" ) )
                     .pauseFor( 1 )
      );
   }//End Method
   
   private void showPropertiesInRow(){
      tutorUser( 
               new TutorMessageBuilder()
                  .withMessage( new TextFlowBuilder()
                           .withPadding( 10 )
                           .normal( "Similarly, you can edit the other properties associated with the item - Calories, " ).newLine()
                           .normal( "Carbohydrates, Fat, Protein and Fibre. By default they are all set to 0.0 so they are " ).newLine()
                           .normal( "optional. I'll go ahead and set these values to show you how it works - simply follow " ).newLine()
                           .normal( "the same steps for changing the name." )
                           .build()
                  )
                  .focussingOn( components().mainTableComponents().row( 0 ).node() )
      );
   }//End Method
   
   private void triggerPropertyEdit(){
      tutorAction( 
               new TutorActionBuilder()
                     .graphicalBlockingAction( glass()::clearMessageAndHighlight )
                     .graphicalNonBlockingAction( () -> components().mainTableComponents().triggerCellEdit( 0, 2 ) )
                     .pauseFor( 1 )
                     .graphicalNonBlockingAction( () -> components().mainTableComponents().row( 0 ).changeCalories( 23 ) )
                     .graphicalNonBlockingAction( () -> components().mainTableComponents().triggerCellEdit( 0, 3 ) )
                     .pauseFor( 1 )
                     .graphicalNonBlockingAction( () -> components().mainTableComponents().row( 0 ).changeMacro( MacroNutrient.Carbohydrates, 0.1 ) )
                     .graphicalNonBlockingAction( () -> components().mainTableComponents().triggerCellEdit( 0, 4 ) )
                     .pauseFor( 1 )
                     .graphicalNonBlockingAction( () -> components().mainTableComponents().row( 0 ).changeMacro( MacroNutrient.Fats, 0.4 ) )
                     .graphicalNonBlockingAction( () -> components().mainTableComponents().triggerCellEdit( 0, 5 ) )
                     .pauseFor( 1 )
                     .graphicalNonBlockingAction( () -> components().mainTableComponents().row( 0 ).changeMacro( MacroNutrient.Protein, 2.9 ) )
                     .graphicalNonBlockingAction( () -> components().mainTableComponents().triggerCellEdit( 0, 6 ) )
                     .pauseFor( 1 )
                     .graphicalNonBlockingAction( () -> components().mainTableComponents().row( 0 ).changeFibre( 2.2 ) )
                     .graphicalNonBlockingAction( () -> components().mainTableComponents().finishCellEdit() )
                     .pauseFor( 1 )
      );
   }//End Method
   
   private void completeTutorial(){
      tutorUser( 
            new TutorMessageBuilder()
               .withMessage( new TextFlowBuilder()
                        .withPadding( 10 )
                        .normal( "That completes the tutorial! You are now fully trained in adding FoodItems to " ).newLine()
                        .normal( "Nuts database (certificate in the post :P). Go check out some of the other tutorials " ).newLine()
                        .normal( "to become a Nuts master! See you soon!" )
                        .build()
                )
               .withRespectTo( components().parent() )
               .pointing( ArrowLocation.BOTTOM_CENTER )
      );
   }//End Method
   
}//End Class
