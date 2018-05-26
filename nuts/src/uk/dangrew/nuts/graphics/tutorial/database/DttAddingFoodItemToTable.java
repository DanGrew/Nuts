package uk.dangrew.nuts.graphics.tutorial.database;

import java.util.Optional;

import org.controlsfx.control.PopOver.ArrowLocation;

import javafx.event.Event;
import javafx.scene.Node;
import uk.dangrew.kode.javafx.hacks.JavaFxHacks;
import uk.dangrew.kode.javafx.style.TextFlowBuilder;
import uk.dangrew.nuts.graphics.database.FoodTypes;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorActionBuilder;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorMessageBuilder;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorialGlass;

public class DttAddingFoodItemToTable extends DatabaseTableTutorial {

   private final JavaFxHacks hacks;
   
   public DttAddingFoodItemToTable( DatabaseComponents components, TutorialGlass glass ) {
      super( components, glass );
      this.hacks = new JavaFxHacks();
   }//End Constructor
   
   @Override public Runnable[] defineInstructions() {
      return new Runnable[]{ 
               this::introduceDatabase,
               this::introduceTable,
               this::showHowToAddAnItem,
               this::triggerAddFood,
               this::showAddedFoodInTable
      };
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
                  .graphicalNonBlockingAction( () -> {
                      components().mainTableFoodTypeDialog().getDialogPane().setMouseTransparent( true );
                      components().mainTableFoodTypeDialog().getDialogPane().addEventFilter( Event.ANY, Event::consume );
                      components().mainTableAddButton().fire();
                  } )
                  .pauseFor( 2 )
                  .graphicalNonBlockingAction( () -> {
                     components().mainTableFoodTypeDialog().setSelectedItem( FoodTypes.FoodItems );
                     components().mainTableFoodTypeDialog().setResult( FoodTypes.FoodItems );
                     components().mainTableFoodTypeDialog().close();
                  } )
                  .pauseFor( 2 )
      );
   }
   
   private void showAddedFoodInTable(){
      Optional< Node > tableRowNode = hacks.lookupTableRow( components().mainTable(), 0 );
      
      if ( tableRowNode.isPresent() ) {
         tutorUser( 
                  new TutorMessageBuilder()
                     .withMessage(  new TextFlowBuilder()
                              .withPadding( 10 )
                              .normal( "Different types of foods can be added. Since we've chosen a FoodItem, " ).newLine()
                              .normal( "a new 'Unnamed' food item has been created in the table." )
                              .build()
                     )
                     .withRespectTo( tableRowNode.get() )
                     .highlighting( tableRowNode.get() ) 
         );
      }
   }//End Method
   
}//End Class
