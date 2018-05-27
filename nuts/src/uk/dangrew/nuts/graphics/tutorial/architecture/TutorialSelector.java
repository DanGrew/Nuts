package uk.dangrew.nuts.graphics.tutorial.architecture;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import uk.dangrew.nuts.graphics.tutorial.database.DatabaseComponents;
import uk.dangrew.nuts.graphics.tutorial.database.DatabaseTutorialOptionBuilder;
import uk.dangrew.nuts.graphics.tutorial.database.DatabaseTutorials;
import uk.dangrew.nuts.graphics.tutorial.database.UiDatabaseTutorialOptionGrid;

public class TutorialSelector {
   
   private final TutorialGlass glass;
   private final DatabaseComponents components;
   
   private final MouseLocationConverter mouseLocationConverter;
   private final EventHandler< MouseEvent > mouseHandler;
   private final DatabaseTutorialOptionBuilder optionBuilder;
   private final Set< Node > enterableComponents;
   
   private Node current;
   
   public TutorialSelector( TutorialGlass glass, DatabaseComponents components ) {
      this( new MouseLocationConverter(), glass, components );
   }//End Constructor
   
   TutorialSelector( 
            MouseLocationConverter mouseLocationConverter, 
            TutorialGlass glass, 
            DatabaseComponents components 
   ) {
      this.components = components;
      this.mouseHandler = this::detectSelected;
      this.mouseLocationConverter = mouseLocationConverter;
      this.glass = glass;
      this.optionBuilder = new DatabaseTutorialOptionBuilder( this );
      this.enterableComponents = new HashSet<>();
      
      this.resetSelector();
   }//End Constructor
   
   public void resetSelector(){
      this.components.generateComponents();
      this.glass.clearMessageAndHighlight();
      this.glass.setOnMouseMoved( mouseHandler );
      this.glass.replaceUnderlyingContent( components.parent() );
      this.enterableComponents.clear();
      this.enterableComponents.add( components.mainTable() );
      this.enterableComponents.add( components.mainTableAddButton() );
   }//End Method
   
   private void detectSelected( MouseEvent event ){
      Optional< Node > nodeContainingMouse = mouseInsideFilter( event );
      if ( !nodeContainingMouse.isPresent() ) {
         return;
      }
      
      if ( nodeContainingMouse.get() == current ) {
         return;
      }
      
      Optional< UiDatabaseTutorialOptionGrid > optionGrid = optionBuilder.optionsFor( components, nodeContainingMouse.get() );
      if ( !optionGrid.isPresent() ) {
         glass.removeTutorMessage();
         glass.removeTutorHighlight();
         current = null;
         return;
      } 
         
      current = nodeContainingMouse.get();
      glass.tutorUser( new TutorMessageBuilder()
               .withMessage( optionGrid.get() )
               .highlighting( nodeContainingMouse.get() )
               .withRespectTo( nodeContainingMouse.get() )
      );
   }//End Method
   
   private Optional< Node > mouseInsideFilter( MouseEvent event ) {
      return enterableComponents.stream()
               .filter( n -> mouseLocationConverter.containedInScene( event, n ) )
               .findFirst();
   }//End Method
   
   public void startTutorial( DatabaseTutorials tutorial ) {
      glass.setOnMouseMoved( null );
      glass.removeTutorHighlight();
      tutorial.generate( components, glass, this ).run();
   }//End Method

}//End Class
