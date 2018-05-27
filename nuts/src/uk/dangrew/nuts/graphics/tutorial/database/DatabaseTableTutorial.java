package uk.dangrew.nuts.graphics.tutorial.database;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import uk.dangrew.nuts.graphics.tutorial.architecture.TutorActionBuilder;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorMessageBuilder;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorialGlass;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorialSelector;

public abstract class DatabaseTableTutorial {

   private final TutorialGlass glass;
   private final TutorialSelector selector;
   private final DatabaseComponents components;
   private final List< Runnable > instructions;
   
   public DatabaseTableTutorial( 
            DatabaseComponents components, 
            TutorialGlass glass,
            TutorialSelector selector
   ) {
      this.components = components;
      this.glass = glass;
      this.selector = selector;
      this.instructions = defineTutorial();
   }//End Constructor
   
   private List< Runnable > defineTutorial() {
      List< Runnable > instructions = new ArrayList<>( defineInstructions() );
      instructions.add( selector::resetSelector );
      return instructions;
   }//End Method
   
   public abstract List< Runnable > defineInstructions();
   
   public void run() {
      nextInstruction().run();
   }//End Method
   
   protected DatabaseComponents components(){
      return components;
   }//End Method
   
   protected TutorialGlass glass(){
      return glass;
   }//End Method
   
   private Runnable nextInstruction(){
      if ( instructions.isEmpty() ) {
         return null;
      }
      
      return instructions.remove( 0 );
   }//End Method
   
   protected void tutorUser( TutorMessageBuilder builder ) {
      glass().tutorUser( builder
               .callingBackTo( nextInstruction() )
               .withConfirmation()
      );
   }//End Method
   
   protected void tutorAction( TutorActionBuilder builder ) {
      glass().tutorAction( builder.callingBackTo( nextInstruction() ) );
   }//End Method
   
}//End Class
