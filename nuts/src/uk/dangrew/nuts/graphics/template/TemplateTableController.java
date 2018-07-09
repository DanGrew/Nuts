package uk.dangrew.nuts.graphics.template;

import uk.dangrew.kode.event.structure.Event;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.meal.MealTableControllerImpl;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionEvent;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionRequest;
import uk.dangrew.nuts.template.Template;

public class TemplateTableController extends MealTableControllerImpl {

   private final FoodSelectionEvent selectionEvents;
   
   public TemplateTableController() {
      this( new FoodSelectionEvent() );
   }//End Constructor
   
   TemplateTableController( FoodSelectionEvent selectionEvents ) {
      this.selectionEvents = selectionEvents;
   }//End Constructor
   
   @Override public FoodPortion createConcept() {
      if ( getShowingMeal() != null && getShowingMeal() instanceof Template ) {
         selectionEvents.fire( new Event<>( new FoodSelectionRequest( ( Template )getShowingMeal() ) ) );
         return null;
      }
      
      return super.createConcept();
   }//End Method
   
}//End Constructor
