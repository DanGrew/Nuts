package uk.dangrew.nuts.graphics.selection;

import java.util.Arrays;

import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.table.ConceptOptions;
import uk.dangrew.nuts.graphics.table.ConceptOptionsImpl;
import uk.dangrew.nuts.graphics.table.FilteredConceptOptions;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

public class UiFoodSelectionController {

   private final Meal meal;
   
   private final ConceptOptions< Food > backingConcepts;
   private final FilteredConceptOptions< Food > filteredConcepts;
   
   private UiFoodSelectionPane selection;
   
   public UiFoodSelectionController( Meal meal, Database database ) {
      this.meal = meal;
      this.backingConcepts = new ConceptOptionsImpl<>( Arrays.asList( database.foodItems(), database.meals() ) );
      this.backingConcepts.options().addListener( new FunctionListChangeListenerImpl<>( 
               a -> fireLayoutChanges(), r -> fireLayoutChanges() 
      ) );
      this.filteredConcepts = new FilteredConceptOptions<>( backingConcepts );
   }//End Constructor
   
   public void controlSelection( UiFoodSelectionPane pane ) {
      this.selection = pane;
      fireLayoutChanges();
   }//End Method

   public void addPortion( FoodPortion portion ) {
      meal.portions().add( portion.duplicate( "" ) );
   }//End Method
   
   public void useSelectionType( FoodSelectionTypes type ) {
      backingConcepts.customSort( type.comparator() );
      fireLayoutChanges();
   }//End Method

   public void invertSort( boolean invert ) {
      filteredConcepts.invertedSorting().set( invert );
      fireLayoutChanges();
   }//End Method

   public void filterOptions( String filter ) {
      filteredConcepts.filterString().set( filter );
      fireLayoutChanges();
   }//End Method
   
   private void fireLayoutChanges(){
      selection.layoutTiles( filteredConcepts.options() );
   }//End Method

}//End Class
