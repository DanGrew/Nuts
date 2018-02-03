package uk.dangrew.nuts.graphics.selection;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.table.ConceptOptions;
import uk.dangrew.nuts.graphics.table.ConceptOptionsImpl;
import uk.dangrew.nuts.graphics.table.FilteredConceptOptions;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

public class UiFoodSelectionController {

   private final ConceptOptions< Food > backingConcepts;
   private final FilteredConceptOptions< Food > filteredConcepts;
   private final Set< FoodPortion > selectedPortions;
   private final Template liveSelectionProperties;
   
   private UiFoodSelectionPane selectionPane;
   
   public UiFoodSelectionController( Database database, Template liveSelectionProperties ) {
      this.liveSelectionProperties = liveSelectionProperties;
      this.selectedPortions = new LinkedHashSet<>();
      this.backingConcepts = new ConceptOptionsImpl<>( Arrays.asList( database.foodItems(), database.meals() ) );
      this.backingConcepts.options().addListener( new FunctionListChangeListenerImpl<>( 
               a -> fireLayoutChanges(), r -> fireLayoutChanges() 
      ) );
      this.filteredConcepts = new FilteredConceptOptions<>( backingConcepts );
   }//End Constructor
   
   public void controlSelection( UiFoodSelectionPane pane ) {
      this.selectionPane = pane;
      fireLayoutChanges();
   }//End Method

   private void addPortionToLive( FoodPortion portion ) {
      liveSelectionProperties.portions().add( portion );
   }//End Method
   
   private void removePortionFromLive( FoodPortion portion ) {
      liveSelectionProperties.portions().remove( portion );
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
      selectionPane.layoutTiles( filteredConcepts.options() );
   }//End Method

   public boolean isSelected( FoodPortion portion ) {
      return selectedPortions.contains( portion );
   }//End Method

   public void select( FoodPortion portion ) {
      if ( selectedPortions.add( portion ) ) {
         selectionPane.setSelected( portion, true );
         addPortionToLive( portion );
      }
   }//End Method

   public void deselect( FoodPortion portion ) {
      if ( selectedPortions.remove( portion ) ) {
         selectionPane.setSelected( portion, false );
         removePortionFromLive( portion );
      }
   }//End Method

   public Set< FoodPortion > getAndClearSelection() {
      Set< FoodPortion > copy = new LinkedHashSet<>( selectedPortions );
      selectedPortions.forEach( p -> selectionPane.setSelected( p, false ) );
      selectedPortions.clear();
      return copy;
   }//End Method

}//End Class
