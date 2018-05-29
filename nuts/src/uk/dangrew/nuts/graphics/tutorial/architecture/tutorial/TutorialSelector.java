package uk.dangrew.nuts.graphics.tutorial.architecture.tutorial;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.controlsfx.control.PopOver.ArrowLocation;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import uk.dangrew.nuts.graphics.tutorial.architecture.tutor.TutorMessageBuilder;
import uk.dangrew.nuts.graphics.tutorial.database.components.DatabaseComponents;
import uk.dangrew.nuts.graphics.tutorial.database.components.DatabaseTutorialOptionBuilder;
import uk.dangrew.nuts.graphics.tutorial.database.components.DatabaseTutorials;
import uk.dangrew.nuts.graphics.tutorial.database.components.UiDatabaseTutorialOptionGrid;

public class TutorialSelector {
   
   private final TutorialGlass glass;
   private final DatabaseComponents components;
   
   private final MouseLocationConverter mouseLocationConverter;
   private final EventHandler< MouseEvent > mouseHandler;
   private final DatabaseTutorialOptionBuilder optionBuilder;
   private final Map< Node, ArrowLocation > enterableComponents;
   
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
      this.enterableComponents = new HashMap<>();
      
      this.resetSelector();
   }//End Constructor
   
   public void resetSelector(){
      this.components.generateComponents();
      this.glass.clearMessageAndHighlight();
      this.glass.setOnMouseMoved( mouseHandler );
      this.glass.replaceUnderlyingContent( components.parent() );
      this.enterableComponents.clear();
      this.enterableComponents.put( components.mainTable(), ArrowLocation.LEFT_CENTER );
      this.enterableComponents.put( components.mainTableAddButton(), ArrowLocation.LEFT_CENTER );
      this.enterableComponents.put( components.mealTable(), ArrowLocation.RIGHT_CENTER );
      this.enterableComponents.put( components.mealTableAddButton(), ArrowLocation.RIGHT_CENTER );
      this.enterableComponents.put( components.mealTableRemoveButton(), ArrowLocation.RIGHT_CENTER );
      this.enterableComponents.put( components.mealTableCopyButton(), ArrowLocation.RIGHT_CENTER );
      this.enterableComponents.put( components.mealTableUpButton(), ArrowLocation.RIGHT_CENTER );
      this.enterableComponents.put( components.mealTableDownButton(), ArrowLocation.RIGHT_CENTER );
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
               .pointing( enterableComponents.get( nodeContainingMouse.get() ) )
               .withHighlight( Color.BLUE )
      );
   }//End Method
   
   private Optional< Node > mouseInsideFilter( MouseEvent event ) {
      return enterableComponents.keySet().stream()
               .filter( n -> mouseLocationConverter.containedInScene( event, n ) )
               .findFirst();
   }//End Method
   
   public void startTutorial( DatabaseTutorials tutorial ) {
      glass.setOnMouseMoved( null );
      glass.removeTutorHighlight();
      tutorial.generate( components, glass, this ).run();
   }//End Method
   
   ArrowLocation arrowFor( Node component ){
      return enterableComponents.get( component );
   }//End Method

}//End Class
