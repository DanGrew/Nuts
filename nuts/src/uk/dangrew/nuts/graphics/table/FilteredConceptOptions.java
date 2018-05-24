package uk.dangrew.nuts.graphics.table;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.kode.comparator.Comparators;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.system.Concept;
import uk.dangrew.nuts.system.ConceptStore;

public class FilteredConceptOptions< TypeT extends Concept > {
   
   private final ConceptOptions< TypeT > backingOptions;
   private final ObservableList< TypeT > filtered;
   
   private final ObjectProperty< Boolean > invertSorting;
   private final ObjectProperty< String > filterString;
   
   private final Set< Predicate< TypeT > > filters;

   public FilteredConceptOptions( ConceptStore< ? extends TypeT > store ) {
      this( new ConceptOptionsImpl<>( store ) );
   }//End Constructor
   
   public FilteredConceptOptions( ConceptOptions< TypeT > options ) {
      this.backingOptions = options;
      this.filtered = FXCollections.observableArrayList( options.options() );
      
      this.invertSorting = new SimpleObjectProperty<>( false );
      this.filterString = new SimpleObjectProperty<>( null );
      
      this.filters = new HashSet<>();
      this.refresh();
      
      this.backingOptions.options().addListener( new FunctionListChangeListenerImpl<>( 
               a -> refresh(), r -> refresh(), this::refresh
      ) );
      
      this.invertSorting.addListener( ( s, o, n ) -> refresh() );
      this.filterString.addListener( ( s, o, n ) -> refresh() );
   }//End Constructor

   public ObservableList< TypeT > options() {
      return filtered;
   }//End Method
   
   public ObjectProperty< Boolean > invertedSorting() {
      return invertSorting;
   }//End Method
   
   public ObjectProperty< String > filterString() {
      return filterString;
   }//End Method
   
   private void refresh(){
      filtered.clear();

      List< TypeT > filteredItems = null;
      if ( filterString.get() != null && filterString.get().trim().length() > 0 ) {
         filteredItems = backingOptions.options().stream()
                  .filter( this::filter )
                  .collect( Collectors.toList() );
      } else {
         filteredItems = new ArrayList<>( backingOptions.options() );
      }
      
      filtered.addAll( filteredItems );
      filters.forEach( filtered::removeIf );
      
      if ( invertSorting.get() ) {
         filtered.sort( Comparators.reverseComparator( backingOptions.comparator() ) );
      } else {
         filtered.sort( backingOptions.comparator() );
      }
   }//End Method 
   
   private boolean filter( TypeT food ) {
      String name = food.properties().nameProperty().get();
      return name.toLowerCase().contains( filterString.get().toLowerCase() );
   }//End Method

   public void applyFilter( Predicate< TypeT > filterIf ) {
      filters.add( filterIf );
      refresh();
   }//End Method

   public void clearFilters() {
      filters.clear();
      refresh();
   }//End Method

}//End Class
