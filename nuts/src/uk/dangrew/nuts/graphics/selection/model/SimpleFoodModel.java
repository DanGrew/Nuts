package uk.dangrew.nuts.graphics.selection.model;

import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.kode.observable.PrivatelyModifiableObservableListImpl;
import uk.dangrew.nuts.food.Food;

public class SimpleFoodModel implements FoodModel {

   private final ObservableList< Food > privateFoods;
   private final ObservableList< Food > publicFoods;
   
   public SimpleFoodModel() {
      this.privateFoods = FXCollections.observableArrayList();
      this.publicFoods = new PrivatelyModifiableObservableListImpl<>( privateFoods );
   }//End Constructor
   
   @Override public ObservableList< Food > concepts() {
      return publicFoods;
   }//End Method

   public void add( Food food ) {
      if ( privateFoods.contains( food ) ) {
         return;
      }
      
      privateFoods.add( food );
   }//End Method

   public void addAll( Collection< Food > collection ) {
      collection.forEach( this::add );
   }//End Method

   public void clear() {
      privateFoods.clear();
   }//End Method

   public void remove( Food food ) {
      privateFoods.remove( food );
   }//End Method
}//End Class
