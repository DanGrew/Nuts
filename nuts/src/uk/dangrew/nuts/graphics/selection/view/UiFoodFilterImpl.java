package uk.dangrew.nuts.graphics.selection.view;

import java.util.Collection;

import uk.dangrew.nuts.graphics.selection.model.FoodFilterApplier;
import uk.dangrew.nuts.graphics.selection.model.FoodFilterModel;
import uk.dangrew.nuts.graphics.selection.model.FoodFilters;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionTypes;
import uk.dangrew.nuts.label.Label;
import uk.dangrew.nuts.store.Database;

public class UiFoodFilterImpl implements UiFoodFilter {
   
   private final FoodFilterModel model;
   
   public UiFoodFilterImpl( Database database, FoodFilterModel model ) {
      this.model = model;
      new FoodFilterApplier( model, database.stockLists().objectList().get( 0 ) );
   }//End Constructor

   @Override public void useSelectionType( FoodSelectionTypes type ) {
      model.databaseConcepts().customSort( type.comparator() );
   }//End Method
   
   @Override public void invertSort( boolean invert ) {
      model.filteredConcepts().invertedSorting().set( invert );
   }//End Method

   @Override public void filterOptions( String filter ) {
      model.filteredConcepts().filterString().set( filter );
   }//End Method
   
   @Override public void applyFilters( Collection< FoodFilters > filters ) {
      model.filters().clear();
      model.filters().addAll( filters );
   }//End Method
   
   @Override public void applyLabels( Collection< Label > labels ) {
      model.labels().clear();
      model.labels().addAll( labels );
   }//End Method
   
}//End Class
