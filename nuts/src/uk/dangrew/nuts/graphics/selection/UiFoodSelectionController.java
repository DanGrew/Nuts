package uk.dangrew.nuts.graphics.selection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.table.ConceptOptions;
import uk.dangrew.nuts.graphics.table.ConceptOptionsImpl;
import uk.dangrew.nuts.graphics.table.FilteredConceptOptions;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

public class UiFoodSelectionController implements UiFoodSelector {

   private final ConceptOptions< Food > backingConcepts;
   private final FilteredConceptOptions< Food > filteredConcepts;
   
   private final Template liveSelectionProperties;
   private final FoodSelectionManager selectionManager;
   private final FoodSelectionFilterApplier filterApplier;
   
   private FoodSelectionPaneManager selectionPane;
   
   public UiFoodSelectionController( Database database, Template liveSelectionProperties ) {
      this.liveSelectionProperties = liveSelectionProperties;
      this.selectionManager = new FoodSelectionManager();
      this.backingConcepts = new ConceptOptionsImpl<>( Arrays.asList( database.foodItems(), database.meals() ) );
      this.backingConcepts.options().addListener( new FunctionListChangeListenerImpl<>( 
               a -> fireLayoutChanges(), r -> fireLayoutChanges() 
      ) );
      this.filteredConcepts = new FilteredConceptOptions<>( backingConcepts );
      this.filterApplier = new FoodSelectionFilterApplier( selectionManager, filteredConcepts, database.stockLists().objectList().get( 0 ) );
   }//End Constructor
   
   public void controlSelection( FoodSelectionPaneManager pane ) {
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
   
   public void applyFilters( Collection< FoodSelectionFilters > filters ) {
      filterApplier.applyFilters( filters );
      fireLayoutChanges();
   }//End Method
   
   private void fireLayoutChanges(){
      selectionPane.layoutTiles( filteredConcepts.options() );
   }//End Method

   @Override public boolean isSelected( FoodPortion portion ) {
      return selectionManager.isSelected( portion );
   }//End Method
   
   public boolean isSelected( Food food ) {
      return selectionManager.isSelected( food );
   }//End Method

   @Override public void select( FoodPortion portion ) {
      if ( selectionManager.select( portion ) ) {
         selectionPane.setSelected( portion, true );
         addPortionToLive( portion );
      }
   }//End Method

   @Override public void deselect( FoodPortion portion ) {
      if ( selectionManager.deselect( portion ) ) {
         selectionPane.setSelected( portion, false );
         removePortionFromLive( portion );
      }
   }//End Method

   public Set< FoodPortion > getAndClearSelection() {
      Set< FoodPortion > selectedPortions = selectionManager.getAndClearSelection();
      selectedPortions.forEach( p -> selectionPane.setSelected( p, false ) );
      return selectedPortions;
   }//End Method

}//End Class
