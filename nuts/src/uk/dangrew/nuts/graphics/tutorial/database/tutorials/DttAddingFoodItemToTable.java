package uk.dangrew.nuts.graphics.tutorial.database.tutorials;

import java.util.Arrays;
import java.util.List;

import org.controlsfx.control.PopOver.ArrowLocation;

import uk.dangrew.kode.javafx.style.TextFlowBuilder;
import uk.dangrew.nuts.graphics.database.FoodTypes;
import uk.dangrew.nuts.graphics.tutorial.architecture.tutor.TutorActionBuilder;
import uk.dangrew.nuts.graphics.tutorial.architecture.tutor.TutorMessageBuilder;
import uk.dangrew.nuts.graphics.tutorial.architecture.tutorial.TutorialGlass;
import uk.dangrew.nuts.graphics.tutorial.architecture.tutorial.TutorialSelector;
import uk.dangrew.nuts.graphics.tutorial.database.components.DatabaseComponents;
import uk.dangrew.nuts.graphics.tutorial.database.components.DatabaseTableTutorial;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

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
                        .withFlowPadding( 10 )
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
               .withMessage( new TextFlowBuilder()
                        .withFlowPadding( 10 )
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
                        .withFlowPadding( 10 )
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
                      components().mainTableFoodTypeDialogManipulator().disableInput();
                      components().mainTableAddButton().fire();
                  } )
                  .pauseFor( 1 )
                  .graphicalNonBlockingAction( () -> {
                     components().mainTableFoodTypeDialogManipulator().selectAndClose( FoodTypes.FoodItems );
                  } )
                  .pauseFor( 2 )
      );
   }
   
   private void showAddedFoodInTable(){
      tutorUser( 
               new TutorMessageBuilder()
                  .withMessage(  new TextFlowBuilder()
                           .withFlowPadding( 10 )
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
                           .withFlowPadding( 10 )
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
                           .withFlowPadding( 10 )
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
                     .graphicalNonBlockingAction( () -> {
                        components().mainTableComponents().row( 0 )
                              .change( NutritionalUnit.Calories, 23 )
                              .triggerCellEdit( 3 );
                     } )
                     .pauseFor( 1 )
                     .graphicalNonBlockingAction( () -> {
                        components().mainTableComponents().row( 0 )
                              .change( NutritionalUnit.Carbohydrate, 0.1 )
                              .triggerCellEdit( 4 );
                     } )
                     .pauseFor( 1 )
                     .graphicalNonBlockingAction( () -> {
                        components().mainTableComponents().row( 0 )
                              .change( NutritionalUnit.Fat, 0.4 )
                              .triggerCellEdit( 5 );
                     } )
                     .pauseFor( 1 )
                     .graphicalNonBlockingAction( () -> {
                        components().mainTableComponents().row( 0 )
                              .change( NutritionalUnit.Protein, 2.9 )
                              .triggerCellEdit( 6 );
                     } )
                     .pauseFor( 1 )
                     .graphicalNonBlockingAction( () -> {
                        components().mainTableComponents().row( 0 )
                              .change( NutritionalUnit.Fibre, 2.2 )
                              .finishCellEdit();
                     } )
                     .pauseFor( 1 )
      );
   }//End Method
   
   private void completeTutorial(){
      tutorUser( 
            new TutorMessageBuilder()
               .withMessage( new TextFlowBuilder()
                        .withFlowPadding( 10 )
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
