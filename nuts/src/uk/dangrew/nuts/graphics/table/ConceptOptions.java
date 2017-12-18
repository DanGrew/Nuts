/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.table;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.kode.comparator.Comparators;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.system.Concept;
import uk.dangrew.nuts.system.ConceptStore;

/**
 * {@link ConceptOptions} provides the combined {@link Concept}s available in the {@link Database}.
 */
public class ConceptOptions< TypeT extends Concept > {

   private final Comparator< TypeT > comparator;
   private final ObservableList< TypeT > options;
   private final ChangeListener< String > nameResponder;

   /**
    * Constructs a new {@link ConceptOptions}.
    * @param store the {@link ConceptStore} to consider.
    */
   public ConceptOptions( ConceptStore< ? extends TypeT > store ) {
      this( Collections.singletonList( store ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link ConceptOptions}.
    * @param stores the {@link Collection} of {@link ConceptStore}s to consider.
    */
   public ConceptOptions( Collection< ConceptStore< ? extends TypeT > > stores ) {
      this.options = FXCollections.observableArrayList();
      this.comparator = Comparators.stringExtractionComparater( f -> f.properties().nameProperty().get() );
      this.nameResponder = ( s, o, n ) -> sort();
      
      for ( ConceptStore< ? extends TypeT > store : stores ) {
         store.objectList().forEach( this::add );
         store.objectList().addListener( new FunctionListChangeListenerImpl<>( 
                  this::add, this::remove 
         ) );
      }
   }//End Constructor
   
   /**
    * Method to add a {@link Concept} to the options.
    * @param concept the {@link Concept} to add.
    */
   private void add( TypeT concept ) {
      options.add( concept );
      concept.properties().nameProperty().addListener( nameResponder );
      sort();
   }//End Method
   
   private void sort(){
      Collections.sort( options, comparator );
   }//End Method

   /**
    * Method to remove a {@link Concept} to the options.
    * @param concept the {@link Concept} to remove.
    */
   private void remove( TypeT concept ) {
      options.remove( concept );
      concept.properties().nameProperty().removeListener( nameResponder );
   }//End Method

   /**
    * Access to the {@link ObservableList} options.
    * @return the {@link ObservableList}.
    */
   public ObservableList< TypeT > options() {
      return options;
   }//End Method

   /**
    * Method to find the first {@link Concept} with the given name.
    * @param name the name to look for.
    * @return the found {@link Concept}.
    */
   public TypeT find( String name ) {
      return options().stream().filter( 
               f -> f.properties().nameProperty().get().equals( name ) 
      ).findFirst().orElse( null );
   }//End Method

   public TypeT first() {
      if ( options.isEmpty() ) {
         return null;
      }
      
      return options.get( 0 );
   }//End Method

}//End Class
