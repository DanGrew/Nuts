package uk.dangrew.nuts.graphics.selection.view;

import java.util.Collection;
import java.util.Set;

import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionFilterApplier;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionFilters;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionManager;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionModel;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionPaneManager;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionTypes;
import uk.dangrew.nuts.label.Label;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

public class UiFoodSelectionController implements UiFoodSelector< FoodPortion > {

   private final Template liveSelectionProperties;
   private final FoodSelectionManager selectionManager;
   private final FoodSelectionModel model;
   
   private FoodSelectionPaneManager selectionPane;
   
   public UiFoodSelectionController( Database database, Template liveSelectionProperties ) {
      this( database, new FoodSelectionModel( database ), liveSelectionProperties );
   }//End Constructor
   
   UiFoodSelectionController( Database database, FoodSelectionModel model, Template liveSelectionProperties ) {
      this.liveSelectionProperties = liveSelectionProperties;
      this.selectionManager = new FoodSelectionManager();
      this.model = model;
      this.model.databaseConcepts().options().addListener( new FunctionListChangeListenerImpl<>( 
               a -> fireLayoutChanges(), r -> fireLayoutChanges() 
      ) );
      new FoodSelectionFilterApplier( selectionManager, model, database.stockLists().objectList().get( 0 ) );
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
      model.databaseConcepts().customSort( type.comparator() );
      fireLayoutChanges();
   }//End Method
   
   public void invertSort( boolean invert ) {
      model.filteredConcepts().invertedSorting().set( invert );
      fireLayoutChanges();
   }//End Method

   public void filterOptions( String filter ) {
      model.filteredConcepts().filterString().set( filter );
      fireLayoutChanges();
   }//End Method
   
   public void applyFilters( Collection< FoodSelectionFilters > filters ) {
      model.filters().clear();
      model.filters().addAll( filters );
      fireLayoutChanges();
   }//End Method
   
   public void applyLabels( Collection< Label > labels ) {
      model.labels().clear();
      model.labels().addAll( labels );
      fireLayoutChanges();
   }//End Method
   
   private void fireLayoutChanges(){
      selectionPane.layoutTiles( model.filteredConcepts().options() );
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
