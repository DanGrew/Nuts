package uk.dangrew.nuts.graphics.selection.model;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.database.FoodTypes;
import uk.dangrew.nuts.graphics.table.ConceptOptions;
import uk.dangrew.nuts.graphics.table.ConceptOptionsImpl;
import uk.dangrew.nuts.graphics.table.FilteredConceptOptions;
import uk.dangrew.nuts.label.Label;
import uk.dangrew.nuts.store.Database;

public class FoodFilterModel {

   private final ConceptOptions< Food > backingConcepts;
   private final FilteredConceptOptions< Food > filteredConcepts;
   
   private final ObservableList< FoodFilters > filters;
   private final ObservableList< Label > labels;
   
   public FoodFilterModel( Database database ) {
      this.backingConcepts = new ConceptOptionsImpl<>( 
               Stream.of( FoodTypes.values() )
                  .map( type -> type.redirect( database ) )
                  .collect( Collectors.toList() ) 
      );
      this.filteredConcepts = new FilteredConceptOptions<>( backingConcepts );
      this.filters = FXCollections.observableArrayList();
      this.labels = FXCollections.observableArrayList();
   }//End Constructor

   public ConceptOptions< Food > databaseConcepts() {
      return backingConcepts;
   }//End Method
   
   public FilteredConceptOptions< Food > filteredConcepts() {
      return filteredConcepts;
   }//End Method
   
   public ObservableList< FoodFilters > filters(){
      return filters;
   }//End Method
   
   public ObservableList< Label > labels(){
      return labels;
   }//End Method
   
}//End Class
